package net.tkshehan.TermPlanner.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.tkshehan.TermPlanner.R;
import net.tkshehan.TermPlanner.database.Repository;
import net.tkshehan.TermPlanner.entities.Assessment;
import net.tkshehan.TermPlanner.entities.Course;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseDetails extends AppCompatActivity {
    int courseID;
    int termID;
    String notes = "";
    Date startDate;
    Date endDate;
    List<Assessment> associatedAssessments;

    EditText editTitle;
    TextView editStartDate;
    TextView editEndDate;
    Spinner editStatus;
    EditText editInstructorName;
    EditText editInstructorEmail;
    EditText editInstructorPhone;

    RecyclerView assessmentRecyclerView;

    Repository repository;
    Calendar myCalendarStart;
    final String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf;

    DatePickerDialog.OnDateSetListener startDateSetListener;

    DatePickerDialog.OnDateSetListener endDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        repository = new Repository(getApplication());
        myCalendarStart = Calendar.getInstance();
        sdf = new SimpleDateFormat(myFormat, Locale.US);

        editTitle = findViewById(R.id.courseTitle);
        editStartDate = findViewById(R.id.courseStart);
        editEndDate = findViewById(R.id.courseEnd);
        editStatus = findViewById(R.id.courseStatus);

        editInstructorName = findViewById(R.id.instructorName);
        editInstructorEmail = findViewById(R.id.instructorEmail);
        editInstructorPhone = findViewById(R.id.instructorNumber);

        assessmentRecyclerView = findViewById(R.id.assessmentRecyclerView);

        // Assign from adapter
        courseID = getIntent().getIntExtra("courseID", 0);
        termID = getIntent().getIntExtra("termID", 0);
        editTitle.setText(getIntent().getStringExtra("title"));
        notes = getIntent().getStringExtra("notes");

        editInstructorName.setText(getIntent().getStringExtra("instructor"));
        editInstructorPhone.setText(getIntent().getStringExtra("phone"));
        editInstructorEmail.setText(getIntent().getStringExtra("email"));

        Course.Status status = (Course.Status)getIntent().getSerializableExtra("status");
        editStatus.setSelection(status.ordinal());

        startDate = (Date)getIntent().getSerializableExtra("startDate");
        endDate = (Date)getIntent().getSerializableExtra("endDate");

        if(startDate != null) {
            editStartDate.setText(sdf.format(startDate));
        }
        if(endDate != null) {
            editEndDate.setText(sdf.format(endDate));
        }


        startDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                editStartDate.setText(sdf.format(myCalendarStart.getTime()));
                startDate = myCalendarStart.getTime();
            }
        };

        editStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String info=editStartDate.getText().toString();
                if(info.equals("Select Date"))info=sdf.format(Calendar.getInstance().getTime());
                try{
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, startDateSetListener, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                editEndDate.setText(sdf.format(myCalendarStart.getTime()));
                endDate = myCalendarStart.getTime();
            }

        };
        editEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String info=editEndDate.getText().toString();
                if(info.equals("Select Date"))info=sdf.format(Calendar.getInstance().getTime());
                try{
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, endDateSetListener, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        // TODO Assessment adapter
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_course_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            this.finish();
            return true;
        }
        if(item.getItemId()== R.id.saveCourseButton) {
            saveCourse();
            return true;
        }

        if(item.getItemId()== R.id.courseNotesButton) {
            // TODO add notes
            return true;
        }
        if(item.getItemId()== R.id.newAssessmentButton) {
            if(termID == 0) {
                Toast.makeText(this, "Save before adding assessments", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(CourseDetails.this, AssessmentDetails.class);
                startActivity(intent);
            }
            return true;
        }
        if(item.getItemId()== R.id.deleteCourseButton) {
            if(termID == 0) {
                this.finish();
            } else {
                repository.delete(getCourseFromForm());
            }
            return true;
        }
        return  super.onOptionsItemSelected(item);
    }

    private void saveCourse() {

        Course course = getCourseFromForm();

        if(courseID == 0) {
            repository.insert(course);
        } else {
            repository.update(course);
        }
    }

    private Course getCourseFromForm() {
        return new Course(
                courseID,
                editTitle.getText().toString(),
                startDate,
                endDate,
                Course.Status.valueOfName(editStatus.getSelectedItem().toString()),
                editInstructorName.getText().toString(),
                editInstructorEmail.getText().toString(),
                editInstructorPhone.getText().toString(),
                termID,
                notes);
    }
}