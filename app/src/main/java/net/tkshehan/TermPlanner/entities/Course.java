package net.tkshehan.TermPlanner.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Objects;

@Entity
public class Course {
    public enum Status {
        InProgress("In Progress"),
        Completed("Completed"),
        Dropped("Dropped"),
        Planned("Planned");
        final String name;
        Status(String s) {
            name = s;
        }

        public static Status valueOfName(String name) {
            if(Objects.equals(name, "In Progress")) {
                return InProgress;
            } else {
                return valueOf(name);
            }
        }

        @NonNull
        @Override
        public String toString() {
            return name;
        }
    }

    @PrimaryKey(autoGenerate = true)
    private final int courseID;

    private String title;
    private Status status;
    private Date startDate;
    private Date endDate;

    private String instructor;
    private String phone;
    private String email;

    private String notes;

    private int termID;

    public Course(int courseID, String title, Date startDate, Date endDate, Status status, String instructor, String email, String phone, int termID, String notes) {
        this.title = title;
        this.courseID = courseID;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.instructor = instructor;
        this.phone = phone;
        this.email = email;
        this.termID = termID;
        this.notes = notes;
    }
    public int getCourseID() {
        return courseID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }
}
