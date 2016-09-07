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
        ResponseEntity<DeduceResponseEntity> entity =
                this.restTemplate.postForEntity("/createMatch",
                        new LinkedMultiValueMap<String, String>(),
                        DeduceResponseEntity.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(entity.getBody().getStatus()).isEqualTo(Response.Status.CREATED);
        assertThat(entity.getBody().getMessage()).contains("Match created: ");
        assertThat(entity.getHeaders().containsKey("Location")).isTrue();
        assertThat(entity.getHeaders().get("Location").size()).isEqualTo(1);

        String id = (String)((LinkedHashMap)entity.getBody().getEntity()).get("id");

        entity = this.restTemplate.getForEntity(String.format("/%s/details", id), DeduceResponseEntity.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);

        //TODO: Keep going.
    }
}
