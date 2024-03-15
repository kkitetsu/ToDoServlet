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
		
		request.setCharacterEncoding("utf-8"); 
		
		String action = request.getParameter("action");
		
		// Todo 追加, 時間ソート, もしくは優先度ソート
		clickedNonSQL(action, request, response);
		
		ModifyDao dao = new ModifyDao();
		
		if (action.equals("Delete")) {
			try {
				dao.delete(request.getParameter("key"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (action.equals("Update")) {
			try {
				dao.update(request.getParameter("key"), 
						   request.getParameter("comment"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (action.equals("AddNew")) {
			try {
				dao.insert(request.getParameter("title"), 
						   request.getParameter("content"), 
						   request.getParameter("priority"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		doGet(request, response);
	}
	
	protected void clickedNonSQL(String action, 
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println(action);
		
		if (action.equals("Add Todo")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/add.jsp");
			dispatcher.forward(request, response);
		} else if (action.equals("AscendingTime")) {
			request.setAttribute("sortTime", action);
			doGet(request, response);
		} else if (action.equals("DescendingTime")) {
			request.setAttribute("sortTime", action);
			doGet(request, response);
		} else if (action.equals("AscendingPri")) {
			request.setAttribute("sortPri", action);
			doGet(request, response);
		} else if (action.equals("DescendingPri")) {
			request.setAttribute("sortPri", action);
			doGet(request, response);
		} else {
			return;
		}
	}

}
