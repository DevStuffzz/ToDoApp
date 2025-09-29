# TodoApp

A multi-mode **Java Todo Application** supporting GUI, console, and headless operation with **user login** and **persistent storage**.  

This project demonstrates advanced Java features including **Swing GUI**, **concurrent console operations**, **file persistence**, and a modular architecture for further expansion.

---

## Features

- **Multi-mode operation**  
  - GUI + Console concurrently  
  - Headless mode (console only)  

- **User Login**  
  - Hardcoded credentials:  
    - Username: `admin`  
    - Password: `1234`  
  - GUI login dialog or console login depending on mode  

- **Task Management**  
  - Add, complete, and remove tasks  
  - Color-coded tasks in GUI (red = incomplete, green = completed)  
  - Pretty console output (`[✓] Task` vs `[✗] Task`)  

- **Persistent Storage**  
  - Tasks saved to `tasks.txt` in `name;true/false` format  
  - Automatically loads saved tasks on startup  

- **Concurrent Console + GUI**  
  - Console commands work concurrently with GUI updates  
  - Supports adding, completing, removing tasks from either interface  

- **Headless Mode**  
  - Launch with `--headless` to run only the console version (no GUI, login in console)  

---

## Getting Started

### Prerequisites

- Java 17+ (JDK recommended)  
- Compatible IDE (IntelliJ, Eclipse) or command-line tools  

---

### Running the App

#### GUI + Console Mode (default)
```bash
java Main
