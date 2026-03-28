# Non-Functional Requirements & Design Considerations

## 1. Transactional Scenarios

### Problem

The core transactional challenge in a booking system is **concurrent seat booking**, where multiple users may attempt to book the same seat simultaneously.

### Current Implementation

* Booking flow is **transactional (@Transactional)**
* Seat availability is validated before booking
* Seat status is updated atomically during booking
* **Pessimistic locking** can be used to reduce race conditions

### Better Approach

* Introduce **seat locking (LOCKED state) with TTL**
* Use **Redis-based distributed locks** for high concurrency
* Implement **idempotent booking APIs** to avoid duplicate bookings
* Use **event-driven confirmation after payment**

---

## 2. Theatre Integration (B2B)

### Approach

* Expose **REST APIs** for theatre partners to:

    * create/update/delete shows
    * upload seat inventory
* Support **batch ingestion APIs** for large theatres
* Use an **adapter layer** to integrate with legacy theatre systems
* Support **localization** (multi-language movies, regional formats)

---

## 3. Scalability & High Availability (99.99%)

### Scaling Strategy

* Use **stateless services** → horizontal scaling
* Partition data by **city/region**
* Use **read replicas** for high read traffic
* Cache frequently accessed data (shows, seat layout) using **Redis**

### Availability Strategy

* Deploy across **multiple availability zones (Multi-AZ)**
* Use **load balancers** and auto-scaling
* Implement **circuit breakers + retries**
* Maintain **database replication and failover**

---

## 4. Payment Integration

### Flow

1. Seats are temporarily locked
2. User initiates payment via gateway (e.g., Razorpay/Stripe)
3. Payment success via **webhook callback**
4. Booking is confirmed and seats marked `BOOKED`

### Design Considerations

* **Idempotent payment handling**
* Retry-safe booking confirmation
* Handle partial failures (payment success but booking failure)

---

## 5. Monetization Strategy

* **Commission per booking** from theatres
* **Convenience fee** for customers
* Premium listing / promotion for theatres
* Ads and featured placements

---

## 6. Security (OWASP Top 10)

* Input validation using annotations (`@Valid`)
* Prevent SQL injection using JPA (parameterized queries)
* Authentication (JWT based) 
* Rate limiting to prevent abuse
* HTTPS for secure communication
* Proper exception handling (no stack trace exposure)

---

## 7. Compliance

* **PCI-DSS** compliance for payment processing
   - Use providers like Stripe, Razorpay, or PayPal
   - Never expose and mask data : CVV, Card Number etc 
   - Use proper authentication and authorization 
* **GDPR / data protection** for user data
* Audit logging for booking and payment events - ELK
