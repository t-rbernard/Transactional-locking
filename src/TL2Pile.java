public class TL2Pile implements Runnable{
	
	private volatile boolean running;
	Register<Integer> r1;
	Register<Integer> r2;

	public TL2Pile(Register<Integer> x, Register<Integer> y) {
		r1 = x;
		r2 = y;
	}
	
	public static void increment (Register<Integer> x, Register<Integer> y){
		
		Transaction<Integer> t = new TL2Transaction<Integer>();
		
		while(!t.isCommited()) {
			try{
				t.begin();
				System.out.println("1ère lecture de x  " + x.read(t));
				System.out.println("1ère lecture de y " + y.read(t));
				x.write(t, (Integer) x.read(t)+1);
				System.out.println("2ème lecture de x  " + x.read(t));
				System.out.println("2ème lecture de y " + y.read(t));
				y.write(t, (Integer) y.read(t)+1);
				System.out.println("3ème lecture de x  " + x.read(t));
				System.out.println("3ème lecture de y " + y.read(t));
				t.try_to_commit();
				System.out.println(t);
				System.out.println(x + "  "  + Integer.toString(x.read(t)));
				System.out.println(y + "  "  + Integer.toString(y.read(t))); 
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public void run() {
		running = true;
		int i = 0;
		while (i < 10){
			System.out.println();
			System.out.println(this);
			increment(r1,r2);
			try {
				Thread.sleep(10);
			} catch (Exception e) {

				e.printStackTrace();
			}
			++i;
		}	
	}
	
	public void stop() {
		this.running = false;
	}
}