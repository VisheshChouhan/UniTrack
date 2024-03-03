# University Student Interface Application

Welcome to the University Student Interface Application repository! This application comprises two separate applications: one for students (Name: Login) and one for teachers (Teachers_App), aimed at facilitating communication and organization within university classrooms.

## Features

### Teachers_App:

1. **Digital Classrooms Creation**: Teachers can create digital classrooms using a unique code, enabling efficient organization of courses.
2. **Attendance Management**: Utilizes a custom algorithm for attendance tracking, allowing teachers to take attendance quickly and accurately.
3. **Attendance Reports**: Teachers can download attendance reports in CSV format with attendance percentages included for each student, simplifying record-keeping.
4. **Assignment Distribution**: Teachers can distribute assignments to students directly through the application.
5. **Multiple Classrooms**: Teachers can create and manage multiple classrooms within the application.
6. **Bulk Mark Upload**: Teachers can upload marks for every student in a classroom simultaneously using a CSV file in a specific format.
7. **Group Chat**: Each classroom features its own group chat functionality, facilitating communication among students and teachers through text messaging and image sharing.

### Students_App:

1. **Classroom Enrollment**: Students can join classrooms using unique codes provided by their teachers.
2. **Attendance Marking**: Students can mark their attendance quickly and conveniently through the application.
3. **Attendance and Performance Tracking**: Students can visually track their attendance, marks, and assignments in graphs, providing a comprehensive overview of their academic progress.
4. **Multiple Classroom Access**: Students can join and access multiple classrooms within the application.
5. **Group Chat Access**: Students can participate in group chats within their classrooms, allowing for communication and collaboration with peers and teachers through text and image sharing.

## Technologies Used

- **Android (Java and XML)**: Used for developing the Student_App login interface.
- **Flutter**: Used for developing the Teachers_App, providing a cross-platform framework for efficient application development.
- **Firebase Services**:
  - Realtime Database: Used for storing and retrieving real-time data, facilitating communication and data synchronization between users.
  - Firebase Authentication: Used for user authentication and secure access to application features.
  - Firestore: Used for storing structured data and managing complex queries, supporting features such as attendance tracking and assignment distribution.

## Installation

To run the application locally, follow these steps:

1. Clone this repository to your local machine.
2. Set up Firebase services for both applications, including Realtime Database, Authentication, and Firestore.
3. Configure the Firebase credentials in the respective application codebases.
4. Build and run the Student_App and Teachers_App using Android Studio for Android and Flutter SDK for Flutter application.

## Contributors

This project was developed by Vishesh Chouhan. Contributions are welcome via pull requests.


