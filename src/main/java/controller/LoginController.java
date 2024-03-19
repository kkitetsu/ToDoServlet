package controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.HashGenerator;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost/taskInfoDB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String view = "/WEB-INF/views/index.jsp";
        req.getRequestDispatcher(view).forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String hashedPassword = HashGenerator.generateHash(password);
            String sql = "SELECT * FROM userInfoTable WHERE user_id=? AND password=?";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, hashedPassword);
                
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt("id");

                    // 2.サーバーからセッションを取得する
                    HttpSession session = req.getSession();
                    // 3.キーと値のペアでセッションに登録する
                    session.setAttribute("id", id);
                    session.setAttribute("username", username);
                    res.sendRedirect("todo");
                } else {
                    String view = "/WEB-INF/views/index.jsp";
                    req.getRequestDispatcher(view).forward(req, res);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database Connection Failed", e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new ServletException("Generate hash Failed", e);
        }
    }
}
