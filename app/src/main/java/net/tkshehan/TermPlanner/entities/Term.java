package net.tkshehan.TermPlanner.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity
public class Term {
    @PrimaryKey
    private int termID;
    private String title;
    private Date startDate;
    private Date endDate;

}
