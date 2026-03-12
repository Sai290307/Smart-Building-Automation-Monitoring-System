package com.SmartBuilding.servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/EnergyServlet")
public class EnergyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/smartbuild";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "manager";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String roomName = request.getParameter("room");
        String meterReadingStr = request.getParameter("meterReading");
        String readingDateStr = request.getParameter("readingDate");
        String buildingIdStr = request.getParameter("buildingId");
        String loggedBy = request.getParameter("loggedBy");

        // 🔹 Get userId from session
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user_id") == null) {
            response.getWriter().print("{\"error\":\"User not logged in\"}");
            return;
        }

        int userId = (int) session.getAttribute("user_id");

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            int buildingId = Integer.parseInt(buildingIdStr);
            BigDecimal energyUsage = new BigDecimal(meterReadingStr);
            Date usageDate = Date.valueOf(readingDateStr);

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "INSERT INTO energylogs "
                    + "(building_id, room_name, energy_usage, usage_date, logged_by, user_id) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, buildingId);
            stmt.setString(2, roomName);
            stmt.setBigDecimal(3, energyUsage);
            stmt.setDate(4, usageDate);
            stmt.setString(5, loggedBy);
            stmt.setInt(6, userId); // 🔹 Bind userId from session

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                response.sendRedirect("energyentry.html?success=1");
            } else {
                response.sendRedirect("energyentry.html?error=1");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("energyEntry.html?error=1");
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
