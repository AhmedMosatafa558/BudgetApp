#  Personal Budgeting Java App

This is a **Java desktop application** that helps users manage their **income, budgeting, reminders, and savings goals** through an intuitive **Graphical User Interface (GUI)** built with `Swing`.

---

##  Features

- **User Authentication**
  - Sign up with email, phone number, and password
  - Login with existing credentials

- **Income Management**
  - Add income entries with source, amount, and date
  - View historical income records

- **Budgeting & Analysis**
  - Set category-based budgets
  - Analyze budgets in a structured view

- **Reminders**
  - Add financial reminders with title, date, and time
  - View upcoming reminders

- **Savings Goals**
  - Create and track progress toward financial goals
  - Visualize current savings vs target amount

- **Dashboard**
  - Quick access to all modules
  - Displays recent transactions

---

##  Technologies Used

- Java 8+
- Java Swing for GUI
- File I/O (no external database)
- Plain text files for data storage:
  - `users.txt` – User info
  - `income.txt` – Income records
  - `budget.txt` – Budget entries
  - `reminders.txt` – User reminders
  - `savings.txt` – Savings goals

---

##  How to Run

1. **Ensure you have Java installed (JDK 8 or later)**  
   You can check with:
   ```bash
   java -version
