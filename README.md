**HC 2025-26 Coding Exercises**
-------------------------------------------------------------------------------------------------------------------------------------------
This repository contains my solutions for the HC 2025-26 Coding Exercises.

It includes:

**Exercise 1 – Design Patterns:** Demonstrations of Behavioral, Creational, and Structural design patterns applied in a Smart Healthcare Monitoring System. Example file: SmartHealthcareSystem.java (used to showcase Singleton, Observer, Strategy, Factory, Decorator, and Adapter patterns).

**Exercise 2 – Mini-Project: Astronaut Daily Schedule Organizer** – a console-based CRUD application that helps astronauts manage daily tasks efficiently. Main file: AstronautScheduler.java
___________________________________________________________________________________________________________________________________________
**Project Structure**
-------------------------------------------------------------------------------------------------------------------------------------------
/ (root)

├── SmartHealthcareSystem.java # Demonstrates IoT healthcare monitoring with design patterns (Exercise 1)

├── AstronautScheduler.java # Main mini-project application (Exercise 2)

└── README.md # Project documentation
___________________________________________________________________________________________________________________________________________
Smart Healthcare Monitoring System
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
=== Singleton Pattern Test ===
Singleton verified: Only one IoTController instance exists.

Number of patients to monitor: 2

Enter patient 1 name: Jeeva Ram

Enter patient 2 name: Praveen Kumar

=== Observer Pattern Test ===
Doctor notified: HeartRate: 60.04134350393505, Temperature: 37.91134212995624
Nurse notified: HeartRate: 60.04134350393505, Temperature: 37.91134212995624
Patient app alert: HeartRate: 60.04134350393505, Temperature: 37.91134212995624
Doctor notified: HeartRate: 60.46582582552064, Temperature: 36.71335194730924
Nurse notified: HeartRate: 60.46582582552064, Temperature: 36.71335194730924
Patient app alert: HeartRate: 60.46582582552064, Temperature: 36.71335194730924

=== Strategy Pattern Test ===
Critical Monitoring: High frequency checks for Jeeva Ram
Post-Surgery Monitoring for Praveen Kumar

=== Sensor Factory Test ===
Reading HeartRateSensor: 85.1
Reading TemperatureSensor: 36.1
Reading HeartRateSensor: 87.9
Reading TemperatureSensor: 37.9

=== Decorator Pattern Test ===
Logging: Some patients' vitals require attention!
Logging: Some patients' vitals require attention!
Base Alert: Some patients' vitals require attention!
Email sent: Some patients' vitals require attention!
SMS sent: Some patients' vitals require attention!

=== Adapter Pattern Test ===
FitbitAdapter reading for HeartRate: 80.5
ArduinoAdapter reading for Temperature: 37.3
BUILD SUCCESSFUL (total time: 1 minute 1 second)

