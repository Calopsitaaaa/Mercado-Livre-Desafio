# or-tools-java-project

## Project Overview
This project is a Java application that utilizes Google's OR-Tools for solving optimization problems. It is structured as a Maven project, making it easy to manage dependencies and build processes.

## Setup Instructions

1. **Clone the Repository**
   Clone this repository to your local machine using:
   ```
   git clone <repository-url>
   ```

2. **Navigate to the Project Directory**
   Change into the project directory:
   ```
   cd or-tools-java-project
   ```

3. **Build the Project**
   Use Maven to build the project and download the necessary dependencies:
   ```
   mvn clean install
   ```

4. **Run the Application**
   To run the application, execute the following command:
   ```
   mvn exec:java -Dexec.mainClass="com.example.App"
   ```

## Usage
The application is designed to demonstrate the capabilities of Google's OR-Tools. You can modify the logic in `src/main/java/com/example/App.java` to implement specific optimization problems.

## Testing
Unit tests for the application can be found in `src/test/java/com/example/AppTest.java`. You can run the tests using:
```
mvn test
```

## Dependencies
This project uses the following dependencies:
- Google's OR-Tools for Java

Make sure to check the `pom.xml` file for the latest version of the OR-Tools dependency.

## Contributing
Contributions are welcome! Please feel free to submit a pull request or open an issue for any enhancements or bugs.

## License
This project is licensed under the MIT License. See the LICENSE file for more details.