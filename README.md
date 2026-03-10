# Digital Wallet Backend System

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)
![Docker](https://img.shields.io/badge/Docker-Compose-blue)

A backend-focused digital wallet application built with **Spring Boot**.  
The system provides wallet management, transaction tracking, and a reward-based coin system.

A simple React frontend is included only as a lightweight UI to interact with the backend API.

---

## Features

- User registration and login
- JWT authentication
- Wallet balance tracking
- Deposit and withdraw operations
- Reward system with cooldown
- Transaction history
- Simple pixel-style UI

---

## Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Security
- JWT Authentication
- PostgreSQL
- JPA / Hibernate

### Frontend
- React
- Vite
- TailwindCSS

### Infrastructure
- Docker
- Docker Compose

---

## Project Structure

--digitalwallet--

backend - Spring Boot wallet API  
frontend - React UI for interacting with the API  
database - PostgreSQL container managed with Docker Compose

---

## Running with Docker

Make sure Docker is installed.

Start the full stack application:

```bash
docker compose up --build
```

Services:

Frontend  
http://localhost:5173

Backend API  
http://localhost:8080

PostgreSQL  
localhost:5432

---

## API Endpoints

### Auth

POST /auth/register  
POST /auth/login

### Wallet

GET /wallet/balance  
POST /wallet/deposit  
POST /wallet/withdraw  
POST /wallet/reward  
GET /wallet/transactions

---

## Demo Gameplay

Users can:

- Click the coin to earn rewards
- Purchase items using wallet balance
- View transaction history

---

## Future Improvements

- CI/CD pipeline
- Cloud deployment
- API documentation (Swagger)