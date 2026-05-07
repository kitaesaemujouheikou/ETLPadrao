# Windows Service Java Application

This is a Java application designed to run as a Windows service, executing scheduled functions every 10 minutes.

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- PostgreSQL database server running locally
- NSSM (Non-Sucking Service Manager) for installing as Windows service

## Building the Application

1. Navigate to the project directory:
   ```
   cd c:\Users\Usuario\Documents\ETLPadrao
   ```

2. Build the project using Maven:
   ```
   mvn clean package
   ```

   This will create a JAR file with dependencies in the `target` directory: `windows-service-1.0-SNAPSHOT-jar-with-dependencies.jar`

## Database Configuration

Before running the application, ensure PostgreSQL is installed and running locally.

1. Install PostgreSQL if not already installed.

2. Create a database and user for the application.

3. Update the connection details in `Main.java`:
   - Replace `YOUR_DATABASE` with your database name
   - Replace `YOUR_USERNAME` with your PostgreSQL username
   - Replace `YOUR_PASSWORD` with your PostgreSQL password

4. Optionally, update the SQL query in `performScheduledTask()` to match your database schema.

## Running as a Console Application

To test the application before installing as a service:

```
java -jar target\windows-service-1.0-SNAPSHOT-jar-with-dependencies.jar
```

The application will start and execute the scheduled task every 10 minutes.

## Installing as a Windows Service

1. Download NSSM from https://nssm.cc/download

2. Extract NSSM to a directory (e.g., `C:\nssm`)

3. Open Command Prompt as Administrator

4. Install the service:
   ```
   C:\nssm\nssm.exe install MyJavaService "C:\Program Files\Java\jdk-11\bin\java.exe" "-jar C:\Users\Usuario\Documents\ETLPadrao\target\windows-service-1.0-SNAPSHOT-jar-with-dependencies.jar"
   ```
   Adjust the paths according to your Java installation and project location.

5. Start the service:
   ```
   sc start MyJavaService
   ```

6. To stop the service:
   ```
   sc stop MyJavaService
   ```

7. To remove the service:
   ```
   sc delete MyJavaService
   ```

## Customizing the Scheduled Task

Edit the `performScheduledTask()` method in `Main.java` to implement your specific functionality.

## Troubleshooting

- Ensure Java is installed and JAVA_HOME is set correctly.
- Check Windows Event Viewer for service-related errors.
- Verify that the JAR file path in NSSM is correct and accessible.

## Dependencies

- PostgreSQL JDBC Driver (included in the JAR)