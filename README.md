## Description
"ExchangeRateService" - it is a RESTful web service that provides current and historical exchange rates. The service allows you to download currency rates for a specific date and receive currency rates for a given currency code and date.

## Technologies
- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database

## Installation and launch
1. Clone the repository:

    ```bash
    git clone https://github.com/yourusername/exchange-rate-service.git
    cd exchange-rate-service
    ```

2. Build a project using Maven:

    ```bash
    mvn clean install
    ```

3. Launch the application:

    ```bash
    mvn spring-boot:run
    ```

## Usage

### Endpoints

#### Downloading currency exchange rates
- **URL:** `/exchange-rate/load`
- **Method:** `GET`
- **Parameters:**
  - `date` (required): date in the format `YYYY-MM-DD`

- **Example:**

    ```bash
    curl -X GET "http://localhost:8080/exchange-rate/load?date=2023-06-28"
    ```

- **Response:**

    ```json
    {
      "message": "Data loaded successfully: X rates"
    }
    ```

#### Получение курса валют

- **URL:** `/exchange-rate`
- **Method:** `GET`
- **Parametrs:**
  - `currencyCode` (required): Currency code (example, `USD`)
  - `date` (required): date in the format `YYYY-MM-DD`

- **Example:**

    ```bash
    curl -X GET "http://localhost:8080/exchange-rate?currencyCode=USD&date=2023-06-28"
    ```

- **Response:**

    ```json
    {
      "id": 1,
      "currencyCode": "USD",
      "date": "2023-06-28",
      "rate": 2.5
    }
    ```

### Developed Kseniya Patapovich
