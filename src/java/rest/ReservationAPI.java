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

import javax.ws.rs.Consumes;
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
import com.metasearch.service.dao.JSONConstants;
import com.metasearch.service.dao.ReservationAuditLog;
import com.metasearch.service.dao.ReservationAuditLogDAO;
import com.metasearch.service.exception.FlightNotFoundException;
import com.metasearch.service.exception.InsufficientTicketsException;

@Path("/api/reservation")
public class ReservationAPI {

	@GET
	@Produces("application/json")
	public Response test() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Service name", "Metasearch flight API Reservations");

		String result = jsonObject.toString();
		return Response.status(200).entity(result).build();
	}

	
	@Path("{flightId}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response fromDateTickets(
			InputStream incomingData, 
			@PathParam("flightId") String flightId) 
			throws JSONException { 
		
		System.out.println("Received request, flight id "+flightId); 
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
		
		// decrement available tickets 
		try { 
			ReservationAuditLogDAO.addEntry(jsonInput, 1); 
			int inputID = jsonInput.getInt("flightID"); 
			int tickets = jsonInput.getInt("numberOfSeats"); 	
			reserved = FlightsDAO.reserve(inputID, tickets);
		} catch(JSONException e) { 
			JSONObject error = new JSONObject(); 
			error.put("httpError", 400); 
			error.put("errorCode", 3); 
			error.put("message", "Illegal input, all the input fields need to be set - "+e.getMessage()); 
			return Response.status(400).entity(error.toString()).build();
		} catch(FlightNotFoundException e) { 
			JSONObject error = new JSONObject(); 
			error.put("httpError", 400); 
			error.put("errorCode", 1); 
			error.put("message", e.getMessage()); 
			ReservationAuditLogDAO.addEntry(jsonInput, 2); 
			return Response.status(400).entity(error.toString()).build();
		} catch (InsufficientTicketsException e) {
			JSONObject error = new JSONObject(); 
			error.put("httpError", 400); 
			error.put("errorCode", 2); 
			error.put("message", e.getMessage()); 
			ReservationAuditLogDAO.addEntry(jsonInput, 2); 
			return Response.status(400).entity(error.toString()).build();
		} 
		// update audit log entry
//		reservationAuditLogEntry.setReqStatus(0); 
		// persist 
		ReservationAuditLogDAO.addEntry(jsonInput, 0); 
		JSONObject jsonObject = new JSONObject(builder.toString()); 
		jsonObject.remove(JSONConstants.RESERVEE_EMAIL); 
		jsonObject.remove(JSONConstants.RESERVEE_PHONE); 
		
		if(reserved != null) { 
			jsonObject.put("origin", reserved.getOriginAirport()); 
			jsonObject.put("destination", reserved.getDestinationAirport()); 
			jsonObject.put("date", FlightsAPI.dateToString(reserved.getDepartureTime())); 
			jsonObject.put("flightTime", reserved.getTripDurationMins());
		}
		jsonObject.put("From", flightId); 
		
		String result = // "@Produces(\"application/json\")" +
		jsonObject.toString();
		return Response.status(200).entity(result).build();
	} 
	
}
