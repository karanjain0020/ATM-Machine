import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
	
	/**
	 * The first name of the user
	 */
	private String firstName;
	
	/**
	 * The last name of the user
	 */
	private String lastName;
	
	/**
	 * The user ID of the user
	 */
	private String uuid;
	
	/**
	 * The hash of the in number of the user
	 */
	private byte pinHash[];
	
	/**
	 * The list of accounts of the user
	 */
	private ArrayList<Account> accounts;

	
	
	//constructor
	/**
	 * 
	 * @param firstName
	 * @param lastName
	 * @param pin
	 * @param bank
	 */
	
	public User(String firstName, String lastName,  String pin, Bank bank) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		
		//hashing pin using MD5 algorithm for secuirity
		
	 	try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			/**
			 * Getting the pin from parameters
			 * Converting it into bytes
			 * Digesting it through the MD algorithm to 
			 * generate a security key in the form of an array
			 * of bytes and storing it in the pinHash array
			 */
			this.pinHash = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.out.println("error, caught NoSuchAlgorithmExeception");
			e.printStackTrace();
			System.exit(1);
		}
		
		//Unique primary ID generator for this user
		this.uuid = bank.getNewUUID();
		
		//initialize accounts array list 
		this.accounts = new ArrayList<Account>();
		
		//log message 
		System.out.printf("New user %s, %s with ID %s created. \n", lastName, firstName, this.uuid);
		 
	}



	/*
	 * This is called encapsulation. We are letting someone from outside, add 
	 * an account to out private accounts field in user from outside 
	 * by letting it pass that account as a param for the methods in here.
	 * If we made the class public, we can cause data insecurity and so we are
	 * not letting account class get access to user objects.   
	 */
	public void addAccount(Account account) {
		this.accounts.add(account);  
	/**
	 * we can do user.accounts.add(account) in the account class by making it public 
	 * But that would be bad and not secure.  
	 */
			
		
	}



	 public String getUUID() {
		return this.uuid; 
	}


	 /**
	  * Hash function is a non invertible function so bank employees cannot get
	  * the actuall pin to be plugged in from the hash numbers they store to validate
	  * the pins. So we are going to validate the pins by checking hash values stored 
	  * with the hash values of the pins entered via the following methods. 
	  * @param pin
	  * @return
	  */
	public boolean validatePin(String pin) {
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			//using isEqual method to compare the two pinhashes after converting apin 
			//via MD5 algorithm
			return MessageDigest.isEqual(md.digest(pin.getBytes()),this.pinHash);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("error, caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		return false;
	}



	public String getFirstName() {
		return this.firstName;
	}



	public void printAccountsSummay() {
		System.out.printf("\n\n%s's accounts summary", this.firstName);
		for(int a=0; a<this.accounts.size(); a++) {
			System.out.printf("%d)%s\n", a+1, this.accounts.get(a).getSummaryLine());
		}
		System.out.println();
		
	}



	public int numAccounts() {
		return this.accounts.size();
	}



	public void printAcctTransactionHistory(int acctIndex) {
		this.accounts.get(acctIndex).printTransHistory();
		
	} 



	public double getAcctBalance(int acctIdx) {
			return this.accounts.get(acctIdx).getBalance();
	}



	public String getAcctUUID(int acctIdx) {
		return this.accounts.get(acctIdx).getUUID();
	}



	public void addAcctTransaction(int acctIdx, double amount, String memo) {
		this.accounts.get(acctIdx).addTansacaction(amount, memo);
		
	}
}
 