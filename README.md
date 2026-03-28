## Functional Coverage

### Implemented in Code

#### Read Scenario
- Browse shows by **movie + city + date**
- Returns theatre, screen, show timings, language, base price, and available seats

#### Seat Layout
- View seat availability for a selected show
- Returns seat number, row, seat type, status, and price

#### Write Scenario
- Book movie tickets by selecting a show and preferred seats
- Includes seat validation, availability checks, booking creation, and seat status update

#### Pricing / Offers
- **50% discount on the third ticket**
- **20% discount on afternoon shows**
- Pricing rules implemented using **Strategy Pattern**
- Multiple offer rules can be combined

#### Technical Coverage
- Spring Boot REST APIs
- Relational DB design using JPA
- Global exception handling
- Request validation
- Swagger/OpenAPI documentation
- Sample seed data using `data.sql`

---