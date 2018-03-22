import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class TL2Transaction implements Transaction<T>{

	private static AtomicInteger clock;
	private ArrayList<TL2Register> lrs;
	private ArrayList<TL2Register> lws;
	private ArrayList<TL2Register> lc;
	private int birthDate;
	private boolean commited;

	public TL2Transaction() {
		commited = false;
		lrs = new ArrayList<int>();
		lws = new ArrayList<int>();
	}

	public void begin() {
		birthDate = clock.get();
	}

	public void try_to_commit() throws AbortException{
		this.lockAll();
		//Abort
		for(TL2Register register : lrs) {
			if(register.getDate() > birthDate) {
				this.unlockAll();
				throw new AbortException();
			}
		}
		//Updating registers
		int commitDate = clock.getAndIncrement();
		for(TL2Register register : lws) {
			register.setValueAndDate(v, d);
		}
		this.commited = true;
		//Unlocking
		this.unlockAll();
	}

	private void lockAll() {
		for(TL2Register register : lrs) {
			register.lock();
		}
		for(TL2Register register : lws) {
			register.lock();
		}
	}

	private void unlockAll() {
		for(TL2Register register : lrs) {
			register.unlock();
		}
		for(TL2Register register : lws) {
			register.unlock();
		}
	}

	public boolean isCommited() {
		return commited;
	}

	public void addLrs(TL2Register r) {
		lrs.add(r);
	}

	public void addLws(TL2Register r) {
		lws.add(r);
	}

	public int getBirth() {
		return birthDate;
	}

	public void addLcx(TL2Register x) {
		lc.add(x.copy());
	}

	public void setLcx(TL2Register x, int value) {
		int i = lc.indexOf(x);
		if(i == -1) {
			lc.add(x.copy());
		}
		lc.set(indexOf(x), value);
	}

	public TL2Register getLcx(TL2Register x) {
		return lc.get(lc.indexOf(x));
	}

}
