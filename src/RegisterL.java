package register;

public interface RegisterTemplate<T> {
  public int getId();
  public T getValue();
  public int getDate();
}

public class IntegerRegister implements RegisterTemplate<T> {

  private int id_;
  private int value_;
  private int date_;

  public IntegerRegister(int id) {
    id_ = id;
    value_ = 0;
    date_ = 0;
  }

  public int getId() {
    return id_;
  }

  public int getValue() {
    return value_;
  }

  public int getDate() {
    return date_;
  }
}
