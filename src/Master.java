
public class Master {
	
	public static void increment (Register<Integer> x) {
		Transaction t = new TL2Transaction();
		while(!t.isCommited()) {
			try{
				t.begin();
				x.write(t, (Integer) x.read(t) + 1);
				System.out.println((String) x.read(t)); 
				t.try_to_commit(); 
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		
		Register<Integer> r = new TL2Register<Integer>();
		increment(r);
		
		increment(r);
		System.out.println(r);
	}
}
