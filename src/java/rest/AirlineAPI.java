/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.metasearch.service.dao.Airline;
import com.metasearch.service.dao.AirlineDAO;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import openshift_deploy.DeploymentConfiguration;
import org.json.JSONObject;

/**
 *
 * @author kaspe
 */
@Path("/api/airline")
public class AirlineAPI
{

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public String getAllAirlines()
    {

        return gson.toJson(DeploymentConfiguration.urls);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAirline(String airJSON)
    {
        try
        {
            Airline b = (Airline) gson.fromJson(airJSON, Airline.class);
            AirlineDAO.addEntry(b.getName(), b.getUrl());
        }
        catch (Exception e)
        {
            JSONObject error = new JSONObject();
            error.put("httpError", 400);
            error.put("errorCode", 3);
            error.put("message", "Illegal input. Please make that both the url and name are unique - " + e.getMessage());
            return Response.status(400).entity(error.toString()).build();
        }
        return Response.status(200).build();
    }

    @DELETE
    @Path("/{name}")
    public Response deleteAirline(@PathParam("name") String name)
    {
        try
        {
            AirlineDAO.deleteAirline(name);
        }
        catch (Exception e)
        {
            JSONObject error = new JSONObject();
            error.put("httpError", 400);
            error.put("errorCode", 3);
            error.put("message", "Illegal input. Please make that the airline exists - " + e.getMessage());
            return Response.status(400).entity(error.toString()).build();
        }
        return Response.status(200).build();
    }
}
