package com.ciet.Skyline.lib;
import java.sql.*;

//https://www.devmedia.com.br/classe-generica-para-conexao-com-postgresql-e-mysql/5492
public class ConexaoDB {

    private String local;
	private String user;
	private String passwd;
	private Connection connection;
	private Statement statment;
	private String str_connection;
	private String driverjdbc;

	public ConexaoDB(String _local, String _port, String _database, String _user, String _passwd) {
            this.setStr_connection("jdbc:postgresql://"+ local +":" + _port +"/"+ _database);
            this.setLocal(_local);
            this.setUser(_user);
            this.setPasswd(_passwd);
            this.setDriverjdbc("org.postgresql.Driver");
  	}

	public void connect(){
            try {
                Class.forName(this.getDriverjdbc());
                this.setConnection(DriverManager.getConnection(this.getStr_connection(), this.getUser(), this.getPasswd()));
                this.setStatment(this.getConnection().createStatement());
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
		try { return getStatment().executeQuery(query); }
                catch (SQLException ex) {
                    ex.printStackTrace();
                    return null;
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
