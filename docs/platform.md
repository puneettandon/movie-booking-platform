# Platform Provisioning, Sizing & Release Requirements

## 1. Technology Choices and Key Drivers

### Chosen Stack

* **Language:** Java 17
* **Framework:** Spring Boot 4
* **Database:** Relational DB (H2 for local/demo, PostgreSQL recommended for production)
* **API Documentation:** Swagger / OpenAPI
* **Build Tool:** Maven

### Key Drivers Behind These Choices

The platform is centered around **transaction-heavy booking workflows**, where **data consistency**, **developer productivity**, and **operational simplicity** are more important than early microservice fragmentation.

#### Why Java + Spring Boot

* Strong ecosystem for REST APIs and enterprise systems
* Mature support for transactions, validation, security, and integration
* Good fit for high-throughput backend systems
* Easy extensibility for future service decomposition

#### Why Relational Database

* Booking and seat allocation require **strong consistency**
* Supports transactional guarantees and row-level locking
* Natural fit for entities like:

    * Movie
    * Theatre
    * Screen
    * Show
    * Seat
    * Booking
    * ShowSeatInventory

#### Why Modular Monolith

The current solution is implemented as a **modular monolith** to:

* reduce operational complexity
* simplify local development and deployment
* keep transactions easy in early product stages

This can later evolve into microservices for:

* Booking
* Payments
* Catalog / Discovery
* Partner Integrations
* Notifications

---

## 2. Database, Transactions, and Data Modelling

### Database Choice

For production, I would use **PostgreSQL** as the primary transactional store.

### Why PostgreSQL

* Strong ACID guarantees
* Reliable concurrency control
* Indexing and partitioning support
* Support for replication and failover

### Core Data Model

The most important design decision is to track seat availability **per show**, not globally.

#### Key Entities

* City
* Theatre
* Screen
* Seat
* Movie
* Show
* ShowSeatInventory
* Booking
* BookingSeat

### Important Design Decision

#### `ShowSeatInventory`

This entity tracks:

* which seats belong to a show
* seat status (`AVAILABLE`, `LOCKED`, `BOOKED`)
* seat-level pricing
* temporary lock expiry (`lockUntil`)

This design ensures:

* the same physical seat can be booked independently across multiple shows
* booking state is isolated per screening

### Transaction Handling

The booking flow is the main transactional path.

#### Booking Transaction Steps

1. Validate show exists
2. Fetch selected seat inventory
3. Validate seats are available
4. Apply pricing rules
5. Create booking record
6. Mark seats as booked
7. Persist booking-seat mapping

### Production Improvement

To strengthen concurrency handling at scale:

* use **pessimistic locking** or `SELECT FOR UPDATE`
* use **temporary seat locks with TTL**
* use **idempotent booking requests**

---

## 3. COTS / Enterprise Systems

To avoid building everything in house, I would integrate best fit enterprise products for specialized concerns.

### Recommended COTS / Managed Systems

#### Payments

* Razorpay / Stripe 
* Handles:

    * payment collection
    * refunds
    * payment status callbacks

#### Caching / Seat Hold Management

* Redis
* Used for:

    * seat lock TTL
    * caching frequently accessed show/seat data
    * reducing DB read pressure

#### Messaging / Async Workflows

* Kafka
* Used for:

    * booking confirmation events
    * payment events
    * notifications

#### Monitoring / Observability

* Grafana
* ELK
* PagerDuty / Opsgenie for alerting

#### Notifications

* Twilio / SendGrid
* Used for:

    * booking confirmations
    * reminders
    * cancellation notifications

---

## 4. Hosting Solution and Sizing

## Proposed Hosting Model

**Cloud-native deployment on AWS** 

### Suggested Components

* **Compute:** Kubernetes (EKS) in case of complex microservices 
* **Database:** Amazon RDS PostgreSQL
* **Cache:** Amazon ElastiCache Redis
* **Object Storage:** Amazon S3
* **CDN:** CloudFront
* **Secrets:** AWS Secrets Manager
* **Monitoring:** CloudWatch + Grafana
* **Queueing:** Kafka

### Deployment Model

For an MVP:

* 2–3 application instances
* 1 primary DB + 1 read replica
* 1 Redis cluster
* Load balancer in front of services

### Initial Sizing Assumptions (MVP)

* 50K–100K daily active users
* 1M+ daily show discovery reads
* 50K–100K booking attempts/day
* Peak spikes during weekends / blockbuster releases

### Scaling Strategy

Reads and writes have different traffic profiles:

#### Read-heavy traffic

Examples:
* movie search
* show discovery
* seat layout browsing

Handled by:

* caching
* read replicas
* search indexing (future)

#### Write-heavy traffic

Examples:

* booking seats
* payment confirmation

Handled by:

* transactional DB
* row-level locking
* queue-backed async workflows

### Hybrid / Multi-Cloud

Not needed initially.
Would only be considered for:

* regulatory constraints
* regional data residency
* extreme availability requirements

---

## 5. Release Management Across Geos and Internationalization

### Release Strategy

Use a standard CI/CD pipeline with:

* pull request validation
* unit/integration test gates
* staging deployment
* production rollout

### Recommended CI/CD

* GitHub Actions / GitLab CI / Jenkins

### Release Patterns

* rolling deployments
* blue-green or canary for risky changes
* feature flags for pricing / partner features

### Internationalization / Localization

To support multiple cities and countries:

* store **timezone-aware show timings**
* support multi-language movie metadata
* support currency abstraction
* configurable tax / pricing rules by geography
* region-specific theatre partner integrations

### Geo Expansion Strategy

Expand regionally in phases:

1. single city
2. multi-city
3. multi-country

At scale:

* partition data by region
* route traffic to nearest region
* localize catalog and offers

---

## 6. Monitoring, Alerting, and Log Analysis

### Monitoring Goals

The platform must be observable across:

* application health
* business success metrics
* infrastructure health
* transactional failures

### Metrics to Monitor

#### Application Metrics

* request throughput
* API latency (p50 / p95 / p99)
* error rate
* booking success/failure rate

#### Infrastructure Metrics

* CPU / memory
* DB connection pool utilization
* DB query latency
* cache hit ratio
* queue lag

#### Business Metrics

* booking conversion
* seat fill rate
* payment success rate
* cancellation rate

### Logging Strategy

Use structured JSON logging with:

* request ID
* booking reference
* user ID
* theatre/show identifiers

### Log Stack

* ELK Stack / OpenSearch
* Centralized searchable logs
* Dashboards for booking and payment failures

### Alerting

Critical alerts should be configured for:

* booking API failures
* payment webhook failures
* DB health degradation
* seat lock / booking contention anomalies

---

## 7. Overall KPIs

### Product KPIs

* booking conversion rate
* repeat booking rate
* average order value
* theatre partner adoption
* city expansion success

### Engineering KPIs

* API availability
* p95 latency
* deployment success rate
* incident frequency

### Operational KPIs

* payment success rate
* seat lock timeout rate
* failed booking rate
* support ticket volume

---

## 8. High-Level Project Plan and Estimates

## Delivery Approach

An iterative MVP-first delivery approach is recommended.

### Phase 1 — Core MVP (1 weeks)

* domain model
* browse shows API
* seat layout API
* booking API
* pricing / offers
* seed/demo data
* Swagger docs

### Phase 2 — Reliability & Partner APIs (1 weeks)

* show management APIs
* seat inventory allocation APIs
* cancellation flow
* concurrency hardening
* payment integration stubs

### Phase 3 — Production Readiness (2 weeks)

* auth/security
* Redis seat locking
* monitoring / alerting
* CI/CD
* staging / production deployment

### Team Estimate (Indicative)

For a lean delivery:

* 2 Backend Engineer
* 1 Frontend Engineer
* 1 QA / SDET
* 1 DevOps / Platform Engineer
* 1 Product / Business stakeholder

### Key Trade-off

The current submission intentionally prioritizes:

* **core booking correctness**
* **domain design**
* **API clarity**
  
