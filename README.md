# 📚 Book Carousel Library App

The **Book Carousel Library App** is a full-stack, feature-rich web application that brings the experience of a physical library into the digital world. With a dynamic carousel-style book browser, secure user authentication, real-time admin-user interaction, and payment functionality, this app is designed for organizations that want to offer seamless digital access to their book collections — including schools, universities, community libraries, and startups building e-library platforms.

## 📝 App Description

The core idea behind this application is to give users an intuitive and engaging interface to explore and borrow books. Users can view available books displayed in a visually attractive carousel format that mimics shelf browsing. Each book includes important details like the title, author, and description. Users can scroll through the carousel, click to learn more, and decide whether to borrow or not. This smooth interaction is supported by responsive UI components that adapt gracefully across devices.

A key highlight of the system is the **role-based architecture**. There are two main roles: **User** and **Admin**.

Regular users can:
- Browse and search for books
- View book descriptions and metadata
- Post queries or messages to the admin
- Borrow books via checkout
- Track the return period for each borrowed book

Once a book is checked out, users have a **7-day return period**. If a book isn’t returned within that time, the system restricts the user from borrowing or returning any further books until a **late fee** is paid. This ensures fair usage and simulates a real-world lending policy. All payments, including late fees and book checkouts, are processed securely through Stripe, providing reliability and PCI compliance.

Admins have a dedicated interface that allows them to:
- Add new books to the catalog with full details
- Manage existing book entries
- View and respond to user queries
- Monitor user activity and borrowing timelines

This ensures admins can actively manage the library while maintaining open communication with users. It promotes transparency and allows the platform to grow and evolve with its community.

## 🛠️ Tech Stack

The Book Carousel Library App is built using a modern and scalable technology stack:

### 🖥️ Frontend
- **React** – A powerful JavaScript library for building dynamic user interfaces.
- **TypeScript** – Adds static typing to JavaScript, improving maintainability and reducing bugs.
- **Bootstrap** – For responsive design and quick UI prototyping.

### 🔐 Authentication
- **Keycloak** – A robust identity and access management tool that handles user authentication, registration, and role-based access control. It ensures secure login flows for both users and admins.

### 🔧 Backend
- **Spring Boot** – A production-ready Java-based backend framework used to build RESTful APIs, handle business logic, and manage database interactions.
- **Stripe API** – Integrated into the backend for handling all payment flows securely, including book checkout and late return charges.

### 🗄️ Database
- **MySQL** – A reliable and efficient relational database used to store all persistent data including book information, user activity, and payment history.

Together, this stack provides a scalable, secure, and high-performance environment for delivering a real-world library experience online.

> Whether you're a reader browsing your next favorite book or an admin managing hundreds of titles, the **Book Carousel Library App** brings everything together in one seamless digital library solution.
