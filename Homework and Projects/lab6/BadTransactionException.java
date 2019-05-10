public class BadTransactionException extends Exception {
	public int amount; 

	public BadTransactionException(int amount){
		super("invalid account:"+amount);
		this.amount=amount;
	}
}