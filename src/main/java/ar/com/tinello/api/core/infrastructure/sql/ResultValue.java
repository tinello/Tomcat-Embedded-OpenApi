package ar.com.tinello.api.core.infrastructure.sql;

public class ResultValue {
  
  private final Object value;

  public ResultValue(final Object value) {
    this.value = value;
  }

  public int getInt() {
    return Integer.valueOf(value.toString());
  }

  public String getString() {
    return value.toString();
  }

  public boolean getBoolean() {
    return Boolean.valueOf(value.toString());
  }

}
