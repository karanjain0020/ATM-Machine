import java.util.Date;

public class Transaction {
	
	/**
	 * Amount of the transaction
	 */
	private double amount; 
	
	/**
	 * Time and date of the transaction 
	 */
	private Date timeStamp;
	
	/**
	 * Memo of the transaction
	 */
	private String memo;
	
	/**
	 * Account in which transaction occurs 
	 */
	private Account inAccount;
	
	/**
	 * Add an account
	 * @param amount
	 * @param inAccount
	 */
	public Transaction(double amount, Account inAccount) {
		this.amount = amount;
		this.inAccount = inAccount;
		this.timeStamp = new Date();
		this.memo = "";
		
	}
	
	
	//Overloading Constructor
	public Transaction(double amount, String memo, Account inAccount) {
		
		
		//call the first constructor above first from this constructor
		this(amount, inAccount);
		
		//set the memo
		this.memo = memo;
		
	}


	public double getAmount() {
		return this.amount;
	}


	public String getSummaryLine() {
		if(this.amount>=0) {
			return String.format("%s: $%.02f: %s", this.timeStamp.toString(), this.amount, this.memo);
		}
		else {
			return String.format("%s: $(%.02f): %s", this.timeStamp.toString(), this.amount, this.memo);
		}
	
	}
}
 