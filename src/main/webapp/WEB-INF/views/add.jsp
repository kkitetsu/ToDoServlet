<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add new todo</title>
<style>
	* { background-color: pink; }
	
	.container {
	    display: flex;
	    justify-content: center; 
	    border: solid 1px yellow;
	}
	
	form { width: 300px; }
	
	form div { margin-bottom: 10px; }
	
	label {
	    display: block; 
	    margin-bottom: 5px; 
	}
	
	input { border: solid 1px blue; }
</style>
</head>
<body>
<div class="container">
	<form action="todo" method="post">
	    <div>
	        <label for="title">Title:</label>
	        <input type="text" id="title" name="title" value="">
	    </div>
	    <div>
	        <label for="content">Content:</label>
	        <input type="text" id="content" name="content" value="">
	    </div>
	    <div>
	        <input type="submit" value="AddNew" name="action">
	    </div>
	</form>
</div>
</body>
</html>