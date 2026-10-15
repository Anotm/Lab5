package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The lighter "mock" style: same kind of requests, but no real server or port is started.
 */
@SpringBootTest
@AutoConfigureMockMvc
class AddressBookMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void postAndGetBuddyInfo() throws Exception {
        mockMvc.perform(post("/api/buddyInfos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Mick\",\"phoneNumber\":\"613-666-4567\"}"))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/buddyInfos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.buddyInfos[*].name", hasItem("Mick")));
    }

    @Test
    void apiRootIsReachable() throws Exception {
        mockMvc.perform(get("/api")).andExpect(status().isOk());
    }
}
