import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TL2Register<T> extends ReentrantLock implements Register<T>{

	private T value;
	private int date;
	private Lock lock;
	
	public TL2Register() {
		value = null;
	}
	
	private TL2Register(T value, int date) {
		this.value = value;
		this.date = date;
	}
	
	public <T> T read (Transaction t) throws AbortException{
		if(t.getLcx() != null) {
			return (T)t.getLcx().getValue();
		}else {
			t.setLcx(this.copy());
			t.addLrs(this);
			if(t.getLcx().getDate() > t.getBirth()) {
				throw new AbortException();
			}else {
				return (T)t.getLcx().getValue();
			}
		}
	}
	
	public void write(Transaction t, T v) throws AbortException {
		t.getLcx().setValue(v);
		t.addLws(this);
	}
	
	public TL2Register copy() {
		return new TL2Register(this.value, this.date);
	}
	
	public int getDate() {
		return date;
	}
	
	public <T> T getValue() {
		return (T) value;
	}
	
	public void setValue(T v) {
		value = v;
	}
	
	public void setValueAndDate(T v, int d) {
		value = v;
		date = d;
	}
}
	
