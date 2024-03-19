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
            gap: 20px;
            grid-template-columns: repeat(auto-fit, minmax(200px, ifr));
    }
    #todo-list {
            display: flex;
            flex-wrap: wrap; 
            justify-content: center;
            gap: 15px; 
    }
    .todo-item {
            border: 1px solid blue;
            border-radius: 5px;
            padding: 10px;
            width: 300px; 
            height: 180px;
            box-sizing: border-box;
            overflow: hidden; 
    }
    .todo-item h3, .todo-item p { margin: 0; }
    .add_btn { text-align: center; margin: 20px; }
    .addTodo_btn { margin: 10px; }
    
    /* Code provided by ChatGPT BELOW CSS */
	.front, .back {
	  backface-visibility: hidden;
	  transition: transform 0.5s;
	}
	
	.front {
	  display: flex;
	  flex-direction: column;
	  justify-content: center;
	  align-items: center;
	}
	
	.back {
	  display: flex;
	  justify-content: center;
	  align-items: center;
	  transform: rotateY(180deg);
	}
	
	.todo-item.flipped .front {
	  transform: rotateY(180deg);
	}
	
	.todo-item.flipped .back {
	  transform: rotateY(0deg);
	}
    
</style>
</head>
<body>
	<h1>Todo List</h1>
	<div class="add_btn">
		<form action="todo" method="post">
			<input type="submit" value="Add Todo" name="action" class="addTodo_btn">
			<div>
				<label for="Time Order">時間並び替え:</label>
			    <select id="priority" name="action">
			        <option value="AscendingTime">古い順</option>
			        <option value="DescendingTime">新しい順</option>
			    </select>
			    <input type="submit" value="Sort Time" name="action">
		    </div>
		</form>
		<form action="todo" method="post">
			<div>
				<label for="priority">優先度並び替え:</label>
			    <select id="priority" name="action">
			        <option value="AscendingPri">低い順</option>
			        <option value="DescendingPri">高い順</option>
			    </select>
			    <input type="submit" value="Sort Priority" name="action">
		    </div>
		</form>
	</div>
    <div id="todo-list">
    	<% // ----------------------------------------------------------------- %>
        <% HashMap<Integer, ArrayList<String>> map = (HashMap<Integer, ArrayList<String>>)request.getAttribute("rows"); %>
        <% for (Map.Entry<Integer, ArrayList<String>> entry : map.entrySet()) { %>
        
        	<% ArrayList<String> value = entry.getValue(); %>
        	<% String color = value.get(5); %>
	        <% Integer id = entry.getKey(); %>
        	<div class="todo-item" style="border: 6px solid <%=color %>; " onclick="toggleCard(this)">
        		<div class="front">
	                <h3>Title: <%=value.get(0) %></h3>
	                <p>Content: <%=value.get(1) %></p> 
	                <h6>Created Time: <%=value.get(2) %></h6> 
                </div>
                <div class="back">
	                <form action="todo" method="post">
	                	<label for="priority">Priority:</label>
					    <select id="priority" name="priority">
					        <option value="HIGH">HIGH</option>
					        <option value="MEDIUM">MEDIUM</option>
					        <option value="LOW">LOW</option>
					    </select>
		    			<input type="text"   value=""         name="comment" style="border: 1px solid white; ">
		    			<input type="submit" value="Update"   name="action">
		    			<input type="hidden" value="<%=id %>" name="key">
		    			<input type="submit" value="Delete"   name="action">
	   			    </form>
   			    </div>
            </div>
        <% } %>
        <% // ----------------------------------------------------------------- %>
    </div>
    <form method="POST" action="logout">
      <input type="submit" value="Logout">
    </form>
    <script>
    	/** Code provided by ChatGPT */
	    function toggleCard(card) {
    	  card.classList.toggle('flipped');
    	}
    	
	 	// Prevent card flip when interacting with input field
	    document.querySelectorAll('.todo-item input').forEach(function(input) {
	      input.addEventListener('click', function(event) {
	        event.stopPropagation();
	      });
	    });
    </script>
</body>
</html>