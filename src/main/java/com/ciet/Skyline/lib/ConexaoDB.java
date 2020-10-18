package com.ciet.Skyline.lib;
import java.sql.*;

//https://www.devmedia.com.br/classe-generica-para-conexao-com-postgresql-e-mysql/5492
public class ConexaoDB {

        public String local = "localhost";
	public String user = "skyline_user";
	public String passwd = "12345";
	public Connection connection;
	public Statement statment;
	public String str_connection = "jdbc:postgresql://localhost:5432/SkylineDB";
	public String driverjdbc = "org.postgresql.Driver";

	public void connect(){
            try {
                Class.forName(driverjdbc);
                this.setConnection(DriverManager.getConnection(str_connection, user, passwd));
                this.setStatment(connection.createStatement());
                
            }catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            }
	}

	public void disconnect(){
            try { getConnection().close(); }
            catch (SQLException ex) {
                System.out.println(ex);
                ex.printStackTrace();
            }
	}

	public ResultSet query(String query){
		try { 
                    return getStatment().executeQuery(query);
                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                    return null;
		}
	}
        
        public int update(String query){
		try { 
                    return getStatment().executeUpdate(query);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                    return 0;
		}
	}

	public String getLocal() { return this.local; }
	public void setLocal(String local) { this.local = local; }

	public String getUser() { return this.user; }
	public void setUser(String user) { this.user = user; }

	public String getPasswd() { return this.passwd; }
	public void setPasswd(String passwd) { this.passwd = passwd; }

	public Connection getConnection() { return this.connection; }
	public void setConnection(Connection connection) { this.connection = connection; }

	public Statement getStatment() { return this.statment; }
	public void setStatment(Statement statment) { this.statment = statment; }

	public String getStr_connection() { return this.str_connection; }
	public void setStr_connection(String str_connection) { this.str_connection = str_connection; }

	public String getDriverjdbc() { return this.driverjdbc; }
	public void setDriverjdbc(String driverjdbc) { this.driverjdbc = driverjdbc; }
}
