import java.util.ArrayList;

public class Master {
	
	public static void main(String[] args) {
		/*
		// Registres pour le jeux de test TL2Increment (plus adaptés)
		Register<Integer> register0 = new TL2Register<Integer>(0);
		Register<Integer> register1 = new TL2Register<Integer>(10);
		Register<Integer> register2 = new TL2Register<Integer>(20);
		
		/*/
		// Registres pour le jeux de test TL2BankAccount (plus adaptés)
		Register<Integer> register0 = new TL2Register<Integer>(5000);
		Register<Integer> register1 = new TL2Register<Integer>(1000);
		Register<Integer> register2 = new TL2Register<Integer>(2000);
		//*/
		
		// Registres pour le jeux de test TL2RandString
		ArrayList<TL2Register<String>> stringRegs = new ArrayList<TL2Register<String>>();
		for(int i = 0; i < 10; ++i) {
			stringRegs.add(new TL2Register<String>(""));
		}
		
		
		ArrayList<TL2Increment> ArrayIncrement = new ArrayList<TL2Increment>();
		
		ArrayList<TL2BankAccount> ArrayAccount = new ArrayList<TL2BankAccount>();
		
		ArrayList<TL2RandString> ArrayRandString = new ArrayList<TL2RandString>();
		
		ArrayList<Thread> ArrayThreads = new ArrayList<Thread>();
		
		int nbThread = 50;
		
		ArrayAccount.add(new TL2BankAccount(register0, register1, register2, 1));
		ArrayAccount.add(new TL2BankAccount(2));
		ArrayAccount.add(new TL2BankAccount(3));
		ArrayAccount.add(new TL2BankAccount(2));
		ArrayAccount.add(new TL2BankAccount(5));
		ArrayAccount.add(new TL2BankAccount(18));
		ArrayAccount.add(new TL2BankAccount(4));
		
		for(int i = 0; i < nbThread; ++i) {
			
			// Pour effectuer les tests avec l'objet TL2Increment
			
			ArrayIncrement.add(new TL2Increment(register0, register1, 100));
			ArrayThreads.add(new Thread(ArrayIncrement.get(i), "Thread " + i));
			
			
			// Pour effectuer les tests avec l'objet TL2BankAccount
			//ArrayThreads.add(new Thread(ArrayAccount.get(i%7), "Thread " + i));
			
			// Pour effectuer les tests avec l'objet TL2RandString
			/*
			ArrayRandString.add(new TL2RandString(stringRegs));
			ArrayThreads.add(new Thread(ArrayRandString.get(i), "Thread " + i));
			*/
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
		
		// Used for debugging
		System.out.println("r0 value : " + register0.getValue());
		System.out.println("r0 date : " + register0.getDate());
		System.out.println("r1 value : " + register1.getValue());
		System.out.println("r1 date : " + register1.getDate());
		System.out.println("r2 value : " + register2.getValue());
		System.out.println("r2 date : " + register2.getDate());
		
		for(int i = 0; i < stringRegs.size(); ++i){
			System.out.println("r" + i + " value : " + stringRegs.get(i).getValue());
		}
	}
}
