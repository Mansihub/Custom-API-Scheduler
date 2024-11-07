# Custom-API-Scheduler

## Overview

**Custom API Scheduler** is an application designed to schedule and execute HTTP APIs at predefined intervals. It allows users to configure multiple APIs with custom schedules and ensures each API is executed based on its last execution time and the specified interval. The system logs the execution results for every API call.

## Features

- **Schedule APIs:** Add APIs with a defined schedule interval.
- **Automatic Execution:** Executes APIs based on their schedule.
- **Execution Logging:** Logs every execution attempt, including success/failure and response.
- **Configurable Interval:** Custom intervals in milliseconds for each API.
- **Error Handling:** Captures errors during execution and logs them.

## Technologies Used

- **Java**: Core programming language for the backend.
- **Spring Boot**: Framework used for building the application.
- **RestTemplate**: Used to make HTTP requests to external APIs.
- **MySQL**: Database for storing scheduled API information and execution logs.
- **Maven**: Dependency management and build tool.

