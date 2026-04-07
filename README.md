# 💰 Finance Dashboard Backend (Production-Ready)

A scalable and production-oriented backend system built using Spring Boot that enables secure financial record management along with dynamic, real-time analytics.

---

# 🚀 Project Objective

The goal of this project is to design a backend system that is not just functional, but also reflects real-world engineering practices:

* Efficient handling of financial records
* Secure authentication and authorization
* Scalable analytics with optimized performance
* Clean architecture following SOLID principles

---

# 🧠 High-Level Design (HLD)

## Architecture Overview

```
Client (Postman / Frontend)
        ↓
Controller Layer (REST APIs)
        ↓
Service Layer (Business Logic)
        ↓
Repository Layer (Data Access)
        ↓
Database (PostgreSQL - Neon)
        ↓
Cache Layer (Redis)
```

---

## Modules

### 🔐 Auth Module

* User registration
* Login with JWT token generation

### 👤 User Module

* Role management (ADMIN / ANALYST / VIEWER)

### 📦 Record Module

* Create, update, delete financial records
* Pagination support
* Soft delete implementation

### 📊 Dashboard Module

* Summary analytics
* Category breakdown
* Monthly trends
* Dynamic filtering

---

# 🔐 Security Flow

```
Login → Generate JWT → Attach token in request → 
JWT Filter validates → SecurityContext set → 
Access controlled via @PreAuthorize
```

---

# 🧩 Low-Level Design (LLD)

## Core Layers

### Entity Layer

* `User`
* `FinancialRecord`
* `AuditLog`

### DTO Layer

* Request DTOs → validation
* Response DTOs → clean API output
* Dashboard DTOs → analytics data

### Service Layer

* Business logic
* Filtering & aggregation
* Security context usage

### Repository Layer

* JPA repositories
* Specification-based filtering

### Controller Layer

* REST endpoints
* Standardized responses

---

# 📊 Dashboard Features

* Total income vs expense
* Category-wise spending
* Monthly trends
* Dynamic filters (date, type, category)

---

# ⚡ Optimization Strategy

### Initial Approach

* Fetch records
* Aggregate in memory

### Optimized Approach

* Efficient DB queries
* Redis caching for repeated dashboard calls

---

# 🔐 Authentication & Authorization

## JWT

* Stateless authentication
* Token includes:

  * email
  * role

## RBAC

| Role    | Access            |
| ------- | ----------------- |
| ADMIN   | Full access       |
| ANALYST | View analytics    |
| VIEWER  | Restricted access |

---

# 📡 API ENDPOINTS (IMPORTANT 🔥)

---

## 🔐 Auth APIs

| Method | Endpoint             | Description     |
| ------ | -------------------- | --------------- |
| POST   | `/api/auth/register` | Register user   |
| POST   | `/api/auth/login`    | Login & get JWT |

---

## 📦 Record APIs

| Method | Endpoint            | Access | Description             |
| ------ | ------------------- | ------ | ----------------------- |
| POST   | `/api/records`      | ADMIN  | Create record           |
| GET    | `/api/records`      | ALL    | Get records (paginated) |
| PUT    | `/api/records/{id}` | ADMIN  | Update record           |
| DELETE | `/api/records/{id}` | ADMIN  | Soft delete record      |

---

## 📊 Dashboard APIs

| Method | Endpoint                  | Access   | Description        |
| ------ | ------------------------- | -------- | ------------------ |
| POST   | `/api/dashboard/summary`  | ANALYST+ | Income vs Expense  |
| POST   | `/api/dashboard/category` | ANALYST+ | Category breakdown |
| POST   | `/api/dashboard/monthly`  | ANALYST+ | Monthly trends     |

---

## 📜 Audit Logs

| Method | Endpoint     | Access | Description          |
| ------ | ------------ | ------ | -------------------- |
| GET    | `/api/audit` | ADMIN  | View system activity |

---

# 🧠 Soft Delete (Important Design Decision)

Instead of permanently deleting records, the system uses a **soft delete strategy**:

```
is_deleted = true
```

### Why this matters:

* Prevents accidental data loss
* Enables audit tracking
* Allows future recovery
* Matches real-world financial systems

### How it works:

* Records are never removed from DB
* Queries always filter:

```sql
WHERE is_deleted = false
```

* Delete API simply updates flag instead of removing data

---

# 🗄️ Database Design

## Users

* id, name, email, password, role, status

## Financial Records

* id, amount, type, category, date
* user_id (FK)
* is_deleted

---

## Key Concepts

* Soft delete
* Foreign key relationships
* Indexed fields:

  * user_id
  * date
  * type

---

# ⚙️ Key Features

* JWT Authentication
* RBAC Authorization
* Financial Record CRUD
* Soft Delete
* Pagination
* Global Exception Handling
* Clean API Responses
* Dashboard Analytics
* Redis Caching

---

# 🧪 Validation & Error Handling

* Centralized exception handler
* Consistent error structure
* Handles:

  * invalid input
  * missing data
  * unauthorized access

---

# 🧠 SOLID Principles

* Single Responsibility → clear separation of layers
* Open/Closed → easy to extend filters & reports
* Liskov Substitution → abstraction via interfaces
* Interface Segregation → DTO separation
* Dependency Inversion → services depend on abstractions

---

# ⚡ Performance Considerations

* DB aggregation over in-memory loops
* Redis caching for repeated queries
* Indexed queries for fast filtering

---

# ⚖️ Trade-offs

| Decision       | Reason               |
| -------------- | -------------------- |
| JWT            | Stateless & scalable |
| Soft delete    | Data safety          |
| DB aggregation | Performance          |
| Redis          | Faster analytics     |
| DTO separation | Clean API            |

---

# 🚀 Future Improvements

* Export reports (CSV / PDF)
* Advanced analytics
* Multi-tenant system
* Microservices architecture
* Async audit logging

---

# 🧾 Setup Instructions

```
1. Clone repository
2. Configure DB in application.properties
3. Run: mvn spring-boot:run
4. Open Swagger:
   http://localhost:8081/swagger-ui/index.html
```

---

# 🎯 Conclusion

This project focuses on building a backend system that is not only functional but also scalable, secure, and aligned with real-world backend practices.

---

**Status:** ✅ Production-ready backend system
