package jcn.deduce.server.util.endpoints;

import jcn.deduce.server.model.DeduceMatch;
import jcn.deduce.server.model.DeduceResponseEntity;
import jcn.deduce.server.db.DeduceMatchRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestMocks {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean(name="deduceMatchRepository")
    private DeduceMatchRepository mockRepo;

    @Before
    public void setup()
    {
        DeduceMatch myMatch = new DeduceMatch("WORD");
        myMatch.setId("1234");

        given(this.mockRepo.save(any(DeduceMatch.class))).willReturn(myMatch);
        given(this.mockRepo.findById("1234")).willReturn(myMatch);
    }

    @Test
    public void test()
    {
        ResponseEntity<DeduceResponseEntity> createdEntity =
                this.restTemplate.postForEntity("/createMatch",
                        new LinkedMultiValueMap<String, String>(),
                        DeduceResponseEntity.class);
    }

}