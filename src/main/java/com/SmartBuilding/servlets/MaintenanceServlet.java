package com.SmartBuilding.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.SmartBuilding.utils.DBConnection;

@WebServlet("/addMaintenance")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class MaintenanceServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String location = request.getParameter("location");
        String issueType = request.getParameter("issue_type");
        String priority = request.getParameter("priority");
        String reportedBy = request.getParameter("reported_by");
        String description = request.getParameter("description");

        // ✅ Get user_id from session
        HttpSession session = request.getSession(false);
        int userId = 0;

        if (session != null && session.getAttribute("user_id") != null) {
            userId = (int) session.getAttribute("user_id");
        }

        Part photosPart = request.getPart("photos");

        try (Connection con = DBConnection.getConnection()) {

            String sql = "INSERT INTO maintenancerequest "
                    + "(building_id, location, issue_type, priority, reported_by, description, status, photo, user_id) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, 1); // building id
            ps.setString(2, location);
            ps.setString(3, issueType);
            ps.setString(4, priority);
            ps.setString(5, reportedBy);
            ps.setString(6, description);
            ps.setString(7, "Pending");

            if (photosPart != null && photosPart.getSize() > 0) {
                InputStream inputStream = photosPart.getInputStream();
                ps.setBlob(8, inputStream);
            } else {
                ps.setNull(8, java.sql.Types.BLOB);
            }

            // ✅ store logged-in user id
            ps.setInt(9, userId);

            ps.executeUpdate();
            ps.close();

            request.setAttribute("message", "Maintenance request submitted successfully!");
            request.getRequestDispatcher("Maintenance.html").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}