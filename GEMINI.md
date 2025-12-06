# Poly_UBs Project Context

## Project Overview

**Poly_UBs** is a Java Spring Boot E-commerce application, developed as an assignment for the SOF3022 course. It follows a standard Spring MVC architecture (Controller-Service-Repository) and uses Thymeleaf for server-side templating.

### Key Technologies
*   **Language:** Java 17
*   **Framework:** Spring Boot 3.5.6
*   **Build Tool:** Maven
*   **Database:** MySQL
*   **Template Engine:** Thymeleaf
*   **Other Libraries:**
    *   **Lombok:** For reducing boilerplate code.
    *   **Firebase Admin:** Likely for file uploads or notifications.
    *   **Spring Security:** For authentication and authorization.
    *   **Dotenv:** For environment variable management.

## Architecture & Structure

The project follows the standard package structure: `com.poly.ubs`

*   `config/`: Configuration classes (Security, WebMvc, DotEnv).
*   `controller/`: Web controllers handling HTTP requests.
*   `entity/`: JPA entities mapping to database tables.
*   `repository/`: Data access layer (Spring Data JPA).
*   `service/`: Business logic layer.
*   `interceptor/`: Custom interceptors (e.g., `AdminAuthInterceptor`).
*   `dto/`: Data Transfer Objects.

### Static Resources
*   **CSS:** Located in `src/main/resources/static/css/`. Organized by feature (admin, auth, user, etc.).
*   **Templates:** Located in `src/main/resources/templates/`.

## Database Setup

The project contains SQL files for initializing the database:
*   `poly_ubs.sql`: Likely the full dump.
*   `poly_ubs_table.sql`: Schema definition.
*   `poly_ubs_data.sql`: Initial data seed.

Ensure your MySQL database is running and configured in `src/main/resources/application.properties` (or via `.env` if configured).

## Building and Running

### Prerequisites
*   Java JDK 17+
*   Maven (wrapper provided)
*   MySQL Server

### Commands

*   **Build:**
    ```bash
    ./mvnw clean install
    ```
*   **Run:**
    ```bash
    ./mvnw spring-boot:run
    ```

## Current Active Task: SePay Integration

The project is currently in the process of integrating **SePay** for QR code bank transfer payments.

**Reference Document:** `PaymentIntegratePlan.md`

**Goals:**
1.  Display a generic SePay QR code on the "Order Complete" page.
2.  Embed the **Bill ID** as the transfer description (`des`).
3.  Embed the **Total Amount** as the transfer amount (`amount`).

**Required Changes:**
1.  **Configuration:** Add SePay config (`account`, `bank code`, `template`) to `application.properties`.
2.  **Frontend (`payment.html`):** Ensure the selected payment method is correctly submitted to the backend (likely requires fixing form nesting or using hidden inputs).
3.  **Backend (`OrderController`):**
    *   Capture `paymentMethod` in `confirmPayment`.
    *   In `complete` method, generate the dynamic SePay QR URL based on the Bill info.
4.  **Frontend (`complete.html`):** Display the generated QR code image.

## Development Conventions

*   **Coding Style:** Standard Java conventions. Lombok is used heavily (`@Data`, `@AllArgsConstructor`, etc.).
*   **Controllers:** Endpoint mappings are standard (e.g., `@GetMapping`, `@PostMapping`).
*   **Configuration:** Use `application.properties` for config, but sensitive keys might be loaded via `DotEnv`.
