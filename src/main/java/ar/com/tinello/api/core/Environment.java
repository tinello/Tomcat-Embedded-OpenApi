package ar.com.tinello.api.core;


public class Environment {

  private final String dbUrl;
  private final String dbUser;
  private final String dbPass;

  public Environment() {
    this.dbUrl = getEnvRequired("DB_URL");
    this.dbUser = getEnvRequired("DB_USER");
    this.dbPass = getEnvRequired("DB_PASS");
  }

  private String getEnvRequired(String name) {
    final var env = System.getenv(name);
    if (env == null) {
      throw new RuntimeException("The " + name + " environment, does not exist.");
    }
    return env;
  }

  public String getDbUrl() {
    return this.dbUrl;
  }

  public String getDbUser() {
    return this.dbUser;
  }

  public String getDbPass() {
    return this.dbPass;
  }
}

