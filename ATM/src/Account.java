import java.util.ArrayList;

public class Account {
	
	/**
	 * Name of the user's account
	 */
	private String accountName; 
	
	
	/**
	 * Id number of the account
	 */
	private String uuid;
	
	/**
	 * Account holder 
	 */
	private User user;
	
	/**
	 * List of transaction of the user's account
	 */
	private ArrayList<Transaction> transactions;
	
	
	
	public Account(String accountName, User user, Bank bank) {
		
		this.accountName = accountName;
		this.user = user;
		
		this.uuid = bank.getNewAccountID();
		
		//Initialize transaction array list
		this.transactions = new ArrayList<Transaction>();
		
		/**adding the particular instance of the 
		 * class account created with this constructor
		 * to the accounts lists inside user and bank
		 * classes as this account will be one of the user's account
		 * as well as one of the many accounts affiliated with the 
		 * bank. Also it is the same object into both lists, not the copies 
		 * of the accounts to different lists. So change is going to be the 
		 * same for both.
		 */
		
		
		
		/*user.addAccount(this); 
		bank.addAccount(this);*/
		
		
	}



	public String getUUID() {
		return this.uuid;
	}



	public String getSummaryLine() {
		//get the account's balance after calculating
		double balance = this.getBalance();
		
		//format depends on whether the balance is negative or positive
		//banks do not like to use negative numbers so they either use red or brackets
		//for negative numbers
		
		if(balance>=0) {
			return String.format("%s : $%.02f : %s", this.uuid, balance, this.accountName);
		}
		else {
			return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.accountName);

		}
	}



	public double getBalance() {
		// Not making just account public just for security and restrict access from outside. 
		//Always channel the fields of a class from getter methods
		double balance = 0;
		for(Transaction t: this.transactions) {
			balance += t.getAmount();
		}
		return balance;
	}



	public void printTransHistory() {
		System.out.printf("\nTransaction history for account %s\n", this.uuid);
		for(int t=this.transactions.size()-1; t>=0; t--) {
			System.out.printf(this.transactions.get(t).getSummaryLine());
		} 
		System.out.println();
	}



	public void addTansacaction(double amount, String memo) {
		//create new transaction object and add it to the list
		
		Transaction newTrans = new Transaction(amount, memo, this);
		this.transactions.add(newTrans);
		
	}

}
