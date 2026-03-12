package com.SmartBuilding.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.SmartBuilding.utils.DBConnection;

@WebServlet("/updateRequestStatus")
public class UpdateRequestStatusServlet extends HttpServlet {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

protected void doPost(HttpServletRequest request,
HttpServletResponse response) throws IOException {

int id = Integer.parseInt(request.getParameter("id"));
String status = request.getParameter("status");

try(Connection con = DBConnection.getConnection()){

String sql = "UPDATE maintenancerequest SET status=? WHERE request_id=?";

PreparedStatement ps = con.prepareStatement(sql);

ps.setString(1, status);
ps.setInt(2, id);

ps.executeUpdate();

response.getWriter().print("success");

}catch(Exception e){
e.printStackTrace();
}

}

}