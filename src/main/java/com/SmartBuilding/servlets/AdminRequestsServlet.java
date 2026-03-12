package com.SmartBuilding.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.json.JSONArray;
import org.json.JSONObject;

import com.SmartBuilding.utils.DBConnection;

@WebServlet("/getAllRequests")
public class AdminRequestsServlet extends HttpServlet {

protected void doGet(HttpServletRequest request,
HttpServletResponse response) throws IOException {

response.setContentType("application/json");

JSONArray arr = new JSONArray();

try(Connection con = DBConnection.getConnection()){

String sql = "SELECT request_id, location, issue_type, priority, reported_by, status FROM MaintenanceRequest ORDER BY created_at DESC";

PreparedStatement ps = con.prepareStatement(sql);

ResultSet rs = ps.executeQuery();

while(rs.next()){

JSONObject obj = new JSONObject();

obj.put("id", rs.getInt("request_id"));
obj.put("location", rs.getString("location"));
obj.put("issue", rs.getString("issue_type"));
obj.put("priority", rs.getString("priority"));
obj.put("reportedBy", rs.getString("reported_by"));
obj.put("status", rs.getString("status"));

arr.put(obj);

}

}catch(Exception e){
e.printStackTrace();
}

response.getWriter().print(arr.toString());

}
}