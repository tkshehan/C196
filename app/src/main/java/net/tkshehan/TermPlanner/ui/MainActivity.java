package net.tkshehan.TermPlanner.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.tkshehan.TermPlanner.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onEnter(View view) {
         Intent intent=new Intent(MainActivity.this, TermList.class);
         startActivity(intent);
    }
}