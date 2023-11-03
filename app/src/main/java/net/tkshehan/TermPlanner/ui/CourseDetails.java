package net.tkshehan.TermPlanner.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
        if(status != null) {
            editStatus.setSelection(status.ordinal());
        }

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
    // TODO Alert when course and assessments start/end

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        associatedAssessments = repository.getAssociatedAssessments(courseID);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(associatedAssessments);
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
            Toast.makeText(this,"Saved", Toast.LENGTH_SHORT).show();
            return true;
        }

        if(item.getItemId()== R.id.courseNotesButton) {
            showNotes();
            return true;
        }
        if(item.getItemId()== R.id.newAssessmentButton) {
            if(termID == 0) {
                Toast.makeText(this, "Save before adding assessments", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(CourseDetails.this, AssessmentDetails.class);
                intent.putExtra("courseID", courseID);
                startActivity(intent);
            }
            return true;
        }
        if(item.getItemId()== R.id.deleteCourseButton) {
            if (termID == 0) {
                this.finish();
            } else {
                // Delete course and assessments
                List<Assessment> assessments = repository.getAssociatedAssessments(courseID);
                for (Assessment assessment : assessments) {
                    repository.delete(assessment);
                }
                repository.delete(getCourseFromForm());
            }
            return true;
        }
        if(item.getItemId()== R.id.notify) {
            if(endDate == null || startDate == null) {
                Toast.makeText(this, "Select a start and end date", Toast.LENGTH_SHORT).show();
            } else {
                notify(startDate, "Course "+ editTitle.getText().toString() + " Starting");
                notify(endDate, "Course "+ editTitle.getText().toString() + " Ending");
            }
            return true;
        }
        return  super.onOptionsItemSelected(item);
    }

    private void notify(Date date, String message) {
        Long trigger = date.getTime();
        Intent intent = new Intent(CourseDetails.this, MyReceiver.class);
        intent.putExtra("key", message);
        PendingIntent sender = PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
    }

    private void showNotes() {
        Dialog builder = new Dialog(this);
        builder.setContentView(R.layout.dialog_notes);
        builder.show();
        EditText editNotes = builder.findViewById(R.id.edit_notes);
        editNotes.setText(notes);
        Button saveNotesButton = builder.findViewById(R.id.saveNotes);
        Button closeNotesButton = builder.findViewById(R.id.closeNotes);
        Button shareNotesButton = builder.findViewById(R.id.shareNotes);

        saveNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notes = editNotes.getText().toString();
                builder.dismiss();
            }
        });
        closeNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
            }
        });
        shareNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, editNotes.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Notes for " + editTitle.getText().toString());
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });
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