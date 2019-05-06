package bankAssignment;

/*
 * This class contains all of the data for the bank account. It's functions allow for access and mutation 
 * of the data.
 */
public class BankAccount {
	private int acntNum;
	private int pin;
	private double savings;
	private double checking;
	private String acntName;
	private boolean checkExists;
	
	// This is the constructor for the BankAccount class. It assigns values to each variable.
	protected BankAccount(int acntNum, int pin, double savings, double checking, String acntName, boolean checkExists) {
		this.acntNum = acntNum;
		this.pin = pin;
		this.savings = savings;
		this.checking = checking;
		this.acntName = acntName;
		this.checkExists = checkExists;
	}
	
	// This function accesses the account number for the bank account.
	protected int getAcntNum() {
		return acntNum;
	}
	
	// This function accesses the pin for the bank account.
	protected int getPin() {
		return pin;
	}
	
	// This function accesses the account name for the bank account.
	protected String getAcntName() {
		return acntName;
	}
	
	// This function accesses the savings account for the bank account.
	protected double getSavings() {
		return savings;
	}
	
	/*
	 * This function checks if the checking account exists and then
	 * accesses the checking account for the bank account.
	 */
	protected double getChecking() {
		if (checkExists == true) {
			return checking;
		}
		else {
			throw new UnsupportedOperationException ("No checking account exists.");
		}
	}
	
	// This function mutates the the savings account for the bank account.
	protected void setSavings(double num) {
		savings = num;
	}
	
	/*
	 * This function checks to if the checking account exists, and then
	 * mutates the checking account for the bank account.
	 */
	protected void setChecking(double num) {
		if (checkExists == true) {
			checking = num;
		}
		else {
			throw new UnsupportedOperationException ("No checking account exists.");
		}
	}
	
	// This function checks if a checking account exists for the bank account.
	protected boolean checkingExists() {
		if (checkExists == true) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// This function checks if the pin is correct for the bank account.
	protected boolean checkPin(int num) {
		if (num == pin) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/* This is the override function for the toString function. It prints out
	 * the bank account information.
	 */
	@Override
	public String toString() {
		if (checkExists == true){	
			return acntNum + "," + pin + "," + savings + "," + checking +","
				+ "," + acntName;
		}
		else {
			return acntNum + "," + pin + "," + savings + "," + acntName;
		}
	}
}
