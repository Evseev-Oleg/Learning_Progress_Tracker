package tracker;

import tracker.course.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * класс имеет различные методы по сбору статистики
 */
public class Statistics {

    /**
     * метод ищет студентов которые хоть что то сделали на курсе
     *
     * @param map в этой таблице храняться все студенты <Почта, Студент>
     * @param index для поиска определенного курса: 0(Java),1(DSA),2(Database),3(Spring)
     * @return возвращаем лист студентов которые сделали хотя бы одну задачу
     */
    public List<Student> studentActivity(Map<String, Student> map, int index) {
        List<Student> studentList = new ArrayList<>();
        for (var stud : map.entrySet()) {
            if (stud.getValue().getListCourse().get(index) != null &&
                    stud.getValue().getListCourse().get(index).getCountTask() != 0)
                studentList.add(stud.getValue());
        }
        return studentList;
    }

    /**
     * метод по поиску популярности курса
     * популярность подразумевает - число студентов записавшихся на курс
     * (если у студента есть выполненные задания - студент записан на курс)
     *
     * @param studentMap в этой таблице храняться все студенты <Почта, Студент>
     * @return возвращаем лист чисел - число людей записавщихся на курс
     * пример list.get(0) - выдаст нам число студентов на курсе Java
     * 1 - DSA
     * 2 - Db
     * 3 - Spring
     */
    public List<Integer> popular(Map<String, Student> studentMap) {
        if (studentMap == null) {
            return null;
        }
        int countStudentsJava = 0;
        int countStudentsDSA = 0;
        int countStudentsBD = 0;
        int countStudentsSpring = 0;
        for (var student : studentMap.entrySet()) {
            List<Course> courses = student.getValue().getListCourse();
            if (courses.get(0).getGrade() != 0) {
                countStudentsJava++;
            }
            if (courses.get(1).getGrade() != 0) {
                countStudentsDSA++;
            }
            if (courses.get(0).getGrade() != 0) {
                countStudentsBD++;
            }
            if (courses.get(0).getGrade() != 0) {
                countStudentsSpring++;
            }
        }
        if (countStudentsJava == 0 && countStudentsDSA == 0
                && countStudentsBD == 0 && countStudentsSpring == 0) {
            return null;
        }
        return List.of(countStudentsJava, countStudentsDSA
                , countStudentsBD, countStudentsSpring);
    }

    /**
     * метод по поиску активности студентов
     * активность подрозумевает - число выполненых заданий на курсе
     *
     * @param studentMap в этой таблице храняться все студенты <Почта, Студент>
     * @return возвращаем лист чисел - общее число выполненных заданий на курсе
     * пример list.get(0) - выдаст нам число выполненных заданий на курсе Java
     * 1 - DSA
     * 2 - Db
     * 3 - Spring
     */
    public List<Integer> activity(Map<String, Student> studentMap) {
        if (studentMap == null) {
            return null;
        }
        int countActivityJava = 0;
        int countActivityDSA = 0;
        int countActivityBD = 0;
        int countActivitySpring = 0;
        for (var student : studentMap.entrySet()) {
            List<Course> courses = student.getValue().getListCourse();
            countActivityJava += courses.get(0).getCountTask();
            countActivityDSA += courses.get(1).getCountTask();
            countActivityBD += courses.get(2).getCountTask();
            countActivitySpring += courses.get(3).getCountTask();
        }
        if (countActivityJava == 0 && countActivityDSA == 0
                && countActivityBD == 0 && countActivitySpring == 0) {
            return null;
        }
        return List.of(countActivityJava, countActivityDSA
                , countActivityBD, countActivitySpring);
    }

    /**
     * метод по расчету средней оценки на курсе
     *
     * @param studentMap в этой таблице храняться все студенты <Почта, Студент>
     * @return возвращаем double числа со средней оценкой по каждому курсу
     * пример list.get(0) - выдаст нам среднюю оценку по Java
     * 1 - DSA
     * 2 - Db
     * 3 - Spring
     */
    public List<Double> easyHardCourse(Map<String, Student> studentMap) {
        if (studentMap == null) {
            return null;
        }
        int countJava = 0;
        int countDSA = 0;
        int countBD = 0;
        int countSpring = 0;
        for (var student : studentMap.entrySet()) {
            List<Course> courses = student.getValue().getListCourse();
            countJava += courses.get(0).getGrade();
            countDSA += courses.get(1).getGrade();
            countBD += courses.get(2).getGrade();
            countSpring += courses.get(3).getGrade();
        }
        if (countJava == 0 && countDSA == 0
                && countBD == 0 && countSpring == 0) {
            return null;
        }
        List<Integer> listCountTask = activity(studentMap);
        double midlGradeJava = (double) countJava / (double) listCountTask.get(0);
        double midlGradeDSA = (double) countDSA / (double) listCountTask.get(1);
        double midlGradeBD = (double) countBD / (double) listCountTask.get(2);
        double midlGradeSpring = (double) countSpring / (double) listCountTask.get(3);

        return List.of(midlGradeJava, midlGradeDSA
                , midlGradeBD, midlGradeSpring);
    }
}
