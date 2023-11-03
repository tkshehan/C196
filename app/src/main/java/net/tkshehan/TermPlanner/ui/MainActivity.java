package net.tkshehan.TermPlanner.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import net.tkshehan.TermPlanner.R;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button enterButton = new Button(this);
        enterButton.setOnClickListener(this::onEnter);
        enterButton.setText("Enter");
        enterButton.setBackgroundColor(Color.parseColor("#508CFC"));
        ConstraintLayout cl = findViewById(R.id.constraint);
        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(300, 150);
        lp.leftToLeft = R.id.constraint;
        lp.rightToRight = R.id.constraint;
        lp.bottomToBottom = R.id.constraint;
        lp.bottomMargin = 250;
        lp.leftMargin = 160;
        lp.rightMargin = 160;
        cl.addView(enterButton, lp);
    }

    public void onEnter(View view) {
         Intent intent=new Intent(MainActivity.this, TermList.class);
         startActivity(intent);
    }
}