import java.util.Date;

interface Transaction<T> {
	
	public void begin () ;
	public void try_to_commit () throws AbortException ;
	public boolean isCommited () ;
	public void addLws(Register<T> r);
	public void addLrs(Register<T> r);
	public int getBirth();
	public Register<T> getLcx();
	public void setLcx(Register<T> l);
}