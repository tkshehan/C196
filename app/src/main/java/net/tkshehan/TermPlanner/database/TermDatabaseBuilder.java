package net.tkshehan.TermPlanner.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import net.tkshehan.TermPlanner.dao.AssessmentDao;
import net.tkshehan.TermPlanner.dao.CourseDao;
import net.tkshehan.TermPlanner.dao.TermDao;
import net.tkshehan.TermPlanner.entities.Assessment;
import net.tkshehan.TermPlanner.entities.Course;
import net.tkshehan.TermPlanner.entities.Term;

@Database(entities = {Term.class, Course.class, Assessment.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class TermDatabaseBuilder extends RoomDatabase {
    public abstract AssessmentDao assessmentDao();
    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    private static volatile TermDatabaseBuilder INSTANCE;

    static TermDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TermDatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TermDatabaseBuilder.class, "MyTermDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
