package com.cg.spring.service;

import java.math.BigDecimal;
import java.util.List;

import com.cg.spring.beans.Customer;
import com.cg.spring.beans.Transaction;
import com.cg.spring.exception.InsufficientBalanceException;
import com.cg.spring.exception.InvalidInputException;

public interface WalletService {
	public Customer createAccount(String name ,String mobileno, BigDecimal amount) throws InvalidInputException;
	public Customer showBalance (String mobileno) throws InvalidInputException;
	public Customer fundTransfer (String sourceMobileNo,String targetMobileNo, BigDecimal amount) throws InvalidInputException, InsufficientBalanceException;
	public Customer depositAmount (String mobileNo,BigDecimal amount ) throws InvalidInputException;
	public Customer withdrawAmount(String mobileNo, BigDecimal amount) throws InvalidInputException, InsufficientBalanceException;
	public List<Transaction> getAllTransactions(String mobileNumber) throws InvalidInputException,InsufficientBalanceException;
	public Transaction saveTransactions(Transaction transaction);
}
