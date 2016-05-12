package rest;

import java.util.Set;
import javax.ws.rs.core.Application;


@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> resources = new java.util.HashSet<>();
    addRestResourceClasses(resources);
    return resources;
  }







    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(httpErrors.GenericExceptionMapper.class);
        resources.add(httpErrors.NotFoundExceptionMapper.class);
        resources.add(rest.Admin.class);
        resources.add(rest.AirlineAPI.class);
        resources.add(rest.CORSFilter.class);
        resources.add(rest.FlightsAPI.class);
        resources.add(rest.ReservationAPI.class);
        resources.add(rest.UsersAPI.class);
        resources.add(security.JWTAuthenticationFilter.class);
        resources.add(security.Login.class);
        resources.add(security.NotAuthorizedExceptionMapper.class);
        resources.add(security.RolesAllowedFilter.class);
    }
}

