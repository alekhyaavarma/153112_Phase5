package com.cg.spring.pl;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cg.spring.beans.Customer;
import com.cg.spring.beans.Transaction;
import com.cg.spring.beans.Wallet;
import com.cg.spring.exception.InsufficientBalanceException;
import com.cg.spring.exception.InvalidInputException;
import com.cg.spring.service.WalletService;
import com.cg.spring.service.WalletServiceImpl;

public class Client 
{
	
	public static void main(String[] args) throws InvalidInputException, InsufficientBalanceException 
	{
		Client client = new Client();
		ApplicationContext context = new ClassPathXmlApplicationContext("projectbeans.xml");
	    WalletService service=(WalletServiceImpl)context.getBean("WalletService");
		while(true) {
			System.out.println("1) Create Account");
			System.out.println("2) Show Balance");
			System.out.println("3) Deposit Amount");
			System.out.println("4) Withdraw Amount");
			System.out.println("5) Fund Transfer");
			System.out.println("6) Print Transactions");
			System.out.println("0) Exit Application");
			
			Scanner sc = new Scanner(System.in);
			
			System.out.println("Enter your choice");
			int choice = sc.nextInt();
			
			switch(choice) {
				case 1:
					Customer customer = new Customer();
					Wallet wallet = new Wallet();
					
					System.out.print("Enter name: ");
					String name = sc.next();
					
					System.out.print("Enter mobileNumber: ");
					String mobileNumber = sc.next();
					
					System.out.print("Enter Amount: ");
					BigDecimal amount = sc.nextBigDecimal();
					
					try 
					{
						customer = service.createAccount(name, mobileNumber, amount);
						System.out.println("Your account has successfully registered");
					} 
					catch (InvalidInputException e) {
						e.printStackTrace();
					}
					break;
					
				case 2:
					System.out.println("Enter mobile number");
					mobileNumber = sc.next();
					
				try {
					customer = service.showBalance(mobileNumber);
					System.out.print("The balance in account " + customer.getName());
					System.out.println(" is " + customer.getWallet().getBalance());
				} catch (InvalidInputException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
					
					break;
				
				case 3:
					System.out.println("Enter mobile number");
					mobileNumber = sc.next();
					
					System.out.println("Enter amount to be deposited");
					amount = sc.nextBigDecimal();
					
					try 
					{
						customer = service.depositAmount(mobileNumber, amount);
						System.out.println("Successfully deposited");
						System.out.println("Account balance is: " + customer.getWallet().getBalance());
					} 
					catch (InvalidInputException e2) 
					{
						e2.printStackTrace();
					}
					
					
					
					break;
				
				case 4:
					System.out.println("Enter mobile number");
					mobileNumber = sc.next();
					
					System.out.println("Enter amount to be withdrawn");
					amount = sc.nextBigDecimal();
					
					try 
					{
						customer = service.withdrawAmount(mobileNumber, amount);
						System.out.println("Successfully withdrawn");
						System.out.println("Account balance is: " + customer.getWallet().getBalance());
					}
					catch (InvalidInputException e1) 
					{
						e1.printStackTrace();
					} catch (InsufficientBalanceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				
				case 5:
					System.out.print("Enter source mobile number: ");
					String sourceMobile = sc.next();
					
					System.out.print("Enter target mobile number: ");
					String targetMobile = sc.next();
					
					System.out.println("Enter amount to be transferred");
					amount = sc.nextBigDecimal();
					
					try 
					{
						customer = service.fundTransfer(sourceMobile, targetMobile, amount);
						System.out.println("Amount has successfully transferred from account " + customer.getName());
						System.out.println("And now your balance is " + customer.getWallet().getBalance());

					} 
					catch (InvalidInputException e) {
						e.printStackTrace();
					} catch (InsufficientBalanceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					break;
				case 6:
					System.out.println("Enter mobile Number");
					mobileNumber = sc.next();
					List<Transaction> transactions = null;
					
					transactions = service.getAllTransactions(mobileNumber);
					
					Iterator<Transaction> it = transactions.iterator();
					
					System.out.println("Type \t\tAmount \t\tDate");
					
					while(it.hasNext()) 
					{
						Transaction transaction = it.next();
						System.out.println(transaction.getTransactionType() + "\t" + 
						transaction.getAmount() + "\t" + 
								transaction.getDate1() + "\t");
					}
					break;
					
				
				case 0:
					System.out.println("Thank you for using our services");
					System.out.println("Good Bye");
					System.exit(0);
				
				default:
					System.out.println("Please enter valid choice");
					break;
			}
			
			
		}
	}
	
	
}
