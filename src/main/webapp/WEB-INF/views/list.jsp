<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ToDo App</title>
<style>
	*  { background-color: pink; }
	h1 { text-align: center; }
	body {
            font-family: Arial, sans-serif;
            margin: 30px;
            padding: 0;
    }
    #todo-list {
            display: flex;
            flex-wrap: wrap; /* Allow items to wrap to the next line */
            gap: 10px; /* Add some space between items */
    }
    .todo-item {
            border: 1px solid blue;
            border-radius: 5px;
            padding: 10px;
            width: 300px; /* Adjust the width as needed */
            box-sizing: border-box;
            overflow: hidden; /* Prevent content from overflowing */
    }
    .todo-item h3, .todo-item p { margin: 0; }
    a { text-align: center }
</style>
</head>
<body>
	<h1>Todo List</h1>
	<form action="todo" method="post">
		<input type="submit" value="Add" name="action">
	</form>
    <div id="todo-list">
        <% HashMap<Integer, ArrayList<String>> map = (HashMap<Integer, ArrayList<String>>)request.getAttribute("rows"); %>
        <% for (Map.Entry<Integer, ArrayList<String>> entry : map.entrySet()) { %>
        
        	<% ArrayList<String> value = entry.getValue(); %>
        	<% String color = value.get(5); %>
	        <% Integer id = entry.getKey(); %>
        	<div class="todo-item" style="border: 6px solid <%=color %>;">
                <h3>Title: <%=value.get(0) %></h3>
                <p>Content: <%=value.get(1) %></p> 
                <form action="todo" method="post">
	    			<input type="text"   value=""         name="comment">
	    			<input type="submit" value="Update"   name="action">
	    			<input type="hidden" value="<%=id %>" name="key">
	    			<input type="submit" value="Delete"   name="action">
   			    </form>
            </div>
        <% } %>
    </div>
</body>
</html>