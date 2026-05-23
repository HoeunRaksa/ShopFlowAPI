# Order Implementation

This document provides a detailed technical specification for the **Order & Payment Flow** implemented in ShopFlowAPI.

---

## 1. Create Order

Creates a new order from the items in the user's shopping cart. It automatically generates a pending payment record.

**Endpoint:** `POST http://localhost:8080/api/order/create`

**Query Parameters:**
- `id` (Optional): ID of an existing location to use for delivery.

**Request Body:**
If `id` is not provided, you must provide the location details:
```json
{
    "receiverName": "John Doe",
    "phoneNumber": "012345678",
    "address": "Street 123, Phnom Penh"
}
```

**Success Response (Status 200):**
```json
{
    "code": 1000,
    "status": 200,
    "message": "Order created successfully. Waiting for payment.",
    "data": {
        "orderId": 5,
        "totalAmount": 25.50,
        "orderStatus": "PENDING",
        "paymentId": 10,
        "paymentCode": "PAY-1716450000000",
        "paymentStatus": "PENDING",
        "createdAt": "2026-05-23T21:16:00"
    }
}
```

**Error Response (e.g., Cart is Empty):**
```json
{
    "code": 4006,
    "status": -1,
    "message": "Cart is empty",
    "data": null
}
```

---

## 2. Payment Callback

The endpoint called by the payment gateway to confirm the payment status.

**Endpoint:** `POST http://localhost:8080/api/payment/callback`

**Request Body:**
```json
{
    "paymentId": 10,
    "paymentStatus": "PAID"
}
```
*Note: Valid statuses are "PAID" or "FAILED".*

**Success Response (Status 200):**
```json
{
    "code": 1001,
    "status": 200,
    "message": "Payment verified and subscription updated successfully",
    "data": "SUCCESS"
}
```

**Error Response (e.g., Payment Not Found):**
```json
{
    "code": 4003,
    "status": -1,
    "message": "Payment not found",
    "data": null
}
```

---

## Order Process Logic

1. **User Checkout**: User calls `/api/order/create`.
2. **Backend Logic**:
   - Validates the cart (must not be empty).
   - Calculates total price.
   - Saves `Order` with `PENDING` status.
   - Clears the user's `Cart`.
   - Creates a `Payment` record with `PENDING` status linked to the `Order`.
3. **Payment Processing**: The frontend uses the `paymentCode` to process the payment with the gateway.
4. **Gateway Callback**: Gateway calls `/api/payment/callback`.
5. **Verification**:
   - If status is `PAID`, both `Payment` and `Order` are updated to `PAID`.
   - If status is `FAILED`, `Payment` is updated to `FAILED`, and the `Order` remains `PENDING` (awaiting another payment attempt).
