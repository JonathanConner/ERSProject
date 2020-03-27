package com.projectone.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.projectone.model.Reimbursement;
import com.projectone.model.User;
import com.projectone.services.UserService;
import com.projectone.util.JSONConverter;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private UserService us;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        this.us  = new UserService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		
		String action = req.getParameter("action");

		switch(action) {
			case "fetchall":fetchAll(req, resp);
				break;
		}
		
	}

	public void fetchAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PrintWriter pw = resp.getWriter();
		
		List<User> users = this.us.findAll();
		
		users.forEach(r->{System.out.println(r.toJSONString());});
		
		String array = JSONConverter.writeUsersToJsonArray(users);//Write the users to a JSON array
		
		pw.println(array);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
