package net.tkshehan.TermPlanner.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity
public class Assessment {
    public enum Type {
        Objective,
        Performance
    }
    @PrimaryKey(autoGenerate = true)
    int assessmentID;
    String title;
    Date startDate;
    Date endDate;
    int courseID;

    Type type;

    public Assessment(int assessmentID, String title, Date startDate, Date endDate, Type type, int courseID) {
        this.assessmentID = assessmentID;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
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

    public Type getType() {return type;}
    public void setType(Type type) {this.type = type;}

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
