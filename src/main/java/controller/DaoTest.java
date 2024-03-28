package controller;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

class DaoTest extends Dao {
	
	PreparedStatement statement = null;
	ResultSet results = null;

	@Test
	public void testDelete() throws Exception {
		ModifyDao dao = new ModifyDao();
		dao.delete("104"); 					  // delete id=104
	    assertEquals("1", getDelFlag("104")); // check if the del_Flag=1 for id=104 
	}
	
	@Test
	public void testDeleteNonExist() throws Exception {
		ModifyDao dao = new ModifyDao();
		int result = dao.delete("0");
		assertEquals("", getDelFlag("0"));    // id=0 does not exist, thus the obtained data should be an empty
		assertEquals(0, result);              // id=0 does not exist, thus the returned num of row should be 0
		assertEquals("1", getDelFlag("104")); // deleting nonexisting data does not alter the other data
		assertEquals("0", getDelFlag("110")); // deleting nonexisting data does not alter the other data
	}
	
	@Test
	public void testUpdateComment() throws Exception {
		ModifyDao dao = new ModifyDao();
		int result = dao.update("104", "xxx", "1");
		assertEquals("xxx", getCommentAndPri("104").get(0)); // comment should be updated to "xxx"
		assertEquals(1, result);
	}
	
	@Test
	public void testUpdatePriority() throws Exception {
		ModifyDao dao = new ModifyDao();
		int result = dao.update("104", "xxx", "3");
		assertEquals("3", getCommentAndPri("104").get(1)); // priority should be updated to 3
		assertEquals(1, result);
	}
	
	@Test 
	public void testUpdateCommentExceed() throws Exception {
		ModifyDao dao = new ModifyDao();
		Exception exception = assertThrows(Exception.class, () -> {
			 dao.update("104", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" + 
							   "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" + 
							   "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" + 
							   "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" + 
							   "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" + 
							   "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" + 
							   "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" + 
							   "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" + 
							   "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" + 
							   "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" + 
							   "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", "1");
		});
		assertEquals(MysqlDataTruncation.class, exception.getClass());
	}
	
	@Test 
	public void testUpdateNullPriority() throws Exception {
		ModifyDao dao = new ModifyDao();
		Exception exception = assertThrows(Exception.class, () -> {
			dao.update("104", "xxx", ""); // priority "" does not make any sense, thus SQL cannot recognize this syntax
		});
		assertEquals(SQLSyntaxErrorException.class, exception.getClass());
	}
	
	public String getDelFlag(String id) throws Exception {
		String sql = "SELECT del_Flag FROM taskInfoTable WHERE " + "id=" + id;
		connect();
    	statement = connection.prepareStatement(sql);
    	results = statement.executeQuery();
    	String toReturn = "";
    	while (results.next()) {
    		toReturn = results.getString("del_Flag");
    	}
    	return toReturn;
	}
	
	public List<String> getCommentAndPri(String id) throws Exception {
		String sql = "SELECT contents, priority FROM taskInfoTable WHERE " + "id=" + id;
		connect();
    	statement = connection.prepareStatement(sql);
    	results = statement.executeQuery();
    	List<String> toReturn = new ArrayList<String>();
    	while (results.next()) {
    		toReturn.add(results.getString("contents"));
    		toReturn.add(results.getString("priority"));
    	}
    	return toReturn;
	}

}
