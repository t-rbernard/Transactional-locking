
interface Register<T> {
	public <T> T read ( Transaction t) throws AbortException ;
	public void write ( Transaction t , T v ) throws AbortException ;
	public int getDate();
	public <T> T getValue();
	public void setValue(T v);
	public void setValueAndDate(T v, int d);
}