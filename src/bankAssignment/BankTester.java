package bankAssignment;

import java.util.Scanner;

/*
 * This class holds the main function for the bank. It displays the initial window for users that 
 * allows them to access their accounts. It also enables users to save and quit.
 */
public class BankTester {
	public static void main(String[] args) {
		
		Bank bank = new Bank();
		Scanner scanner = new Scanner(System.in);
		int choice = 1;
		
		while (choice != 2)
		{
			System.out.println("Options:");
			System.out.println(" 1)Login");
			System.out.println(" 2)Save and Quit");
			System.out.print("Choice:");
			choice = scanner.nextInt();
			
			switch(choice){
			
				case 1:
					bank.LogIn();
					break;
				case 2:
					bank.writeDB();
					System.exit(1);
					break;
				default:
					System.out.println ("Invalid choice");
			}
		}
	}
}
