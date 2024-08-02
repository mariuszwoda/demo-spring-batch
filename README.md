# Spring Batch Application Setup and Execution Guide

## Prerequisites

1. **Java 8 or higher**: Ensure you have JDK installed.
2. **Maven**: Ensure Maven is installed and configured.
3. **Oracle Database**: Ensure Oracle Database is set up and accessible.
4. **SSL Certificate**: Ensure you have the SSL certificate file (`certificate.p12`).

## Project Setup

### Clone the Repository

```sh
git clone <repository-url>
cd <repository-directory>
```

### Configure Application Properties

Edit the `src/main/resources/application.properties` file to include your database and SMB details:

```properties
spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/orclpdb1
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

spring.batch.job.enabled=false
spring.batch.jdbc.initialize-schema=always

# SMB Configuration
smb.certPath=path/to/certificate.p12
smb.certPassword=your_cert_password
```

### Build the Project

```sh
mvn clean install
```

## Running the Application

### Run the Spring Boot Application

```sh
mvn spring-boot:run
```

This will start the Spring Boot application and initialize the batch job infrastructure.

### Access Swagger UI

Open your web browser and navigate to:

```
http://localhost:8080/swagger-ui/
```

## Triggering the Job

### Trigger the Batch Job via Swagger UI

1. **Open Swagger UI**: Navigate to `http://localhost:8080/swagger-ui/`.
2. **Find the Batch Controller**: Look for the `/batch/run` endpoint.
3. **Execute the Endpoint**:
    - Click on the `/batch/run` endpoint.
    - Click the "Try it out" button.
    - Fill in the required parameters:
        - `sourcePath`: Path to the source directory on the SMB share.
        - `targetPath`: Path to the target directory on the SMB share.
        - `hostname`: SMB share hostname (e.g., `share-host.com`).
        - `username`: Username for SMB authentication.
        - `password`: Password for SMB authentication.
    - Click the "Execute" button to trigger the job.

### Example Parameters

```json
{
  "sourcePath": "\\\\share-host.com\\source",
  "targetPath": "\\\\share-host.com\\target",
  "hostname": "share-host.com",
  "username": "your_smb_username",
  "password": "your_smb_password"
}
```

## Logs and Monitoring

- **Application Logs**: Logs are available in the console output.
- **Spring Batch Dashboard**: Use Spring Batch Actuator endpoints for monitoring job status.

## Error Handling

- **Check Logs**: Review the console logs for any errors or exceptions.
- **Database**: Check the Spring Batch metadata tables in your Oracle database for job execution details.

---

By following these instructions, you should be able to set up, run, and trigger the Spring Batch application using the provided Swagger UI.