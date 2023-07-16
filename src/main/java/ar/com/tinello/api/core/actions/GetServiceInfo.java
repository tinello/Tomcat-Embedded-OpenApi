package ar.com.tinello.api.core.actions;

import java.sql.SQLException;

import javax.sql.DataSource;

public class GetServiceInfo {
  
  private final DataSource ds;

  public GetServiceInfo(DataSource ds) {
    this.ds = ds;
  }

  public void execute() {
    try (
      var conn = ds.getConnection();
      var stmt = conn.createStatement();
      var rs = stmt.executeQuery("select 1");
      ) {

        while(rs.next()){
          System.out.println(rs.getInt(1));
        }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}
