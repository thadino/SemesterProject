
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

/**
 *
 * @author Dino
 */
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;


import com.metasearch.service.dao.Flight;
import com.metasearch.service.dao.FlightsDAO;
import com.metasearch.service.dao.User;
import com.metasearch.service.dao.UsersDAO;
import com.sun.jersey.core.util.Base64;

@Path("/api/user")
public class UsersAPI {

	@GET
	@Produces("application/json")
	public Response flights() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Service name", "Metasearch flight API Flight search");

		String result = // "@Produces(\"application/json\")" +
		jsonObject.toString();
		return Response.status(200).entity(result).build();
	}

	@Path("{role}")
	@GET
	@Produces("application/json")
	public Response fromDateTickets(@PathParam("role") String role) {
		try {
			List<User> users = UsersDAO.getByRole(role); 
			System.out.println("Users list "+users); 
			JSONObject jsonObject = new JSONObject();
			
			List<JSONObject> jsonUsers = new ArrayList<JSONObject>();
			for (User user: users) {
				JSONObject jsonUser= new JSONObject();
				jsonUser.put("userName", user.getUserName()); 
//				jsonUser.put("role", user.getRole()); 
				
				jsonUsers.add(jsonUser);
			}

			// jsonObject.put("Flights", flights.toString());
			jsonObject.put("users", jsonUsers);
			String result = // "@Produces(\"application/json\")" +
			jsonObject.toString();

			return Response.status(200).entity(result).build();
		} catch (JSONException e) {
			JSONObject error = new JSONObject();
			error.put("httpError", 400);
			error.put("errorCode", 3);
			error.put(
					"message",
					"Illegal input, id and tickets have to be set "
							+ e.getMessage());
			return Response.status(400).entity(error.toString()).build(); 
		} catch (Exception e) {
			JSONObject error = new JSONObject();
			error.put("httpError", 400);
			error.put("errorCode", 3);
			error.put(
					"message",
					"Illegal input, id and tickets have to be set "
							+ e.getMessage());
			return Response.status(400).entity(error.toString()).build();
		}
	}

	

	@Path("{user}")
	@DELETE
	@Produces("application/json")
	public Response deleteUser(@PathParam("user") String user) throws JSONException {
		UsersDAO.deleteUser(user);
		return Response.status(200).build();
	} 
	
	@Path("new") 
	@POST
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON) 
	public Response addUser(InputStream incomingData) { 
		StringBuilder builder = new StringBuilder(); 
		Flight reserved = null; 
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				builder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + builder.toString()); 
		JSONObject jsonInput = new JSONObject(builder.toString()); 
		String userName = jsonInput.getString("userName"); 
		String password = jsonInput.getString("passWord"); 
		byte[] salt = Passwords.getNextSalt(); 
		String hashSaltString = new String(Base64.encode(salt)); 
		byte[] passwordHash = Passwords.hash(password.toCharArray(), salt); 
		String passwordHashString = new String(Base64.encode(passwordHash)); 
		String role = jsonInput.getString("role"); 
		if(!role.equals("admin") && !role.equals("user")) { 
			JSONObject error = new JSONObject();
			error.put("httpError", 400);
			error.put("errorCode", 3);
			error.put(
					"message",
					"Illegal input, the role needs to be \"admin\" or \"user\". ");
			return Response.status(400).entity(error.toString()).build(); 
		} 
//		User user = new User(); 
////		user.setUserName(userName); 
////		user.setRole(role); 
////		user.setPasswordHash(passwordHashString);
////		user.setHashSalt(hashSaltString); 
//		
//		UsersDAO.addEntry(user); 
		
		return Response.status(200).build(); 
	}

}
