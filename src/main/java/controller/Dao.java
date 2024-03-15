package controller;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class Dao {
	protected String url       = "jdbc:mysql://localhost/taskInfoDB";
    protected String user      = "root";
    protected String password  = "password";
    protected String tableName = "taskInfoTable";
    protected Connection connection; 
    
    final String ASCTIME = "AscendingTime";
    final String DECTIME = "DescendingTime";
    final String ASCPRI  = "AscendingPri";
    final String DECPRI  = "DescendingPri";
    
    public void connect() throws Exception {
    	Class.forName("com.mysql.jdbc.Driver");
    	connection = DriverManager.getConnection(url, user, password);
    }
    
}
