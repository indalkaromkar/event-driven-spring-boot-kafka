# Spring Boot Kafka Event-Driven Application

A sample Spring Boot application demonstrating event-driven architecture using Apache Kafka for message publishing and consuming.

## Features

- **Event-driven messaging** with Apache Kafka
- **REST API** for sending messages
- **Auto message consumption** with Kafka listeners
- **OpenAPI documentation** with Swagger UI
- **Cloud-ready** configuration for CloudKarafka

## Quick Start

### Prerequisites

- Java 11 or higher
- Maven 3.6+
- Kafka cluster (local or cloud-based like CloudKarafka)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd event-driven-spring-boot-kafka
   ```

2. **Set up environment variables**
   ```bash
   export CLOUDKARAFKA_BROKERS="your-kafka-brokers"
   export CLOUDKARAFKA_USERNAME="your-username"
   export CLOUDKARAFKA_PASSWORD="your-password"
   ```

3. **Build the application**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

## Usage

### API Documentation

Access the interactive API documentation at:
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

### Sending Messages

**Endpoint**: `POST /api/messages`

**Request Body**:
```json
{
  "id": 1,
  "message": "Hello Kafka!"
}
```

**Example using curl**:
```bash
curl -X POST http://localhost:8080/api/messages \
  -H "Content-Type: application/json" \
  -d '{"id": 1, "message": "Hello Kafka!"}'
```

### Automatic Message Generation

The application automatically sends 19 sample messages on startup for demonstration purposes.

### Message Consumption

Messages are automatically consumed and logged to the console in the format:
```
topic-partition[offset] "message"
```

## Configuration

### Environment Variables

| Variable | Description | Required |
|----------|-------------|----------|
| `CLOUDKARAFKA_BROKERS` | Kafka broker URLs | Yes |
| `CLOUDKARAFKA_USERNAME` | Kafka username | Yes |
| `CLOUDKARAFKA_PASSWORD` | Kafka password | Yes |

### Application Properties

Key configuration properties in `application.properties`:

- **Bootstrap Servers**: Kafka cluster connection
- **Security Protocol**: SASL_SSL for secure connections
- **Consumer Group**: Isolated message consumption
- **Topic**: Default topic for message publishing

## Monitoring

### Console Logs

- **Producer logs**: Shows sent messages with topic information
- **Consumer logs**: Displays received messages with partition and offset details

### Health Checks

The application includes Spring Boot Actuator endpoints for monitoring (if enabled).

## Troubleshooting

### Common Issues

1. **Connection refused**: Verify Kafka broker URLs and network connectivity
2. **Authentication failed**: Check username and password credentials
3. **Topic not found**: Ensure the topic exists or auto-creation is enabled

### Debug Mode

Enable debug logging by adding to `application.properties`:
```properties
logging.level.org.springframework.kafka=DEBUG
```

## Support

For issues and questions:
1. Check the console logs for error messages
2. Verify environment variables are set correctly
3. Ensure Kafka cluster is accessible and running