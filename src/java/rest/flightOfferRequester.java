/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author kaspe
 */
public class flightOfferRequester implements Callable<String>
{

    private final String ipToPing;
    public flightOfferRequester(String ipToPing)
    {
        this.ipToPing = ipToPing;
    }

    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    List<Future<String>> fList = new ArrayList();

    ExecutorService executor = Executors.newFixedThreadPool(5);

    @Override
    public String call() throws Exception
    {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(ipToPing);
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        String line = "";
        
        String a = "";
        StringBuilder sb = new StringBuilder();
        JsonObject jo1 = new JsonObject();
        while ((line = rd.readLine()) != null)
        {
            sb.append(line);
            a += line;
            
//            jo1.add("flightOffer", gson.toJsonTree(line));
            
//            System.out.println("line---: " + line);
        }
//        a = a + ",";
        jo1.addProperty("url", ipToPing);
        jo1.add("flightOffer", gson.toJsonTree(a, String.class));
        System.out.println(a);
        
        return gson.toJson(jo1);
    }
}
