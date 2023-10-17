package net.tkshehan.TermPlanner.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    int assessmentID;
    String title;
    Date startDate;
    Date endDate;
    int courseID;

    public Assessment(int assessmentID, String title, Date startDate, Date endDate, int courseID) {
        this.assessmentID = assessmentID;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseID = courseID;
    }

    public int getAssessmentID() {
        return assessmentID;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
