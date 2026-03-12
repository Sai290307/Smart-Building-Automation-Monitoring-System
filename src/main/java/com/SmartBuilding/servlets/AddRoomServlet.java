package com.SmartBuilding.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addRoom")
public class AddRoomServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/smartbuild";
    private static final String DB_USER = "root";        // change to your DB user
    private static final String DB_PASSWORD = "manager"; // change to your DB password

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Read form parameters
        String roomNumber = request.getParameter("room_number");
        String roomType = request.getParameter("room_type");
        String floorStr = request.getParameter("floor");
        String buildingIdStr = request.getParameter("building_id");
        String capacityStr = request.getParameter("capacity");
        String equipment = request.getParameter("equipment");
        String notes = request.getParameter("notes");

        int capacity = 0;
        if (capacityStr != null && !capacityStr.isEmpty()) {
            capacity = Integer.parseInt(capacityStr);
        }

        response.setContentType("text/html");
        
        // Basic validation
        if (roomNumber == null || roomType == null || floorStr == null || buildingIdStr == null ||
            roomNumber.isEmpty() || roomType.isEmpty() || floorStr.isEmpty() || buildingIdStr.isEmpty()) {
            response.getWriter().println("All fields are required!");
            return;
        }

        int floor = Integer.parseInt(floorStr);
        int buildingId = Integer.parseInt(buildingIdStr);

        // JDBC connection
        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "INSERT INTO Room (room_number, room_type, floor, building_id, capacity, equipment, notes) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, roomNumber);
            ps.setString(2, roomType);
            ps.setInt(3, floor);
            ps.setInt(4, buildingId);
            ps.setInt(5, capacity);      // optional
            ps.setString(6, equipment);  // optional
            ps.setString(7, notes);
            
            int rows = ps.executeUpdate();

            if (rows > 0) {
                response.getWriter().println("<h3>Room added successfully!</h3>");
                response.getWriter().println("<a href='addRoom.html'>Add Another Room</a>");
            } else {
                response.getWriter().println("<h3>Error: Room not added!</h3>");
            }

            ps.close();
            conn.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("Database driver not found: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database error: " + e.getMessage());
        } catch (NumberFormatException e) {
            response.getWriter().println("Floor and Building ID must be numbers!");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect GET to the form
        response.sendRedirect("addRoom.html");
    }
}
