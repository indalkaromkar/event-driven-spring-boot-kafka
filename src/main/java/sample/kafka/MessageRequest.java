package sample.kafka;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Message request payload")
public class MessageRequest {
    
    @Schema(description = "Message ID", example = "1")
    private Integer id;
    
    @Schema(description = "Message content", example = "Hello Kafka")
    private String message;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}