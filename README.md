# 🏦 MetLife Claims Processing Platform  
### _Built during MetLife ##########_

---

## 🚀 Overview
The **Claims Processing Platform** is a backend system developed for the **MetLife Hack4Job 2025 Hackathon**, focusing on efficient claim lifecycle management using **Spring Boot** and **MongoDB**.  
It provides structured APIs for user management, claim tracking, and admin dashboard analytics — built for scalability, clarity, and real-world insurance workflows.

---

## 💡 Key Features

### 👥 User Management
- Register and manage users  
- Prevent duplicate registrations using email uniqueness validation  

### 💰 Claims Management
- Submit new claims with policy and claim details  
- Track claim statuses: `PASSED`, `REJECTED`, `PROCESSING`  
- Automatically persist claim data in MongoDB  

### 📊 Admin Dashboard APIs
- Total Claims  
- Top 5 High-Value Claims  
- Claims Passed, Rejected, and Processing counts  
- Total Claimed Amount (per status type)  

### 🧾 API Design
- REST-compliant API endpoints  
- Proper HTTP responses and exception handling  
- Integrated with Swagger UI for documentation  

---

## 🧩 Tech Stack

| Component | Technology |
|------------|-------------|
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.x |
| **Database** | MongoDB (Compass + Community Server) |
| **Build Tool** | Maven |
| **API Docs** | Swagger (Springdoc OpenAPI 3) |
| **Version Control** | Git + GitHub |
| **Deployment Ready** | Dockerfile template (backend only) |

---

| Endpoint                  | Method | Description                  |
| ------------------------- | ------ | ---------------------------- |
| `/api/users`              | POST   | Create a new user            |
| `/api/users`              | GET    | Get all users                |
| `/api/claims`             | POST   | Submit a new claim           |
| `/api/claims`             | GET    | Retrieve all claims          |
| `/api/dashboard/summary`  | GET    | Get total counts and amounts |
| `/api/claims/high-amount` | GET    | Get top 5 high-value claims  |
