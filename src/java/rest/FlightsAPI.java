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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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


import com.metasearch.service.dao.Flight;
import com.metasearch.service.dao.FlightsDAO;
import com.metasearch.service.dao.SearchLogDAO;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/api/flights")
public class FlightsAPI
{


    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    ExecutorService executor = Executors.newFixedThreadPool(4);
    static String[] urls =
    {
        "http://dummyairline6-pagh.rhcloud.com/dummyAirline6/api/flightinfo/",
        "http://dummyairline6v2-pagh.rhcloud.com/dummyAirline6/api/flightinfo/",
        "http://dummyairline6-pagh.rhcloud.com/dummyAirline6/api/flightinfo/",
        "http://dummyairline6v2-pagh.rhcloud.com/dummyAirline6/api/flightinfo/",
        "http://dummyairline6-pagh.rhcloud.com/dummyAirline6/api/flightinfo/",
        "http://dummyairline6v2-pagh.rhcloud.com/dummyAirline6/api/flightinfo/",
        "http://dummyairline6-pagh.rhcloud.com/dummyAirline6/api/flightinfo/"
    };

    @GET
    @Produces("application/json")
    public Response flights() throws JSONException
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Service name", "Metasearch flight API Flight search");

        String result = // "@Produces(\"application/json\")" +
                jsonObject.toString();
        return Response.status(200).entity(result).build();
    }

    @Path("{from}/{date}/{tickets}")
    @GET
    @Produces("application/json")
    public String fromDateTickets(@PathParam("from") String from,
            @PathParam("date") String date, @PathParam("tickets") int tickets)
    {
        List<Future<String>> fList = new ArrayList();
        SearchLogDAO.addEntry(from, null, date, tickets);
//        AuditLogDAO.addEntry(from, null, date, tickets);
        try
        {
            String b = "";
            for (int i = 0; i < urls.length; i++)
            {
                b = "";
                b = urls[i] + from + "/" + date + "/" + tickets;
                System.out.println(b);
                Callable task = new flightOfferRequester(b);

                fList.add(executor.submit(task));

            }
            executor.shutdown();

            JsonArray ja = new JsonArray();
            for (Future<String> fList1 : fList)
            {

                ja.add(gson.toJsonTree(fList1.get()));
            }

//            System.out.println(ja.toString().replace("\\", "").replace("\"\"\"", ""));
            return gson.toJson(ja).replace("\\", "").replace("\"\"\"", "").replace("\"\"", "").replace("n ", "").replace("\"{", "{").replace("\"n}\"", "}");//.replace("\"n}\"", "");
        }
        catch (ExecutionException e)
        {
            throw new NullPointerException();

        }
        catch (InterruptedException ex)
        {
            throw new NullPointerException();
        }

    }

    public static String dateToString(Date departureTime)
    {
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

    private static String padString(int toPad, int size)
    {
        String stringValue = String.valueOf(toPad);
        if (stringValue.length() < size)
        {
            StringBuilder builder = new StringBuilder();
            int toAdd = size - stringValue.length();
            for (int i = 0; i < toAdd; i++)
            {
                builder.append("0");
            }
            builder.append(stringValue);
            return builder.toString();
        }
        return stringValue;
    }

    private Date parseDate(String dateString)
    {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try
        {
            date = parser.parse(dateString);
        }
        catch (ParseException e)
        {
            return null;
        }
        return date;
    }

    @Path("{from}/{to}/{date}/{tickets}")
    @GET
    @Produces("application/json")
    public String fromToDateTickets(@PathParam("from") String from,
            @PathParam("to") String to, @PathParam("date") String date,
            @PathParam("tickets") int tickets) throws JSONException
    {
        List<Future<String>> fList = new ArrayList();
        SearchLogDAO.addEntry(from, to, date, tickets);
//        AuditLogDAO.addEntry(from, to, date, tickets);

        try
        {
            String b = "";
            for (int i = 0; i < urls.length; i++)
            {
                b = "";
                b = urls[i] + from + "/" + to +"/" + date + "/" + tickets;
                System.out.println(b);
                Callable task = new flightOfferRequester(b);

                fList.add(executor.submit(task));

            }
            executor.shutdown();

            JsonArray ja = new JsonArray();
            for (Future<String> fList1 : fList)
            {

                ja.add(gson.toJsonTree(fList1.get()));
            }

//            System.out.println(ja.toString().replace("\\", "").replace("\"\"\"", ""));
            return gson.toJson(ja).replace("\\", "").replace("\"\"\"", "").replace("\"\"", "").replace("n ", "").replace("\"{", "{").replace("\"n}\"", "}");
        }
        catch (ExecutionException e)
        { 
           throw new NullPointerException();

        }
        catch (InterruptedException ex)
        {
            throw new NullPointerException();
        }

    }

}
