# AirForce Database Management with JDBC

This project implements a basic AirForce database management system using JDBC (Java Database Connectivity) to interact with a MySQL server. 

The database facilitates deployment of aircraft at bases, commissioning of aviators, registration of sorties, and more.

## How to Run

### Prerequisites

Ensure the MySQL JDBC driver is installed on your system and note down its path as 'P'.

### Step 1: Clone the Repository

Clone this repository to your local machine:

```
git clone <repository_url>
```

### Step 2 : Navigate to the Source Directory 
```
cd src
```
### Step 3 : Set the class path.
Set the classpath to include the MySQL JDBC driver (replace P with the actual path to mysql-connector-java-x.x.x.jar):
```
export CLASSPATH = 'P:.'
```
### Step 4 : Compile and Run the Java Program
```
javac main.java QueryHandler.java
java main
```

Now provide the credentials for the MySQL server.