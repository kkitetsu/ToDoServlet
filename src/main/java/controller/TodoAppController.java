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
import javax.servlet.http.HttpSession;

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
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		if (session.getAttribute("id") == null) {
			response.sendRedirect("login");
			return;
		}
		
		request.setCharacterEncoding("utf-8"); 
		ListDao dao = new ListDao();
		
		System.out.println(request.getParameter("action"));
		
		try {
			HashMap<Integer, ArrayList<String>> selectedData = dao.select("");
			if (request.getAttribute("sortTime") != null) {
				selectedData = dao.select((String) request.getAttribute("sortTime"));
			} else if (request.getAttribute("sortPri") != null) {
				selectedData = dao.select((String) request.getAttribute("sortPri"));
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
		
		HttpSession session = request.getSession();
		
		if (session.getAttribute("id") == null) {
			response.sendRedirect("login");
			return;
		}
		
		request.setCharacterEncoding("utf-8"); 
		
		String action = request.getParameter("action");
		
		// Todo 追加, 時間ソート, もしくは優先度ソート
		clickedAddOrSort(action, request, response);
		
		ModifyDao dao = new ModifyDao();
		
		try {
			if (action.equals("Delete")) {
				dao.delete(request.getParameter("key"));
			} else if (action.equals("Update")) {
				dao.update(request.getParameter("key"), 
						   request.getParameter("comment"), 
						   request.getParameter("priority"));
			} else if (action.equals("AddNew")) {
				dao.insert(request.getParameter("title"), 
						   request.getParameter("content"), 
						   request.getParameter("priority"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		doGet(request, response);
	}
	
	protected void clickedAddOrSort(String action, 
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (action.equals("Add Todo")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/add.jsp");
			dispatcher.forward(request, response);
		} else if (action.equals("AscendingTime") || action.equals("DescendingTime")) {
			request.setAttribute("sortTime", action);
			doGet(request, response);
		} else if (action.equals("AscendingPri") || action.equals("DescendingPri")) {
			request.setAttribute("sortPri", action);
			doGet(request, response);
		} else {
			return;
		}
	}

}
