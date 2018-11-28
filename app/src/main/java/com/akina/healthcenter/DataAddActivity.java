package com.akina.healthcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Calendar;

public class DataAddActivity extends AppCompatActivity {

    String datatype;
    NumberPicker data, unit, hour, min, ampm, day, month, year;
    TextView val_tv, unit_tv, time_tv, date_tv;
    SeekBar moodbar;
    Button value;
    int currMood;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        datatype = extras.getString(MainActivity.DATA_TYPE);
        Calendar today = Calendar.getInstance();

        String[] w_str_unit = {"kg"};
        String[] bg_str_unit = {"mmol/L"};
        String[] ampm_str = {"AM","PM"};
        String[] month_str_unit = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};

        String[] min_str = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
                            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                            "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
                            "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
                            "40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
                            "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};

        moodbar = findViewById(R.id.mood_bar);
        data = findViewById(R.id.data_pick);
        unit = findViewById(R.id.unit_pick);

        hour = findViewById(R.id.time_hour);
        min = findViewById(R.id.time_min);
        ampm = findViewById(R.id.time_ampm);

        day = findViewById(R.id.time_day);
        month = findViewById(R.id.time_month);
        year = findViewById(R.id.time_year);

        val_tv = findViewById(R.id.val_btn_txt);
        unit_tv = findViewById(R.id.val_btn_unit_txt);
        time_tv = findViewById(R.id.time_btn_txt);
        date_tv = findViewById(R.id.date_btn_txt);
        TextView title = findViewById(R.id.edit_add_title);

        // Initialization
        day.setMinValue(1);
        day.setMaxValue(31);
        month.setDisplayedValues(month_str_unit);
        month.setMaxValue(11);
        year.setMinValue(1500);
        year.setMaxValue(9999);

        day.setValue(today.get(Calendar.DAY_OF_MONTH));
        month.setValue(today.get(Calendar.MONTH));
        year.setValue(today.get(Calendar.YEAR));

        hour.setMinValue(1);
        hour.setMaxValue(12);
        min.setDisplayedValues(min_str);
        min.setMaxValue(59);
        ampm.setDisplayedValues(ampm_str);
        ampm.setMaxValue(1);

        int currHour = today.get(Calendar.HOUR_OF_DAY);

        hour.setValue(currHour);
        ampm.setValue(0);
        if(currHour > 11)
        {
            hour.setValue(currHour - 12);
            ampm.setValue(1);
        }
        if(hour.getValue() == 0)
            hour.setValue(12);
        min.setValue(today.get(Calendar.MINUTE));
        time_tv.setText(Integer.toString(hour.getValue()) + ":" + min.getDisplayedValues()[min.getValue()]  + ampm.getDisplayedValues()[ampm.getValue()] );

        day.setVisibility(View.INVISIBLE);
        month.setVisibility(View.INVISIBLE);
        year.setVisibility(View.INVISIBLE);
        hour.setVisibility(View.INVISIBLE);
        min.setVisibility(View.INVISIBLE);
        ampm.setVisibility(View.INVISIBLE);

        date_tv.setText(Integer.toString(day.getValue()) + "-" + month.getDisplayedValues()[month.getValue()] + "-" + Integer.toString(year.getValue()) );

        SetClickListeners();
        InitializeNumberPickers();

        switch (datatype)
        {
            case ("WEIGHT"):
                data.setVisibility(View.VISIBLE);
                unit.setVisibility(View.VISIBLE);
                data.setMaxValue(500);

                data.setValue(50);
                val_tv.setText("50");
                unit_tv.setText("kg");
                unit.setDisplayedValues(w_str_unit);
                value.setText("Weight");
                title.setText("Add Weight");
                break;
            case ("BLOODGLUCOSE"):
                data.setVisibility(View.VISIBLE);
                unit.setVisibility(View.VISIBLE);
                data.setMaxValue(50);
                data.setValue(4);
                val_tv.setText("4");
                unit_tv.setText("mmol/L");
                unit.setDisplayedValues(bg_str_unit);
                value.setText("Blood Glucose");
                title.setText("Add Blood Glucose");
                break;
            case ("MOOD"):
                title.setText("Add Mood");

                moodbar.setVisibility(View.VISIBLE);
                val_tv.setText("");
                unit_tv.setText("Ok");
                currMood = 3;
                value.setText("Mood");
                moodbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        currMood = progress + 1;
                        switch(progress)
                        {
                            case 0:
                                moodbar.setThumb(getDrawable(R.mipmap.bad));
                                unit_tv.setText("Bad");
                                break;
                            case 1:
                                moodbar.setThumb(getDrawable(R.mipmap.not_so_good));
                                unit_tv.setText("Not So Good");
                                break;
                            case 2:
                                moodbar.setThumb(getDrawable(R.mipmap.ok));
                                unit_tv.setText("Ok");
                                break;
                            case 3:
                                moodbar.setThumb(getDrawable(R.mipmap.happy));
                                unit_tv.setText("Happy");
                                break;
                            case 4:
                                moodbar.setThumb(getDrawable(R.mipmap.excellent));
                                unit_tv.setText("Excellent");
                                break;
                        }

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                break;

        }



    }

    void SetClickListeners()
    {
        Button save = findViewById(R.id.save_btn);
        Button cancel = findViewById(R.id.cancel_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar newDate = Calendar.getInstance();
                int actualHour = hour.getValue();
                if(actualHour == 12)
                    actualHour = 0;

                if(ampm.getValue() == 1)
                    actualHour += 12;

                newDate.set(year.getValue(), month.getValue(), day.getValue(), actualHour, min.getValue());
                switch (datatype)
                {
                    case ("WEIGHT"):
                        MainActivity.wList.add(new DataObj(Integer.parseInt(val_tv.getText().toString()), newDate));
                        break;
                    case ("BLOODGLUCOSE"):
                        MainActivity.bgList.add(new DataObj(Integer.parseInt(val_tv.getText().toString()), newDate));
                        break;
                    case ("MOOD"):
                        MainActivity.mList.add(new DataObj(currMood, newDate));
                        break;

                }
                DatalistActivity.adapter.notifyDataSetChanged();
                MainActivity.updateData();
                DataAddActivity.super.finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataAddActivity.super.finish();
            }
        });

        value = findViewById(R.id.val_btn);
        Button date = findViewById(R.id.date_btn);
        Button time = findViewById(R.id.time_btn);


        value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day.setVisibility(View.INVISIBLE);
                month.setVisibility(View.INVISIBLE);
                year.setVisibility(View.INVISIBLE);
                hour.setVisibility(View.INVISIBLE);
                min.setVisibility(View.INVISIBLE);
                ampm.setVisibility(View.INVISIBLE);
                switch (datatype)
                {
                    case ("WEIGHT"):
                        data.setVisibility(View.VISIBLE);
                        unit.setVisibility(View.VISIBLE);
                        break;
                    case ("BLOODGLUCOSE"):
                        data.setVisibility(View.VISIBLE);
                        unit.setVisibility(View.VISIBLE);
                        break;
                    case ("MOOD"):
                        moodbar.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day.setVisibility(View.VISIBLE);
                month.setVisibility(View.VISIBLE);
                year.setVisibility(View.VISIBLE);
                hour.setVisibility(View.INVISIBLE);
                min.setVisibility(View.INVISIBLE);
                ampm.setVisibility(View.INVISIBLE);
                data.setVisibility(View.INVISIBLE);
                unit.setVisibility(View.INVISIBLE);
                moodbar.setVisibility(View.INVISIBLE);
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day.setVisibility(View.INVISIBLE);
                month.setVisibility(View.INVISIBLE);
                year.setVisibility(View.INVISIBLE);
                hour.setVisibility(View.VISIBLE);
                min.setVisibility(View.VISIBLE);
                ampm.setVisibility(View.VISIBLE);
                data.setVisibility(View.INVISIBLE);
                unit.setVisibility(View.INVISIBLE);
                moodbar.setVisibility(View.INVISIBLE);
            }
        });

    }

    void InitializeNumberPickers()
    {
        data.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                val_tv.setText(Integer.toString(picker.getValue()));
            }
        });
        hour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                time_tv.setText(Integer.toString(hour.getValue()) + ":" + min.getDisplayedValues()[min.getValue()] + ampm.getDisplayedValues()[ampm.getValue()] );
            }
        });
        min.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                time_tv.setText(Integer.toString(hour.getValue()) + ":" + min.getDisplayedValues()[min.getValue()]  + ampm.getDisplayedValues()[ampm.getValue()] );
            }
        });
        ampm.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                time_tv.setText(Integer.toString(hour.getValue()) + ":" + min.getDisplayedValues()[min.getValue()]  + ampm.getDisplayedValues()[ampm.getValue()] );
            }
        });


        day.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                date_tv.setText(Integer.toString(day.getValue()) + "-" + month.getDisplayedValues()[month.getValue()] + "-" + Integer.toString(year.getValue()) );
            }
        });
        month.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                date_tv.setText(Integer.toString(day.getValue()) + "-" + month.getDisplayedValues()[month.getValue()] + "-" + Integer.toString(year.getValue()) );
            }
        });
        year.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                date_tv.setText(Integer.toString(day.getValue()) + "-" + month.getDisplayedValues()[month.getValue()] + "-" + Integer.toString(year.getValue()) );
            }
        });

    }
}
