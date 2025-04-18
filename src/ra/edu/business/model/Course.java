package ra.edu.business.model;

import java.time.LocalDate;

public class Course {
    private int courseId;
    private String name;
    private int duration;
    private String instructor;
    private LocalDate createAt;

    public Course() {
    }

    public Course(int courseId, String name, int duration, String instructor, LocalDate createAt) {
        this.courseId = courseId;
        this.name = name;
        this.duration = duration;
        this.instructor = instructor;
        this.createAt = createAt;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }
}