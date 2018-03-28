import java.util.ArrayList;

public class Master {
	
	public static void main(String[] args) {
		
		Register<Integer> register0 = new TL2Register<Integer>(0);
		Register<Integer> register1 = new TL2Register<Integer>(10);
		Register<Integer> register2 = new TL2Register<Integer>(10);
		
		ArrayList<BankAccount> ArrayAccount = new ArrayList<BankAccount>();
		ArrayList<Thread> ArrayThreads = new ArrayList<Thread>();
		
		int nbThread = 5;
		
		ArrayAccount.add(new BankAccount(register0, register1, register2, 1));
		ArrayAccount.add(new BankAccount(register0, register1, register2, 2));
		ArrayAccount.add(new BankAccount(register0, register1, register2, 3));
		ArrayAccount.add(new BankAccount(register0, register1, register2, 2));
		ArrayAccount.add(new BankAccount(register0, register1, register2, 3));
		
		for(int i = 0; i < nbThread; ++i) {
			ArrayThreads.add(new Thread(ArrayAccount.get(i), "Thread " + i));
		}
		
		// We launch all of the threads
		try {
			for(int i = 0; i < nbThread; ++i) {
				ArrayThreads.get(i).start();
			}
		} catch (Exception e) {e.printStackTrace();}
		
		// We wait for all of the threads to complete 
		for(int i = 0; i < nbThread; ++i) {
			try {
				ArrayThreads.get(i).join();
			}catch(Exception e) {e.printStackTrace();}
		}
		System.out.println("r0 value : " + register0.getValue());
		System.out.println("r0 date : " + register0.getDate());
		System.out.println("r1 value : " + register1.getValue());
		System.out.println("r1 date : " + register1.getDate());
		System.out.println("r2 value : " + register2.getValue());
		System.out.println("r2 date : " + register2.getDate());
	}
}
