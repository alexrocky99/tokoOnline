package com.online.module.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HibernateRequestFilter implements Filter {

	@Autowired()
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	@Autowired
	private HttpServletRequest servletRequest;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("Initializing After...");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpSession httpsSession = ((HttpServletRequest) request).getSession(false);
		
		try {
			if (servletRequest == null || servletRequest.getSession().getAttribute("userLoginSession") == null) {
				if (httpsSession != null && sessionFactory != null) {
					System.out.println("Starting a database transaction");
					sessionFactory.getCurrentSession().setFlushMode(FlushMode.MANUAL);
					sessionFactory.getCurrentSession().beginTransaction();
					
					chain.doFilter(request, response);
					
					System.out.println("Committing the database transaction");
					sessionFactory.getCurrentSession().getTransaction().commit();
				} else {
					System.out.println("Session Time Out!");
					request.getRequestDispatcher("/pages/home.faces").forward(request, response);
				}
			} else {
				request.getRequestDispatcher("/pages/home.faces").forward(request, response);
			}
		} catch (StaleObjectStateException staleEx) {
			System.out.println("This interceptor does not implement optimistic concurrency control!");
			System.out.println("Your application will not work until you add compensation actions!");
			throw staleEx;
		} catch (Throwable te) {
			te.printStackTrace();
			try {
				if (sessionFactory.getCurrentSession().getTransaction().isActive()) {
					System.out.println("Trying to rollback database transaction after exception");
					sessionFactory.getCurrentSession().getTransaction().rollback();
				}
			} catch (Throwable tex) {
				System.out.println("Could not rollback transaction after exception!" + tex.getMessage());
			}
			
			throw new ServletException(te);
		}
	}

	@Override
	public void destroy() {
		System.out.println("Destroying filter...");
	}

}
