interface Transaction<T> {
	
	public void begin () ;
	public void try_to_commit () throws AbortException ;
	public boolean isCommited () ;
	public void addLws(Register<T> r);
	public void addLrs(Register<T> r);
	public int getBirth();
	public boolean setLcx(Register<T> register);
	public void setLcxValue(int id, T value);
	public Register<T> getLcx(int id);
	public void addNewLcx(Register<T> register, T  value);
	public void addNewLcx(Register<T> register);
}