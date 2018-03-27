public class TL2Pile implements Runnable{
	
	private volatile boolean running;
	Register<Integer> r1;

	public TL2Pile(Register<Integer> x) {
		r1 = x;
	}
	
	public static void increment (Register<Integer> x, int i){
		
		Transaction<Integer> t = new TL2Transaction<Integer>();
		
		while(!t.isCommited()) {
			try{
				t.begin();
				x.write(t, (Integer) x.read(t)+1);
				t.try_to_commit();
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
	}

	public void run() {
		this.running = true;
		int i = 0;
		while (i < 10){
			increment(r1, i);
			++i;
		}
		System.out.println(this);
		System.out.println("r1 value : " + r1.getValue());
		System.out.println("r1 date  : " + r1.getDate());
	}
	
	public void stop() {
		this.running = false;
	}
}