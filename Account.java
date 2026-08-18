
public class Account {

	private String cardId = "";
	private String userName = "";
	private String gender = "";
	private String passWord = "";	
	private double balance = 0.0;
	private double limit = 0.0; // Users define the withdrawal amount.
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getUserName() {
		return (gender.equalsIgnoreCase("Man") ? "Mr. " : "Ms. ") + userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		
		return passWord;
	}

	public void setPassWord(String passWord) {
		
			this.passWord = passWord;
		
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getLimit() {
		return limit;
	}

	public void setLimit(double limit) {
		this.limit = limit;
	}
	
}
