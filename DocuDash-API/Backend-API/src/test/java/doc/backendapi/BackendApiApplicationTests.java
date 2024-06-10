package doc.backendapi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApiApplicationTests {

    @Autowired
    private BackendApiApplication backendApiApplication;

    @Test
    void contextLoads() {
    }

    @Test
    void applicationContextTest() {
        assertThat(backendApiApplication).isNotNull();
    }
}
