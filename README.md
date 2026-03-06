# TCP Group Chat Application (JavaFX)

A real-time group chat built with **Java (TCP sockets)** and **JavaFX**, using a **Server–Client** architecture.  
Course project for **CSC3374 (Architecture)**.

## What’s Inside
Two separate projects:

- **TCPServerApp**: accepts multiple clients (thread-per-connection), broadcasts messages, and includes a JavaFX dashboard (connected users + activity logs).
- **TCPClientApp**: JavaFX chat client with username login, read-only mode, and connection status indicators.

## Tech Stack
- Java 17
- TCP Sockets (`Socket`, `ServerSocket`)
- Streams: `DataInputStream` / `DataOutputStream`
- JavaFX UI + CSS
- Maven (Shade plugin for runnable JARs)
- Model/View separation (models do not import JavaFX)

## How to Run

### Prerequisites
- Java 17+ installed
- Maven installed (only needed if you want to build from source)

### Option 1 — Run prebuilt JARs
Start the server:
```bash
java -jar TCPServerApp-1.0-SNAPSHOT.jar

POJECT STRUCTURE:
TCPServerApp/
├── pom.xml
└── src/main/
    ├── java/com/chat/server/
    │   ├── ServerApp.java
    │   ├── model/
    │   │   ├── Server.java
    │   │   └── ClientHandler.java
    │   └── view/
    │       └── ServerUI.java
    └── resources/
        ├── config.properties
        └── styles.css

TCPClientApp/
├── pom.xml
└── src/main/
    ├── java/com/chat/client/
    │   ├── ClientApp.java
    │   ├── model/
    │   │   └── Client.java
    │   └── view/
    │       └── ClientUI.java
    └── resources/
        ├── config.properties
        └── styles.css
