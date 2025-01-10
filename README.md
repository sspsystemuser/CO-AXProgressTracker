# CO-AXProgressTracker Web Application: Data Extraction, Cleaning, and Visualization

## Overview
This is a Java-based Spring Boot application designed to extract data from Trello APIs, clean and process the data, and store it in a PostgreSQL database. The application also integrates with Apache Superset for data visualization.

---

## Features
- Automated data extraction from Trello using REST APIs.
- Data cleaning and transformation processes.
- Scheduled tasks running every 3 hours to keep data updated.
- Data storage in PostgreSQL for structured and optimized querying.
- Environment-specific configurations for development and production.

---

## Project Structure
```
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── com.example.project
│   │   │   │   ├── repository    # Database interaction layer
│   │   │   │   ├── service       # Business logic layer
│   │   │   │   ├── handler       # API call handlers
│   │   │   │   ├── model         # Data models and entities
│   │   │   │   └── scheduler     # Scheduled tasks
│   │   ├── resources
│   │   │   ├── application-dev.properties  # Development configuration
│   │   │   ├── application-prod.properties # Production configuration
│   │   │   └── sql                # SQL scripts for database initialization
├── pom.xml                         # Maven dependencies
└── README.md                       # Project documentation
```

---

## Getting Started

### Prerequisites
- **Java 11+**
- **PostgreSQL 13+**
- **Maven 3.6+**
- **Trello API Key and OAuth Token**

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-org/your-repo.git
   cd your-repo
   ```

2. Configure the database:
   - Create a PostgreSQL database.
   - Update the `application-dev.properties` and `application-prod.properties` files with your database credentials.

3. Add Trello API credentials:
   - Update the `application-dev.properties` and `application-prod.properties` files with your Trello API Key and OAuth Token.

4. Build the project:
   ```bash
   mvn clean install
   ```

5. Run the application:
   ```bash
   mvn spring-boot:run
   ```

---

## Usage

### Scheduler
- The application includes a scheduler that runs every 3 hours to fetch data from Trello APIs and updates the database.

### APIs Used
- `Get Board API`
- `Get Members of Board API`
- `Get Lists of Board API`
- `Get List of Cards of the Board API`
- `Get Actions Performed on Card API`

### Trello API Integration
- Trello APIs are integrated using Spring's **RestTemplate** to handle REST calls.

### Database
- Extracted data is stored in PostgreSQL with tables for boards, members, lists, cards, and actions.
- Views and materialized views are created for efficient querying and analysis.

---

## Configuration

### Application Properties
- **Development:** `src/main/resources/application-dev.properties`
- **Production:** `src/main/resources/application-prod.properties`

Example:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
trello.api.key=your_api_key
trello.api.token=your_oauth_token
```

---

## Contributing

1. Fork the repository.
2. Create a new branch for your feature:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Description of your changes"
   ```
4. Push the changes:
   ```bash
   git push origin feature-name
   ```
5. Submit a pull request.

---

### Acknowledgments  
- [Spring Boot](https://spring.io/projects/spring-boot) for backend development.  
- [PostgreSQL](https://www.postgresql.org/) for database management.  
- [Apache Superset](https://superset.apache.org/) for data visualization.  
- [Trello API](https://developer.atlassian.com/cloud/trello/) for providing the data. 

## Contact
For any questions or issues, please contact [Jinal Solia] at [jinal.solia@gmail.com].