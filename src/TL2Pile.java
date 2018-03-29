public class TL2Pile implements Runnable{
	
	private volatile boolean running;
	Register<Integer> r0;
	Register<Integer> r1;
	int nbIt;

	public TL2Pile(Register<Integer> x, int it) {
		r0 = x;
		nbIt = it;
	}

	public TL2Pile(Register<Integer> x, Register<Integer> y, int it) {
		r0 = x;
		r1 = y;
		nbIt = it;
	}
	
	public static void increment (Register<Integer> x){
		
		Transaction<Integer> t = new TL2Transaction<Integer>();

		while(!t.isCommited()) {
			try{
				t.begin();
				x.write(t, (Integer) x.read(t)+1);
				t.try_to_commit();
			}catch(Exception e){
				//System.out.println(e);
			}
		}
	}
	
	public static void increment2 (Register<Integer> x, Register<Integer> y){
		
		Transaction<Integer> t = new TL2Transaction<Integer>();

		while(!t.isCommited()) {
			try{
				t.begin();
				x.write(t, (Integer) x.read(t)+1);
				y.write(t, (Integer) y.read(t)+1);
				t.try_to_commit();
			}catch(Exception e){
				//e.printStackTrace();
			}
		}
	}
	
	public void run() {
		this.running = true;

		for(int i = 0; i < nbIt; ++i) {
			increment(r0);

		}
	}
	
	public void stop() {
		this.running = false;
	}
}