package controller;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.http.HttpSession;

public abstract class Dao {
	protected String url       = "jdbc:mysql://localhost/taskInfoDB";
    protected String user      = "root";
    protected String password  = "password";
    protected String tableName = "taskInfoTable";
    protected Connection connection; 
    protected HttpSession session;
    
    public void connect() throws Exception {
    	Class.forName("com.mysql.jdbc.Driver");
    	connection = DriverManager.getConnection(url, user, password);
    }
    
}
