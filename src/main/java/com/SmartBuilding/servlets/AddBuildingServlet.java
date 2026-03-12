package com.SmartBuilding.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.SmartBuilding.utils.DBConnection;

@WebServlet("/addBuilding")
public class AddBuildingServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7669947191213254059L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String location = request.getParameter("location");

        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO Building(name, location) VALUES (?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, location);

            ps.executeUpdate();
            response.sendRedirect("success.jsp");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
