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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.metasearch.service.dao.AuditLogDAO;
import com.metasearch.service.dao.Flight;
import com.metasearch.service.dao.FlightsDAO;

@Path("/api/flights")
public class FlightsAPI {

	@GET
	@Produces("application/json")
	public Response flights() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Service name", "Metasearch flight API Flight search");

		String result = // "@Produces(\"application/json\")" +
		jsonObject.toString();
		return Response.status(200).entity(result).build();
	}

	@Path("{from}/{date}/{tickets}")
	@GET
	@Produces("application/json")
	public Response fromDateTickets(@PathParam("from") String from,
			@PathParam("date") String date, @PathParam("tickets") int tickets) {
		try {
			AuditLogDAO.addEntry(from, null, date, tickets);
			
			JSONObject jsonObject = new JSONObject();
			// jsonObject.put("Service name", "Metasearch flight API");

			Date dateParsed = parseDate(date);
			List<Flight> flights = FlightsDAO.getFlights(from, dateParsed,
					tickets);
			jsonObject.put("airline", "REST airline");
			List<JSONObject> jsonFlights = new ArrayList<JSONObject>();
			for (Flight flight : flights) {
				JSONObject jsonFlight = new JSONObject();
				jsonFlight.put("date", dateToString(flight.getDepartureTime()));
				jsonFlight.put("numberOfSeats", flight.getSeatsAvailable());
				jsonFlight.put("totalPrice",
						tickets * flight.getPricePerSeatEuro());
				jsonFlight.put("flightID", flight.getId());
				jsonFlight.put("traveTime", flight.getTripDurationMins());
				jsonFlight.put("destination", flight.getDestinationAirport());
				jsonFlight.put("origin", flight.getOriginAirport());
				jsonFlights.add(jsonFlight);
			}

			// jsonObject.put("Flights", flights.toString());
			jsonObject.put("flights", jsonFlights);
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

	public static String dateToString(Date departureTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(departureTime);
		StringBuilder builder = new StringBuilder();
		builder.append(padString(calendar.get(Calendar.YEAR), 4));
		builder.append("-");
		builder.append(padString(calendar.get(Calendar.MONTH) + 1, 2));
		builder.append("-");
		builder.append(padString(calendar.get(Calendar.DATE), 2));
		builder.append("T");
		builder.append(padString(calendar.get(Calendar.HOUR_OF_DAY), 2));
		builder.append(":");
		builder.append(padString(calendar.get(Calendar.MINUTE), 2));
		builder.append(":");
		builder.append(padString(calendar.get(Calendar.SECOND), 2));
		return builder.toString();
	}

	private static String padString(int toPad, int size) {
		String stringValue = String.valueOf(toPad);
		if (stringValue.length() < size) {
			StringBuilder builder = new StringBuilder();
			int toAdd = size - stringValue.length();
			for (int i = 0; i < toAdd; i++) {
				builder.append("0");
			}
			builder.append(stringValue);
			return builder.toString();
		}
		return stringValue;
	}

	private Date parseDate(String dateString) {
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = parser.parse(dateString);
		} catch (ParseException e) {
			return null;
		}
		return date;
	}

	@Path("{from}/{to}/{date}/{tickets}")
	@GET
	@Produces("application/json")
	public Response fromToDateTickets(@PathParam("from") String from,
			@PathParam("to") String to, @PathParam("date") String date,
			@PathParam("tickets") int tickets) throws JSONException {
		AuditLogDAO.addEntry(from, to, date, tickets);

		JSONObject jsonObject = new JSONObject();
		// jsonObject.put("Service name", "Metasearch flight API");

		Date dateParsed = parseDate(date);
		List<Flight> flights = FlightsDAO.getFlights(from, to, dateParsed,
				tickets);
		jsonObject.put("airline", "REST airline");
		List<JSONObject> jsonFlights = new ArrayList<JSONObject>();
		for (Flight flight : flights) {
			JSONObject jsonFlight = new JSONObject();
			jsonFlight.put("date", dateToString(flight.getDepartureTime()));
			jsonFlight.put("numberOfSeats", flight.getSeatsAvailable());
			jsonFlight
					.put("totalPrice", tickets * flight.getPricePerSeatEuro());
			jsonFlight.put("flightID", flight.getId());
			jsonFlight.put("traveTime", flight.getTripDurationMins());
			jsonFlight.put("destination", flight.getDestinationAirport());
			jsonFlight.put("origin", flight.getOriginAirport());
			jsonFlights.add(jsonFlight);
		}

		// jsonObject.put("Flights", flights.toString());
		jsonObject.put("flights", jsonFlights);
		String result = // "@Produces(\"application/json\")" +
		jsonObject.toString();
		return Response.status(200).entity(result).build();
	}

}
