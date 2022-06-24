package com.online.module.engine;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Initializer extends HttpServlet{

	private static final long serialVersionUID = 5379729165156790509L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		System.out.println("Initializer Lexlex99 Engine Run");
	}

}
