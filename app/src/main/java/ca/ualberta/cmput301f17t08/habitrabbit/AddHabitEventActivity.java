package ca.ualberta.cmput301f17t08.habitrabbit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AddHabitEventActivity extends AppCompatActivity {
    private AddHabitEventActivity activity = this;
    private ImageView imagePreview;
    private Bitmap bmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_habit_event);

        final Habit habit = (Habit) getIntent().getSerializableExtra("habit");

        final EditText habitTitle = findViewById(R.id.habit_name_field);
        final EditText habitComment = findViewById(R.id.habit_comment_field);
        imagePreview = findViewById(R.id.image_preview);
        Button addButton = findViewById(R.id.add_habit_event_button);

        habitTitle.setText(habit.getName());

        final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Edmonton"));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = habitComment.getText().toString();

                Boolean error = false;

                if (comment.length() > 20) {
                    habitTitle.setError("Please keep title under 20 characters");
                    error = true;
                }
                else if (bmp != null && bmp.getByteCount() > 65536){
                    Toast.makeText(activity, "Please select a size smaller than 65Kb",
                            Toast.LENGTH_LONG).show();
                    error = true;
                }

                if (!error){
                    // TODO assign the habit event to the habit
                    HabitEvent event = new HabitEvent(habit, calendar.getTime(), comment, null, bmp);
                    System.out.println("Created Habit Event");
                }
            }
        });

    }

    public void pickImage(View v) {
        // clear the old image if anything was selected before
        bmp = null;
        imagePreview.setImageResource(0);

        // show the dialog for picking an image
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    // does the image selection and displays the image in the preview window
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            try {
                InputStream inputStream = activity.getContentResolver().openInputStream(data
                            .getData());

                bmp = BitmapFactory.decodeStream(inputStream);
                imagePreview.setImageBitmap(bmp);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void showMenu(View v){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

}