package com.codegym.dao;
import com.codegym.model.User; import java.sql.*; import java.util.*;
public class UserDAO implements IUserDAO {
 private static final String URL="jdbc:mysql://localhost:3306/user_management?useSSL=false&serverTimezone=UTC"; private static final String USER="root"; private static final String PASSWORD="";
 static { try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch (ClassNotFoundException e) { throw new ExceptionInInitializerError(e); } }
 private Connection connect() throws SQLException { return DriverManager.getConnection(URL,USER,PASSWORD); }
  public List<User> selectUsers(String country,String sort)throws SQLException { List<User> out=new ArrayList<>(); try(Connection c=connect();CallableStatement p=c.prepareCall("{call get_all_users(?,?)}")){p.setString(1,country==null?"":country.trim());p.setString(2,sort);try(ResultSet r=p.executeQuery()){while(r.next())out.add(new User(r.getInt("id"),r.getString("name"),r.getString("email"),r.getString("country")));}} return out; }

 public User findById(int id)throws SQLException { try(Connection c=connect();PreparedStatement p=c.prepareStatement("SELECT id,name,email,country FROM users WHERE id=?")){p.setInt(1,id);try(ResultSet r=p.executeQuery()){return r.next()?new User(r.getInt(1),r.getString(2),r.getString(3),r.getString(4)):null;}} }
 public void insert(User u)throws SQLException { try(Connection c=connect();PreparedStatement p=c.prepareStatement("INSERT INTO users(name,email,country) VALUES(?,?,?)")){setUser(p,u);p.executeUpdate();} }
  public void update(User u)throws SQLException { try(Connection c=connect();CallableStatement p=c.prepareCall("{call update_user(?,?,?,?)}")){p.setInt(1,u.getId());p.setString(2,u.getName());p.setString(3,u.getEmail());p.setString(4,u.getCountry());p.executeUpdate();} }
  public void delete(int id)throws SQLException { try(Connection c=connect();CallableStatement p=c.prepareCall("{call delete_user(?)}")){p.setInt(1,id);p.executeUpdate();} }
 private void setUser(PreparedStatement p,User u)throws SQLException {p.setString(1,u.getName());p.setString(2,u.getEmail());p.setString(3,u.getCountry());}
 public void insertUpdateWithoutTransaction() throws SQLException {
  String insertSql="INSERT INTO Employee (name,salary,created_Date) VALUES (?,?,?)";
  String updateSql="UPDATE Employee SET salary=? WHERE name=?";
  String dropSql="DROP TABLE IF EXISTS Employee";
  String createSql="CREATE TABLE Employee (id INT AUTO_INCREMENT PRIMARY KEY,name VARCHAR(120) NOT NULL,salary DECIMAL(15,2) NOT NULL,created_Date TIMESTAMP)";
  try(Connection c=connect();Statement st=c.createStatement();PreparedStatement insert=c.prepareStatement(insertSql);PreparedStatement update=c.prepareStatement(updateSql)) {
   st.execute(dropSql); st.execute(createSql);
   insert.setString(1,"Quynh"); insert.setBigDecimal(2,new java.math.BigDecimal("10")); insert.setTimestamp(3,java.sql.Timestamp.valueOf(java.time.LocalDateTime.now())); insert.executeUpdate();
   insert.setString(1,"Ngan"); insert.setBigDecimal(2,new java.math.BigDecimal("20")); insert.setTimestamp(3,java.sql.Timestamp.valueOf(java.time.LocalDateTime.now())); insert.executeUpdate();
   update.setBigDecimal(2,new java.math.BigDecimal("999.99")); update.setString(2,"Quynh"); update.executeUpdate();
  } catch(SQLException e) { System.out.println("Ã„ÂÃƒÂ£ bÃ¡ÂºÂ¯t Ã„â€˜Ã†Â°Ã¡Â»Â£c lÃ¡Â»â€”i trong quÃƒÂ¡ trÃƒÂ¬nh thÃ¡Â»Â±c thi SQL:"); e.printStackTrace(); }
  }
  public void insertUpdateUseTransaction() throws SQLException {
   String insertSql="INSERT INTO Employee (name,salary,created_Date) VALUES (?,?,?)";
   String updateSql="UPDATE Employee SET salary=? WHERE name=?";
   try(Connection c=connect();PreparedStatement insert=c.prepareStatement(insertSql);PreparedStatement update=c.prepareStatement(updateSql)) {
    c.setAutoCommit(false);
    try {
     insert.setString(1,"Quynh"); insert.setBigDecimal(2,new java.math.BigDecimal("10")); insert.setTimestamp(3,java.sql.Timestamp.valueOf(java.time.LocalDateTime.now())); insert.executeUpdate();
     insert.setString(1,"Ngan"); insert.setBigDecimal(2,new java.math.BigDecimal("20")); insert.setTimestamp(3,java.sql.Timestamp.valueOf(java.time.LocalDateTime.now())); insert.executeUpdate();
     // CÃ¡Â»â€˜ tÃƒÂ¬nh sai index Ã„â€˜Ã¡Â»Æ’ lÃ¡ÂºÂ§n chÃ¡ÂºÂ¡y Ã„â€˜Ã¡ÂºÂ§u kiÃ¡Â»Æ’m chÃ¡Â»Â©ng rollback. Ã„ÂÃ¡Â»â€¢i 2 thÃƒÂ nh 1 Ã„â€˜Ã¡Â»Æ’ commit thÃƒÂ nh cÃƒÂ´ng.
     update.setBigDecimal(2,new java.math.BigDecimal("999.99")); update.setString(2,"Quynh"); update.executeUpdate();
     c.commit();
    } catch(SQLException e) {
     try { c.rollback(); } catch(SQLException rollback) { e.addSuppressed(rollback); }
     System.out.println("LÃ¡Â»â€”i xÃ¡ÂºÂ£y ra, Transaction Ã„â€˜ÃƒÂ£ rollback: "+e.getMessage());
    } finally {
     try { c.setAutoCommit(true); } catch(SQLException ignored) { }
    }
   }
  }

  public void addUserTransaction(User u,int[] permissionIds)throws SQLException { try(Connection c=connect()){c.setAutoCommit(false);try{int userId;try(PreparedStatement p=c.prepareStatement("INSERT INTO users(name,email,country) VALUES(?,?,?)",Statement.RETURN_GENERATED_KEYS)){setUser(p,u);p.executeUpdate();try(ResultSet r=p.getGeneratedKeys()){if(!r.next())throw new SQLException("Could not obtain generated user id");userId=r.getInt(1);}}if(permissionIds!=null){try(PreparedStatement p=c.prepareStatement("INSERT INTO user_permission(user_id,permission_id) VALUES(?,?)")){for(int ignored:permissionIds){p.setInt(1,userId);p.setInt(2,999999);p.executeUpdate();}}}c.commit();}catch(SQLException e){try{c.rollback();}catch(SQLException rollback){e.addSuppressed(rollback);}throw e;}finally{try{c.setAutoCommit(true);}catch(SQLException ignored){}}}}
}

