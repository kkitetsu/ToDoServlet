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
	
	/**
	 * Select the data from table.
	 * 
	 * @param order AscendingTime, DescendingTime, AscendingPri, DescendingPri, or nothing (default)
	 * @return HashMap consists of each data, in int id and string list data value
	 * @throws Exception
	 */
	public HashMap<Integer, ArrayList<String>> select(String order) throws Exception {
		
    	PreparedStatement statement = null;
    	ResultSet results = null;
    	String sql = "SELECT * FROM " + tableName + " WHERE del_Flag=0 " + order;
    	
    	LinkedHashMap<Integer, ArrayList<String>> l = new LinkedHashMap<Integer, ArrayList<String>>();
    
    	connect();
    	statement = connection.prepareStatement(sql);
    	results = statement.executeQuery();
    	
    	while (results.next()) {
    		ArrayList<String> row = new ArrayList<String>();
			row.add(results.getString("title"));
			row.add(results.getString("contents"));
			row.add(results.getString("createdAt"));
			row.add(results.getString("updatedAt"));
			row.add(results.getString("del_Flag"));
			
			int priority = results.getInt("priority");
			if (priority == 1) {
				row.add("green");
			} else if (priority == 2) {
				row.add("orange");
			} else {
				row.add("red");
			}
			
    		int id = results.getInt("id");
    		l.put(id, row);
    	}
    	
    	return l;
    
    }
	
}
