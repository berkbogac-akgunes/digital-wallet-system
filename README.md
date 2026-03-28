# Digital Wallet Backend System

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)
![Docker](https://img.shields.io/badge/Docker-Compose-blue)

Production-style backend system built with Spring Boot, implementing secure JWT-based authentication, wallet management, and transaction tracking. Containerized with Docker, deployed on AWS EC2, and supported by a CI/CD pipeline using GitHub Actions.

🔗 Live Demo: http://13.62.99.211:5173

---

## Overview

This project simulates a real-world backend system with:
- Secure authentication (JWT)
- Wallet balance management
- Transaction tracking
- Reward system with cooldown logic
- RESTful API design with layered architecture

---

## Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Security
- JPA / Hibernate
- PostgreSQL
  
### Frontend (Lightweight UI)
- React
- Vite
- Tailwind CSS

### Infrastructure
- Docker / Docker Compose
- AWS EC2 (Deployment)
- GitHub Actions (CI/CD)
  
### Testing
- Mockito (Unit Testing)

---

## Key Features
- Stateless JWT-based authentication
- Wallet operations (deposit, withdraw, balance tracking)
- Transaction history tracking
- Reward system with cooldown logic
- Inventory system for user-owned items
- Containerized deployment with Docker
- Automated CI/CD pipeline

---

## Architecture

Layered architecture:

- Controller
- Service
- Repository
- Security
- Exception Handling

---

## CI/CD Pipeline

Pipeline is triggered on every push to the main branch:

- Runs Maven tests
- Builds Docker image
- Deploys to AWS EC2 via SSH

---

## API Endpoints

### Auth
- POST /auth/register
- POST /auth/login

### Wallet
- GET /wallet/balance
- POST /wallet/deposit
- POST /wallet/withdraw
- POST /wallet/reward
- GET /wallet/transactions
- GET /wallet/inventory

---

## Running Locally

```bash
docker compose up --build
```

- Frontend: http://localhost:5173
- Backend: http://localhost:8080

---

## Notes
This project was built to simulate a production-ready backend system and explore real-world architecture, deployment, and CI/CD practices.

---
