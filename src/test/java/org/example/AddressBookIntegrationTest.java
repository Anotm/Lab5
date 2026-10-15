package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Full integration tests: the real application is started on a random port
 * and real HTTP requests are sent to it.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AddressBookIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Test
    void postThenGetBuddyInfo() {
        Map<String, String> buddy = Map.of("name", "Alice", "phoneNumber", "613-111-2222");

        ResponseEntity<String> created = restTemplate.postForEntity("/api/buddyInfos", buddy, String.class);
        assertThat(created.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI location = created.getHeaders().getLocation();
        assertThat(location).isNotNull();

        ResponseEntity<JsonNode> fetched = restTemplate.getForEntity(location, JsonNode.class);
        assertThat(fetched.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(fetched.getBody().get("name").asText()).isEqualTo("Alice");
        assertThat(fetched.getBody().get("phoneNumber").asText()).isEqualTo("613-111-2222");
    }

    @Test
    void postThenGetAddressBook() {
        ResponseEntity<String> created = restTemplate.postForEntity("/api/addressBooks", Map.of(), String.class);
        assertThat(created.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        ResponseEntity<String> list = restTemplate.getForEntity("/api/addressBooks", String.class);
        assertThat(list.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(list.getBody()).contains("addressBooks");
    }

    @Test
    void addressBookPageShowsBuddies() {
        AddressBook book = new AddressBook();
        book.addBuddy(new BuddyInfo("Bob", "613-333-4444"));
        book = addressBookRepository.save(book);

        ResponseEntity<String> page = restTemplate.getForEntity("/addressbook/" + book.getId(), String.class);
        assertThat(page.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(page.getBody()).contains("Bob").contains("613-333-4444");
    }

    @Test
    void unknownAddressBookReturns404() {
        ResponseEntity<String> page = restTemplate.getForEntity("/addressbook/999999", String.class);
        assertThat(page.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void homePageLoads() {
        ResponseEntity<String> page = restTemplate.getForEntity("/", String.class);
        assertThat(page.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(page.getBody()).contains("AddressBook App");
    }
}
