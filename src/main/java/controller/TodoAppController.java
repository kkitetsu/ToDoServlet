package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
		ListDao dao = new ListDao();
		
		try {
			HashMap<Integer, ArrayList<String>> selectedData = dao.select();
			HashMap<Integer, ArrayList<String>> newData = new HashMap<Integer, ArrayList<String>>();
			Iterator<Map.Entry<Integer, ArrayList<String>>> iterator = selectedData.entrySet().iterator();
			if (false) {
				ArrayList<Integer> high   = new ArrayList<>();
				ArrayList<Integer> medium = new ArrayList<>();
				ArrayList<Integer> low    = new ArrayList<>();
				while (iterator.hasNext()) {
					Map.Entry<Integer, ArrayList<String>> entry = iterator.next();
					ArrayList<String> tmp = entry.getValue();
					if (tmp.get(5).equals("red")) {
						high.add(entry.getKey());
					} else if (tmp.get(5).equals("orange")) {
						medium.add(entry.getKey());
					} else {
						low.add(entry.getKey());
					}
				}
				for (int i = 0; i < high.size(); i++) {
					int key = high.get(i);
					newData.put(key, selectedData.get(key));
				}
				for (int i = 0; i < medium.size(); i++) {
					int key = high.get(i);
					newData.put(key, selectedData.get(key));
				}
				for (int i = 0; i < low.size(); i++) {
					int key = high.get(i);
					newData.put(key, selectedData.get(key));
				}
			}
			request.setAttribute("rows", dao.select());
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
		
		String action = request.getParameter("action");
		
		if (action.equals("Add")) {
			String url = "WEB-INF/views/add.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
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
				String title   = request.getParameter("title");
				String content = request.getParameter("content");
				dao.insert(title, content);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		doGet(request, response);
	}

}
