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

## 3. Order History

Retrieves the authenticated user's order history.

**Endpoint:** `GET http://localhost:8080/api/order`

**Success Response (Status 200):**
```json
{
    "code": 1002,
    "status": 200,
    "message": "Order history retrieved successfully",
    "data": [
        {
            "id": 5,
            "totalPrice": 25.50,
            "status": "PAID",
            "createAt": "2026-05-23T21:16:00",
            "items": [
                {
                    "id": 12,
                    "product": {
                        "id": 101,
                        "name": "Wireless Mouse",
                        "price": 10.50,
                        "imageUrl": "http://localhost:8080/uploads/mouse.jpg"
                    },
                    "quantity": 1,
                    "price": 10.50
                }
            ]
        }
    ]
}
```

---

## Order Process Logic

1. **User Checkout**: User calls `/api/order/create`.
2. **Backend Logic & Data Persistence**:
   - Validates the cart (must not be empty).
   - Calculates total price.
   - **Saves `Order` information**: A new record is created in the `orders` table with `PENDING` status.
   - **Saves `OrderItem` details**: Every item from the cart is persisted into the `order_items` table linked to the `Order`. This ensures that even if the cart is later cleared, the full **Order History** is preserved for the user.
   - Creates a `Payment` record with `PENDING` status linked to the `Order`.
3. **Payment Processing**: The frontend uses the `paymentCode` to process the payment with the gateway.
4. **Gateway Callback**: Gateway calls `/api/payment/callback`.
5. **Verification & Fulfillment**:
   - If status is `PAID`:
     - Both `Payment` and `Order` are updated to `PAID`.
     - The user's **Cart is cleared** (since the order is now finalized and info is securely saved in history).
   - If status is `FAILED`, `Payment` is updated to `FAILED`, and the `Order` remains `PENDING` (awaiting another payment attempt, cart remains intact).
