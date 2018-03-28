
public class BankAccount implements Runnable {

	private volatile boolean running;
	Register<Integer> register1;
	Register<Integer> register2;
	Register<Integer> register3;
	int choice;
	
	// Constructor
	public BankAccount(Register<Integer> x, Register<Integer> y, Register<Integer> z, int c) {
		this.register1 = x;
		this.register2 = y;
		this.register3 = z;
		this.choice = c;
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
				if(reg.read(t) - amount >= 0)
					reg.write(t, (Integer) reg.read(t) - amount);
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
		
		switch(choice) {
			case 1:
				for(int i = 0; i < 5; ++i) {
					addMoney(register1, 100);
				}
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			default:
				break;
		}
	}
	
	public void stop() {
		this.running = false;
	}
}
