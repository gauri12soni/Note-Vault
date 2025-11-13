# NoteVault - Secure Note Management Application

[![Java](https://img.shields.io/badge/Java-17-blue?logo=java)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2-green?logo=spring)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.9-blue?logo=apachemaven)](https://maven.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)](https://www.mysql.com/)

---

## Overview

**NoteVault** is a secure and scalable note management application built with **Spring Boot**. It allows users to register, log in, and manage personal notes efficiently. The system ensures secure authentication with **JWT tokens** and enables CRUD operations with support for pagination and search.  

This project is ideal for **backend-focused developers** and demonstrates best practices in Spring Boot, REST API design, and secure application development.

---

## Key Features

- **User Authentication**
  - Secure registration and login using JWT
  - Passwords are hashed with BCrypt for security

- **Notes Management**
  - Create, read, update, and delete notes
  - Tag-based note management
  - Pagination and search functionality

- **Security**
  - Role-based access control
  - Only authenticated users can manage their own notes

- **Logging**
  - Comprehensive logging with SLF4J and Logback
  - Logs registration, login, and note operations for monitoring

---

## Tech Stack

| Layer           | Technology                  |
|-----------------|----------------------------|
| Backend         | Java 17, Spring Boot 3.2   |
| Database        | PostgreSQL 15              |
| Build Tool      | Maven 3.9                  |
| Security        | Spring Security, JWT       |
| Validation      | Jakarta Validation API     |
| Logging         | SLF4J, Logback             |

---

## Installation & Setup

1. **Clone the repository**
```bash
git clone https://github.com/your-username/NoteVault.git
cd NoteVault
