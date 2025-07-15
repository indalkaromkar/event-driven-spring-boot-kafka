package sample.kafka;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SampleMessageTest {

    @Test
    void createMessage() {
        SampleMessage message = new SampleMessage(1, "test message");
        
        assertThat(message.getId()).isEqualTo(1);
        assertThat(message.getMessage()).isEqualTo("test message");
        assertThat(message.toString()).isEqualTo("SampleMessage{id=1, message='test message'}");
    }
}