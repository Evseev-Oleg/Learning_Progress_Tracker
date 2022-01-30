package tracker;

import java.util.*;
import java.util.stream.Collectors;

/**
 * класс выполняет все действия по вводу и выводу информации
 * а так же хранению студентов в final Map<String, Student> studentMap
 * и назначения им id
 */
public class Action {
    private int countId = 10000;
    private final Map<String, Student> studentMap = new TreeMap<>();
    Scanner scannerLine = new Scanner(System.in);
    private int count = 0;
    private final Statistics statisticsStudent = new Statistics();

    /**
     * метод распознающий основные команды.
     * такие как
     * exit - выход с программы
     * add students - добавить студента(переход на другой метод, где основные команды не доступны)
     * back - возвратит в начало этого метода для ввода команды
     * list - возвратит у всех студентов их id
     * add points - добавить оценки студенту по id(переход на другой метод, где основные команды не доступны)
     * find - поиск студента по id(переход на другой метод, где основные команды не доступны)
     * statistics - статистика по всем курсам
     * notify - создать письмо студентам которые закончили какие либо курсы
     */
    public void checkStr() {
        String str = scannerLine.nextLine();
        if (str == null || str.strip().length() == 0) {
            System.out.println("No input.");
            checkStr();
        } else {
            switch (str) {
                case "exit": {
                    System.out.println("Bye!");
                    break;
                }
                case "add students": {
                    System.out.println("Enter student credentials or 'back' to return:");
                    addStudent();
                    break;
                }
                case "back": {
                    System.out.println("Enter 'exit' to exit the program.");
                    checkStr();
                    break;
                }
                case "list": {
                    if (studentMap.isEmpty()) {
                        System.out.println("No students found.");
                    } else {
                        System.out.println("Students:");
                        for (var student : studentMap.entrySet()) {
                            System.out.println(student.getValue().getId());
                        }
                    }
                    checkStr();
                    break;
                }
                case "add points": {
                    System.out.println("Enter an id and points or 'back' to return:");
                    addPoints();
                    break;
                }
                case "find": {
                    System.out.println("Enter an id or 'back' to return:");
                    findStudent();
                    break;
                }
                case "statistics": {
                    System.out.println("Type the name of a course to " +
                            "see details or 'back' to quit:");
                    statisticAll();
                    informCourse();
                    checkStr();
                    break;
                }
                case "notify": {
                    notifyStudent();
                    break;
                }
                default: {
                    System.out.println("Error: unknown command!");
                    checkStr();
                    break;
                }
            }
        }
    }

    /**
     * создать письмо студентам которые закончили какие либо курсы
     */
    private void notifyStudent() {
        List<String> listCourses = List.of("Java", "DSA", "Databases", "Spring");
        List<Student> studentList = new ArrayList<>();
        for (var student : studentMap.entrySet()) {
            studentList.add(student.getValue());
        }
        List<Student> result = studentList.stream().filter(element ->
                element.getListCourse().get(0).getGrade() >= 600 ||
                        element.getListCourse().get(1).getGrade() >= 400 ||
                        element.getListCourse().get(2).getGrade() >= 490 ||
                        element.getListCourse().get(3).getGrade() >= 550
        ).collect(Collectors.toList());
        if (result.size() == 0) {
            System.out.println("Total 0 students have been notified.");
            checkStr();
        }
        for (Student student : result) {
            List<Integer> indexList = new ArrayList<>();
            if (student.getListCourse().get(0).getGrade() >= 600) {
                indexList.add(0);
                student.getListCourse().get(0).setGrade(0);
                student.getListCourse().get(0).setCountTask(0);
            }
            if (student.getListCourse().get(1).getGrade() >= 400) {
                indexList.add(1);
                student.getListCourse().get(1).setGrade(0);
                student.getListCourse().get(1).setCountTask(0);
            }
            if (student.getListCourse().get(2).getGrade() >= 490) {
                indexList.add(2);
                student.getListCourse().get(2).setGrade(0);
                student.getListCourse().get(2).setCountTask(0);
            }
            if (student.getListCourse().get(3).getGrade() >= 550) {
                indexList.add(3);
                student.getListCourse().get(3).setGrade(0);
                student.getListCourse().get(3).setCountTask(0);
            }
            for (Integer integer : indexList) {
                System.out.println("To: " + student.getEmail());
                System.out.println("Re: Your Learning Progress");
                System.out.println("Hello, John Doe! You have accomplished our " +
                        listCourses.get(integer) + " course!");
            }
        }
        System.out.println("Total " + result.size() + " students have been notified.");
        checkStr();
    }

    /**
     * метод выдает информацию по курсу на консоль
     * прмеры ввода: java,dsa,Database,Spring
     * back - возвратит к основным командам
     * по другому вводу выдаст - Unknown course.
     */
    private void informCourse() {
        String strCourse = scannerLine.nextLine().toLowerCase();
        switch (strCourse) {
            case "back": {
                break;
            }
            case "java": {
                System.out.println("Java");
                System.out.println("id" + "\t\t" + "points" + "\t\t" + "completed");
                List<Student> studentList = statisticsStudent.studentActivity(studentMap, 0);
                Comparator<Student> studentComparator = Comparator.comparingDouble(m ->
                        (double) m.getListCourse().get(0).getGrade() * 100 / 600);
                studentComparator = studentComparator.reversed();
                Comparator<Student> resComparator = studentComparator.thenComparing(Student::getId);
                studentList.sort(resComparator);
                for (Student student : studentList) {
                    Formatter formatter = new Formatter(Locale.US);
                    double num = (double) student.getListCourse()
                            .get(0).getGrade() * 100.0 / 600.0;
                    System.out.println(student.getId() + "\t\t" +
                            student.getListCourse().get(0).getGrade() + "\t\t" +
                            formatter.format("%.1f",
                                    num) + "%");
                }
                informCourse();
                break;
            }
            case "dsa": {
                System.out.println("DSA");
                System.out.println("id" + "\t\t" + "points" + "\t\t" + "completed");
                List<Student> studentList = statisticsStudent.studentActivity(studentMap, 1);
                Comparator<Student> studentComparator = Comparator.comparingDouble(m ->
                        (double) m.getListCourse().get(1).getGrade() * 100 / 400);
                studentComparator = studentComparator.reversed();
                Comparator<Student> resComparator = studentComparator.thenComparing(Student::getId);
                studentList.sort(resComparator);
                for (Student student : studentList) {
                    Formatter formatter = new Formatter(Locale.US);
                    double num = (double) student.getListCourse()
                            .get(1).getGrade() * 100 / 400;
                    System.out.println(student.getId() + "\t\t" +
                            student.getListCourse().get(1).getGrade() + "\t\t" +
                            formatter.format("%.1f",
                                    num) + "%");
                }
                informCourse();
                break;
            }
            case "databases": {
                System.out.println("Databases");
                System.out.println("id" + "\t\t" + "points" + "\t\t" + "completed");
                List<Student> studentList = statisticsStudent.studentActivity(studentMap, 2);
                Comparator<Student> studentComparator = Comparator.comparingDouble(m ->
                        (double) m.getListCourse().get(2).getGrade() * 100 / 480);
                studentComparator = studentComparator.reversed();
                Comparator<Student> resComparator = studentComparator.thenComparing(Student::getId);
                studentList.sort(resComparator);
                for (Student student : studentList) {
                    Formatter formatter = new Formatter(Locale.US);
                    double num = (double) student.getListCourse()
                            .get(2).getGrade() * 100 / 480;
                    System.out.println(student.getId() + "\t\t" +
                            student.getListCourse().get(2).getGrade() + "\t\t" +
                            formatter.format("%.1f",
                                    num) + "%");
                }
                informCourse();
                break;
            }
            case "spring": {
                System.out.println("Spring");
                System.out.println("id" + "\t\t" + "points" + "\t\t" + "completed");
                List<Student> studentList = statisticsStudent.studentActivity(studentMap, 3);
                Comparator<Student> studentComparator = Comparator.comparingDouble(m ->
                        (double) m.getListCourse().get(3).getGrade() * 100 / 550);
                studentComparator = studentComparator.reversed();
                Comparator<Student> resComparator = studentComparator.thenComparing(Student::getId);
                studentList.sort(resComparator);
                for (Student student : studentList) {
                    Formatter formatter = new Formatter(Locale.US);
                    double num = (double) student.getListCourse()
                            .get(3).getGrade() * 100 / 550;
                    System.out.println(student.getId() + "\t\t" +
                            student.getListCourse().get(3).getGrade() + "\t\t" +
                            formatter.format("%.1f",
                                    num) + "%");
                }
                informCourse();
                break;
            }
            default: {
                System.out.println("Unknown course.");
                informCourse();
                break;
            }
        }
    }

    /**
     * метод выдает на консоль информацию по всем курсам
     */
    private void statisticAll() {
        List<String> listCourses = List.of("Java", "DSA", "Databases", "Spring");
        System.out.print("Most popular: ");
        List<Integer> resPopular = statisticsStudent.popular(this.studentMap);
        List<Integer> resActivity = statisticsStudent.activity(this.studentMap);
        List<Double> resEasyHard = statisticsStudent.easyHardCourse(this.studentMap);
        List<Integer> index1 = new ArrayList<>();
        List<Integer> index2 = new ArrayList<>();
        if (resPopular == null) {
            System.out.println("n/a");
        } else {
            int max = resPopular.indexOf(Collections.max(resPopular));
            for (int i = 0; i < resPopular.size(); i++) {
                if (resPopular.get(i).equals(resPopular.get(max))) {
                    index1.add(i);
                }
            }
            if (index1.size() == 0) {
                System.out.println("n/a");
            } else {
                for (int i = 0; i < index1.size(); i++) {
                    if (i == index1.size() - 1) {
                        System.out.println(listCourses.get(index1.get(i)));
                    } else {
                        System.out.print(listCourses.get(index1.get(i)) + ", ");
                    }
                }
            }
        }
        System.out.print("Least popular: ");
        if (resPopular == null) {
            System.out.println("n/a");
        } else {
            int min = resPopular.indexOf(Collections.min(resPopular));
            for (int i = 0; i < resPopular.size(); i++) {
                if (resPopular.get(i).equals(resPopular.get(min))) {
                    index2.add(i);
                }
            }
            int count1 = 0;
            for (int i = 0; i < index2.size(); i++) {
                if (index1.contains(index2.get(i))) {
                    count1++;
                } else {
                    if (i == index2.size() - 1) {
                        System.out.println(listCourses.get(index2.get(i)));
                    } else if (!index1.contains(index2.get(i))) {
                        System.out.print(listCourses.get(index2.get(i)) + ", ");
                    }
                }

            }
            if (count1 == index2.size()) {
                System.out.println("n/a");
            }
        }

        System.out.print("Highest activity: ");
        if (resActivity == null) {
            System.out.println("n/a");
        } else {
            int max = resActivity.indexOf(Collections.max(resActivity));
            index1.clear();
            for (int i = 0; i < resActivity.size(); i++) {
                if (resActivity.get(i).equals(resActivity.get(max))) {
                    index1.add(i);
                }
            }
            if (index1.size() == 0) {
                System.out.println("n/a");
            } else {
                for (int i = 0; i < index1.size(); i++) {
                    if (i == index1.size() - 1) {
                        System.out.println(listCourses.get(index1.get(i)));
                    } else {
                        System.out.print(listCourses.get(index1.get(i)) + ", ");
                    }
                }
            }
        }
        System.out.print("Lowest activity: ");
        if (resActivity == null) {
            System.out.println("n/a");
        } else {
            int min = resActivity.indexOf(Collections.min(resActivity));
            index2.clear();
            for (int i = 0; i < resActivity.size(); i++) {
                if (resActivity.get(i).equals(resActivity.get(min))) {
                    index2.add(i);
                }
            }
            int count2 = 0;
            for (int i = 0; i < index2.size(); i++) {
                if (index1.contains(index2.get(i))) {
                    count2++;
                } else {
                    if (i == index2.size() - 1) {
                        System.out.println(listCourses.get(index2.get(i)));
                    } else if (!index1.contains(index2.get(i))) {
                        System.out.print(listCourses.get(index2.get(i)) + ", ");
                    }
                }
            }
            if (count2 == index2.size()) {
                System.out.println("n/a");
            }
        }

        System.out.print("Easiest course: ");
        if (resEasyHard == null) {
            System.out.println("n/a");
        } else {
            int max = resEasyHard.indexOf(Collections.max(resEasyHard));
            index1.clear();
            for (int i = 0; i < resEasyHard.size(); i++) {
                if (resEasyHard.get(i).equals(resEasyHard.get(max))) {
                    index1.add(i);
                }
            }
            if (index1.size() == 0) {
                System.out.println("n/a");
            } else {
                for (int i = 0; i < index1.size(); i++) {
                    if (i == index1.size() - 1) {
                        System.out.println(listCourses.get(index1.get(i)));
                    } else {
                        System.out.print(listCourses.get(index1.get(i)) + ", ");
                    }
                }
            }
        }
        System.out.print("Hardest course: ");
        double min;
        if (resEasyHard == null) {
            System.out.println("n/a");
        } else {
            min = resEasyHard.get(0) != 0 ? resEasyHard.get(0)
                    : resEasyHard.get(1) != 0 ? resEasyHard.get(1)
                    : resEasyHard.get(2) != 0 ? resEasyHard.get(2)
                    : resEasyHard.get(3);
            for (Double aDouble : resEasyHard) {
                if (aDouble != 0 && aDouble < min) {
                    min = aDouble;
                }
            }
            index2.clear();
            for (int i = 0; i < resEasyHard.size(); i++) {
                if (resEasyHard.get(i) == min) {
                    index2.add(i);
                }
            }
            int count4 = 0;
            for (int i = 0; i < index2.size(); i++) {
                if (index1.contains(index2.get(i))) {
                    count4++;
                } else {
                    if (i == index2.size() - 1) {
                        System.out.println(listCourses.get(index2.get(i)));
                    } else {
                        System.out.print(listCourses.get(index2.get(i)) + ", ");
                    }
                }
            }
            if (count4 == index2.size()) {
                System.out.println("n/a");
            }
        }
    }

    /**
     * вывод информации о студенте по Id
     */
    private void findStudent() {
        String strId = scannerLine.nextLine();
        if ("back".equals(strId)) {
            checkStr();
            return;
        }

        if (searchStudentForId(strId)) {
            System.out.println("No student is found for id=" + strId + ".");
        } else {
            for (var student : studentMap.entrySet()) {
                if (student.getValue().getId() == Integer.parseInt(strId)) {
                    System.out.println(strId + " points: Java="
                            + student.getValue().getListCourse().get(0).getGrade()
                            + "; DSA=" + student.getValue().getListCourse().get(1).getGrade()
                            + "; Databases=" + student.getValue().getListCourse().get(2).getGrade()
                            + "; Spring=" + student.getValue().getListCourse().get(3).getGrade());
                }
            }
        }
        findStudent();
    }

    /**
     * метод добавления оценок
     */
    private void addPoints() {
        String strPoint = scannerLine.nextLine();
        if ("back".equals(strPoint)) {
            checkStr();
            return;
        }
        String[] arrPoint = strPoint.split(" ");

        if (arrPoint.length != 5) {
            System.out.println("Incorrect points format");
            addPoints();
        }

        for (int i = 1; i < arrPoint.length; i++) {
            if (!arrPoint[i].matches("\\d+")) {
                System.out.println("Incorrect points format");
                addPoints();
            }
        }

        if (searchStudentForId(arrPoint[0])) {
            System.out.println("No student is found for id=" + arrPoint[0] + ".");
            addPoints();
        }

        int gradeJava = Integer.parseInt(arrPoint[1]);
        int gradeDSA = Integer.parseInt(arrPoint[2]);
        int gradeBD = Integer.parseInt(arrPoint[3]);
        int gradeSpring = Integer.parseInt(arrPoint[4]);
        for (var student : studentMap.entrySet()) {
            if (student.getValue().getId() == Integer.parseInt(arrPoint[0])) {
                student.getValue().getListCourse().get(0).setGrade(student.getValue().
                        getListCourse().get(0).getGrade() + gradeJava);
                student.getValue().getListCourse().get(1).setGrade(student.getValue().
                        getListCourse().get(1).getGrade() + gradeDSA);
                student.getValue().getListCourse().get(2).setGrade(student.getValue().
                        getListCourse().get(2).getGrade() + gradeBD);
                student.getValue().getListCourse().get(3).setGrade(student.getValue().
                        getListCourse().get(3).getGrade() + gradeSpring);
                if (gradeJava != 0) {
                    student.getValue().getListCourse().get(0).setCountTask(student.getValue().
                            getListCourse().get(0).getCountTask() + 1);
                }
                if (gradeDSA != 0) {
                    student.getValue().getListCourse().get(1).setCountTask(student.getValue().
                            getListCourse().get(1).getCountTask() + 1);
                }
                if (gradeBD != 0) {
                    student.getValue().getListCourse().get(2).setCountTask(student.getValue().
                            getListCourse().get(2).getCountTask() + 1);
                }
                if (gradeSpring != 0) {
                    student.getValue().getListCourse().get(3).setCountTask(student.getValue().
                            getListCourse().get(3).getCountTask() + 1);
                }
            }
        }
        System.out.println("Points updated.");
        addPoints();
    }

    /**
     * метод добавления студентов
     */
    private void addStudent() {
        String str = scannerLine.nextLine();
        if (str == null) {
            System.out.println("No input.");
        } else {
            if (str.equals("back")) {
                System.out.println("Total " + count + " students have been added.");
                checkStr();
            } else {
                checkIncorrectInput(str);
            }
        }
    }

    /**
     * метод проверяет правильность введения данных о студенте
     *
     * @param str введенная строка
     */
    private void checkIncorrectInput(String str) {
        String[] partsStr = str.split(" ");
        String firstname;
        String lastname;
        String email;
        StringBuilder lastnameStringBuilder = new StringBuilder();

        if (partsStr.length < 3 || str.strip().length() == 0) {
            System.out.println("Incorrect credentials.");
            addStudent();
        } else if (partsStr.length == 3) {
            firstname = partsStr[0];
            lastname = partsStr[1];
            email = partsStr[2];
            checkRegUser(firstname, lastname, email);
        } else {
            firstname = partsStr[0];
            email = partsStr[partsStr.length - 1];
            for (int i = 1; i < partsStr.length - 1; i++) {
                if (i == partsStr.length - 2) {
                    lastnameStringBuilder.append(partsStr[i]);
                } else {
                    lastnameStringBuilder.append(partsStr[i]).append(" ");
                }
            }
            lastname = lastnameStringBuilder.toString();
            checkRegUser(firstname, lastname, email);
        }
    }

    /**
     * метод прповерки правильности введения имени и почты
     *
     * @param firstname введеное имя
     * @param lastname введенная фамилия
     * @param email введенная почта
     */
    private void checkRegUser(String firstname, String lastname, String email) {
        String patternFirstname = "^[a-zA-Z](['-](?!['-])|[a-zA-Z])*[a-zA-Z]$";
        String patternLastname = "^[a-zA-Z]([\\s'-](?![\\s'-])|[a-zA-Z])*[a-zA-Z]$";
        String patternEmail = "^\\w+([\\\\.-]?\\w+)*@\\w+([\\\\.-]?\\w+)*\\.[a-z0-9]+$";
        if (!firstname.matches(patternFirstname)) {
            System.out.println("Incorrect first name.");
            addStudent();
        } else if (!lastname.matches(patternLastname)) {
            System.out.println("Incorrect last name.");
            addStudent();
        } else if (!email.matches(patternEmail)) {
            System.out.println("Incorrect email.");
            addStudent();
        } else {
            if (studentMap.containsKey(email)) {
                System.out.println("This email is already taken.");
            } else {
                studentMap.put(email, new Student(countId++, firstname, lastname,
                        email));
                System.out.println("The student has been added.");
                count++;
            }
            addStudent();
        }
    }

    /**
     * поиск студента по Id
     *
     * @param id введенное id
     * @return существут введеный студент или нет
     */
    private boolean searchStudentForId(String id) {
        boolean flag = false;
        if (!id.matches("\\d+")) {
            return true;
        } else {
            for (var student : studentMap.entrySet()) {
                if (student.getValue().getId() == Integer.parseInt(id)) {
                    flag = true;
                    break;
                }
            }
        }
        return !flag;
    }
}
