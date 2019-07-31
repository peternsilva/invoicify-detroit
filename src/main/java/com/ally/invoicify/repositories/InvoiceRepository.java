package com.ally.invoicify.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ally.invoicify.models.Invoice;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByCompanyId(Long companyId);

    List<Invoice> findByPaidOnNotNull();

    List<Invoice> findByPaidOnNull();
    
    @Query("select avg(initialBalance) from Invoice i")
    double findAverageInvoiceAmount();

    @Query("select sum(currentBalance) / count(company.id) from Invoice i where paidOn = null")
    Double findAverageCurrentBalanceByCompany();


    @Query("select avg(paidOn - createdOn) from Invoice i where paidOn != null")
    Double findAverageTimeToPay();

    @Query("select sum(initialBalance) from Invoice i where paidOn != null")
    List<Invoice> findSumInitalBalance();


    @Query("select sum(currentBalance) from Invoice i where paidOn = null")
    List<Invoice> findTotalOutstandingAmount();

    @Query("select count(id) from Invoice i WHERE paidOn = null")
    List<Invoice> findTotalOutstandingInvoices();

    @Query("select count(id) from Invoice i WHERE paidOn != null")
    List<Invoice> findTotalPaidInvoices();

    

    

    

    

    
    


    
}
