package com.metasearch.service.dao; 

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;


/**
 * @author Goran
 *
 */
public class FlightsDAOTest { 
	
	
	private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("FlightService");
	
	
	public static void main(String[] args) { 
	        EntityTransaction transaction = null;
	        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
	        try {
	            // Get a transaction
	            transaction = manager.getTransaction();
	            // Begin the transaction
	            transaction.begin();

	            String currentDateString = "02/27/2012 17:00:00";
	            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
	            Date date = parser.parse("2016-07-26"); 
	            List<Flight> flights = FlightsDAO.getFlights("CPH", date, 10); 
	            System.out.println(flights); 
	            
	            List<Flight> flights1 = FlightsDAO.getFlights("CPH", "LIS", date, 20); 
	            System.out.println(flights1); 
	            
	            // Commit the transaction
	            transaction.commit();
	        } catch (Exception ex) {
	            // If there are any exceptions, roll back the changes
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            // Print the Exception
	            ex.printStackTrace();
	        } finally {
	            // Close the EntityManager
	            manager.close();
	        }
	}
}
