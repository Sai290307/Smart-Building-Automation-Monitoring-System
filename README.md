# [cite_start]Smart Building Automation & Monitoring System (SBAMS) [cite: 31, 208]

## 📌 Project Overview
[cite_start]The Smart Building Automation & Monitoring System (SBAMS) is a digital solution designed to manage building operations, including room usage, power logs, maintenance schedules, and safety checks[cite: 35, 249, 250]. [cite_start]It addresses the limitations of manual logbooks, Excel sheets, and verbal reporting, which often lead to delayed maintenance actions, lack of historical records, and poor accountability[cite: 38, 39, 41, 42, 43]. [cite_start]By digitizing these processes, SBAMS provides a centralized monitoring platform for authorized users[cite: 36, 40, 44].

### 👥 Stakeholders
* [cite_start]**Building Admin** [cite: 46]
* [cite_start]**Maintenance Staff** [cite: 47]
* [cite_start]**Security Staff** [cite: 48]
* [cite_start]**Manager** [cite: 49]

---

## 🚀 Key Features

### [cite_start]1. Interactive Frontend & Dashboard [cite: 81, 140]
* [cite_start]**Data Entry Forms:** HTML forms for entering room details, manual energy usage values, and submitting maintenance requests[cite: 85, 86, 87, 88].
* [cite_start]**Responsive UI:** Built using CSS and the Bootstrap framework for responsive page layouts[cite: 89, 93, 221].
* [cite_start]**Client-Side Validation:** JavaScript validation to enforce mandatory fields and numeric range checks[cite: 90, 91, 92].
* [cite_start]**Advanced Search & Filtering:** A dynamic dashboard featuring category-based filters (Maintenance Requests, Energy Usage Logs, Room Status) and a search bar to find records by room number, request ID, or description[cite: 144, 145, 146, 147, 148, 149, 150, 151].
* [cite_start]**Sorting:** Records can be sorted latest-first or by status (e.g., Pending, Completed)[cite: 152, 153, 154].

### [cite_start]2. Backend Engineering & Data Processing [cite: 103, 104]
* [cite_start]**Servlets & JSP:** Dedicated Java Servlets handle adding building data, creating maintenance requests, and viewing logs[cite: 108, 109, 110, 111]. [cite_start]Data is dynamically displayed using JavaServer Pages (JSP)[cite: 113, 224].
* [cite_start]**Real-Time Updates:** Implements AJAX polling using the Fetch API to request building data every 10–20 seconds[cite: 127, 227]. [cite_start]The backend returns JSON responses (e.g., maintenance status, recent energy logs) to dynamically refresh the dashboard status section without full page reloads[cite: 128, 129, 130, 131].

### [cite_start]3. Database Design [cite: 59, 217]
* [cite_start]**Relational Schema:** Designed using Entity-Relationship (ER) modeling and justified normalization decisions (1NF, 2NF, 3NF)[cite: 69, 70, 73, 218].
* [cite_start]**Core Entities:** Includes tables for `User`, `Building`, `Room`, `EnergyUsageLog`, and `MaintenanceRequest` with appropriate Primary and Foreign Keys[cite: 64, 65, 66, 67, 68, 71, 72].
* [cite_start]**JDBC Connectivity:** Establishes secure database connections using JDBC[cite: 112].

### [cite_start]4. Security & Authentication [cite: 163, 229]
* [cite_start]**Role-Based Login:** Servlet and JDBC-based authentication restricting data entry and admin dashboards to authorized Building Admin and Maintenance Staff[cite: 167, 168, 169, 170, 173, 174, 175].
* [cite_start]**Cryptography:** Passwords are encrypted using MD5 or SHA-256 hashing[cite: 171, 230].
* [cite_start]**Session Management:** Implemented using `HttpSession` to securely track user sessions[cite: 172, 230].
* [cite_start]**SQL Injection Prevention:** Strict usage of `PreparedStatement` to prevent unauthorized database manipulation[cite: 114, 176, 230].

---

## [cite_start]💻 Technology Stack [cite: 234, 235]
* [cite_start]**Frontend:** HTML, CSS, JavaScript, Bootstrap [cite: 236]
* [cite_start]**Backend:** Java Servlets, JSP, JDBC [cite: 237]
* [cite_start]**Database:** Relational SQL Database [cite: 61, 70]
* [cite_start]**Server Deployment:** Apache Tomcat [cite: 194, 238]

---

## [cite_start]🛠️ Installation & Deployment [cite: 184]

1. **Database Setup:**
   * [cite_start]Import the provided SQL relational schema to your database server to create the necessary entities (`User`, `Building`, `Room`, `EnergyUsageLog`, `MaintenanceRequest`)[cite: 63].
   * [cite_start]Configure the JDBC database URL, username, and password in your Java application configuration[cite: 112].

2. **Application Build:**
   * Clone the repository to your local machine.
   * [cite_start]Compile the Java Servlets and JSP pages[cite: 108, 113].

3. **Server Deployment:**
   * Package the application into a `.war` file.
   * [cite_start]Deploy the `.war` file to an Apache Tomcat server[cite: 194, 233].
   * Access the application via the local host port configured for Tomcat.

---

## [cite_start]🔮 Future Scope [cite: 26, 269]
[cite_start]While the current system relies heavily on manual data entry by authorized staff for energy usage and maintenance logs[cite: 271], the future roadmap includes:
* [cite_start]**IoT Sensor Integration:** Transitioning to automated, real-time monitoring of room usage and energy logs[cite: 273].
* [cite_start]**Predictive Maintenance:** Implementing Machine Learning algorithms to predict equipment failures[cite: 274].
* [cite_start]**Mobile Accessibility:** Developing a dedicated mobile application for remote system access and alerts[cite: 275].
