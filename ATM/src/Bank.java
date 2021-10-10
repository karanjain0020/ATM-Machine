import java.util.ArrayList;
import java.util.Random;

public class Bank {

	/**
	 * Name of the bank
	 */
	  String bankName;

	/**
	 * List of customers of the bank
	 */
	private ArrayList<User> customers;

	/**
	 * List of all the accounts of the bank
	 */
	private ArrayList<Account> accounts;
	
	/**
	 * Create a new bank object
	 * Constructor
	 * @param name
	 */
	public Bank(String name)
	{
		this.bankName = name;
		this.customers = new ArrayList<User>();
		this.accounts = new ArrayList<Account>();
		
	}
	
	
	/**
	 * Generate a new unique user ID that does not exist for any other users
	 * 
	 * @return
	 */
	public String getNewUUID() {
		String uuid = "";
		Random rnb = new Random();
		int length = 6;
		boolean nonUnique = false;

		/**
		 * Does statements in the do loop repeatedly until while statement is true.
		 */
		do {

			// generate the number
			for (int c = 0; c < length; c++) {
				// typecasting the primitive int type to wrapper integer
				// class with more functionality. Like toString is method of
				// integer wrapper class.
				uuid += ((Integer) rnb.nextInt(10)).toString();
			}

			// check to make sure that it's unique
			for (User u : this.customers) {
				if (uuid.compareTo(u.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}

		} while (nonUnique);

		return uuid;
	}

	
	public String getNewAccountID() {
		String uuid = "";
		Random rnb = new Random();
		int length = 12;
		boolean nonUnique = false;

		/**
		 * Does statements in the do loop repeatedly until while statement is true.
		 */
		do {

			// generate the number
			for (int c = 0; c < length; c++) {
				// typecasting the primitive int type to wrapper integer
				// class with more functionality. Like toString is method of
				// integer wrapper class.
				uuid += ((Integer) rnb.nextInt(10)).toString();
			}

			// check to make sure that it's unique
			for (Account a : this.accounts) {
				if (uuid.compareTo(a.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}

		} while (nonUnique);

		return uuid;

	}

	/**
	 * 
	 * @param account
	 */
	public void addAccount(Account account) {
		this.accounts.add(account);
	}
	
	/**
	 * Create a new user of the bank
	 * @param firstName
	 * @param lastName
	 * @param pin
	 * @return
	 */
	public User addUser(String firstName, String lastName, String pin) {
		//create a new User object add it to our list
		User newUser = new User(firstName, lastName, pin, this);
		this.customers.add(newUser);
		
		//create a savings account for the user and add it to the bank and
		//user account lists.
		Account newAccount = new Account("Savings", newUser, this);
		newUser.addAccount(newAccount);
		this.addAccount(newAccount);
		
		return newUser;
	}
	
	//when the user logs in, checks if the userId is valid and then checks if their pin is valid
	public User userlogin(String userId, String pin) {
		
		//search through the list of users
		for(User u: this.customers) {
			
			//check user ID is correct
			if(u.getUUID().compareTo(userId)==0 && u.validatePin(pin)) {
				return u;
				
			}	
		}
		//if we haven't found the user or validated the pin
		return null;
	}


	public String getName() {
		return this.bankName;
	}

}
