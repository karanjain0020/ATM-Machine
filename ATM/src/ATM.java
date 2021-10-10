import java.util.Scanner;

public class ATM {

	public static void main(String[] args) {

		// initialise Scanner
		Scanner sc = new Scanner(System.in);

		// initialise Bank
		Bank bank = new Bank("Bank of America");

		// add a user which will create a user with a checking account
		User aUser = bank.addUser("Karan", "Jain", "1122");

		// add a checking account for the above user
		Account aAccount = new Account("Checking", aUser, bank);
		aUser.addAccount(aAccount);
		bank.addAccount(aAccount);

		User testUser;

		while (true) {

			// will stay in login prompt until the user ID and pin are authenticated.
			// the following is the loop for that

			testUser = ATM.mainMenuPrompt(bank, sc);

			// will stay on the main menu page until the user quits

			// since we cannot have more than once scanner in System.in
			// we are encapsulating scanner in Bank and ATM classes
			// by adding them as param here
			ATM.printUserMenu(testUser, sc);
		}
	}

	// static method as there is no data in the ATM class
	private static User mainMenuPrompt(Bank bank, Scanner sc) {
		// init
		String userID;
		String pin;
		User authUser;

		// prompt the user for user iD pin/combo until a correct one is achieved
		do {
			System.out.printf("\n\nWelcome to %s\n\n", bank.getName());
			System.out.print("Enter user ID: ");
			userID = sc.nextLine();
			System.out.printf("Enter pin: ");
			pin = sc.nextLine();

			// Try to find and get the user object corresponding to the ID and pin combo
			authUser = bank.userlogin(userID, pin);
			if (authUser == null) {
				// just tell them that the combination is incorrect and not the whie thing
				// to not give too much information. Security.
				System.out.println("Incorrect user ID or Pin" + "Please try again ");
			}
		} while (authUser == null); // continues to show main menu prompt until successfull login

		return authUser; // ????
	}

	private static void printUserMenu(User testUser, Scanner sc) {

		// print summary of user's account
		testUser.printAccountsSummay();

		// init
		int choice;

		// user menu
		do {
			System.out.printf("Welcome %s, what would you like to do?", testUser.getFirstName());
			System.out.println(" 1) Show account transaction ");
			System.out.println(" 2) Withdraw ");
			System.out.println(" 3) Deposite ");
			System.out.println(" 4) Transfer ");
			System.out.println(" 5) Quit ");
			System.out.println();
			System.out.println(" Enter Choice ");
			choice = sc.nextInt();

			if (choice < 1 || choice > 5) {
				System.out.println("Invalid choice. Please choose 1-5");
			}

		} while (choice < 1 || choice > 5);

		// process the choice: this is outside the do while loop
		switch (choice) {

		case 1:
			ATM.showTransHistory(testUser, sc);

		case 2:
			ATM.withdraw(testUser, sc);

		case 3:
			ATM.deposite(testUser, sc);

		case 4:
			ATM.transfer(testUser, sc);

		}
		// recursion if the choice is anything other than 5. It only quits at 5. At
		// anything else it will call the menu to do one of the above
		// so we only quit userMenu when choice is 5
		if (choice != 5) {
			ATM.printUserMenu(testUser, sc);
		}

	}

	/**
	 * 
	 * @param testUser
	 * @param sc
	 */
	private static void showTransHistory(User testUser, Scanner sc) {
		int theAcct;
		// get the account whose history the user wants to look at

		do {
			System.out.printf("Enter the number of (1-%d) of the accuount\n" + "whose transaction you want to see: ",
					testUser.numAccounts());
			theAcct = sc.nextInt() - 1;
			if (theAcct < 0 || theAcct >= testUser.numAccounts()) {
				System.out.println("Invalid Account. Please try again.");
			}
		} while (theAcct < 0 || theAcct >= testUser.numAccounts());

		// print transacton history
		testUser.printAcctTransactionHistory(theAcct);
	}

	/**
	 * 
	 * @param testUser
	 * @param sc
	 */
	private static void transfer(User testUser, Scanner sc) {
		//init
		int fromAcct;
		int toAcct;
		double amount;
		double acctBal;
		
		
		
		//get the from account
		
		do {
			 System.out.printf("Enter the number of (1-%d) of the accuount\n" +
					 			"to transfer from: ", 
								testUser.numAccounts());
			 fromAcct = sc.nextInt()-1;
			 if(fromAcct<0 || fromAcct>=testUser.numAccounts()) {
				 System.out.println("Invalid Account. Please try again.");
			 }
		}while(fromAcct<0 || fromAcct>=testUser.numAccounts());
		acctBal = testUser.getAcctBalance(fromAcct);
		
		//get the to account
		
		do {
			 System.out.printf("Enter the number of (1-%d) of the accuount\n" +
					 			"to transfer to: ", 
								testUser.numAccounts());
			 toAcct = sc.nextInt()-1;
			 if(toAcct<0 || toAcct>=testUser.numAccounts()) {
				 System.out.println("Invalid Account. Please try again.");
			 }
		}while(toAcct<0 || toAcct>=testUser.numAccounts());
		
		//get the amount to transfer
		
		do {
			System.out.printf("Enter the amount to transfer (max $%.02f): $",
					acctBal);
			amount = sc.nextDouble();
			//the user can cheat the bank by transferring negative balances which would 
			//subtract the negative from from account making it actually add money
			if(amount<0) {
				System.out.println("Amount must be greater than zero. ");
			} else if(amount>acctBal) {
				System.out.printf("Amount cannot be greater than\n" +
						"balance of $%.92f. \n", acctBal);
			}
		}while(amount<0|| amount >acctBal);
		
		
		//finally do the transfer
		
		testUser.addAcctTransaction(fromAcct, -1*amount, String.format(
				"Transfer to account %s", testUser.getAcctUUID(toAcct)));
		
		testUser.addAcctTransaction(toAcct, amount, String.format(
				"Transfer to account %s", testUser.getAcctUUID(toAcct)));
	}
		
		private static void withdraw(User testUser, Scanner sc) {
			int fromAcct;
			double amount;
			double acctBal;
			String memo;
			
			
			
			//get the from account
			
			do {
				 System.out.printf("Enter the number of (1-%d) of the accuount\n" +
						 			"to transfer from: ", 
									testUser.numAccounts());
				 fromAcct = sc.nextInt()-1;
				 if(fromAcct<0 || fromAcct>=testUser.numAccounts()) {
					 System.out.println("Invalid Account. Please try again.");
				 }
			}while(fromAcct<0 || fromAcct>=testUser.numAccounts());
			acctBal = testUser.getAcctBalance(fromAcct);
			
			//get the amount to transfer
			
			do {
				System.out.printf("Enter the amount to transfer (max $%.02f): $",
						acctBal);
				amount = sc.nextDouble();
				//the user can cheat the bank by transferring negative balances which would 
				//subtract the negative from from account making it actually add money
				if(amount<0) {
					System.out.println("Amount must be greater than zero. ");
				} else if(amount>acctBal) {
					System.out.printf("Amount cannot be greater than\n" +
							"balance of $%.92f. \n", acctBal);
				}
			}while(amount<0|| amount >acctBal);
			
			sc.nextLine();
			
			//get a memo
			System.out.println("Enter a memo: ");
			memo = sc.nextLine();
			
			//do the withdrawl
			
			testUser.addAcctTransaction(fromAcct, -1*amount, memo);	
			
		}
	
		private static void deposite(User testUser, Scanner sc) {
			int toAcct;
			double amount;
			double acctBal;
			String memo;
			
			
			
			//get the from account
			
			do {
				 System.out.printf("Enter the number of (1-%d) of the accuount\n" +
						 			"to transfer from: ", 
									testUser.numAccounts());
				 toAcct = sc.nextInt()-1;
				 if(toAcct<0 || toAcct>=testUser.numAccounts()) {
					 System.out.println("Invalid Account. Please try again.");
				 }
			}while(toAcct<0 || toAcct>=testUser.numAccounts());
			acctBal = testUser.getAcctBalance(toAcct);
			
			//get the amount to transfer
			
			do {
				System.out.printf("Enter the amount to transfer (max $%.02f): $",
						acctBal);
				amount = sc.nextDouble();
				//the user can cheat the bank by transferring negative balances which would 
				//subtract the negative from from account making it actually add money
				if(amount<0) {
					System.out.println("Amount must be greater than zero. ");
				} else if(amount>acctBal) {
					System.out.printf("Amount cannot be greater than\n" +
							"balance of $%.92f. \n", acctBal);
				}
			}while(amount<0|| amount >acctBal);
			
			sc.nextLine();
			
			//get a memo
			System.out.println("Enter a memo: ");
			memo = sc.nextLine();
			
			//do the deposite
			
			testUser.addAcctTransaction(toAcct, amount, memo);
	
	
	}
		
}
