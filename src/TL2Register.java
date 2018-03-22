import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TL2Register<T> extends ReentrantLock implements Register<T>{
	
	private static AtomicInteger idCounter = new AtomicInteger(0);
	private T value;
	private int date;
	private int id;
	
	public TL2Register() {
		this.value = null;
	}
	
	public TL2Register(T value) {
		this.value = value;
	}
	
	private TL2Register(T value, int date) {
		this.value = value;
		this.date = date;
		this.id = idCounter.getAndIncrement();
	}
	
	private TL2Register(T value, int date, int id) {
		this.value = value;
		this.date = date;
		this.id = id;
	}
	
	public T read (Transaction<T> t) throws AbortException{
		if(t.getLcx(id) != null) {
			return (T)t.getLcx(id).getValue();
		}else {
			t.addNewLcx(this.copy(), this.value);
			t.addLrs(this);

			if(t.getLcx(id).getDate() > t.getBirth()) {
				throw new AbortException();
			}else {
				return (T)t.getLcx(id).getValue();
			}
		}
	}
	
	public void write(Transaction<T> t, T v) throws AbortException {
		if(t.getLcx(id) == null){
			t.addNewLcx(this, v);
		}
		
		t.setLcxValue((TL2Register<T>)this, v);
		t.addLws(this);
	}
	
	public Register<T> copy() {
		return new TL2Register(this.value, this.date, this.id);
	}
	
	public int getDate() {
		return date;
	}
	
	public T getValue() {
		return (T) value;
	}
	
	public void setValue(T v) {
		value = v;
	}
	
	public void setValueAndDate(T v, int d) {
		value = v;
		date = d;
	}
	
	public int getId() {
		return id;
	}
	
	@Override
	public boolean equals(Object register) {
		if (register == null) return false;
	    if (register == this) return true;
	    if (!(register instanceof TL2Register<?>)) return false;
		TL2Register<T> r = (TL2Register<T>) register;
		return this.id == r.getId();
	}
}
	
