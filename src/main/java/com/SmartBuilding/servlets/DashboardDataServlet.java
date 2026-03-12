package com.SmartBuilding.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.SmartBuilding.utils.DBConnection;

@WebServlet("/dashboardData")
public class DashboardDataServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject json = new JSONObject();

        try (Connection con = DBConnection.getConnection()) {

            /* ===== GET LOGGED-IN USER ===== */
            HttpSession session = request.getSession(false);

            if (session == null || session.getAttribute("user_id") == null) {
                response.getWriter().print("{\"error\":\"User not logged in\"}");
                return;
            }

            int userId = (int) session.getAttribute("user_id");

            System.out.println("Logged in user id: " + userId);

            /* ===== DASHBOARD STATS ===== */
            JSONObject stats = new JSONObject();

            // Total Rooms
            PreparedStatement psRooms = con.prepareStatement(
                    "SELECT COUNT(*) FROM room");
            ResultSet rsRooms = psRooms.executeQuery();

            if (rsRooms.next()) {
                stats.put("totalRooms", rsRooms.getInt(1));
            }

            // Energy Usage (only this user's logs)
            PreparedStatement psEnergy = con.prepareStatement(
                    "SELECT IFNULL(SUM(energy_usage),0) FROM energylogs");

            ResultSet rsEnergy = psEnergy.executeQuery();

            if (rsEnergy.next()) {
                stats.put("energyUsage", rsEnergy.getInt(1));
            }

            // Active Maintenance Requests (user only)
            PreparedStatement psActive = con.prepareStatement(
                    "SELECT COUNT(*) FROM maintenancerequest WHERE status!='Completed' AND user_id=?");
            psActive.setInt(1, userId);

            ResultSet rsActive = psActive.executeQuery();

            if (rsActive.next()) {
                stats.put("activeRequests", rsActive.getInt(1));
            }

            json.put("stats", stats);

            /* ===== MAINTENANCE REQUEST TABLE ===== */

            JSONArray maintenanceArray = new JSONArray();

            String sql = "SELECT location, issue_type, priority, status, created_at "
                       + "FROM maintenancerequest "
                       + "WHERE user_id=? "
                       + "ORDER BY created_at DESC "
                       + "LIMIT 5";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                JSONObject obj = new JSONObject();

                obj.put("room", rs.getString("location"));
                obj.put("issue", rs.getString("issue_type"));
                obj.put("priority", rs.getString("priority"));
                obj.put("status", rs.getString("status"));
                obj.put("date",
                        rs.getTimestamp("created_at")
                          .toLocalDateTime()
                          .toLocalDate()
                          .toString());

                maintenanceArray.put(obj);
            }

            json.put("maintenance", maintenanceArray);

            /* ===== SEND JSON RESPONSE ===== */
            response.getWriter().print(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}