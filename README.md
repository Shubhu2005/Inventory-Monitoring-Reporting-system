# 📦✨ Inventory Monitoring System

## 🧠 Overview
The **Inventory Monitoring System** is a **Java-based console application** that helps businesses efficiently manage products, monitor stock levels, and receive **real-time email alerts** when quantities fall below defined thresholds.  

It includes **Admin & User roles**, **secure OTP-based login**, **CSV report generation**, and an **automated background stock alert service** that keeps the system running smoothly after login.  

---

## 🚀 Key Features

### 👤 User Management
✅ Signup with **OTP-based email verification**  
✅ Secure login with **role-based access (Admin/User)**  
✅ **Email verification required** before login  
✅ Password protection and validation  

---

### 📦 Inventory Management
🗂️ Add, update, delete, and view products  
📉 Set **custom stock thresholds** per product  
🔍 Filter and search by **price or name**  
📑 Generate and **email CSV reports**  
⚙️ Integrated **CRUD operations** with validation & error handling  

---

### 🔔 Stock Alert Service
⚡ Automatically starts after login  
⏱️ Checks stock levels every 5 minutes  
📩 Sends **email alerts** when quantity ≤ threshold  
👥 Works for both Admin and User roles  
🔄 Runs continuously in the background  

---

### 📧 Email Notifications
✉️ Sends **OTP emails** during signup  
📬 Sends **low-stock alerts** and **inventory reports**  
🔐 Configurable via **Mailtrap** or **Gmail SMTP**  
🛠️ Uses **JavaMail API** for secure communication  

---

## ⚙️ Technology Stack

| 🧩 Category | 🛠️ Tools / Technologies |
|-------------|--------------------------|
| 💻 Programming Language | Java |
| 🧠 IDE | IntelliJ IDEA |
| 🗄️ Database | MySQL (via JDBC) |
| 📂 File Handling | CSVHelper Library |
| 📬 Email Service | JavaMail (SMTP) |
| 🧪 Testing | JUnit, Mockito |
| 🔁 Version Control | Git & GitHub |

---

## 🔒 Security Highlights
🔐 **OTP-based user verification** during registration  
📨 **Email confirmation** required before login  
🧍 **Role-based access control** (Admin/User)  
⚠️ Strong **input validation** and exception handling  

---

## 🧪 Testing Overview
🧾 **JUnit** used for unit testing core modules  
🧩 **Mockito** used for DAO & Service layer testing  
🔍 Covered CRUD, validation, and error handling  
✅ Verified **end-to-end flow** of login → stock update → alert  

---

## 💾 Data Management
🗃️ Product data stored in both **MySQL database** and **CSV files**  
📊 CSV used for **report generation and backups**  
📉 **Threshold column** added for real-time stock tracking  
📑 Pagination with SQL **LIMIT** and **OFFSET** for efficient data retrieval  

---

## 📬 Reporting & Alerts
🧾 Auto-generates **CSV inventory reports**  
📩 Emails reports to Admin directly  
🔔 Sends **real-time low-stock alerts** to users  
✅ Prevents product shortages & ensures smooth operations  

---

## 🧠 Concepts Implemented
💡 Object-Oriented Programming (OOP)  
💾 JDBC Database Connectivity  
📂 CSV File Handling  
⚠️ Exception Handling  
📧 Email API (SMTP Integration)  
🧪 Unit & Integration Testing (JUnit, Mockito)  
🔁 Git Workflow & Version Control  

---

## 🏁 Outcome
The **Inventory Monitoring System** delivers:  
🎯 Real-time stock monitoring  
🔔 Automated low-stock alerts  
🔐 Secure, verified user access  
📧 Reliable email-based reporting  

This project combines **Java OOP, JDBC, Email Integration, and Automation** into one efficient, real-world inventory solution.

---


## 📜 License
🪪 This project is open-source and available for educational and learning purposes.
