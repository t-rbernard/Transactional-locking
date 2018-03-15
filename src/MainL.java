public static void Main(String args[]) {
  public static AtomicInteger clock = 0;

  IntegerRegister r1 = new IntegerRegister(0);
  IntegerRegister r2 = new IntegerRegister(1);
  IntegerRegister r3 = new IntegerRegister(2);

  IntegerTransaction t1 = new IntegerTransaction(3);
  IntegerTransaction t2 = new IntegerTransaction(3);
  IntegerTransaction t3 = new IntegerTransaction(3);
}
