package controller;

import java.sql.PreparedStatement;

public class ModifyDao extends Dao {
	
	public ModifyDao() {
		// Empty constructor
	}
	
	public int update(String id, String comment, String priority) throws Exception {
	    String sql = "UPDATE " + tableName + " SET contents=\"" + comment + "\"" + ", priority=" + priority + " WHERE id=" + id;
	    	
		connect();
		PreparedStatement statement = connection.prepareStatement(sql);
		return statement.executeUpdate();
	}
	
	public int delete(String id) throws Exception {
		String sql = "UPDATE " + tableName + " SET updatedAt=CURRENT_TIMESTAMP, del_Flag=1 WHERE id=" + id;
		
		connect();
		PreparedStatement statement = connection.prepareStatement(sql);
		return statement.executeUpdate();
	}
	
	public int insert(String title, String content, String priority) throws Exception {
		String sql = "INSERT INTO " + tableName + 
				" (title, contents, createdAt, priority) VALUES (\"" + 
				   title + "\", \"" + content + "\", CURRENT_TIMESTAMP, " + priority + ")";
		
	    connect();
		PreparedStatement statement = connection.prepareStatement(sql);
		return statement.executeUpdate();
	}
	    
}
