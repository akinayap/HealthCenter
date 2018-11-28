package com.akina.healthcenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public enum Datatype {
        WEIGHT,
        BLOODGLUCOSE,
        MOOD
    }

    public static final String DATA_TYPE = "NONE";

    public static ArrayList<DataObj> wList = new ArrayList<>();
    public static ArrayList<DataObj> bgList = new ArrayList<>();
    public static ArrayList<DataObj> mList = new ArrayList<>();

    RelativeLayout wBtn, bgBtn, mBtn;
    public static TextView wTxt, bgTxt, mTxt, wDateTxt, bgDateTxt, mDateTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wList.add(new DataObj(52, Calendar.getInstance()));
        wList.add(new DataObj(5, Calendar.getInstance()));
        wList.add(new DataObj(0, Calendar.getInstance()));
        bgList.add(new DataObj(550, Calendar.getInstance()));
        bgList.add(new DataObj(550, Calendar.getInstance()));
        bgList.add(new DataObj(570, Calendar.getInstance()));
        bgList.add(new DataObj(5420, Calendar.getInstance()));
        wList.add(new DataObj(520, Calendar.getInstance()));
        wList.add(new DataObj(510, Calendar.getInstance()));
        mList.add(new DataObj(2, Calendar.getInstance()));
        wList.add(new DataObj(580, Calendar.getInstance()));
        mList.add(new DataObj(1, Calendar.getInstance()));
        wList.add(new DataObj(50, Calendar.getInstance()));
        mList.add(new DataObj(2, Calendar.getInstance()));
        mList.add(new DataObj(3, Calendar.getInstance()));
        mList.add(new DataObj(4, Calendar.getInstance()));
        mList.add(new DataObj(3, Calendar.getInstance()));
        mList.add(new DataObj(3, Calendar.getInstance()));
        mList.add(new DataObj(5, Calendar.getInstance()));


        wBtn = findViewById(R.id.weight_btn);
        bgBtn = findViewById(R.id.blood_glucose_btn);
        mBtn = findViewById(R.id.mood_btn);

        wBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                showData(Datatype.WEIGHT);
            }
        });

        bgBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                showData(Datatype.BLOODGLUCOSE);
            }
        });

        mBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                showData(Datatype.MOOD);
            }
        });

        wTxt = findViewById(R.id.weight_data_txt);
        bgTxt = findViewById(R.id.blood_glucose_data_txt);
        mTxt = findViewById(R.id.mood_data_txt);

        wDateTxt = findViewById(R.id.weight_date_txt);
        bgDateTxt = findViewById(R.id.blood_glucose_date_txt);
        mDateTxt = findViewById(R.id.mood_date_txt);

        updateData();

    }

    public static void updateData()
    {
        DateFormat dateFormat = new SimpleDateFormat("d MMMM YYYY, h:mm a", Locale.ENGLISH);

        if(wList.isEmpty()) {
            wTxt.setText("-");
            wDateTxt.setText("");
        } else {
            wTxt.setText(Integer.toString(wList.get(0).m_data));
            wDateTxt.setText(dateFormat.format(wList.get(0).m_time.getTimeInMillis()));
        }


        if(bgList.isEmpty()) {
            bgTxt.setText("-");
            bgDateTxt.setText("");
        } else {
            bgTxt.setText(Integer.toString(bgList.get(0).m_data));
            bgDateTxt.setText(dateFormat.format(bgList.get(0).m_time.getTimeInMillis()));
        }

        if(mList.isEmpty()) {
            mTxt.setText("-");
            mDateTxt.setText("");
        } else {
            switch(mList.get(0).m_data)
            {
                case 1:
                    mTxt.setText("Bad");
                    break;
                case 2:
                    mTxt.setText("Not So Good");
                    break;
                case 3:
                    mTxt.setText("Ok");
                    break;
                case 4:
                    mTxt.setText("Happy");
                    break;
                case 5:
                    mTxt.setText("Excellent");
                    break;
            }
            mDateTxt.setText(dateFormat.format(mList.get(0).m_time.getTimeInMillis()));
        }
    }

    public void showData(Datatype type)
    {
        Intent intent = new Intent(this, DatalistActivity.class);

        Bundle extras = new Bundle();
        extras.putString(DATA_TYPE, type.name());
        Log.e("type", type.name());
        intent.putExtras(extras);
        startActivity(intent);
    }
}
