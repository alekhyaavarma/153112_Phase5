package com.cg.spring.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;

import com.cg.spring.beans.Customer;
import com.cg.spring.beans.Transaction;
import com.cg.spring.beans.Wallet;
import com.cg.spring.exception.InsufficientBalanceException;
import com.cg.spring.exception.InvalidInputException;
import com.cg.spring.repo.TransactionRepo;
import com.cg.spring.repo.WalletRepo;


@Component(value="WalletService")
public class WalletServiceImpl implements WalletService{
	
	@Autowired(required=true)
	WalletRepo repo;
	
	@Autowired
	TransactionRepo repo1;

	
	//@Transactional(propagation=Propagation.REQUIRED)
	public Customer createAccount(String name, String mobileNo, BigDecimal amount) throws InvalidInputException{
		if(isValid(mobileNo) && isValidName(name) && amount.compareTo(new BigDecimal(0)) > 0) {
			Wallet wallet = new Wallet();
			Customer customer = new Customer();
		
			wallet.setBalance(amount);
			customer.setName(name);
			customer.setMobileNo(mobileNo);
			customer.setWallet(wallet);
			return repo.save(customer);
			
		}
		else throw new InvalidInputException();

	}

	public Customer showBalance(String mobileNo) throws InvalidInputException {
		
		
		Customer customer=repo.findOne(mobileNo);
		
		
		if(customer!=null)
			return customer;
		else
			throw new InvalidInputException("Invalid mobile no ");
	}

	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) throws InvalidInputException, InsufficientBalanceException {
		
		if(isValid(sourceMobileNo) == false || isValid(targetMobileNo) == false) 
			throw new InvalidInputException();
		
		Customer customer = withdrawAmount(sourceMobileNo, amount);
		depositAmount(targetMobileNo, amount);
		
		return customer;
	}

	public Customer depositAmount(String mobileNo, BigDecimal amount) throws InvalidInputException 
	{
		if(amount.compareTo(new BigDecimal(0)) <= 0) 
			throw new InvalidInputException("Enter valid amount");
		
		if(isValid(mobileNo)) {
			Customer customer = repo.findOne(mobileNo);
			Wallet wallet = customer.getWallet();
			
			wallet.setBalance(wallet.getBalance().add(amount));
			
			
			repo.save(customer);
			
			Transaction t = new Transaction(mobileNo, "deposit",customer.getWallet().getBalance(),new Date());
			saveTransactions(t);
			
			
			return customer;
		}
		else throw new InvalidInputException("Enter valid mobile number");
	}

	public boolean isValid(String mobileNo) {
		if(mobileNo.matches("[1-9][0-9]{9}")) 
		{
			return true;
		}		
		else 
			return false;
	}
	
	private boolean isValidName(String name) {
		if( name == null || name.trim().isEmpty() )
			return false;
		return true;
	}

	public Customer withdrawAmount(String mobileNo, BigDecimal amount) throws InvalidInputException, InsufficientBalanceException 
	{	
		if(amount.compareTo(new BigDecimal(0)) <= 0) 
			throw new InvalidInputException("Enter valid amount");
		
		if(isValid(mobileNo)) 
		{
			Customer customer = repo.findOne(mobileNo);
			Wallet wallet = customer.getWallet();
			
			if(amount.compareTo(wallet.getBalance()) > 0) 
				throw new InsufficientBalanceException("Amount is not sufficient in your account");
			
			wallet.setBalance(wallet.getBalance().subtract(amount));
			customer.setWallet(wallet);
			
			
			repo.save(customer);
			Transaction t = new Transaction(mobileNo, "withdraw",customer.getWallet().getBalance(),new Date());
			saveTransactions(t);
			
			
			return customer;
		}
		else throw new InvalidInputException("Enter valid mobile number");
	}

	@Override
	public List<Transaction> getAllTransactions(String mobileNumber)
			throws InvalidInputException, InsufficientBalanceException {
		
		return repo1.findByMobileNumber(mobileNumber);
	}

	@Override
	public Transaction saveTransactions(Transaction transaction) {
		
		return repo1.save(transaction);
	}
}
