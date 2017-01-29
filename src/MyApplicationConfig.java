import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("introsde.local-database-service")
public class MyApplicationConfig extends ResourceConfig {
    public MyApplicationConfig () {
        packages("resources");
    }
}
