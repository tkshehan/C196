package net.tkshehan.TermPlanner.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AssessmentDetails extends AppCompatActivity {

    int assessmentID;
    int courseID;
    Date startDate;
    Date endDate;

    EditText editTitle;
    Spinner editType;
    TextView editStartDate;
    TextView editEndDate;

    Repository repository;
    Calendar myCalendarStart;
    final String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf;

    DatePickerDialog.OnDateSetListener startDateSetListener;
    DatePickerDialog.OnDateSetListener endDateSetListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        repository = new Repository(getApplication());
        myCalendarStart = Calendar.getInstance();
        sdf = new SimpleDateFormat(myFormat, Locale.US);

        editTitle = findViewById(R.id.assessmentTitle);
        editStartDate = findViewById(R.id.assessmentStart);
        editEndDate = findViewById(R.id.assessmentEnd);
        editType = findViewById(R.id.assessmentStatus);

        editTitle.setText(getIntent().getStringExtra("title"));
        assessmentID = getIntent().getIntExtra("assessmentID", 0);
        courseID = getIntent().getIntExtra("courseID", 0);
        Assessment.Type type = (Assessment.Type)getIntent().getSerializableExtra("type");
        if(type != null) {
            editType.setSelection(type.ordinal());
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
                new DatePickerDialog(AssessmentDetails.this, startDateSetListener, myCalendarStart
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
                new DatePickerDialog(AssessmentDetails.this, endDateSetListener, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_assessment_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            this.finish();
            return true;
        }
        if(item.getItemId()== R.id.saveAssessmentButton) {
            saveAssessment();
            Toast.makeText(this,"Saved", Toast.LENGTH_SHORT).show();
            this.finish();
            return true;
        }
        if(item.getItemId()== R.id.deleteAssessmentButton) {
            if(assessmentID == 0) {
                this.finish();
            } else {
                repository.delete(getAssessmentFromForm());
                this.finish();
            }
            return true;
        }

        return  super.onOptionsItemSelected(item);
    }

    private Assessment getAssessmentFromForm() {
        return new Assessment(
                assessmentID,
                editTitle.getText().toString(),
                startDate,
                endDate,
                Assessment.Type.valueOf(editType.getSelectedItem().toString()),
                courseID
        );
    }

    private void saveAssessment() {
        Assessment assessment = getAssessmentFromForm();

        if (assessment.getAssessmentID() == 0) {
            repository.insert(assessment);
        } else {
            repository.update(assessment);
        }
    }
}