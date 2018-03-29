/* This object have been created in the need of the project and is a classical object
 * 
 * @author Romain Bernard, Loïc Boutin, Ivan Dromigny
 * 
 */

public class TL2BankAccount implements Runnable {

	private volatile boolean running;
	private static Register<Integer> register1;
	private static Register<Integer> register2;
	private static Register<Integer> register3;
	int choice;
	
	// Constructor
	public TL2BankAccount(Register<Integer> x, Register<Integer> y, Register<Integer> z, int c) {
		TL2BankAccount.register1 = x;
		TL2BankAccount.register2 = y;
		TL2BankAccount.register3 = z;
		this.choice = c;
	}
	
	public TL2BankAccount(int c) {
		this.choice = c;
	}
	
	//Only for testing so we use getValue instead of read
	public static String getBalances() {
		return register1.getValue() + "\n" + register2.getValue() + "\n" + register3.getValue();
	}

	// Function use to add money on a bank account
	public static void addMoney (Register<Integer> reg, int amount){
		
		Transaction<Integer> t = new TL2Transaction<Integer>();

		while(!t.isCommited()) {
			try{
				t.begin();
				reg.write(t, (Integer) reg.read(t) + amount);
				t.try_to_commit();
			}catch(Exception e){
				//e.printStackTrace();
			}
		}
	}
	
	// Function use to take money on a bank account
	public static void takeMoney (Register<Integer> reg, int amount){
		
		Transaction<Integer> t = new TL2Transaction<Integer>();

		while(!t.isCommited()) {
			try{
				t.begin();
				if(reg.read(t) - amount >= 0) {
					reg.write(t, (Integer) reg.read(t) - amount);
				} else {
					//System.out.println("Not enough money");
				}
				t.try_to_commit();
			}catch(Exception e){
				//e.printStackTrace();
			}
		}
	}
	
	// Function use to transfer money on a bank account
	public static void transerMoney (Register<Integer> regRec, Register<Integer> regGive, int amount){
		
		Transaction<Integer> t = new TL2Transaction<Integer>();

		while(!t.isCommited()) {
			try{
				t.begin();
				if(regGive.read(t) - amount < 0) {
					regGive.write(t, (Integer) regGive.read(t) - amount);
					regRec.write(t, (Integer) regRec.read(t) + amount);
				} else {
					//System.out.println("Not enough money");
				}
				t.try_to_commit();
			}catch(Exception e){
				//e.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		
		this.running = true;
		
		//System.out.println("Balance before operation : \n" + getBalances());
		// Different actions happened function of the choice
		switch(choice) {
			case 1:
				for(int i = 0; i < 5; ++i) {
					addMoney(register1, 100);
					addMoney(register2, 100);
					takeMoney(register3, 50);
				}
				break;
			case 2:
				addMoney(register1, 100);
				addMoney(register2, 100);
				takeMoney(register3, 50);
				break;
			case 3:
				transerMoney(register2, register3, 50);
				break;
			case 4:
				transerMoney(register1, register2, 100);
				break;
			case 5:
				addMoney(register1, 5000);
				addMoney(register2, 5000);
				addMoney(register3, 5000);
				break;
			default:
				takeMoney(register1, 5);
				takeMoney(register2, 5);
				takeMoney(register3, 5);
				break;
		}

		//System.out.println("Balance after operation : \n" + getBalances());
	}
	
	public void stop() {
		this.running = false;
	}
}
