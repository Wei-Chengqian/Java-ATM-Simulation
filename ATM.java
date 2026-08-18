/*ATM 
 *业务要求：
 *1.欢迎页面引导：登陆 / 注册
 *2.登陆：
 *a:引导用户输入卡号&密码，成功后进入主程序
 *b:主程序：引导用户选择服务：
 *     查询账户，存款，取款，修改密码，退出登陆，注销已有账户
 *3:注册：
 *a:引导用户输入个人信息
 *b:主程序：引导用户输入姓名，性别，密码，确认密码，随机生成卡号
 */
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
public class ATM {
	
	private ArrayList<Account> accounts = new ArrayList<>(); // Save  account		
	private Scanner stdIn = new Scanner(System.in);
	private Account logInAcc;//Save login account

	// ATM Start page
	public void start(){
		while(true){
			System.out.println("=== Welcome to millionaire bank's ATM ===");
			System.out.println("1. User Login.\n"
								+ "2. Open an account.");
			System.out.print("Please select a service:  ");
			int choice = stdIn.nextInt();
			switch(choice) {
				case 1:
					logIn();
					break;
				case 2:
					createAccount();
					break;
				default:
					System.out.println("Invalid input");
			}
		}
	}
	//>>>>>>>>>>>>>>>>>>>>> Account Create <<<<<<<<<<<<<<<<<<<<<<<  
	private void createAccount() {
		System.out.println("==System Account Opening Process==");
		// ================== username =====================
		Account acc = new Account();
		System.out.println("Please enter your username : ");
		String name = stdIn.next();
		acc.setUserName(name);
		// ================== gender =====================
		while (true) {
			System.out.println("Please enter your gender : ");
			String gender = stdIn.next();
				if(gender.equalsIgnoreCase("Man")||gender.equalsIgnoreCase("Woman")) {
					acc.setGender(gender);
					break;
				}else {
					System.out.println("Invalid gender input.");
				}
		}
		// ================== password / confirm password =====================
		while(true) {
			System.out.println("Please enter a 6-digit password : ");
			String password = stdIn.next();
			
			if(password.matches("\\d{6}")) {
				acc.setPassWord(password);
				break;
			}else {
				System.out.println("Invalid password input.");
			}
		}

		while(true) { //  confirm password
			System.out.println("Please confirm your password : "); 
			String confirmPW = stdIn.next();
			
			if(confirmPW.equals(acc.getPassWord())) {
				System.out.println("Password set successfully. ");
				break;
			}else {
			System.out.println("Your confirm password does not match. ");
			}
		}
		// ================== limit =====================
		System.out.println("Please enter the cash withdrawal limit : ");
		double limit = stdIn.nextDouble();
		acc.setLimit(limit);
		// ================== cardId(8 digits) =====================
		String cardId = cardId();
		acc.setCardId(cardId);
		//save account info
		accounts.add(acc);
		System.out.println("Congratulations : " + acc.getUserName() + " Your account has been successfully opened. "
				+ "Your card number is :" + acc.getCardId());
	}// method 
	
	// Random Card Number Generation
	private String cardId() {
		// random 8 digits number
		while (true) {
			String cardId = "";
			Random r = new Random();
			for (int i = 0; i < 8; ++i) {
				int num = r.nextInt(10);
				cardId += num;
			}
			Account acc = getAccountByCardId(cardId);
			if (acc == null) {
				//no account open 
				return cardId;
			} 
		}
	}
	// Check for duplicates
	private Account getAccountByCardId(String cardId) {
		for(int i = 0; i < accounts.size() ; ++i) {
			Account acc = accounts.get(i);
			if(acc.getCardId().equals(cardId)) {
				return acc;
			}
		}
		return null; // no account
	}
	
	//>>>>>>>>>>>>>>>>>>>>> Account Login <<<<<<<<<<<<<<<<<<<<<<<  
	private void logIn() {
		System.out.println("==Account Login==");
		// check if have an Account
		if(accounts.size() == 0) {
			System.out.println("There are currently no accounts in the system; please open an account first.");
			return;
		}
		
		while (true) {
			System.out.println("Please enter your card number : ");
			String cardId = stdIn.next();
			Account acc = getAccountByCardId(cardId);
			if (acc == null) { 
				System.out.println("Invalid card number, please try again.");
			}else{
				while (true) {
					System.out.println("Please enter your password : ");
					String password = stdIn.next();
					if (acc.getPassWord().equals(password)) {
						System.out.println("Login successful! Welcome : "  + acc.getUserName());
						// Display the operation interface
						logInAcc = acc;
						showUserCommand();
						return; // back to the Login Screen
						
					} else {//no
						System.out.println("Invalid password, please try again.");
					} 
				}// inner while 
			}//else
		}// outer while 
		
	}
	//================== Post login interface =====================
	private void showUserCommand() {
	//Guide logged in users to select features.
		while (true) {
			System.out.println(logInAcc.getUserName() + " Please select the service you would like to use.");
			System.out.println("1. Check Account");
			System.out.println("2. Deposit");
			System.out.println("3. Withdrawal");
			System.out.println("4. Transfer");
			System.out.println("5. Change Password");
			System.out.println("6. Log out");
			System.out.println("7. Close Account");
			int command = stdIn.nextInt();
			switch (command) {
				case 1:
					checkAccountInfo();
					break;
				case 2:
					Deposit();
					break;
				case 3:
					Withdrawal();
					break;
				case 4:
					transfer();
					break;
				case 5:
					PasswordReset();
					return;
				case 6:
					System.out.println(logInAcc.getUserName() + " Successfully logged out of the system!");
					return;
				case 7:
					if(CloseAccount()) {
						return; //back to welcome page
					}
					break;
				default:
					System.out.println("Invalid input: Operation does not exist!");
			}
		}//while
	}
	//================== check Account =====================
	private void checkAccountInfo() {
		System.out.println("==Current account information is as follows==");
		System.out.println("Card number : " +logInAcc.getCardId());
		System.out.println("Account holder : "+logInAcc.getUserName());
		System.out.println("Gender : "+logInAcc.getGender());
		System.out.println("Remaining balance : "+logInAcc.getBalance());
		System.out.println("Cash withdrawal limit : "+logInAcc.getLimit());
		System.out.println(" ");
	}
	//================== Deposit / Withdrawal =====================
	private void Deposit() {
		System.out.println("==Deposit==");
		System.out.println("Please enter the deposit amount : ");
		double amount = stdIn.nextDouble();
		//In this project, the default withdrawal or deposit amount is a positive number.
		logInAcc.setBalance(logInAcc.getBalance() + amount);
		System.out.println("Deposit successful! Deposit amount: " + amount 
							+ "\nBalance : "+ logInAcc.getBalance());
	}
	private void Withdrawal() {
		System.out.println("==Withdrawal==");
			if(logInAcc.getBalance() < 100) {
				System.out.println("Withdrawal failed. The balance is less than $100; "
									+ "the minimum withdrawal amount is $100.");
				return;
			}
			while(true) {
				System.out.println("Please enter the withdrawal amount: ");
				double amount = stdIn.nextDouble();
				//In this project, the default withdrawal or deposit amount is a positive number.
				if(logInAcc.getBalance() >= amount) {
					if(amount > logInAcc.getLimit()) {
						System.out.println("The withdrawal amount exceeds the limit.\n"
										+ "Maximum cash withdrawal per transaction : "+logInAcc.getLimit());
					}else {
						logInAcc.setBalance(logInAcc.getBalance() - amount);
						System.out.println("Withdrawal successful!\n"
								+ " Withdrawal amount : " + amount);
						System.out.println("Updated balance : " + logInAcc.getBalance());
						break;
					}
				}else {
					System.out.println("Insufficient balance!\n"
								+ "Current balance is : " + logInAcc.getBalance());
				}
			}
	}
	private void transfer() {

	    System.out.println("==Transfer==");

	    if (accounts.size() < 2) {
	        System.out.println("There are no users available for transfer!");
	        return;
	    }

	    if (logInAcc.getBalance() <= 0) {
	        System.out.println("There is currently no transferable balance!");
	        return;
	    }

	    while (true) {

	        System.out.println("Please enter the recipient's card number:");
	        String cardNum = stdIn.next();

	        Account acc = getAccountByCardId(cardNum);

	        if (acc == null) {
	            System.out.println("Card number does not exist; please try again.");
	            continue;
	        }

	        if (acc == logInAcc) {
	            System.out.println("You cannot transfer money to yourself.");
	            continue;
	        }

	        stdIn.nextLine();  // consume leftover newline

	        System.out.println("Please enter the recipient's full name:");
	        String inputName = stdIn.nextLine();

	        if (!inputName.equalsIgnoreCase(acc.getUserName())) {
	            System.out.println("Name does not match; please try again.");
	            continue;
	        }

	        System.out.println("Please enter the transfer amount:");
	        double money = stdIn.nextDouble();

	        if (money <= 0) {
	            System.out.println("Invalid transfer amount.");
	            continue;
	        }

	        if (money > logInAcc.getBalance()) {
	            System.out.println("Insufficient balance.");
	            continue;
	        }

	        logInAcc.setBalance(logInAcc.getBalance() - money);
	        acc.setBalance(acc.getBalance() + money);

	        System.out.println("Transfer successful!");
	        System.out.println("Remaining balance: $" + logInAcc.getBalance());

	        return;
	    }
	}
	private boolean CloseAccount() {
		System.out.println("==Delete Account==");
		System.out.println("Do you wish to confirm the closure of your account? (y/n) : ");
		String command = stdIn.next();
		switch(command) {
			case "y":
				if(logInAcc.getBalance() == 0) {
					System.out.println("Account successfully closed!");
					accounts.remove(logInAcc);
					return true;
				}else {
					System.out.println("Account closure failed; please clear the account balance first.");
					return false;
				}
			default:
				System.out.println("Account retained!");
				return false;
		}
	}
	private void PasswordReset() {
		System.out.println("==Password Reset");
		while(true) {
			System.out.println("Please enter your current password : ");
			String cp = stdIn.next();
			if(!cp.equals(logInAcc.getPassWord())){	
				System.out.println("Incorrect password.");
			}else {
					while(true) {
					System.out.println("Please enter your new password : ");
					String np = stdIn.next();
					System.out.println("Please confirm your new password : ");
					String cnp = stdIn.next();
					if(np.equals(cnp)) {
						logInAcc.setPassWord(cnp);
						System.out.println("Password changed successfully!");
						return;
					}else {
						System.out.println("Incorrect password!");
					}
				}
			} //else
		}//while
	}
}// class


