# Developer Documentation

## Architecture Overview

This Spring Boot application implements an event-driven architecture using Apache Kafka for asynchronous message processing.

### Core Components

#### 1. Application Entry Point
- **[SampleKafkaApplication](src/main/java/sample/kafka/SampleKafkaApplication.java)**: Main Spring Boot application class
- **ApplicationRunner Bean**: Automatically sends 19 sample messages on startup

#### 2. Message Producer
- **[Producer](src/main/java/sample/kafka/Producer.java)**: Kafka message publisher
- Uses `KafkaTemplate<String, String>` for message sending
- Configured topic via `${cloudkarafka.topic}` property

#### 3. Message Consumer
- **[Consumer](src/main/java/sample/kafka/Consumer.java)**: Kafka message listener
- `@KafkaListener` annotation for automatic message consumption
- Extracts partition, topic, and offset metadata

#### 4. REST API
- **[MessageController](src/main/java/sample/kafka/MessageController.java)**: REST endpoint for message publishing
- Single POST endpoint: `/api/messages`
- OpenAPI/Swagger documentation integrated

#### 5. Data Models
- **[SampleMessage](src/main/java/sample/kafka/SampleMessage.java)**: Core message entity
- **[MessageRequest](src/main/java/sample/kafka/MessageRequest.java)**: REST API request DTO

## Technical Stack

### Dependencies
- **Spring Boot 2.7.18**: Application framework
- **Spring Kafka**: Kafka integration
- **Spring Web**: REST API support
- **SpringDoc OpenAPI**: API documentation
- **Jackson**: JSON serialization

### Configuration

#### Kafka Configuration
```properties
# Connection
spring.kafka.bootstrap-servers=${CLOUDKARAFKA_BROKERS}
spring.kafka.properties.security.protocol=SASL_SSL
spring.kafka.properties.sasl.mechanism=SCRAM-SHA-256

# Authentication
spring.kafka.properties.sasl.jaas.config=org.apache.kafka.common.security.scram.ScramLoginModule required username="${CLOUDKARAFKA_USERNAME}" password="${CLOUDKARAFKA_PASSWORD}";

# Consumer Configuration
spring.kafka.consumer.group-id=${CLOUDKARAFKA_USERNAME}-consumers
spring.kafka.consumer.auto-offset-reset=latest
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer

# Producer Configuration
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer
```

## Development Setup

### Local Development

1. **Environment Setup**
   ```bash
   # Required environment variables
   export CLOUDKARAFKA_BROKERS="localhost:9092"  # or your Kafka cluster
   export CLOUDKARAFKA_USERNAME="your-username"
   export CLOUDKARAFKA_PASSWORD="your-password"
   ```

2. **Build and Test**
   ```bash
   mvn clean compile
   mvn test
   mvn spring-boot:run
   ```

### Testing

#### Unit Tests
- **Location**: `src/test/java/sample/kafka/`
- **Coverage**: All main components have corresponding test classes
- **Framework**: Spring Boot Test with embedded Kafka

#### Running Tests
```bash
# All tests
mvn test

# Specific test class
mvn test -Dtest=MessageControllerTest

# Integration tests
mvn verify
```

## API Design

### REST Endpoints

#### POST /api/messages
**Purpose**: Send message to Kafka topic

**Request**:
```json
{
  "id": 1,
  "message": "Hello Kafka"
}
```

**Response**:
```json
"Message sent successfully"
```

**Implementation Flow**:
1. Controller receives `MessageRequest`
2. Converts to `SampleMessage`
3. Producer sends to configured Kafka topic
4. Returns success response

## Message Flow

### Producer Flow
```
REST API → MessageController → Producer → KafkaTemplate → Kafka Topic
```

### Consumer Flow
```
Kafka Topic → @KafkaListener → Consumer.processMessage() → Console Output
```

### Message Format
- **Serialization**: String-based (message content only)
- **Headers**: Kafka metadata (partition, offset, topic)
- **Logging**: Structured console output

## Extension Points

### Adding New Message Types

1. **Create Message Class**
   ```java
   public class CustomMessage {
       private String type;
       private Object payload;
       // getters/setters
   }
   ```

2. **Update Producer**
   ```java
   public void send(CustomMessage message) {
       // Custom serialization logic
   }
   ```

3. **Update Consumer**
   ```java
   @KafkaListener(topics = "custom-topic")
   public void processCustomMessage(String message) {
       // Custom deserialization logic
   }
   ```

### Configuration Customization

#### Multiple Topics
```properties
app.topics.orders=${CLOUDKARAFKA_USERNAME}-orders
app.topics.notifications=${CLOUDKARAFKA_USERNAME}-notifications
```

#### Consumer Groups
```properties
spring.kafka.consumer.group-id=${CLOUDKARAFKA_USERNAME}-${app.service.name}
```

## Monitoring and Observability

### Logging
- **Producer**: Message sent confirmation
- **Consumer**: Message received with metadata
- **Error Handling**: Exception logging with context

### Metrics (Future Enhancement)
- Message throughput
- Consumer lag
- Error rates
- Processing latency

## Security Considerations

### Authentication
- SASL/SCRAM-SHA-256 for Kafka authentication
- Environment-based credential management

### Best Practices
- No hardcoded credentials
- Secure environment variable handling
- SSL/TLS for data in transit

## Performance Tuning

### Producer Optimization
```properties
spring.kafka.producer.batch-size=16384
spring.kafka.producer.linger-ms=5
spring.kafka.producer.compression-type=snappy
```

### Consumer Optimization
```properties
spring.kafka.consumer.max-poll-records=500
spring.kafka.consumer.fetch-min-size=1024
spring.kafka.listener.concurrency=3
```

## Deployment

### Docker Support (Future)
```dockerfile
FROM openjdk:11-jre-slim
COPY target/spring-boot-sample-kafka-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### Environment-Specific Configuration
- **Development**: Local Kafka cluster
- **Staging**: Managed Kafka service
- **Production**: High-availability Kafka cluster

## Contributing

### Code Style
- Follow Spring Boot conventions
- Use meaningful variable names
- Add JavaDoc for public methods
- Maintain test coverage above 80%

### Pull Request Process
1. Create feature branch
2. Add/update tests
3. Update documentation
4. Submit PR with clear description