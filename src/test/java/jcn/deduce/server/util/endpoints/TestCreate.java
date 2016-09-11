package jcn.deduce.server.util.endpoints;

import jcn.deduce.server.model.DeduceResponseEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import javax.ws.rs.core.Response;

import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestCreate
{
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreate()
    {
        ResponseEntity<DeduceResponseEntity> createdEntity =
                this.restTemplate.postForEntity("/createMatch",
                        new LinkedMultiValueMap<String, String>(),
                        DeduceResponseEntity.class);

        assertThat(createdEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(createdEntity.getBody().getStatus()).isEqualTo(Response.Status.CREATED);
        assertThat(createdEntity.getBody().getMessage()).contains("Match created: ");
        assertThat(createdEntity.getHeaders().containsKey("Location")).isTrue();
        assertThat(createdEntity.getHeaders().get("Location").size()).isEqualTo(1);

        String id = (String)((LinkedHashMap)createdEntity.getBody().getEntity()).get("id");

        ResponseEntity<DeduceResponseEntity> gettedEntity =
                this.restTemplate.getForEntity(String.format("/%s/details", id), DeduceResponseEntity.class);

        assertThat(gettedEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat((String)((LinkedHashMap)createdEntity.getBody().getEntity()).get("id")).isEqualTo(id);


        //TODO: Keep going.
    }
}
