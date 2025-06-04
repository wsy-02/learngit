import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student {
    private String name;
    private String studentId;
    private String course;
    private double grade;

    public Student(String name, String studentId, String course, double grade) {
        this.name = name;
        this.studentId = studentId;
        this.course = course;
        this.grade = grade;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourse() {
        return course;
    }

    public double getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return String.format("%-10s%-15s%-15s%-5.1f", name, studentId, course, grade);
    }
}

class StudentGradeSystem {
    private List<Student> students;
    private Scanner scanner;

    public StudentGradeSystem() {
        this.students = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void addStudent(String name, String studentId, String course, double grade) {
        // 检查成绩是否合法
        if (grade < 0 || grade > 100) {
            System.out.println("错误：成绩必须在0-100之间");
            return;
        }

        // 检查学号是否已存在
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                System.out.println("错误：该学号已存在");
                return;
            }
        }

        // 添加记录
        students.add(new Student(name, studentId, course, grade));
        System.out.println("成功添加学生成绩记录");
    }

    public void queryGrades(String queryType, String queryValue) {
        List<Student> results = new ArrayList<>();

        for (Student student : students) {
            switch (queryType) {
                case "name":
                    if (student.getName().equals(queryValue)) {
                        results.add(student);
                    }
                    break;
                case "student_id":
                    if (student.getStudentId().equals(queryValue)) {
                        results.add(student);
                    }
                    break;
                case "course":
                    if (student.getCourse().equals(queryValue)) {
                        results.add(student);
                    }
                    break;
            }
        }

        if (results.isEmpty()) {
            System.out.println("未找到匹配的记录");
            return;
        }

        System.out.println("\n查询结果：");
        System.out.println("--------------------------------------------------");
        System.out.printf("%-10s%-15s%-15s%-5s\n", "姓名", "学号", "课程", "成绩");
        System.out.println("--------------------------------------------------");
        for (Student result : results) {
            System.out.println(result);
        }
        System.out.println("--------------------------------------------------");
    }

    public void calculateStatistics(String course) {
        List<Double> grades = new ArrayList<>();

        for (Student student : students) {
            if (student.getCourse().equals(course)) {
                grades.add(student.getGrade());
            }
        }

        if (grades.isEmpty()) {
            System.out.println("课程 '" + course + "' 没有成绩记录");
            return;
        }

        double sum = 0;
        double max = grades.get(0);
        double min = grades.get(0);

        for (double grade : grades) {
            sum += grade;
            if (grade > max) max = grade;
            if (grade < min) min = grade;
        }

        double average = sum / grades.size();

        System.out.println("\n课程 '" + course + "' 成绩统计：");
        System.out.println("------------------------------");
        System.out.printf("平均分: %.2f\n", average);
        System.out.println("最高分: " + max);
        System.out.println("最低分: " + min);
        System.out.println("------------------------------");
    }

    public void displayMenu() {
        System.out.println("\n学生成绩管理系统");
        System.out.println("==============================");
        System.out.println("1. 录入学生成绩");
        System.out.println("2. 查询学生成绩");
        System.out.println("3. 统计课程成绩");
        System.out.println("4. 退出系统");
        System.out.println("==============================");
    }

    public void run() {
        while (true) {
            displayMenu();
            System.out.print("请输入选项(1-4): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addGradeInterface();
                    break;
                case "2":
                    queryGradeInterface();
                    break;
                case "3":
                    statisticsInterface();
                    break;
                case "4":
                    System.out.println("感谢使用学生成绩管理系统，再见！");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("无效的选项，请重新输入");
            }
        }
    }

    private void addGradeInterface() {
        System.out.println("\n录入学生成绩");
        System.out.println("------------------------------");
        System.out.print("请输入学生姓名: ");
        String name = scanner.nextLine();

        System.out.print("请输入学号: ");
        String studentId = scanner.nextLine();

        System.out.print("请输入课程名称: ");
        String course = scanner.nextLine();

        double grade = 0;
        while (true) {
            try {
                System.out.print("请输入成绩(0-100): ");
                grade = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("错误：请输入有效的数字");
            }
        }

        addStudent(name, studentId, course, grade);
    }

    private void queryGradeInterface() {
        System.out.println("\n查询学生成绩");
        System.out.println("------------------------------");
        System.out.println("1. 按姓名查询");
        System.out.println("2. 按学号查询");
        System.out.println("3. 按课程查询");
        System.out.println("------------------------------");

        while (true) {
            System.out.print("请选择查询方式(1-3): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("请输入要查询的学生姓名: ");
                    String name = scanner.nextLine();
                    queryGrades("name", name);
                    return;
                case "2":
                    System.out.print("请输入要查询的学号: ");
                    String studentId = scanner.nextLine();
                    queryGrades("student_id", studentId);
                    return;
                case "3":
                    System.out.print("请输入要查询的课程名称: ");
                    String course = scanner.nextLine();
                    queryGrades("course", course);
                    return;
                default:
                    System.out.println("无效的选项，请重新输入");
            }
        }
    }

    private void statisticsInterface() {
        System.out.println("\n统计课程成绩");
        System.out.println("------------------------------");
        System.out.print("请输入要统计的课程名称: ");
        String course = scanner.nextLine();
        calculateStatistics(course);
    }
}

public class Main {
    public static void main(String[] args) {
        StudentGradeSystem system = new StudentGradeSystem();
        system.run();
    }
}