**HC 2025-26 Coding Exercises**
-------------------------------------------------------------------------------------------------------------------------------------------
This repository contains my solutions for the HC 2025-26 Coding Exercises.

It includes:

**Exercise 1 – Design Patterns:** Demonstrations of Behavioral, Creational, and Structural design patterns applied in a Smart Healthcare Monitoring System. Example file: SmartHealthcareSystem.java (used to showcase Singleton, Observer, Strategy, Factory, Decorator, and Adapter patterns).

**Exercise 2 – Mini-Project: Astronaut Daily Schedule Organizer** – a console-based CRUD application that helps astronauts manage daily tasks efficiently. Main file: AstronautScheduler.java
___________________________________________________________________________________________________________________________________________
**Project Structure**
-------------------------------------------------------------------------------------------------------------------------------------------
```
/ (root)

├── SmartHealthcareSystem.java # Demonstrates IoT healthcare monitoring with design patterns (Exercise 1)

├── AstronautScheduler.java # Main mini-project application (Exercise 2)

└── README.md # Project documentation
```
___________________________________________________________________________________________________________________________________________
1.Smart Healthcare Monitoring System
-------------------------------------------------------------------------------------------------------------------------------------------
This is a console-based Java application designed to simulate patient monitoring in a smart healthcare environment using IoT concepts and software design patterns. The system collects vital signs (heart rate, temperature), applies different monitoring strategies, and sends alerts to doctors, nurses, and patients when required.

The application enables the system to:
• Register patients dynamically and assign monitoring strategies (Normal, Critical, Post-Surgery)
• Simulate IoT sensors for heart rate and temperature readings
• Notify healthcare staff and patients in real time when vitals are updated
• Send alerts with layered communication (logging, email, SMS)
• Integrate external device readings using adapters (e.g., Fitbit, Arduino)
___________________________________________________________________________________________________________________________________________
**Key Features**
-------------------------------------------------------------------------------------------------------------------------------------------
• Singleton Pattern
Ensures only one instance of IoTController manages all patients and monitoring data.

• Observer Pattern
Notifies doctors, nurses, and patients when new vitals are recorded.

• Strategy Pattern
Allows flexible monitoring strategies (Normal, Critical, Post-Surgery) depending on patient conditions.

• Factory Pattern
Generates sensor objects (HeartRate, Temperature) dynamically without exposing creation logic.

• Decorator Pattern
Enhances alerts with logging, email, and SMS features layered over the base alert system.

• Adapter Pattern
Allows integration of external IoT devices (Fitbit, Arduino) for standardized data collection.

• Exception Handling and Logging
All invalid inputs and operations are handled gracefully with meaningful messages.
___________________________________________________________________________________________________________________________________________
**Optional Features**
-------------------------------------------------------------------------------------------------------------------------------------------
• Support for additional sensors (e.g., blood pressure, oxygen level)
• Advanced alert rules for critical threshold detection
• Persistent storage of patient monitoring history
__________________________________________________________________________________________________________________________________________
**Sample Console Interaction – AstronautScheduler** **(EXCERCISE 1)**
------------------------------------------------------------------------------------------------------------------------------------------
=== **Singleton Pattern Test** ===
```
Singleton verified: Only one IoTController instance exists.

Number of patients to monitor: 2

Enter patient 1 name: Jeeva Ram

Enter patient 2 name: Praveen Kumar
````
=== **Observer Pattern Test** ===
```
Doctor notified: HeartRate: 60.04134350393505, Temperature: 37.91134212995624
Nurse notified: HeartRate: 60.04134350393505, Temperature: 37.91134212995624
Patient app alert: HeartRate: 60.04134350393505, Temperature: 37.91134212995624
Doctor notified: HeartRate: 60.46582582552064, Temperature: 36.71335194730924
Nurse notified: HeartRate: 60.46582582552064, Temperature: 36.71335194730924
Patient app alert: HeartRate: 60.46582582552064, Temperature: 36.71335194730924
```
=== **Strategy Pattern Test** ===
```
Critical Monitoring: High frequency checks for Jeeva Ram
Post-Surgery Monitoring for Praveen Kumar
```
=== **Sensor Factory Test** ===
```
Reading HeartRateSensor: 85.1
Reading TemperatureSensor: 36.1
Reading HeartRateSensor: 87.9
Reading TemperatureSensor: 37.9
```
=== **Decorator Pattern Test** ===
```
Logging: Some patients' vitals require attention!
Logging: Some patients' vitals require attention!
Base Alert: Some patients' vitals require attention!
Email sent: Some patients' vitals require attention!
SMS sent: Some patients' vitals require attention!
```
=== **Adapter Pattern Test** ===
```
FitbitAdapter reading for HeartRate: 80.5
ArduinoAdapter reading for Temperature: 37.3
BUILD SUCCESSFUL (total time: 1 minute 1 second)
```
__________________________________________________________________________________________________________________________________________
**1.Astronaut Daily Schedule Organizer**
This is a console-based Java application designed to help astronauts efficiently manage their daily schedules and tasks.

The application enables astronauts to:

• Add new tasks with the following details:

• Description of the task

• Start Time (HH:mm)

• End Time (HH:mm)

• Priority Level (High / Medium / Low)

• Remove existing tasks by description

• View all tasks sorted by start time

• Prevent overlapping tasks by validating new entries against existing tasks

• Handle invalid inputs gracefully, with clear error messages

**Key Features**

• Singleton Pattern - Ensures that only one instance of ScheduleManager exists to manage all tasks in the system.

• Factory Pattern - TaskFactory is used to create Task objects, centralizing and standardizing object creation.

• Observer Pattern - Alerts the user if a new task conflicts with an existing task, maintaining schedule integrity.

• Exception Handling and Logging.

• All errors and invalid operations are handled gracefully.

• Actions and errors are logged to astronaut_schedule.log for traceability.

**Optional Features**

• Edit existing tasks

• Mark tasks as completed

• View tasks filtered by priority

**How to Run**
```
Clone the repository or download the .java files.
Open a terminal in the project folder.
```
**Compile the Java files:**
```
javac AstronautScheduler.java
javac MedicationAppAllPatterns.java
```
**Run the desired program:**
```
java AstronautScheduler
or
java MedicationAppAllPatterns
```
__________________________________________________________________________________________________________________________________________________________________________________________
**Sample Console Interaction – AstronautScheduler**
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Adding a Task**
```
Choose an option: 1
Description: Morning Exercise
Start time (HH:mm): 07:00
End time (HH:mm): 08:00
Priority (High/Medium/Low): High
Task added successfully. No conflicts.
```
**Handling Conflict**
```
Choose an option: 1
Description: Training Session
Start time (HH:mm): 07:30
End time (HH:mm): 08:30
Priority (High/Medium/Low): High
Error: Task conflicts with existing task "Morning Exercise".
```
**Viewing Tasks**
```
Choose an option: 3
07:00 - 08:00: Morning Exercise [HIGH]
```
**Editing a Task**
```
Choose an option: 4
Description of task to edit: Morning Exercise
New description: Morning Workout
New start time (HH:mm): 06:30
New end time (HH:mm): 07:30
New priority (High/Medium/Low): High
Task updated successfully.
```
**Marking Task Completed**
```
Choose an option: 5
Description of task to mark completed: Morning Workout
Task marked as completed.
```
**Viewing Tasks by Priority**
```
Choose an option: 6
Enter priority (High/Medium/Low): High
06:30 - 07:30: Morning Workout [HIGH] (Completed)
Exiting Choose an option: 0 Exiting. Goodbye!
```
__________________________________________________________________________________________________________________________________________________________________________________________
**Positive and Negative Test Cases – AstronautScheduler**
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Positive Cases**
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
1.**Add Task Enter task title**: 
```
Morning Exercise 
Enter start time (HH:mm): 07:00 
Enter end time (HH:mm): 08:00 
Enter priority (High/Medium/Low): High Task added successfully. 
No conflicts.
```
2.**Add Task Enter task title**: 
```
Team Meeting 
Enter start time (HH:mm): 09:00 
Enter end time (HH:mm): 10:00 
Enter priority (High/Medium/Low): Medium Task added successfully. 
No conflicts.
```
3.**View Tasks**
```
07:00 - 08:00: Morning Exercise [High]
09:00 - 10:00: Team Meeting [Medium]
```
4.**Remove Task **
```
Select the task number to remove:
07:00 - 08:00: Morning Exercise [High]
09:00 - 10:00: Team Meeting [Medium] Enter task number: 1 Task "Morning Exercise" removed successfully.
```
**Negative Cases**
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
1.**Add Task** : 
```
Enter task title
Training Session Enter start time (HH:mm): 09:30 
Enter end time (HH:mm): 10:30 
Enter priority (High/Medium/Low): High Error: 
Task conflicts with existing task "Team Meeting".
```
2.**View Tasks** (when empty) 
```
No tasks scheduled for the day.
```
3.**Remove Task** (when empty) 
```
No tasks scheduled for the day.
```
4.**Remove Task** (invalid number) 
```
Select the task number to remove:
09:00 - 10:00: Team Meeting [Medium]
Enter task number: 5 Error: Invalid task number.
```
