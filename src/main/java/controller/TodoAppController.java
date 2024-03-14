package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TodoAppController
 */
@WebServlet("/todo")
public class TodoAppController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TodoAppController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8"); 
		ListDao dao = new ListDao();
		
		try {
			HashMap<Integer, ArrayList<String>> selectedData = dao.select("");
			if (request.getAttribute("sortTime") != null) {
				selectedData = dao.select("sortTime");
			} else if (request.getAttribute("sortPri") != null) {
				selectedData = dao.select("sortPri");
			}
			request.setAttribute("rows", selectedData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String url = "WEB-INF/views/list.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8"); 
		
		String action = request.getParameter("action");
		
		if (action.equals("Add Todo")) {
			String url = "WEB-INF/views/add.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		} else if (action.equals("Sort Time")) {
			request.setAttribute("sortTime", "sortTime");
			doGet(request, response);
		} else if (action.equals("Sort Priority")) {
			request.setAttribute("sortPri", "sortPri");
			doGet(request, response);
		}
		
		ModifyDao dao = new ModifyDao();
		
		if (action.equals("Delete")) {
			try {
				String id = request.getParameter("key");
				dao.delete(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (action.equals("Update")) {
			try {
				String id = request.getParameter("key");
				String newComment = request.getParameter("comment");
				dao.update(id, newComment);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (action.equals("AddNew")) {
			try {
				String title    = request.getParameter("title");
				String content  = request.getParameter("content");
				String priority = request.getParameter("priority");
				dao.insert(title, content, priority);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		doGet(request, response);
	}

}
