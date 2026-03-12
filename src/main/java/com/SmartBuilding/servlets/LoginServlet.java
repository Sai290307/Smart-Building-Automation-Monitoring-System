package com.SmartBuilding.servlets;
import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.SmartBuilding.utils.DBConnection;
import com.SmartBuilding.utils.PasswordUtil;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String encryptedPassword = PasswordUtil.encryptPassword(password);

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT user_id FROM users WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, encryptedPassword);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int userId = rs.getInt("user_id");

                // Create session
                HttpSession session = request.getSession();

                // Store user id in session
                session.setAttribute("user_id", userId);

                response.sendRedirect("dashboard.html");

            } else {
                response.getWriter().println("Invalid login");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}