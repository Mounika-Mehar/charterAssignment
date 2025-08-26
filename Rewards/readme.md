
# Rewards System

This project is a Spring Boot application for calculating and managing customer reward points based on transactions. It demonstrates a service-oriented architecture to implement a reward calculation system.

---

## Features

- Calculate reward points for individual transactions  
- Calculate rewards for all transactions of a customer  
- Get monthly reward points for a customer  
- Get total reward points for a customer  
- Get rewards summary for all customers  

---

## Technologies Used

- Java 17  
- Spring Boot  
- Lombok  
- Maven  
- JUnit 5 and Mockito for testing  

---

## Project Structure

- **entity/**  
  Contains classes like `Customer`, `Transaction`, and `RewardPoint`.

- **dto/**  
  Contains Data Transfer Objects (DTOs) such as `MonthlyRewardDTO`, `TotalRewardDTO`, and `CustomerRewardSummaryDTO`.

- **service/**  
  Contains business logic interfaces and implementations like `RewardService` and `RewardServiceImpl`.

- **test/**  
  Contains unit tests for services using JUnit and Mockito.

---

## Reward Calculation Logic

- Transactions above 50 units earn reward points.  
- For every dollar spent between 50 and 100, the customer earns 1 point.  
- For every dollar spent above 100, the customer earns 2 points plus 50 points for the first 50 dollars over 50.  

**Example Calculation:**  

Transaction of 120 →  
- 50 points for spending between 50–100  
- 40 points for spending 20 above 100  
- **Total = 90 points**

---

##  API Endpoints & Example Outputs  

###  Add Transaction  
**POST** `/transactions/{customerId}`  

**Request**  
```json
{
  "amount": 120.00,
  "date": "2025-08-01"
}
````

---

###  Get Monthly Rewards

**GET** `/rewards/{customerId}/monthly`

**Response**

```json
[
  { "month": "JUNE", "points": 90 },
  { "month": "JULY", "points": 75 },
  { "month": "AUGUST", "points": 40 }
]
```

---

###  Get Total Rewards

**GET** `/rewards/{customerId}/total`

**Response**

```json
{
  "customerId": 101,
  "totalPoints": 205
}
```

---

###  Get Rewards for All Customers

**GET** `/rewards/all`

**Response**

```json
[
  { "customerId": 101, "customerName": "Alice", "totalPoints": 205 },
  { "customerId": 102, "customerName": "Bob", "totalPoints": 120 },
  { "customerId": 103, "customerName": "Charlie", "totalPoints": 95 }
]
```

---

###  Get Customer Reward Summary

**GET** `/rewards/{customerId}/customer-rewards`

**Response**

```json
{
  "customerId": 101,
  "monthlyRewards": [
    { "month": "JUNE", "points": 90 },
    { "month": "JULY", "points": 75 },
    { "month": "AUGUST", "points": 40 }
  ],
  "totalPoints": 205
}
```




