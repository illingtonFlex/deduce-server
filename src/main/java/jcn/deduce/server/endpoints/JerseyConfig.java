package jcn.deduce.server.endpoints;


import jcn.deduce.server.model.JacksonObjectMapperProvider;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig
{
    public JerseyConfig()
    {
        packages("jcn.deduce.server");
        register(Create.class);
        register(ListWords.class);
//        register(CORSResponseFilter.class);

        //logging
//        register(new LoggingFilter(Logger.getLogger(JerseyConfig.class.getName()), true));

        //JSON parsing/conversion
        register(JacksonObjectMapperProvider.class);
        register(JacksonFeature.class);

    }
}
