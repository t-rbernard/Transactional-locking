import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class TL2Transaction<T> implements Transaction<T>{

	private ArrayList<TL2Register<T>> lrs;
	private ArrayList<TL2Register<T>> lws;
	public ArrayList<TL2Register<T>> lcx;
	private int birthDate;
	private static AtomicInteger clock = new AtomicInteger(0);
	private boolean commited;
	
	public TL2Transaction() {
		lcx = new ArrayList<TL2Register<T>>();
		lrs = new ArrayList<TL2Register<T>>();
		lws = new ArrayList<TL2Register<T>>();
		commited = false;
	}
	
	public void begin() {
		lcx.clear();
		lrs.clear();
		lws.clear();
		birthDate = clock.get();
		commited = false;
	}
	
	public void try_to_commit() throws AbortException{
		
		ArrayList<TL2Register<T>> allRegisters = new ArrayList<TL2Register<T>>(lws);
		allRegisters.addAll(lrs);
		
		Collections.sort(allRegisters, new Comparator<TL2Register<T>>() {
	        @Override 
	        public int compare(TL2Register<T> r1, TL2Register<T> r2) {
	            return r1.getId() - r2.getId();
	        }
	    });

		for(TL2Register<T> register : allRegisters) {
			//System.out.println(this + " try to lock the registers");
			register.lock();
			//System.out.println(this + " registers are locked : " + registre.isHeldByCurrentThread());
		}
		
		
		for(TL2Register<T> lr : lrs) {
			if(lr.getDate() > birthDate) {
				for(TL2Register<T> register : allRegisters) 
					if(register.isHeldByCurrentThread()) register.unlock();
				commited = false;
				throw new AbortException(this + " Aboooooooooooooooort Commit");
			}
		}
		
		int commitDate = clock.incrementAndGet();
		
		for(Register<T> register : lws) {
			register.setValueAndDate(getLcx(register.getId()).getValue(), commitDate);
		}
		
		for(TL2Register<T> register : allRegisters) 
			if(register.isHeldByCurrentThread()) register.unlock();
		this.commited = true;
	}
	
	public boolean isCommited() {
		return commited;
	}
	
	public void addLrs(Register<T> r) {
		lrs.add((TL2Register<T>)r);
	}
	
	public void addLws(Register<T> r) {
		lws.add((TL2Register<T>)r);
	}
	
	public int getBirth() {
		return birthDate;
	}
    
    public Register<T> getLcx(int id){
    	for(int i = 0; i < lcx.size(); ++i){
    		if(lcx.get(i).getId() == id) {
    			return lcx.get(i);
    		}
    	}
    	return null;
    }
    
	public boolean setLcx(Register<T> register) {
		for(int i = 0; i < lcx.size(); ++i){
    		if(lcx.get(i).getId() == register.getId()) {
    			lcx.set(i, (TL2Register<T>)register);
    			return true;
    		}
    	}
		return false;
	}
	
	public void setLcxValue(int id, T value) {
		for(int i = 0; i < lcx.size(); ++i){
    		if(lcx.get(i).getId() == id) {
    			lcx.get(i).setValue(value);
    			return;
			}
		}
	}
    
    public void addNewLcx(Register<T> register, T value) {
    	register.setValue(value);
    	lcx.add((TL2Register<T>) register);
    }
    
    public void addNewLcx(Register<T> register) {
    	lcx.add((TL2Register<T>) register);
    }
}
