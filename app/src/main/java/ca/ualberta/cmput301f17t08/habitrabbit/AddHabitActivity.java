package ca.ualberta.cmput301f17t08.habitrabbit;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddHabitActivity extends AppCompatActivity {
    private AddHabitActivity activity = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_habit);

        final EditText habitTitle = (EditText) findViewById(R.id.habit_title_field);
        final EditText habitReason = (EditText) findViewById(R.id.habit_reason_field);
        final EditText dateSelector= (EditText) findViewById(R.id.habit_date_selector);
        Button addHabitButton = (Button) findViewById(R.id.add_habit_button);


        // Date Picker (Source: https://goo.gl/nmN56M)
        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // update the editText label
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
                dateSelector.setText(format.format(calendar.getTime()));
            }
        };

        dateSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddHabitActivity.this, R.style.date_picker, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // submit button
        addHabitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String title = habitTitle.getText().toString();
                String reason = habitReason.getText().toString();
                Date d = calendar.getTime();

                // TODO do error checking here

                // TODO create a new habit object here and associate that with the user
            }
        });



    }

}
