import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class TL2Transaction<T> implements Transaction<T>{

	private ArrayList<Register<T>> lrs;
	private ArrayList<Register<T>> lws;
	private int birthDate;
	private static AtomicInteger clock;
	private TL2Register lcx;
	
	public TL2Transaction() {
		
		lrs = new ArrayList<Register<T>>();
		lws = new ArrayList<Register<T>>();
	}
	
	public void begin() {
		birthDate = clock.get();
	}
	
	public void try_to_commit() throws AbortException{
		
		for(Register<T> register : lrs) {
			if(register.getDate() > birthDate) {
				
				throw new AbortException();
			}
		}
		int commitDate = clock.getAndIncrement();
		for(Register<T> register : lws) {
			register.setValueAndDate(v, d);
		}
	}
	
	public boolean isCommited() {
		return true;
	}
	
	public void addLrs(Register<T> r) {
		lrs.add(r);
	}
	
	public void addLws(Register<T> r) {
		lws.add(r);
	}
	
	public int getBirth() {
		return birthDate;
	}
	
    public <T> ArrayList<T> union(ArrayList<T> list1, ArrayList<T> list2) {
        Set<T> set = new HashSet<T>();

        set.addAll(list1);
        set.addAll(list2);

        return new ArrayList<T>(set);
    }
    
    public Register<T> getLcx() {
    	return lcx;
    }
    
    public void setLcx(Register<T> l) {
    	lcx = (TL2Register) l;
    }
}
