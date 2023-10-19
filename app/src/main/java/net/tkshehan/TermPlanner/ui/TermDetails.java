package net.tkshehan.TermPlanner.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import net.tkshehan.TermPlanner.R;

public class TermDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
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
        if(item.getItemId()== R.id.newCourseButton) {
            Intent intent=new Intent(TermDetails.this, CourseDetails.class);
            startActivity(intent);
        }
        if(item.getItemId()== R.id.deleteTermButton) {
            // Check if term is empty before deleting
        }

        return  super.onOptionsItemSelected(item);
    }
}