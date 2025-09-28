import java.util.*;

// ==================== Observer Pattern ====================
interface Observer {
    void update(double heartRate, double temperature);
}

class DoctorDashboard implements Observer {
    public void update(double heartRate, double temperature) {
        System.out.println("Doctor notified: HeartRate: " + heartRate + ", Temperature: " + temperature);
    }
}

class NurseStation implements Observer {
    public void update(double heartRate, double temperature) {
        System.out.println("Nurse notified: HeartRate: " + heartRate + ", Temperature: " + temperature);
    }
}

class PatientApp implements Observer {
    public void update(double heartRate, double temperature) {
        System.out.println("Patient app alert: HeartRate: " + heartRate + ", Temperature: " + temperature);
    }
}

// ==================== Strategy Pattern ====================
interface MonitoringStrategy {
    void monitor(String patientName);
}

class NormalMonitoring implements MonitoringStrategy {
    public void monitor(String patientName) {
        System.out.println("Normal Monitoring for patient: " + patientName);
    }
}

class CriticalMonitoring implements MonitoringStrategy {
    public void monitor(String patientName) {
        System.out.println("Critical Monitoring: High frequency checks for " + patientName);
    }
}

class PostSurgeryMonitoring implements MonitoringStrategy {
    public void monitor(String patientName) {
        System.out.println("Post-Surgery Monitoring for " + patientName);
    }
}

// ==================== Singleton Pattern ====================
class IoTController {
    private static IoTController instance;
    private List<Patient> patients;

    private IoTController() {
        patients = new ArrayList<>();
    }

    public static IoTController getInstance() {
        if (instance == null) instance = new IoTController();
        return instance;
    }

    public void registerPatient(Patient p) {
        patients.add(p);
    }

    public void monitorPatients() {
        for (Patient p : patients) {
            p.getStrategy().monitor(p.getName());
        }
    }

    public List<Patient> getPatients() {
        return patients;
    }
}

// ==================== Factory Pattern ====================
interface Sensor {
    double readData();
}

class HeartRateSensor implements Sensor {
    public double readData() {
        return 60 + Math.random() * 40;
    }
}

class TemperatureSensor implements Sensor {
    public double readData() {
        return 36 + Math.random() * 2;
    }
}

class SensorFactory {
    public static Sensor createSensor(String type) {
        if ("HeartRate".equals(type)) return new HeartRateSensor();
        else if ("Temperature".equals(type)) return new TemperatureSensor();
        else throw new IllegalArgumentException("Unknown sensor type");
    }
}

// ==================== Decorator Pattern ====================
interface Alert {
    void send(String message);
}

class BaseAlert implements Alert {
    public void send(String message) {
        System.out.println("Base Alert: " + message);
    }
}

class LoggingDecorator implements Alert {
    private Alert alert;
    public LoggingDecorator(Alert alert) { this.alert = alert; }
    public void send(String message) {
        System.out.println("Logging: " + message);
        alert.send(message);
    }
}

class EmailDecorator extends LoggingDecorator {
    public EmailDecorator(Alert alert) { super(alert); }
    public void send(String message) {
        super.send(message);
        System.out.println("Email sent: " + message);
    }
}

class SMSDecorator extends LoggingDecorator {
    public SMSDecorator(Alert alert) { super(alert); }
    public void send(String message) {
        super.send(message);
        System.out.println("SMS sent: " + message);
    }
}

// ==================== Adapter Pattern ====================
interface SensorAdapter {
    double getValue();
}

class FitbitAdapter implements SensorAdapter {
    public double getValue() {
        return 70 + Math.random() * 20;
    }
}

class ArduinoAdapter implements SensorAdapter {
    public double getValue() {
        return 36 + Math.random() * 2;
    }
}

// ==================== Patient Class ====================
class Patient {
    private String name;
    private double heartRate;
    private double temperature;
    private MonitoringStrategy strategy;
    private List<Observer> observers = new ArrayList<>();

    public Patient(String name) { this.name = name; }
    public String getName() { return name; }
    public void addObserver(Observer o) { observers.add(o); }
    public void setVitals(double hr, double temp) {
        this.heartRate = hr;
        this.temperature = temp;
        // notifications triggered only here if needed
    }
    public void notifyObservers() {
        for (Observer o : observers) o.update(heartRate, temperature);
    }
    public void setStrategy(MonitoringStrategy strategy) { this.strategy = strategy; }
    public MonitoringStrategy getStrategy() { return strategy; }
    public double getHeartRate() { return heartRate; }
    public double getTemperature() { return temperature; }
}

// ==================== Main Application ====================
public class SmartHealthcareSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        // Singleton Pattern Test
        System.out.println("\n=== Singleton Pattern Test ===");
        IoTController controller1 = IoTController.getInstance();
        IoTController controller2 = IoTController.getInstance();
        if (controller1 == controller2)
            System.out.println("Singleton verified: Only one IoTController instance exists.");

        System.out.print("\nNumber of patients to monitor: ");
        int numPatients = sc.nextInt();
        sc.nextLine();

        for (int i = 1; i <= numPatients; i++) {
            System.out.print("\nEnter patient " + i + " name: ");
            String name = sc.nextLine();
            Patient p = new Patient(name);

            // Assign random strategy using Java 8 switch
            int choice = rand.nextInt(3);
            switch (choice) {
                case 0:
                    p.setStrategy(new NormalMonitoring());
                    break;
                case 1:
                    p.setStrategy(new CriticalMonitoring());
                    break;
                case 2:
                    p.setStrategy(new PostSurgeryMonitoring());
                    break;
                default:
                    p.setStrategy(new NormalMonitoring());
            }

            // Add observers
            p.addObserver(new DoctorDashboard());
            p.addObserver(new NurseStation());
            p.addObserver(new PatientApp());

            // Register patient
            controller1.registerPatient(p);

            // Set initial vitals
            Sensor hrSensor = SensorFactory.createSensor("HeartRate");
            Sensor tempSensor = SensorFactory.createSensor("Temperature");
            p.setVitals(hrSensor.readData(), tempSensor.readData());
        }

        // Observer Pattern Test
        System.out.println("\n=== Observer Pattern Test ===");
        for (Patient p : controller1.getPatients()) {
            // Trigger notifications explicitly
            p.notifyObservers();
        }

        // Strategy Pattern Test
        System.out.println("\n=== Strategy Pattern Test ===");
        controller1.monitorPatients();

        // Sensor Factory Test
        System.out.println("\n=== Sensor Factory Test ===");
        for (int i = 0; i < numPatients; i++) {
            Sensor hrSensor = SensorFactory.createSensor("HeartRate");
            Sensor tempSensor = SensorFactory.createSensor("Temperature");
            System.out.println("Reading HeartRateSensor: " + String.format("%.1f", hrSensor.readData()));
            System.out.println("Reading TemperatureSensor: " + String.format("%.1f", tempSensor.readData()));
        }

        // Decorator Pattern Test
        System.out.println("\n=== Decorator Pattern Test ===");
        Alert alert = new BaseAlert();
        alert = new EmailDecorator(alert);
        alert = new SMSDecorator(alert);
        alert.send("Some patients' vitals require attention!");

        // Adapter Pattern Test
        System.out.println("\n=== Adapter Pattern Test ===");
        SensorAdapter fitbit = new FitbitAdapter();
        SensorAdapter arduino = new ArduinoAdapter();
        System.out.println("FitbitAdapter reading for HeartRate: " + String.format("%.1f", fitbit.getValue()));
        System.out.println("ArduinoAdapter reading for Temperature: " + String.format("%.1f", arduino.getValue()));

        sc.close();
    }
}
