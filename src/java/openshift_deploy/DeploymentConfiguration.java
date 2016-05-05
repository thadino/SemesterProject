
package openshift_deploy;



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
public class DeploymentConfiguration implements ServletContextListener {

  public static String PU_NAME = "FlightService";

  @Override
  public void contextInitialized(ServletContextEvent sce) {
      //If we are testing, then this:
     if(sce.getServletContext().getInitParameter("testEnv")!=null){
         PU_NAME="PU_TEST";
     }
    Map<String, String> env = System.getenv();
    //If we are running in the OPENSHIFT environment change the pu-name 
    if (env.keySet().contains("OPENSHIFT_MYSQL_DB_HOST")) {
      PU_NAME = "PU_OPENSHIFT";
    }
    
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
  }
}