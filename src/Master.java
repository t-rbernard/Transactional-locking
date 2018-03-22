
public class Master {
	
	public static void increment (Register<Integer> x) {
		Transaction<Integer> t = new TL2Transaction<Integer>();
		while(!t.isCommited()) {
			try{
				t.begin();
				x.write(t, (Integer) x.read(t)+1);
				t.try_to_commit();
				System.out.println(Integer.toString(x.read(t))); 
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		
		Register<Integer> r = new TL2Register<Integer>(1);
		increment(r);
		increment(r);
		increment(r);
	}
}
