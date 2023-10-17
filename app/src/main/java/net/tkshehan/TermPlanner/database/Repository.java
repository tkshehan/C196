package net.tkshehan.TermPlanner.database;

import android.app.Application;

import net.tkshehan.TermPlanner.dao.AssessmentDao;
import net.tkshehan.TermPlanner.dao.CourseDao;
import net.tkshehan.TermPlanner.dao.TermDao;
import net.tkshehan.TermPlanner.entities.Assessment;
import net.tkshehan.TermPlanner.entities.Course;
import net.tkshehan.TermPlanner.entities.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private final TermDao termDao;
    private final CourseDao courseDao;
    private final AssessmentDao assessmentDao;

    private List<Term> allTerms;
    private List<Course> associatedCourses;
    private List<Assessment> associatedAssessments;
    private final int SLEEP_DURATION = 500;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        TermDatabaseBuilder db = TermDatabaseBuilder.getDatabase(application);
        termDao = db.termDao();
        courseDao = db.courseDao();
        assessmentDao = db.assessmentDao();
    }


    // Terms
    public List<Term> getAllTerms(){
        databaseExecutor.execute(() -> {
            allTerms = termDao.getAllTerms();
        });

        try {
            Thread.sleep(SLEEP_DURATION);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return allTerms;
    }

    public List<Course> getAssociatedCourses(int termID){
        databaseExecutor.execute(() -> {
            associatedCourses = termDao.getAssociatedCourses(termID);
        });

        try {
            Thread.sleep(SLEEP_DURATION);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return associatedCourses;
    }

    public void insert(Term term) {
        databaseExecutor.execute(() -> {
            termDao.insert(term);
        });
        try {
            Thread.sleep(SLEEP_DURATION);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Term term) {
        databaseExecutor.execute(() -> {
            termDao.update(term);
        });
        try {
            Thread.sleep(SLEEP_DURATION);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Term term) {
        databaseExecutor.execute(() -> {
            termDao.delete(term);
        });
        try {
            Thread.sleep(SLEEP_DURATION);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Courses

    public void insert(Course course) {
        databaseExecutor.execute(() -> {
            courseDao.insert(course);
        });
        try {
            Thread.sleep(SLEEP_DURATION);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Course course) {
        databaseExecutor.execute(() -> {
            courseDao.update(course);
        });
        try {
            Thread.sleep(SLEEP_DURATION);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Course course) {
        databaseExecutor.execute(() -> {
            courseDao.delete(course);
        });
        try {
            Thread.sleep(SLEEP_DURATION);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Assessment> getAssociatedAssessments(int courseID) {
        databaseExecutor.execute(() -> {
            associatedAssessments = courseDao.getAssociatedAssessments(courseID);
        });
        try {
            Thread.sleep(SLEEP_DURATION);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return associatedAssessments;
    }

    // Assessments
    public void insert(Assessment assessment) {
        databaseExecutor.execute(() -> {
            assessmentDao.insert(assessment);
        });
        try {
            Thread.sleep(SLEEP_DURATION);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Assessment assessment) {
        databaseExecutor.execute(() -> {
            assessmentDao.update(assessment);
        });
        try {
            Thread.sleep(SLEEP_DURATION);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Assessment assessment) {
            databaseExecutor.execute(() -> {
                assessmentDao.delete(assessment);
        });
        try {
            Thread.sleep(SLEEP_DURATION);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
