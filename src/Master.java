import java.util.ArrayList;

public class Master {
	
	public static void main(String[] args) {
		
		System.out.println("BEGGGINNNN");
		Register<Integer> r0 = new TL2Register<Integer>(0);
		Register<Integer> r1 = new TL2Register<Integer>(10);
		
		ArrayList<TL2Pile> ArrayT = new ArrayList<TL2Pile>();
		ArrayList<Thread> ArrayTh = new ArrayList<Thread>();
		
		int nbThread = 10;
		
		for(int i = 0; i < nbThread; ++i) {
			ArrayT.add(new TL2Pile(r0, r1, 1000));
			ArrayTh.add(new Thread(ArrayT.get(i), "thread " + i));
		}
		
		try {
			for(int i = 0; i < nbThread; ++i) {
				ArrayTh.get(i).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int i = 0; i < nbThread; ++i) {
			try {
				ArrayTh.get(i).join();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("r0 value : " + r0.getValue());
		System.out.println("r0 date : " + r0.getDate());
		System.out.println("r1 value : " + r1.getValue());
		System.out.println("r1 date : " + r1.getDate());
	}
}
