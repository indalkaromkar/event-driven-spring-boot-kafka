package sample.kafka;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
@Tag(name = "Messages", description = "Kafka message operations")
public class MessageController {

    private final Producer producer;

    public MessageController(Producer producer) {
        this.producer = producer;
    }

    @PostMapping
    @Operation(summary = "Send message", description = "Send a message to Kafka topic")
    public ResponseEntity<String> sendMessage(@RequestBody MessageRequest request) {
        SampleMessage message = new SampleMessage(request.getId(), request.getMessage());
        producer.send(message);
        return ResponseEntity.ok("Message sent successfully");
    }
}