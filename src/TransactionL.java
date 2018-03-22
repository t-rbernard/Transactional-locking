package transaction;

public interface TransactionTemplate {
  public void begin();
  public void try_to_commit();
  public boolean isCommited();
  public int read(int reg);
  public void write(int reg);
}

public class IntegerTransaction implements TransactionTemplate {

  public int registers;
  private int birthDate_;
  private ArrayList<int> lc_;
  private ArrayList<boolean> lcF_;
  private ArrayList<int> lcD_;
  private ArrayList<ArrayList<int>> lrs_;
  private ArrayList<ArrayList<int>> lws_;

  public IntegerTransaction(int regs) {
    registers = regs;
    for (int i = 0; i < regs; i++) {
      lc_.push(0);
      lcF_.push(false);
      lrs_.push(ArrayList<int>);
      lws_.push(ArrayList<int>);
    }
  }

  public void begin() {
    birthDate_ = Main.clock;
    for (int i = 0; i < registers; i++) {
      lcD_.push(birthDate_);
    }
  }

  public int read(IntegerRegister reg) {
    if (lcF_[reg.getId()]) {
      return lc_[reg.getId()];
    } else {
      lc_[reg.getId()] = reg.getValue();
      lcD_[reg.getId()] = reg.getDate();
      lcF_[reg.getId()] = true;
      lrs_[reg.getId()].push(reg.getValue());
        if(lcD_[reg.getId()] > birthDate_) {
          // TODO EXCEPTION
        } else {
          return lcx_[reg.getId()];
        }
    }
  }

  public void write(IntegerRegister reg, int value) {
    lc_[reg.getId()] = value;
    lws_[reg.getId()].push(reg.getValue());
  }

  public try_to_commit() {

  }
}
