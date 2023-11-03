package net.tkshehan.TermPlanner.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity
public class Term {
    @PrimaryKey(autoGenerate = true)
    private final int termID;
    private String title;
    private Date startDate;
    private Date endDate;

    public int getTermID() {
        return termID;
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


    public Term(int termID, String title, Date startDate, Date endDate) {
        this.termID = termID;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
