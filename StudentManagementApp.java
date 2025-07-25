package PROJECTS;
import java.io.*;
import java.util.*;
class Student implements Serializable {
        private String name;
        private int rollNumber;
        private String grade;

        public Student(String name, int rollNumber, String grade) {
            this.name = name;
            this.rollNumber = rollNumber;
            this.grade = grade;
        }

        public String getName() { return name; }
        public int getRollNumber() { return rollNumber; }
        public String getGrade() { return grade; }

        public void setName(String name) { this.name = name; }
        public void setGrade(String grade) { this.grade = grade; }

        @Override
        public String toString() {
            return "Roll No: " + rollNumber + ", Name: " + name + ", Grade: " + grade;
        }
    }

    class StudentManagementSystem {
        private List<Student> students;
        private final String fileName = "students.dat";

        public StudentManagementSystem() {
            students = new ArrayList<>();
            loadFromFile();
        }

        public void addStudent(Student student) {
            if (getStudentByRoll(student.getRollNumber()) != null) {
                System.out.println("Student with this roll number already exists!");
                return;
            }
            students.add(student);
            saveToFile();
            System.out.println("Student added successfully.");
        }

        public void removeStudent(int rollNumber) {
            Student student = getStudentByRoll(rollNumber);
            if (student != null) {
                students.remove(student);
                saveToFile();
                System.out.println("Student removed.");
            } else {
                System.out.println("Student not found.");
            }
        }

        public void editStudent(int rollNumber, String newName, String newGrade) {
            Student student = getStudentByRoll(rollNumber);
            if (student != null) {
                student.setName(newName);
                student.setGrade(newGrade);
                saveToFile();
                System.out.println("Student updated.");
            } else {
                System.out.println("Student not found.");
            }
        }

        public void searchStudent(int rollNumber) {
            Student student = getStudentByRoll(rollNumber);
            if (student != null) {
                System.out.println(student);
            } else {
                System.out.println("Student not found.");
            }
        }

        public void displayAllStudents() {
            if (students.isEmpty()) {
                System.out.println("No students available.");
            } else {
                for (Student s : students) {
                    System.out.println(s);
                }
            }
        }

        private Student getStudentByRoll(int rollNumber) {
            for (Student s : students) {
                if (s.getRollNumber() == rollNumber) return s;
            }
            return null;
        }

        private void saveToFile() {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
                oos.writeObject(students);
            } catch (IOException e) {
                System.out.println("Error saving data.");
            }
        }

        @SuppressWarnings("unchecked")
        private void loadFromFile() {
            File file = new File(fileName);
            if (!file.exists()) return;

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
                students = (List<Student>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading data.");
            }
        }
    }

    public class StudentManagementApp {
        private static final Scanner scanner = new Scanner(System.in);
        private static final StudentManagementSystem sms = new StudentManagementSystem();

        public static void main(String[] args) {
            int choice;
            do {
                System.out.println("\n--- STUDENT MANAGEMENT SYSTEM ---");
                System.out.println("1. Add Student");
                System.out.println("2. Edit Student");
                System.out.println("3. Remove Student");
                System.out.println("4. Search Student");
                System.out.println("5. Display All Students");
                System.out.println("6. Exit");
                System.out.print("Enter choice: ");

                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid input. Enter a number: ");
                    scanner.next();
                }

                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1 -> addStudentUI();
                    case 2 -> editStudentUI();
                    case 3 -> removeStudentUI();
                    case 4 -> searchStudentUI();
                    case 5 -> sms.displayAllStudents();
                    case 6 -> System.out.println("Exiting...");
                    default -> System.out.println("Invalid choice.");
                }
            } while (choice != 6);
        }

        private static void addStudentUI() {
            System.out.print("Enter name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter roll number: ");
            int rollNumber = readInt();

            System.out.print("Enter grade: ");
            String grade = scanner.nextLine().trim();

            if (name.isEmpty() || grade.isEmpty()) {
                System.out.println("Name and grade cannot be empty.");
                return;
            }

            sms.addStudent(new Student(name, rollNumber, grade));
        }

        private static void editStudentUI() {
            System.out.print("Enter roll number to edit: ");
            int rollNumber = readInt();

            System.out.print("Enter new name: ");
            String newName = scanner.nextLine().trim();
            System.out.print("Enter new grade: ");
            String newGrade = scanner.nextLine().trim();

            if (newName.isEmpty() || newGrade.isEmpty()) {
                System.out.println("Fields cannot be empty.");
                return;
            }

            sms.editStudent(rollNumber, newName, newGrade);
        }

        private static void removeStudentUI() {
            System.out.print("Enter roll number to remove: ");
            int rollNumber = readInt();
            sms.removeStudent(rollNumber);
        }

        private static void searchStudentUI() {
            System.out.print("Enter roll number to search: ");
            int rollNumber = readInt();
            sms.searchStudent(rollNumber);
        }

        private static int readInt() {
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input. Enter a number: ");
                scanner.next();
            }
            int num = scanner.nextInt();
            scanner.nextLine(); // consume newline
            return num;
        }
}

