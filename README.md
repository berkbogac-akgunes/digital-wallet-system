# Digital Wallet System

A backend-focused digital wallet application built with **Spring Boot**.  
The project implements authentication, wallet management, transactions, and a reward system.

A lightweight React frontend is included only as a simple UI to interact with the backend API.

## Features

- User registration and login
- JWT authentication
- Wallet balance tracking
- Deposit and withdraw functionality
- Reward system with 5-second cooldown
- Transaction history
- Simple pixel-style frontend

## Tech Stack

### Backend
- Java
- Spring Boot
- Spring Security
- JWT Authentication
- PostgreSQL
- JPA / Hibernate

### Frontend
- React
- TailwindCSS
- Vite

## Project Structure

```
digital-wallet-system
backend  - Spring Boot API
frontend - React UI
```

## API Endpoints

Auth
- POST `/auth/register`
- POST `/auth/login`

Wallet
- GET `/wallet/balance`
- POST `/wallet/deposit`
- POST `/wallet/withdraw`
- POST `/wallet/reward`
- GET `/wallet/transactions`

## Demo Gameplay

Users can:
- Click the coin to earn rewards
- Purchase items using wallet balance
- View transaction history

## Future Improvements

- Docker support
- CI/CD pipeline
- AWS deployment