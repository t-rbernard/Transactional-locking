import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TL2Register extends ReentrantLock implements Register<int>{

	private static AtomicInteger idCounter;
	private int id;
	private int value;
	private int date;

	public TL2Register() {
		value = null;
	}

	private TL2Register(int value, int date) {
		this.value = value;
		this.date = date;
		this.id = idCounter.getAndIncrement();
	}

	public int read (Transaction t) throws AbortException{
		if(t.getLcx() != null) {
			return t.getLcx().getValue();
		}else {
			t.setLcx(this.copy());
			t.addLrs(this.copy);
			if(t.getLcx().getDate() > t.getBirth()) {
				throw new AbortException();
			}else {
				return t.getLcx(this).getValue();
			}
		}
	}

	public void write(Transaction t, int v) throws AbortException {
		t.setLcx(this, v);
		t.addLws(this.copy());
	}

	public TL2Register copy() {
		return new TL2Register(this.value, this.date);
	}

	public int getDate() {
		return date;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int v) {
		value = v;
	}

	public void setValueAndDate(int v, int d) {
		value = v;
		date = d;
	}

	public boolean equals(Object register) {
		return
	}
}
