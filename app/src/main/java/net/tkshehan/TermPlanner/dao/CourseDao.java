package net.tkshehan.TermPlanner.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import net.tkshehan.TermPlanner.entities.Assessment;
import net.tkshehan.TermPlanner.entities.Course;

import java.util.List;

@Dao
public interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM Assessment WHERE courseID=:courseID")
    List<Assessment> getAssociatedAssessments(int courseID);

}
