import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class TL2Transaction<T> implements Transaction<T>{

	private ArrayList<TL2Register<T>> lrs;
	private ArrayList<TL2Register<T>> lws;
	private ArrayList<TL2Register<T>> lcx;
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
		birthDate = clock.get();
	}
	
	public void try_to_commit() throws AbortException{
		/*
		Collections.sort(lws, new Comparator<TL2Register<T>>() {
	        @Override 
	        public int compare(TL2Register<T> r1, TL2Register<T> r2) {
	            return r1.getId() - r2.getId();
	        }
	    });
		
		Collections.sort(lrs, new Comparator<TL2Register<T>>() {
	        @Override 
	        public int compare(TL2Register<T> r1, TL2Register<T> r2) {
	            return r1.getId() - r2.getId();
	        }
	    });
		*/
		for(TL2Register<T> r1 : lws) 
			if(!r1.isLocked()) r1.lock();
		for(TL2Register<T> r2 : lrs) 
			if(!r2.isLocked()) r2.lock();
		
		
		for(TL2Register<T> register : lrs) {
			if(register.getDate() > birthDate) {
				for(TL2Register<T> r1 : lws) 
					if(r1.isLocked()) r1.unlock();
				for(TL2Register<T> r2 : lrs) 
					if(r2.isLocked()) r2.unlock();
				throw new AbortException();
			}
		}
		
		int commitDate = clock.getAndIncrement();
		for(Register<T> register : lws) {
			register.setValueAndDate(getLcx(register.getId()).getValue(), commitDate);
		}
		
		for(TL2Register<T> r1 : lws) 
			if(r1.isLocked()) r1.unlock();
		for(TL2Register<T> r2 : lrs) 
			if(r2.isLocked()) r2.unlock();
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
    
    public boolean registerIsPresent(Register<T> register) {
    	return lcx.contains(register);
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
	
	public void setLcxValue(Register<T> register, T value) {
		lcx.get(lcx.indexOf(register)).setValue(value);
	}
    
    public void addNewLcx(Register<T> register, T value) {
    	register.setValue(value);
    	lcx.add((TL2Register<T>) register);
    }
}
