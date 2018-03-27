
public class Master {
	
	public static void increment (Register<Integer> x, Register<Integer> y, Register<Integer> z){
		
		Transaction<Integer> t = new TL2Transaction<Integer>();
		
		while(!t.isCommited()) {
			try{
				t.begin();
				System.out.println("1ère lecture de x " + x.read(t));
				System.out.println("1ère lecture de y " + y.read(t));
				System.out.println("1ère lecture de z " + z.read(t));
				x.write(t, (Integer) x.read(t)+1);
				System.out.println("2ème lecture de x " + x.read(t));
				System.out.println("2ème lecture de y " + y.read(t));
				System.out.println("2ème lecture de z " + z.read(t));
				y.write(t, (Integer) y.read(t)+1);
				System.out.println("3ème lecture de x " + x.read(t));
				System.out.println("3ème lecture de y " + y.read(t));
				System.out.println("3ème lecture de z " + z.read(t));
				z.write(t, (Integer) y.read(t)+1);
				System.out.println("4ème lecture de x " + x.read(t));
				System.out.println("4ème lecture de y " + y.read(t));
				System.out.println("4ème lecture de z " + z.read(t));
				t.try_to_commit();
				System.out.println(t);
				System.out.println(x + "  "  + Integer.toString(x.read(t)));
				System.out.println(y + "  "  + Integer.toString(y.read(t))); 
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		
		Register<Integer> r0 = new TL2Register<Integer>(0);

		Register<Integer> r1 = new TL2Register<Integer>(100);

		TL2Pile pile1 = new TL2Pile(r0,r1);
		TL2Pile pile2 = new TL2Pile(r0,r1);
		
		
		try {
			new Thread(pile1).start();
			new Thread(pile2).start();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
