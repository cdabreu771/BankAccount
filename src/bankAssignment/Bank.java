package bankAssignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

/*
 *  This class holds all the functions that can be done to bank account data. It takes in a
 * 	database of accounts and stores the information into an array of BankAccount objects.
 */
public class Bank {
	
	private ArrayList<BankAccount> list = new ArrayList<BankAccount>();
	private final String location = "/Users/Camille/Documents/Assignment0/src/bankAssignment/bankDB.txt";
	private Scanner scanner = new Scanner(System.in);
	private File file;
	DecimalFormat decimalFormat = new DecimalFormat("#.00");
	
	//This function is the constructor for the Bank class. It imports a file, and calls the reader.
	protected Bank() {
		
		file = new File(location);
		readDB();
	}
	
	/*
	 * This function reads the file and separates the content into its appropriate bank account object.
	 * It then inserts this bank account into an array of bank accounts.
	 */
	private void readDB()
	{
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file)); //Bufferedreader reads one line.
			String line = "";
			
			while ((line = bufferedReader.readLine()) != null) {
				
				String[] accountinfo = line.split(",");
				BankAccount bankAccount = null;
				
				if(accountinfo.length == 5) {	// string.length() array.length <-- if array is of single variables
					 bankAccount = new BankAccount(Integer.parseInt(accountinfo[0]), Integer.parseInt(accountinfo[1]),
							Double.parseDouble(accountinfo[2]), Double.parseDouble(accountinfo[3]), accountinfo[4], true);
				} 
				else if (accountinfo.length == 4) {
					 bankAccount = new BankAccount(Integer.parseInt(accountinfo[0]), Integer.parseInt(accountinfo[1]),
							Double.parseDouble(accountinfo[2]), 0, accountinfo[3], false);
				}
				else {
					System.out.println ("Error:Incorrect account format.");
				}
				
				if (bankAccount != null) {
					list.add(bankAccount);
				}
			}		
		} catch (FileNotFoundException ex) {
			System.out.println("FileNotFoundException Occurred");
		} catch (IOException ex) {
			System.out.println ("IOException Occurred");
		}
	}
	
	/*
	 * This function handles the log-in features for the user. This includes asking for the 
	 * account number, verifying that it exists, and then requesting a correct pin. If a checking account does not
	 * exist, this function will display savings account information and allow a user to
	 * make changes to the account.
	 */
	protected void LogIn() {
		
		int acntNum;
		int pin;
		
		System.out.print("Enter in Bank Account Number: ");
		acntNum = scanner.nextInt();
		
		BankAccount usersAccount = null;
		
		for (int i = 0; i < list.size(); i ++) { // arrayList.size() <-- if array is of objects
			BankAccount bankAccount = list.get(i);  //<-- arrayList.get(i), array[i]
			if(bankAccount.getAcntNum() == acntNum) {
				usersAccount = bankAccount;
				break;
			}
		}
		
		if(usersAccount == null) {
			System.out.println("Invalid Account number");
			return;
		}
		
		boolean flag = false;
		
		for (int i = 0; i < 3; i++) {
			System.out.print("Enter in pin: ");
			pin = scanner.nextInt();
	
			if (usersAccount.checkPin(pin)) {
				flag = true;
				break;
			}
			
			System.out.println("Invalid Pin: Number of attempts left: " + (2 - i));
		}
		
		if (flag) {		
			if (usersAccount.checkingExists()) {
				showChoice (usersAccount);
			}
			else {
				flag = true;
				
				System.out.println("Your savings account has a blance of: $" + decimalFormat.format(usersAccount.getSavings()));
				System.out.println("How would you like to proceed:");
				
				while(flag) {
					System.out.println("(Enter in positive number to deposit, negative number to withdraw)");
					System.out.print("Amount: $");
					double newAmount2 = scanner.nextDouble();
				
					if (newAmount2 + usersAccount.getSavings() > 0 ) {
						changeAccount(newAmount2, usersAccount, "savings");
						flag = false;
						return;
					}
					System.out.println("Error: Negative balance. Please try again:");
				}
			}		
		}
		
		return;	
	}
	
	/*
	 * This function allows a user to check their checking account and savings information, as well as request
	 * a change to the account. If requested, it will call the change function.
	 */
	private void showChoice(BankAccount usersAccount){
	
		System.out.println("Hello " + usersAccount.getAcntName() + ", which account would you like to access:");
		System.out.println(" 1)Checkings");
		System.out.println(" 2)Savings");
		System.out.print("Choice:");
		
		int choice = scanner.nextInt();
		
		switch(choice) {
		
			case 1:
				System.out.println("Your checking account has a balance of: $" + decimalFormat.format(usersAccount.getChecking()));
				System.out.println("How would you like to proceed? ");
				System.out.println("(Enter in positive number to deposit, negative number to withdraw)");
				System.out.print("Amount: $");
				double newAmount = scanner.nextDouble();
				if (newAmount + usersAccount.getChecking() > 0 ) {
					changeAccount(newAmount,usersAccount, "checking");
					return;
				}
				else {
					System.out.println("Error: Negative balance. Please try again.");
					showChoice(usersAccount);
				}
				break;
			case 2:
				System.out.println("Your savings account has a blance of: $" + decimalFormat.format(usersAccount.getSavings()));
				System.out.println("How would you like to proceed:");
				System.out.println("(Enter in positive number to deposit, negative number to withdraw)");
				System.out.print("Amount: $");
				double newAmount2 = scanner.nextDouble();
				if (newAmount2 + usersAccount.getSavings() > 0 ) {
					changeAccount(newAmount2, usersAccount, "savings");
					return;
				}
				else {
					System.out.println("Error: Negative balance. Please try again.");
					showChoice(usersAccount);
				}
				break;
			default:
				System.out.println ("Invalid choice");
				showChoice(usersAccount);
		}
	}
	
	/*
	 * This function allows a user make changes to their checking or savings account. This includes
	 * a withdrawal or deposit.
	 */
	private void changeAccount(double newAmount, BankAccount usersAccount, String type) {
		
		
		
		if (type == "checking") {
			usersAccount.setChecking(newAmount + usersAccount.getChecking());
			System.out.println("Thank you, your new checkings balance is $" + decimalFormat.format(usersAccount.getChecking()));
			System.out.println("Returning to login screen"); 
		}
		else if (type == "savings") {
			usersAccount.setSavings(newAmount + usersAccount.getSavings());
			System.out.println("Thank you, your new savings balance is $" + decimalFormat.format(usersAccount.getSavings()));
			System.out.println("Returning to login screen"); 
		}
		return;
	}
	
	//This function writes all changes to the bank accounts back to the bank database file.
	protected void writeDB() {
		try {
			FileOutputStream writer = new FileOutputStream(file,false); //false because we do not 
			//want to append (add duplicates) to the original file
			
			
			for (int i = 0; i < list.size(); i ++) {
				StringBuilder account = new StringBuilder();
				account.append(list.get(i).getAcntNum());
				account.append(",");
				account.append(list.get(i).getPin());
				account.append(",");
				account.append(decimalFormat.format(list.get(i).getSavings()));
				account.append(",");
				if (list.get(i).checkingExists()) {
					account.append(decimalFormat.format(list.get(i).getChecking()));
					account.append(",");
				}
				account.append(list.get(i).getAcntName());
				account.append("\n");
				
				writer.write(account.toString().getBytes());
			}
			return;
		} catch(FileNotFoundException ex) { 
			System.out.println("FileNotFoundException occurred.");
		} catch (IOException ex) {
			System.out.println("IOException occurred.");
		}
	}
}
