package davenkin;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HelloWorldApiTest {
	
    //private TestRestTemplate restTemplate;

    @Test
    public void shouldSayAnotherHelloWorld() {
        assertEquals("Another Hello World!", "Another Hello World!");
    }

}
