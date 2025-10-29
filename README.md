# 📦 Inventory Monitoring System

## 🧠 Overview  
The **Inventory Monitoring System** is a Java-based console application designed to help businesses efficiently manage stock, track product inventory, and receive **real-time email alerts** when quantities fall below their defined thresholds.

It supports **admin and user roles**, **secure login with email verification**, **CSV report generation**, and an **automatic stock alert service** that runs in the background after login.


## 🚀 Features

### 👤 User Management  
- Signup with OTP-based email verification  
- Secure login with role-based access (Admin/User)  
- Email verification required before login  

### 📦 Inventory Management  
- Add, update, remove, and view products  
- Set **custom stock threshold** per product  
- Filter products by price range  
- Generate and email CSV reports of all products  

### 🔔 Stock Alert Service  
- Automatically starts after user login  
- Checks stock levels every 5 minutes  
- Sends email alerts when quantity ≤ threshold  
- Works for both Admin and User roles  

### 📧 Email Notifications  
- Sends OTP during signup verification  
- Sends low-stock alerts and inventory reports  
- Configurable via Mailtrap or Gmail SMTP  

---

