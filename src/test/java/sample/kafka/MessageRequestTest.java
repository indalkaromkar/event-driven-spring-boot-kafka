package sample.kafka;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessageRequestTest {

    @Test
    void gettersAndSetters() {
        MessageRequest request = new MessageRequest();
        request.setId(1);
        request.setMessage("test");

        assertThat(request.getId()).isEqualTo(1);
        assertThat(request.getMessage()).isEqualTo("test");
    }
}