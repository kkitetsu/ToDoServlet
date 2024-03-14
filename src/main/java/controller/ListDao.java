package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ListDao extends Dao {
	ListDao() {
		// Empty constructor
	}
	
	public HashMap<Integer, ArrayList<String>> select(boolean isOrderTime) throws Exception {
    	PreparedStatement statement = null;
    	ResultSet results = null;
    	String sql = "";
    	if (isOrderTime) {
    		sql = "SELECT * FROM " + tableName + " WHERE del_Flag=0 ORDER BY createdAt";
    	} else {
    		sql = "SELECT * FROM " + tableName + " WHERE del_Flag=0";
    	}
    	
    	LinkedHashMap<Integer, ArrayList<String>> l = new LinkedHashMap<Integer, ArrayList<String>>();
    
    	connect();
    	statement = connection.prepareStatement(sql);
    	results = statement.executeQuery();
    	
    	while (results.next()) {
    		ArrayList<String> row = new ArrayList<String>();
    		for (int i = 0; i < 5; i++) {
    			row.add(results.getString("title"));
    			row.add(results.getString("contents"));
    			row.add(results.getString("createdAt"));
    			row.add(results.getString("updatedAt"));
    			row.add(results.getString("del_Flag"));
    			String priority = results.getString("priority");
    			if (priority.equals("LOW")) {
    				row.add("green");
    			} else if (priority.equals("MEDIUM")) {
    				row.add("orange");
    			} else {
    				row.add("red");
    			}
    		}
    		int id = results.getInt("id");
    		l.put(id, row);
    	}
    	
    	return l;
    
    }
	
}
