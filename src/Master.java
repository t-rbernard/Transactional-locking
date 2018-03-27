
public class Master {
	
	public static void main(String[] args) {
		
		Register<Integer> r0 = new TL2Register<Integer>(0);

		TL2Pile pile1 = new TL2Pile(r0);
		TL2Pile pile2 = new TL2Pile(r0);
		
		
		try {
			new Thread(pile1).start();
			new Thread(pile2).start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
