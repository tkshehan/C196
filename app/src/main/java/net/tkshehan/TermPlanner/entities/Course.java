package net.tkshehan.TermPlanner.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Course {
    public int getCourseID() {
        return courseID;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    @PrimaryKey(autoGenerate = true)
    private int courseID;

    private String status;
    private Date startDate;
    private Date endDate;

    private String instructor;
    private String phone;
    private String email;

    private String notes;

    private int termID;

    public Course(int courseID, String status, Date startDate, Date endDate, String instructor, String phone, String email, String notes, int termID) {
        this.courseID = courseID;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.instructor = instructor;
        this.phone = phone;
        this.email = email;
        this.notes = notes;
        this.termID = termID;
    }
}
