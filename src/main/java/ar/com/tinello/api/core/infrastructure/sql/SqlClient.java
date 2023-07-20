package ar.com.tinello.api.core.infrastructure.sql;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class SqlClient {
  
  private final DataSource ds;

  public SqlClient(final DataSource ds) {
    this.ds = ds;
  }

  public List<List<ResultValue>> query(final String query, final Object... args) {
    
    final var result = new ArrayList<List<ResultValue>>();

    try (
      final var conn = ds.getConnection();
      final var stmt = conn.prepareStatement(query); 
      ) {

        for (int i = 0; i < args.length; i++) {
          setStatementParam(stmt, args[i], i+1);
        }
        
        try (final var rs = stmt.executeQuery();) {
          while(rs.next()){
            int column = 1;
            final var values = new ArrayList<ResultValue>();
            boolean haveColumn = true;
            while (haveColumn) {
              try {
                values.add(new ResultValue(rs.getObject(column)));
                column++;  
              } catch (Exception e) {
                haveColumn = false;
              }
            }
            result.add(values);
          }
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return result;
  }


  private void setStatementParam(final PreparedStatement stmt, final Object arg, final int index) throws SQLException {
    if (arg instanceof Integer val) {
      stmt.setInt(index, val);
    } else if (arg instanceof Long val) {
      stmt.setLong(index, val);
    } else if (arg instanceof String val) {
      stmt.setString(index, val);
    } else if (arg instanceof Float val) {
      stmt.setFloat(index, val);
    } else if (arg instanceof BigDecimal val) {
      stmt.setBigDecimal(index, val);
    } else if (arg instanceof Boolean val) {
      stmt.setBoolean(index, val);
    } else {
      stmt.setNull(index, Types.NULL);
    }
  }

}
