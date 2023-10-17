package net.tkshehan.TermPlanner.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Course {
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

}
