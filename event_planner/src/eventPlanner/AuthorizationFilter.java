package eventPlanner;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import eventDAO.HibernateUtil;
import systemDAO.UserProfileDAO;
import systemPD.Token;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthorizationFilter implements ContainerRequestFilter {

	String username;
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public AuthorizationFilter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// TODO Auto-generated method stub
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		
		if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
			throw new NotAuthorizedException("Authorization header must be provided");
		}
		
		String token = authorizationHeader.substring("Bearer ".length()).trim();
		
		try{
			System.out.println(UserProfileDAO.getAllToken().get(0).getToken());
			setUsername(validateToken(token));
		}catch(Exception e){
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
		
		final SecurityContext currentSecurityContext = requestContext.getSecurityContext();
		requestContext.setSecurityContext(new SecurityContext(){

			@Override
			public String getAuthenticationScheme() {
				// TODO Auto-generated method stub
				return "Bearer";
			}

			@Override
			public Principal getUserPrincipal() {
				// TODO Auto-generated method stub
				return new Principal(){

					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return getUsername();
					}
					
				};
			}

			@Override
			public boolean isSecure() {
				// TODO Auto-generated method stub
				return currentSecurityContext.isSecure();
			}

			@Override
			public boolean isUserInRole(String arg0) {
				// TODO Auto-generated method stub
				return true;
			}
			
		});
		
	}
	
	private String validateToken(String tokenString) throws Exception{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		Transaction tx = s.beginTransaction();
		
		ArrayList<Token> tkn =(ArrayList<Token>) s.createQuery("from Token where token = ?").setParameter(0, tokenString).list();
		s.flush();
		s.clear();
		tx.commit();
		s.close();
		
		Date date = new Date();
		String dt = tkn.get(0).getTokenExpire().toString();
		String cmp = LocalDate.now().toString();
		System.out.println();
		if(tkn.get(0).getToken()==null|| !dt.equals(cmp)) throw new Exception();
		
		return tkn.get(0).getUser().getUserName();
	
		
	}

}
