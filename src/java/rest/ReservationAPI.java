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

import com.google.gson.*;

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

import com.metasearch.service.dao.Reservation;
import com.metasearch.service.dao.ReservationAuditLog;
import com.metasearch.service.dao.ReservationAuditLogDAO;
import com.metasearch.service.dao.ReservationDAO;
import com.metasearch.service.exception.FlightNotFoundException;
import com.metasearch.service.exception.InsufficientTicketsException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import openshift_deploy.DeploymentConfiguration;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;

@Path("/api/reservation")
public class ReservationAPI
{

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @GET

    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("Admin")
    @Path("/all")
    public String getAllReservations()
    {
        EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        try
        {
            Query q = em.createNamedQuery("reservation.findAll", Reservation.class);
            return gson.toJson(q.getResultList());
        }
        finally
        {

        }
    }

    @GET
    @Produces("application/json")
    public Response test() throws JSONException
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Service name", "Metasearch flight API Reservations");

        String result = jsonObject.toString();
        return Response.status(200).entity(result).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{reserveeName}")
    public String getAllYourReservations(@PathParam("reserveeName") String userName)
    {
        EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        try
        {
            Query q = em.createNamedQuery("reservation.findByName", Reservation.class);
            q.setParameter("name", userName);
            System.out.println("her er res: " + q.getResultList().get(0));

            return (gson.toJson(q.getResultList())).replace("\\u0027", "").replace("\\", "").replace("\"[", "[").replace("\"]", "]").replace("]\"", "]");

        }
        finally
        {
            em.close();
        }
    }

    @Path("/{flightId}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json")
    public Response reserve(InputStream incomingData, @PathParam("flightId") String flightId) throws JSONException
    {

        System.out.println("Received request, flight id " + flightId);
        StringBuilder builder = new StringBuilder();
//        Flight reserved = null;
        try
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null)
            {
                builder.append(line);
            }
        }
        catch (Exception e)
        {
            System.out.println("Error Parsing: - ");
        }
//        System.out.println("Data Received: " + builder.toString());
        JSONObject jsonInput = new JSONObject(builder.toString());

        // decrement available tickets 
        try
        {
            System.out.println("llama");
            ReservationAuditLogDAO.addEntry(jsonInput, 1);
            String inputID = jsonInput.getString("flightID");
            System.out.println("------------------------------------------------------------------ her er :" + inputID);
            int tickets = jsonInput.getInt("numberOfSeats");
//            reserved = FlightsDAO.reserve(inputID, tickets);

            String postUrl = jsonInput.getString("url") + inputID;
            jsonInput.remove("url");
//            System.out.println("HER ER JSON INPUT: " + jsonInput.toString());
//            System.out.println("HER ER POSTURL: " + postUrl);
            Gson gson = new Gson();
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost(postUrl);
            JsonObject jo = new JsonObject();
            System.out.println("HER ER JSONIN: " + jsonInput.toString());
            jo.addProperty("flightID", jsonInput.getString("flightID"));
            jo.addProperty("numberOfSeats", jsonInput.getInt("numberOfSeats"));
            jo.addProperty("reserveeName", jsonInput.getString("reserveeName"));
            jo.addProperty("reserveePhone", jsonInput.getString("reserveePhone"));
            jo.addProperty("reserveeEmail", jsonInput.getString("reserveeEmail"));

            jo.addProperty("passengers", gson.toJson(jsonInput.getJSONArray("passengers").toString()));
//            String c = gson.toJson(jsonInput).replace("\"map\":", "");
//            c = c.substring(1, c.length()-1);
//            System.out.println("HER ER POSTINGSTRING " + gson.toJson(jo).replace("\\\\\\", "").replace("\"\\\"", "").replace("\\\"\"", ""));
            StringEntity postingString = new StringEntity(gson.toJson(jo).replace("\\\\\\", "").replace("\"\\\"", "").replace("\\\"\"", ""));
//            System.out.println("HER ER POSTINGSTRING: " + c);
            post.setEntity(postingString);
            System.out.println("");
            post.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(post);

            if (response.getStatusLine().getStatusCode() == 200)
            {
                Reservation r = new Reservation(jsonInput.getString("reserveeEmail"), jsonInput.getString("reserveeName"), jsonInput.getString("reserveePhone"), jsonInput.getInt("numberOfSeats"), jsonInput.getJSONArray("passengers").toString(), jsonInput.getString("flightID"));
                ReservationDAO.addEntry(r);
            }
//            System.out.println("asdfasdfasdf " + response.getStatusLine().toString());
//            System.out.println("HER ER RESPONSE FRA AIRLINE: " + response.toString());

        }
        catch (JSONException e)
        {
            JSONObject error = new JSONObject();
            error.put("httpError", 400);
            error.put("errorCode", 3);
            error.put("message", "Illegal input, all the input fields need to be set asdf - " + e.getMessage());
            return Response.status(400).entity(error.toString()).build();
        }
//        catch (FlightNotFoundException e)
//        {
//            JSONObject error = new JSONObject();
//            error.put("httpError", 400);
//            error.put("errorCode", 1);
//            error.put("message", e.getMessage());
//            ReservationAuditLogDAO.addEntry(jsonInput, 2);
//            return Response.status(400).entity(error.toString()).build();
//        }
//        catch (InsufficientTicketsException e)
//        {
//            JSONObject error = new JSONObject();
//            error.put("httpError", 400);
//            error.put("errorCode", 2);
//            error.put("message", e.getMessage());
//            ReservationAuditLogDAO.addEntry(jsonInput, 2);
//            return Response.status(400).entity(error.toString()).build();
//        }
        catch (IOException ex)
        {
            JSONObject error = new JSONObject();
            error.put("httpError", 500);
            error.put("errorCode", 2);
            error.put("message", ex.getMessage());
            ReservationAuditLogDAO.addEntry(jsonInput, 2);
            return Response.status(500).entity(error.toString()).build();
        }
        // update audit log entry
//		reservationAuditLogEntry.setReqStatus(0); 
        // persist 
        ReservationAuditLogDAO.addEntry(jsonInput, 0);
        JSONObject jsonObject = new JSONObject(builder.toString());
        jsonObject.remove(JSONConstants.RESERVEE_EMAIL);
        jsonObject.remove(JSONConstants.RESERVEE_PHONE);

//        if (reserved != null)
//        {
//            jsonObject.put("origin", reserved.getOriginAirport());
//            jsonObject.put("destination", reserved.getDestinationAirport());
//            jsonObject.put("date", FlightsAPI.dateToString(reserved.getDepartureTime()));
//            jsonObject.put("flightTime", reserved.getTripDurationMins());
//        }
        jsonObject.put("flightID", flightId);
        jsonObject.remove("url");

        String result = // "@Produces(\"application/json\")" +
                jsonObject.toString();
        return Response.status(200).entity(result).build();
    }


}
