package ar.com.tinello.api.web;

public class ServerException extends RuntimeException {

  public ServerException(Exception e) {
    super(e);
  }
  
}
