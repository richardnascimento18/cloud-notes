# 📓 CloudNotes

A sample Java **Hexagonal Architecture** project built with **Spring Boot**.
CloudNotes demonstrates how to structure a clean, modular application while integrating with **AWS services** (via LocalStack) and **Postgres**.

---

## ✨ Features

* Create and manage **users** and their notes
* **Hexagonal architecture** (Ports & Adapters) for clean separation of concerns
* **REST API** (Spring Boot Web)
* **Relational persistence** with Postgres + JPA
* **Object storage** with S3 (via AWS SDK)
* **Local development** with LocalStack for AWS simulation

---

## 🏗️ Tech Stack

* **Java 17+**
* **Spring Boot 3.5.5**
* **Spring Data JPA** + Hibernate
* **Postgres** (Through Docker)
* **LocalStack** (AWS services simulation)
* **AWS SDK v2** (S3, DynamoDB)
* **Docker + Docker Compose**
* **JUnit 5 / Mockito** for testing

---

## 📂 Project Structure

```
cloudnotes/
 ├── app/                  # Application entry point
 │    └── CloudNotesApp.java
 │
 ├── core/                 # Domain logic (no framework dependencies)
 │    ├── model/           # Entities (User, Note)
 │    ├── ports/           # Input (use cases) and Output (repositories)
 │    └── service/         # Use case implementations
 │
 ├── adapters/             # Technology implementations
 │    ├── in/web/          # REST controllers
 │    ├── out/persistence/ # JPA repositories
 │    ├── out/storage/     # S3 adapter
 │    └── out/nosql/       # DynamoDB adapter
 │
 └── config/               # Spring Boot config beans
```

---

## ⚡ Getting Started

### 1. Clone the repo

```bash
git clone https://github.com/your-username/cloudnotes.git
cd cloudnotes
```

### 2. Start LocalStack + Postgres

```bash
docker-compose up -d
```

### 3. Run the app

```bash
./mvnw spring-boot:run
```

### 4. Try the API

```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"userName": "Alice", "email": "alice@example.com"}'
```

---

## 🧪 Running Tests

```bash
./mvnw test
```

---

## 🎯 Bonus Ideas

* Add JWT authentication with Spring Security
* Extend to Notes management (`/notes` endpoints)
* Persist notes metadata in DynamoDB (via LocalStack)
* Store note attachments in S3

---

## 📜 License

MIT License.

---