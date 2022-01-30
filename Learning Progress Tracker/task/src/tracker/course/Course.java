package tracker.course;

/**
 * абстрактный класс описывающий все курсы
 * содержит два поля grade(общее число оценок курса)
 * countTask(количество выполненых заданий)
 */
public abstract class Course {
    private int grade;
    private int countTask;

    public Course(int grade, int countTask) {
        this.grade = grade;
        this.countTask = countTask;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getCountTask() {
        return countTask;
    }

    public void setCountTask(int countTask) {
        this.countTask = countTask;
    }
}
