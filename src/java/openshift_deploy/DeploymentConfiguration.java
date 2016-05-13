package openshift_deploy;

import com.metasearch.service.dao.Airline;
import com.metasearch.service.dao.Reservation;

import com.metasearch.service.dao.Role;
import com.metasearch.service.dao.User;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javax.persistence.Query;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import security.PasswordStorage;

@WebListener

public class DeploymentConfiguration implements ServletContextListener
{

    public static Role userRole = new Role("User");
    public static Role adminRole = new Role("Admin");
    public static String PU_NAME = "FlightService";

    /**
     *
     */
    public static List<Airline> urls;

    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        urls = new ArrayList();
        //If we are testing, then this:
        if (sce.getServletContext().getInitParameter("testEnv") != null)
        {
            PU_NAME = "PU_TEST";
        }
        Map<String, String> env = System.getenv();
        //If we are running in the OPENSHIFT environment change the pu-name 
        if (env.keySet().contains("OPENSHIFT_MYSQL_DB_HOST"))
        {
            PU_NAME = "PU_OPENSHIFT";
        }
        try
        {
            ServletContext context = sce.getServletContext();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
            EntityManager em = emf.createEntityManager();

            //This flag is set in Web.xml -- Make sure to disable for a REAL system
            boolean makeTestUsers = context.getInitParameter("makeTestUsers").toLowerCase().equals("true");
            if (!makeTestUsers
                    || (em.find(User.class, "user") != null && em.find(User.class, "admin") != null && em.find(User.class, "user_admin") != null))
            {
                return;
            }

            User user = new User("user", PasswordStorage.createHash("test"), "user@user.dk");
            User admin = new User("admin", PasswordStorage.createHash("test"), "admin@admin.dk");
            User both = new User("user_admin", PasswordStorage.createHash("test"), "user_admin@user_admin.dk");
            user.AddRole(userRole);
            admin.AddRole(adminRole);
            both.AddRole(userRole);
            both.AddRole(adminRole);

            Reservation r1 = new Reservation("user@user.dk", "user", "12345678", 1, "[{\"firstName\":\"Peter\",\"lastName\":\"Peterson\"}]", "CPH-STN-292");
            Reservation r2 = new Reservation("user@user.dk", "user", "12345678", 2, "[{\"firstName\":\"Peter\",\"lastName\":\"Peterson\"},{\"firstName\":\"john\",\"lastName\":\"john\"}]", "CPH-STN-075");

            Reservation r3 = new Reservation("admin@admin.dk", "admin", "12323445678", 1, "[{\"firstName\":\"admin\",\"lastName\":\"adminsen\"}]", "CPH-STN-075");
            Reservation r4 = new Reservation("admin@admin.dk", "admin", "12345234678", 2, "[{\"firstName\":\"admin\",\"lastName\":\"adminsen\"},{\"firstName\":\"user\",\"lastName\":\"userino\"}]", "CPH-STN-020");

            Reservation r5 = new Reservation("user_admin@user_admin.dk", "user_admin", "12345678", 1, "[{\"firstName\":\"user_admin\",\"lastName\":\"user_adminsen\"}]", "CPH-STN-075");
            Reservation r6 = new Reservation("user_admin@user_admin.dk", "user_admin", "12345678", 1, "[{\"firstName\":\"user_admin\",\"lastName\":\"user_adminsen\"},{\"firstName\":\"user\",\"lastName\":\"bubber\"}]", "CPH-STN-254");

            Airline al1 = new Airline("Group6airlines1", "http://dummyairline6-pagh.rhcloud.com/dummyAirline6/api/flights/");
            Airline al2 = new Airline("angularairline", "http://dummyairline6-pagh.rhcloud.com/dummyAirline6/api/flights/");
            Airline al3 = new Airline("Group6airlines3", "http://magnustest.cloudapp.net/api/api/flights/");
            Airline al4 = new Airline("angularairline", "http://dummyairline6-pagh.rhcloud.com/dummyAirline6/api/flights/");
            Airline al5 = new Airline("Group6airlines5", "http://dummyairline6-pagh.rhcloud.com/dummyAirline6/api/flights/");
            Airline al6 = new Airline("angularairline", "http://magnustest.cloudapp.net/api/api/flights/");
            Airline al7 = new Airline("Group6airlines7", "http://dummyairline6-pagh.rhcloud.com/dummyAirline6/api/flights/");
            try
            {
                em.getTransaction().begin();
                em.persist(userRole);
                em.persist(adminRole);

                em.persist(user);
                em.persist(admin);
                em.persist(both);

                em.persist(r1);
                em.persist(r2);
                em.persist(r3);
                em.persist(r4);
                em.persist(r5);
                em.persist(r6);

                em.persist(al1);
                em.persist(al2);
                em.persist(al3);
                em.persist(al4);
                em.persist(al5);
                em.persist(al6);
                em.persist(al7);

                em.getTransaction().commit();

                Query q = em.createNamedQuery("airline.findAll", Airline.class);
                for (int a = 0; a < q.getResultList().size(); a++)
                {
                    Airline bob = (Airline) q.getResultList().get(a);
                    urls.add(bob);
                    System.out.println("Følgende URL lagt i liste: " + urls.get(a).getUrl());
                }

            }
            finally
            {
                em.close();
            }
        }
        catch (PasswordStorage.CannotPerformOperationException ex)
        {
            Logger.getLogger(DeploymentConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {
    }
}
