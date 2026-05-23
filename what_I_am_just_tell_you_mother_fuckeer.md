### Technical Specification for Subscription Upgrades

Here are the exact Request and Response bodies for the implemented endpoints.

#### 1. Upgrade Subscription (Payment Flow)
This endpoint creates a pending payment. It requires card details but **does not store them**.

**Endpoint:** `PATCH http://localhost:8080/api/user/upgrade-subscription`

**Request Body:**
```json
{
    "subscriptionId": 2,
    "cardNumber": "4111111111111111",
    "expiryMonth": "12",
    "expiryYear": "2028",
    "securityCode": "765"
}
```
*Plan IDs: 1 = FREE, 2 = PREMIUM, 3 = ULTIMATE*

**Success Response (Status 200):**
```json
{
    "code": 1000,
    "status": 200,
    "message": "Payment created successfully. Waiting for payment confirmation.",
    "data": {
        "paymentId": 1,
        "paymentCode": "PAY-1716450000000",
        "subscriptionId": 2,
        "plan": "PREMIUM",
        "amount": 9.99,
        "paymentStatus": "PENDING"
    }
}
```

---

#### 2. Payment Callback
This is the endpoint the payment gateway calls to confirm the payment.

**Endpoint:** `POST http://localhost:8080/api/payment/callback`

**Request Body:**
```json
{
    "paymentId": 1,
    "paymentStatus": "PAID"
}
```
*Note: If status is "PAID", the user's subscription is automatically updated.*

**Success Response:**
```json
{
    "code": 1001,
    "status": 200,
    "message": "Payment verified and subscription updated successfully",
    "data": "SUCCESS"
}
```

---

#### 3. Upgrade Plan (Direct)
This endpoint updates the plan immediately without checking for payment.

**Endpoint:** `PATCH http://localhost:8080/api/user/upgrade-plan`

**Request Body:**
```json
{
    "id": 2
}
```

**Success Response (Status 200):**
```json
{
    "code": 1000,
    "status": 200,
    "message": "Plan upgraded successfully",
    "data": {
        "userId": 1,
        "plan": "PREMIUM",
        "planName": "Premium Member",
        "startDate": "2026-05-23T20:10:00",
        "endDate": "2026-06-23T20:10:00",
        "active": true
    }
}
```

**Error Response (e.g., Missing ID):**
```json
{
    "code": 4001,
    "status": -1,
    "message": "Plan id is required",
    "data": null
}
```

All endpoints are configured in `SecurityConfig.java`. The callback endpoint is permitted for all, while user endpoints require a valid JWT token.