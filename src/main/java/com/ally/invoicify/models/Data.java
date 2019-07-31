package com.ally.invoicify.models;

import java.util.List;



public class Data {

    private double avergeInvoiceAmount;

    private Double averageCurrentBalanceByCompany;

    private Double findAverageTimeToPay;

    private List<Invoice> findSumInitalBalance;

    private List<Invoice> findTotalOutstandingAmount;

    private List<Invoice> findTotalOutstandingInvoices;

    private List<Invoice> findTotalPaidInvoices;

    private List<BillingRecord> findByRecordType;

    private List<NewPayment> findByMethodType;

    public double getAvergeInvoiceAmount() {
        return avergeInvoiceAmount;
    }

    public void setAvergeInvoiceAmount(double avergeInvoiceAmount) {
        this.avergeInvoiceAmount = avergeInvoiceAmount;
    }

    public List<NewPayment> getFindByMethodType() {
        return findByMethodType;
    }

    public void setFindByMethodType(List<NewPayment> findByMethodType) {
        this.findByMethodType = findByMethodType;
    }

    public List<BillingRecord> getFindByRecordType() {
        return findByRecordType;
    }

    public void setFindByRecordType(List<BillingRecord> findByRecordType) {
        this.findByRecordType = findByRecordType;
    }

    public List<Invoice> getFindTotalPaidInvoices() {
        return findTotalPaidInvoices;
    }

    public void setFindTotalPaidInvoices(List<Invoice> findTotalPaidInvoices) {
        this.findTotalPaidInvoices = findTotalPaidInvoices;
    }

    public List<Invoice> getFindTotalOutstandingInvoices() {
        return findTotalOutstandingInvoices;
    }

    public void setFindTotalOutstandingInvoices(List<Invoice> findTotalOutstandingInvoices) {
        this.findTotalOutstandingInvoices = findTotalOutstandingInvoices;
    }

    public List<Invoice> getFindTotalOutstandingAmount() {
        return findTotalOutstandingAmount;
    }

    public void setFindTotalOutstandingAmount(List<Invoice> findTotalOutstandingAmount) {
        this.findTotalOutstandingAmount = findTotalOutstandingAmount;
    }

    public List<Invoice> getFindSumInitalBalance() {
        return findSumInitalBalance;
    }

    public void setFindSumInitalBalance(List<Invoice> findSumInitalBalance) {
        this.findSumInitalBalance = findSumInitalBalance;
    }

    public Double getFindAverageTimeToPay() {
        return findAverageTimeToPay;
    }

    public void setFindAverageTimeToPay(Double findAverageTimeToPay) {
        this.findAverageTimeToPay = findAverageTimeToPay;
    }

    public Double getAverageCurrentBalanceByCompany() {
        return averageCurrentBalanceByCompany;
    }

    public void setAverageCurrentBalanceByCompany(Double averageCurrentBalanceByCompany) {
        this.averageCurrentBalanceByCompany = averageCurrentBalanceByCompany;
    }
	
}
