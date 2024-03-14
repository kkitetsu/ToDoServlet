package controller;

import java.sql.PreparedStatement;

public class ModifyDao extends Dao {
	
	public ModifyDao() {
		// Empty constructor
	}
	
	public int update(String id, String comment) throws Exception {
	    String sql = "UPDATE " + tableName + " SET contents=\"" + comment + "\"" + " WHERE id=" + id;
	    System.out.println(sql);
	    	
		connect();
		PreparedStatement statement = connection.prepareStatement(sql);
		int results = statement.executeUpdate();
		return results;
	}
	
	public int delete(String id) throws Exception {
		String sql = "UPDATE " + tableName + " SET updatedAt=CURRENT_TIMESTAMP, del_Flag=1 WHERE id=" + id;
	    System.out.println(sql);
		
		connect();
		PreparedStatement statement = connection.prepareStatement(sql);
		int results = statement.executeUpdate();
		return results;
	}
	
	public int insert(String title, String content) throws Exception {
		String sql = "INSERT INTO " + tableName + 
				" (title, contents, createdAt) VALUES (\"" + 
				   title + "\", \"" + content + "\", CURRENT_TIMESTAMP)";
	    System.out.println(sql);
		
	    connect();
		PreparedStatement statement = connection.prepareStatement(sql);
		int results = statement.executeUpdate();
		return results;
	}
	    
}