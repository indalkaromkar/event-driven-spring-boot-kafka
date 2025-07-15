package sample.kafka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MessageController.class, excludeAutoConfiguration = {})
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Producer producer;

    @MockBean
    private ApplicationRunner applicationRunner;

    @Test
    void sendMessage() throws Exception {
        mockMvc.perform(post("/api/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"message\":\"test\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Message sent successfully"));

        verify(producer, times(1)).send(any(SampleMessage.class));
    }
}