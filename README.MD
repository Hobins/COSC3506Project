# Construction Management System

## Description
This system allows users to login via the hardcoded values we have set to see the different screens and runs purely in memory.
which means that once the server is stopped, all data is reset. 

## Installation
To run this project, you will need have the following installed:
- JDK 21.0.2 or higher
- Maven 4.0.0
- an IDE (IntelliJ, Eclipse, etc.) Personally we used IntelliJ for this project.

## Usage
To run the project, you can run the `main` method in the `MainScreen` class. This will call the `WebServer` class and host a server on `localhost:8081`.
Once it launches a screen should pop up, and you can login into the screens with the following credentials:
- Username: `admin`
- Password: `123`
To see the admin panel and functions.

or
- Username: `user`
- Password: `123`
To see the user panel and functions.

or
- Username: `contractor`
- Password: `123`
To see the contractor panel and functions.

