/* This object have been created for the only purpose of debugging
 * It the implementation of the method increment present in the project's subject
 * A second method, increment2 have been created for testing if the implementation of 
 * the function try_to_commit is deadlock-free
 * 
 * @author Romain Bernard, Loïc Boutin, Ivan Dromigny
 * 
 */


public class TL2Increment implements Runnable{
	
	private volatile boolean running;
	Register<Integer> r0;
	Register<Integer> r1;
	int nbIt;
	int choice;

	public TL2Increment(Register<Integer> x, int it) {
		r0 = x;
		nbIt = it;
	}

	public TL2Increment(Register<Integer> x, Register<Integer> y, int it) {
		r0 = x;
		r1 = y;
		nbIt = it;
	}
	
	public static void increment(Register<Integer> x){
		
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
	
	public static void increment2(Register<Integer> x, Register<Integer> y){
		
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
		
		if(r1 == null) {
			for(int i = 0; i < nbIt; ++i) {
				increment(r0);
			}
		} else {
			for(int i = 0; i < nbIt; ++i) {
				increment2(r0, r1);
				increment2(r1, r0);
			}
		}
	}
	
	public void stop() {
		this.running = false;
	}
}