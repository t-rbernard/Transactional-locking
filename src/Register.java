
interface Register<T> {
	public T read ( Transaction<T> t) throws AbortException ;
	public void write ( Transaction<T> t , T v ) throws AbortException ;
	public int getDate();
	public T getValue();
	public void setValue(T v);
	public void setValueAndDate(T v, int d);
	public Register<T> copy();
	public int getId();
}