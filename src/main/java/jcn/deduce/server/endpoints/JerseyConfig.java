package jcn.deduce.server.endpoints;

import jcn.deduce.server.model.JacksonObjectMapperProvider;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig
{
    public JerseyConfig()
    {
        register(Create.class);
        register(ListWords.class);
        register(LetterAtIndex.class);
        register(Details.class);
        register(Solve.class);

        //JSON parsing/conversion
        register(JacksonObjectMapperProvider.class);
        register(JacksonFeature.class);

        property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, "true");

    }
}
