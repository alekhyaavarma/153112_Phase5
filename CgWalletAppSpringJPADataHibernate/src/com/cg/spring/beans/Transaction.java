package com.cg.spring.beans;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="Transaction")
public class Transaction {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	String mobileNumber;
	String transactionType;
	BigDecimal amount;
	Date date1;
	
	
	public Transaction() {
		super();
	}


	public Transaction(String mobileNumber, String transactionType, BigDecimal amount, Date date1) {
		super();
		this.mobileNumber = mobileNumber;
		this.transactionType = transactionType;
		this.amount = amount;
		this.date1 = date1;
	}


	public Transaction(int id, String mobileNumber, String transactionType, BigDecimal amount, Date date1) {
		super();
		this.id = id;
		this.mobileNumber = mobileNumber;
		this.transactionType = transactionType;
		this.amount = amount;
		this.date1 = date1;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getMobileNumber() {
		return mobileNumber;
	}


	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}


	public String getTransactionType() {
		return transactionType;
	}


	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}


	public BigDecimal getAmount() {
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	public Date getDate1() {
		return date1;
	}


	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	
	

	
}
