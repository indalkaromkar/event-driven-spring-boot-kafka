package sample.kafka;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ConsumerTest {

    @InjectMocks
    private Consumer consumer;

    @Test
    void processMessage() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        consumer.processMessage("test message", 0, "test-topic", 123L);

        assertThat(outputStream.toString()).contains("test-topic-0[123] \"test message\"");
        System.setOut(System.out);
    }
}