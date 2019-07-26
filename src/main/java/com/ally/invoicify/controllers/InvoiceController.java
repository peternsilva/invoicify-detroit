package com.ally.invoicify.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ally.invoicify.models.BillingRecord;
import com.ally.invoicify.models.Invoice;
import com.ally.invoicify.models.InvoiceLineItem;
import com.ally.invoicify.models.InvoiceView;
import com.ally.invoicify.models.User;
import com.ally.invoicify.repositories.BillingRecordRepository;
import com.ally.invoicify.repositories.CompanyRepository;
import com.ally.invoicify.repositories.InvoiceLineItemRepository;
import com.ally.invoicify.repositories.InvoiceRepository;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {
	
	@Autowired
	private BillingRecordRepository recordRepository;
	
	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private InvoiceLineItemRepository invoiceLineItemRepository;

	@Autowired
	private CompanyRepository companyRepository;
	
	@PostMapping("{clientId}")
	public Invoice createInvoice(@RequestBody InvoiceView invoiceView, @PathVariable long clientId, Authentication auth) {
		User creator = (User) auth.getPrincipal();
		List<BillingRecord> records = recordRepository.findByIdIn(invoiceView.getRecordIds());
		long nowish = Calendar.getInstance().getTimeInMillis();
		Date now = new Date(nowish);
		Invoice invoice = new Invoice();
		invoice.setInvoiceDescription(invoiceView.getInvoiceDescription());
		double initialInvoiceBalance = 0;
		
		List<InvoiceLineItem> items = new ArrayList<InvoiceLineItem>();
		for (BillingRecord record : records) {
			InvoiceLineItem lineItem = new InvoiceLineItem();
			lineItem.setBillingRecord(record);
			lineItem.setCreatedBy(creator);
			lineItem.setCreatedOn(now);
			lineItem.setInvoice(invoice);
			items.add(lineItem);
			initialInvoiceBalance += record.getTotal();
		}
		
		invoice.setLineItems(items);
		invoice.setCreatedBy(creator);
		invoice.setCreatedOn(now);
		invoice.setCompany(companyRepository.findOne(clientId));
		invoice.setPaidOn(null);
		invoice.setInitialBalance(initialInvoiceBalance);
		invoice.setCurrentBalance(initialInvoiceBalance);
		
		return invoiceRepository.save(invoice);
	}
	
	@GetMapping("/duplicate/{invoiceId}")
	public Invoice duplicateInvoice(@PathVariable long invoiceId) {

		Invoice alreadyExistingInvoice = invoiceRepository.findOne(invoiceId);
		Invoice newInvoice = new Invoice();
		long nowish = Calendar.getInstance().getTimeInMillis();
		Date now = new Date(nowish);
		
		List<InvoiceLineItem> newLineItems = new ArrayList<InvoiceLineItem>();
		List<InvoiceLineItem> oldLineItems = alreadyExistingInvoice.getLineItems();

		for(InvoiceLineItem lineItem : oldLineItems){
			newLineItems.add(lineItem);
		}

		newInvoice.setLineItems(newLineItems);
		newInvoice.setInvoiceDescription(alreadyExistingInvoice.getInvoiceDescription());
		newInvoice.setCreatedBy(alreadyExistingInvoice.getCreatedBy());
		newInvoice.setCreatedOn(now);
		newInvoice.setCompany(alreadyExistingInvoice.getCompany());
		newInvoice.setInitialBalance(alreadyExistingInvoice.getInitialBalance());
		newInvoice.setCurrentBalance(alreadyExistingInvoice.getInitialBalance());
		
		Invoice savedInvoice = invoiceRepository.save(newInvoice);	

		createDuplicateLineItem(invoiceLineItemRepository.findAll(), savedInvoice.getId(), alreadyExistingInvoice.getId(), now);

		return savedInvoice;
	}

	@GetMapping
	public List<Invoice> list() {
		return invoiceRepository.findAll();
	}
  
  @GetMapping("{id}")
	public Invoice getOne(@PathVariable Long id) {
		return invoiceRepository.findOne(id);
	}
  
  @GetMapping("/company/{companyId}")
	public List<Invoice> list(@PathVariable long companyId) {
		System.out.println("COMPANY ID -->"+companyId);
		return invoiceRepository.findByCompanyId(companyId);
	}	

	private void createDuplicateLineItem(List<InvoiceLineItem> lineItemRepo, long newInvoiceId, long existingInvoiceId, Date now){
		for(InvoiceLineItem lineItem : lineItemRepo){
			if(lineItem.getInvoice().getId().equals(existingInvoiceId)){
				InvoiceLineItem newLineItem = new InvoiceLineItem();
				newLineItem.setBillingRecord(lineItem.getBillingRecord());
				newLineItem.setCreatedBy(lineItem.getCreatedBy());
				newLineItem.setCreatedOn(now);
				newLineItem.setInvoice(invoiceRepository.findOne(newInvoiceId));
				invoiceLineItemRepository.save(newLineItem);
			}
		}
	}
}