package net.tkshehan.TermPlanner.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity
public class Assessment {
    @PrimaryKey
    int assessmentID;
    String title;
    Date startDate;
    Date endDate;

    int courseID;
}
