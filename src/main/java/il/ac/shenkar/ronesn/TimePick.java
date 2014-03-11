package il.ac.shenkar.ronesn;

/**
 * Created by Noam Rones on 3/3/14.
 */

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.TimeZone;


/**
 * class TimePick
 * set alarm to start  notification about
 */

public class TimePick extends FragmentActivity implements
        OnClickListener {

    Button btnCalendar, btnTimePicker, btnCancel,btnOK;

    Calendar today = Calendar.getInstance();
    // Variable for storing current date and time
    private int Year, Month, Day, Hour, Minute;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
        //create and set the buttons
        btnCalendar = (Button) findViewById(R.id.btnCalendar);
        btnTimePicker = (Button) findViewById(R.id.btnTimePicker);
        btnOK = (Button) findViewById(R.id.btnOk);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCalendar.setOnClickListener(this);
        btnCalendar.setText(getTime("date"));
        btnTimePicker.setOnClickListener(this);
        btnTimePicker.setText(getTime("time"));
        btnCancel.setOnClickListener(this);
        btnOK.setOnClickListener(this);
    }

    /**
     * when click on one of the button execution the suitable function
     **/
    @Override
    public void onClick(View v) {

        if (v == btnCalendar) {
            // Process to get Current Date
            Year = today.get(Calendar.YEAR);
            Month = today.get(Calendar.MONTH);
            Day = today.get(Calendar.DAY_OF_MONTH);

            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // Display Selected date in textbox
                            btnCalendar.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            today.set(Calendar.DATE, dayOfMonth);
                            today.set(Calendar.MONTH, monthOfYear);
                            today.set(Calendar.YEAR, year);
                        }
                    }, Year, Month, Day);
            dpd.show();
        }
        if (v == btnTimePicker) {
            // Process to get Current Time
            Hour = today.get(Calendar.HOUR_OF_DAY);
            Minute = today.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog tpd = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            // Display Selected time in textbox
                            String time=hourOfDay + ":";
                            if(minute<10)
                                time+="0";
                            time+=minute;
                            btnTimePicker.setText(time);
                            today.set(Calendar.HOUR, hourOfDay);
                            today.set(Calendar.MINUTE, minute);
                            today.set(Calendar.SECOND, 0);
                        }
                    }, Hour, Minute, false);
            tpd.show();
        }

        if (v == btnOK) {//button OK click-set the alarm to the required time

            long difference;//holds the gap in millisecond between now and the required time
            AlarmManager am;

            difference= today.getTimeInMillis()-Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis()-12*60*60*1000;
            am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            // when the alarm wake up he'll active the TimeAlarm class
            Intent intent = new Intent(this, TimeAlarm.class);
            Bundle b =getIntent().getExtras();
            intent.putExtras(b);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                    intent, PendingIntent.FLAG_ONE_SHOT);
            am.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+difference, pendingIntent);
            finish();
        }
        if (v == btnCancel) {//cancel the alarm setting
            finish();
        }
    }

    /**
     * Helper method to set the current time
     * return string with the current time
     **/
    private String getTime(String type) {
        String str="";
        if (type.equals("date")){//set the current date
            str+=today.get(Calendar.DAY_OF_MONTH)+"/";
            str+=(today.get(Calendar.MONTH)+1)+"/";
            str+=today.get(Calendar.YEAR);
        }
        if (type.equals("time")){ //set the current time
            str+=today.get(Calendar.HOUR_OF_DAY)+":";
            Minute=today.get(Calendar.MINUTE);
            if(Minute<10)
                str+="0";
            str+=Minute;
        }
        return str;
    }
}