package sample.kafka;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private Producer producer;

    @Test
    void send() {
        ReflectionTestUtils.setField(producer, "topic", "test-topic");
        SampleMessage message = new SampleMessage(1, "test");
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        producer.send(message);

        verify(kafkaTemplate).send("test-topic", "test");
        assertThat(outputStream.toString()).contains("Sent sample message");
        System.setOut(System.out);
    }
}