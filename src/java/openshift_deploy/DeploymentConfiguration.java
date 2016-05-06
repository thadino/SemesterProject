package openshift_deploy;


import com.metasearch.service.dao.Reservation;

import com.metasearch.service.dao.Role;
import com.metasearch.service.dao.User;
import facades.UserFacade;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.ws.rs.core.Context;
import security.PasswordStorage;

@WebListener

public class DeploymentConfiguration implements ServletContextListener
{

    public static String PU_NAME = "FlightService";

    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
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
            Role userRole = new Role("User");
            Role adminRole = new Role("Admin");

            User user = new User("user", PasswordStorage.createHash("test"), "user@user.dk");
            User admin = new User("admin", PasswordStorage.createHash("test"), "admin@admin.dk");
            User both = new User("user_admin", PasswordStorage.createHash("test"), "user_admin@user_admin.dk");
            user.AddRole(userRole);
            admin.AddRole(adminRole);
            both.AddRole(userRole);
            both.AddRole(adminRole);

            Reservation r1 = new Reservation("user@user.dk", "User", "12345678", 1, "[{'firstName':'Peter','lastName':'Peterson'}]", "CPH-STN-292");
            Reservation r2 = new Reservation("user@user.dk", "User", "12345678", 2, "[{'firstName':'Peter','lastName':'Peterson'},{'firstName':'john','lastName':'john'}]", "CPH-STN-075");

            Reservation r3 = new Reservation("admin@admin.dk", "admin", "12323445678", 1, "[{'firstName':'admin','lastName':'adminsen'}]", "CPH-STN-075");
            Reservation r4 = new Reservation("admin@admin.dk", "admin", "12345234678", 2, "[{'firstName':'admin','lastName':'adminsen'},{'firstName':'user','lastName':'userino'}]", "CPH-STN-020");

            Reservation r5 = new Reservation("user_admin@user_admin.dk", "User", "12345678", 1, "[{'firstName':'user_admin','lastName':'user_adminsen'}]", "CPH-STN-075");
            Reservation r6 = new Reservation("user_admin@user_admin.dk", "User", "12345678", 1, "[{'firstName':'user_admin','lastName':'user_adminsen'},{'firstName':'user','lastName':'bubber'}]", "CPH-STN-254");

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
                em.getTransaction().commit();
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

