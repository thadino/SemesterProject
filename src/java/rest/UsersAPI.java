
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
import com.metasearch.service.dao.Reservation;
import com.metasearch.service.dao.User;
import com.metasearch.service.dao.UsersDAO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.PUT;
import openshift_deploy.DeploymentConfiguration;
import security.PasswordStorage;

@Path("/api/user")
public class UsersAPI
{
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public String getAllUsers()
    {
        JsonArray ja = new JsonArray();
        for(int i = 0; i < UsersDAO.getAllUsers().size(); i++)
        {
            JsonObject jo = new JsonObject();
            jo.addProperty("userName", UsersDAO.getAllUsers().get(i).getUserName());
            jo.addProperty("email", UsersDAO.getAllUsers().get(i).getEmail());
            ja.add(jo);            
        }
        
        return gson.toJson(ja);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editUser(InputStream incomingData)
    {
        try
        {
            StringBuilder builder = new StringBuilder();
            Flight reserved = null;
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
            System.out.println("Data Received: " + builder.toString());

            JSONObject jsonInput = new JSONObject(builder.toString());

            UsersDAO.editUser(jsonInput);
        }
        catch (Exception e)
        {
            JSONObject error = new JSONObject();
            error.put("httpError", 400);
            error.put("errorCode", 3);
            error.put(
                    "message",
                    "Illegal input: "
                    + e.getMessage());
            return Response.status(400).entity(error.toString()).build();
        }
        return Response.status(200).build();
    }

    @Path("/{role}")
    @GET
    @Produces("application/json")
    public Response getByRole(@PathParam("role") String role)
    {
        try
        {
            List<User> users = UsersDAO.getByRole(role);
            System.out.println("Users list " + users);
            JSONObject jsonObject = new JSONObject();

            List<JSONObject> jsonUsers = new ArrayList<JSONObject>();
            for (User user : users)
            {
                JSONObject jsonUser = new JSONObject();
                jsonUser.put("userName", user.getUserName());
//				jsonUser.put("role", user.getRole()); 

                jsonUsers.add(jsonUser);
            }

            // jsonObject.put("Flights", flights.toString());
            jsonObject.put("users", jsonUsers);
            String result = // "@Produces(\"application/json\")" +
                    jsonObject.toString();

            return Response.status(200).entity(result).build();
        }
        catch (JSONException e)
        {
            JSONObject error = new JSONObject();
            error.put("httpError", 400);
            error.put("errorCode", 3);
            error.put(
                    "message",
                    "Illegal input, id and tickets have to be set "
                    + e.getMessage());
            return Response.status(400).entity(error.toString()).build();
        }
        catch (Exception e)
        {
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

    @Path("/{user}")
    @DELETE
    @Produces("application/json")
    public Response deleteUser(@PathParam("user") String user) throws JSONException
    {
        UsersDAO.deleteUser(user);
        return Response.status(200).build();
    }

    @Path("/new")
    @POST
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(InputStream incomingData)
    {
        StringBuilder builder = new StringBuilder();
        Flight reserved = null;
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
        System.out.println("Data Received: " + builder.toString());

        JSONObject jsonInput = new JSONObject(builder.toString());
        String userName = jsonInput.getString("userName");
        System.out.println(" ----- " + userName);
        String password = jsonInput.getString("passWord");
        System.out.println(" ----- " + password);
        String email = jsonInput.getString("email");
        System.out.println(" ----- " + email);
        String role = jsonInput.getString("role");
        System.out.println(" ----- " + role);

        if (!role.equalsIgnoreCase("Admin") && !role.equalsIgnoreCase("User"))
        {
            JSONObject error = new JSONObject();
            error.put("httpError", 400);
            error.put("errorCode", 3);
            error.put(
                    "message",
                    "Illegal input, the role needs to be \"admin\" or \"user\". ");
            return Response.status(400).entity(error.toString()).build();
        }

        User usr;
        try
        {
            usr = new User(userName, PasswordStorage.createHash(password), email);
        }
        catch (PasswordStorage.CannotPerformOperationException ex)
        {
            JSONObject error = new JSONObject();
            error.put("httpError", 400);
            error.put("errorCode", 3);
            error.put(
                    "message",
                    "Illegal input, choose a new password");
            return Response.status(400).entity(error.toString()).build();
        }
        if (role.equalsIgnoreCase("user"))
        {
            usr.AddRole(DeploymentConfiguration.userRole);
        }
        if (role.equalsIgnoreCase("admin"))
        {
            usr.AddRole(DeploymentConfiguration.adminRole);
        }

        System.out.println(usr.toString());
        UsersDAO.addEntry(usr);
        return Response.status(200).build();
    }

}
