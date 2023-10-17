package net.tkshehan.TermPlanner.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import net.tkshehan.TermPlanner.entities.Course;
import net.tkshehan.TermPlanner.entities.Term;

import java.util.List;

@Dao
public interface TermDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete (Term term);

    @Query("SELECT * FROM TERM ORDER BY termID ASC;")
    List<Term> getAllTerms();

    @Query("SELECT * FROM COURSE WHERE termID=:termID;")
    List<Course> getAssociatedCourses(int termID);
}
