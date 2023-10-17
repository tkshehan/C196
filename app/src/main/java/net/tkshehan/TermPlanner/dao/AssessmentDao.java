package net.tkshehan.TermPlanner.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import net.tkshehan.TermPlanner.entities.Assessment;

@Dao
public interface AssessmentDao {
    @Insert
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);
    @Delete
    void delete(Assessment assessment);
}
