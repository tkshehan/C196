package net.tkshehan.TermPlanner.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.tkshehan.TermPlanner.R;
import net.tkshehan.TermPlanner.database.Repository;
import net.tkshehan.TermPlanner.entities.Course;
import net.tkshehan.TermPlanner.entities.Term;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermDetails extends AppCompatActivity {
    int termID;
    Repository repository;
    String title;
    Date startDate;
    Date endDate;
    List<Course> associatedCourses;

    String myFormat;
    SimpleDateFormat sdf;

    EditText editTitle;
    TextView editStartDate;
    TextView editEndDate;

    Calendar myCalendarStart;
    DatePickerDialog.OnDateSetListener startDateSetListener;
    DatePickerDialog.OnDateSetListener endDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        myCalendarStart = Calendar.getInstance();
        myFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        repository = new Repository(getApplication());


        termID = getIntent().getIntExtra("termID", 0);
        title = getIntent().getStringExtra("title");
        startDate = (Date)getIntent().getSerializableExtra("startDate");
        endDate = (Date)getIntent().getSerializableExtra("endDate");

        editTitle = findViewById(R.id.termTitle);
        editTitle.setText(title);

        editStartDate = findViewById(R.id.termStart);
        if(startDate != null) {
            editStartDate.setText(sdf.format(startDate));
        }


        editEndDate = findViewById(R.id.termEnd);
        if (endDate != null) {
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
                new DatePickerDialog(TermDetails.this, startDateSetListener, myCalendarStart
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
                new DatePickerDialog(TermDetails.this, endDateSetListener, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    @Override
    protected  void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.coursesRecyclerView);
        associatedCourses = repository.getAssociatedCourses(termID);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(associatedCourses);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_term_details, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            this.finish();
            return true;
        }
        if(item.getItemId()== R.id.saveTermButton) {
            saveTerm();
            return true;
        }
        if(item.getItemId()== R.id.newCourseButton) {
            if(termID == 0) {
                Toast.makeText(this, "Save before adding courses", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(TermDetails.this, CourseDetails.class);
                startActivity(intent);
            }
            return true;
        }
        if(item.getItemId()== R.id.deleteTermButton) {
            if(termID == 0) {
                this.finish();
            } else if(associatedCourses.size() > 0) {
                Toast.makeText(this,"Remove all courses before deleting Term", Toast.LENGTH_LONG).show();
            } else {
                Term term = new Term(termID, title, startDate, endDate);
                repository.delete(term);
                this.finish();
            }
            return true;
        }
        return  super.onOptionsItemSelected(item);
    }

    private void saveTerm() {
            Term term;
            term = new Term(termID, editTitle.getText().toString(), startDate, endDate);
            if(termID == 0) {
                repository.insert(term);
            } else {
                repository.update(term);
            }
            this.finish();
    }
}