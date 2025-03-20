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

  public String getEnv(String name, String defaultValue) {
    try {
      return getEnvRequired(name);
    } catch (Exception e) {
      return defaultValue;
    }
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

  public String getOtelServiceName() {
    try {
      return getEnvRequired("OTEL_SERVICE_NAME");
    } catch (Exception e) {
      return "tomcat-api";
    }
  }

  public String getOtelGrpcEndpoint() {
    try {
      return getEnvRequired("OTEL_GRPC_ENDPOINT");
    } catch (Exception e) {
      return "http://localhost:4317";
    }
  }
}

