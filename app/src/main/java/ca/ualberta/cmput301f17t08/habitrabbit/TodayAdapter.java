package ca.ualberta.cmput301f17t08.habitrabbit;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The defined adapter for today page layout
 */

public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.ViewHolder> {
    private ArrayList<Habit> habits;
    private Activity context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView habitNameLabel;
        public TextView habitReasonLabel;
        public LinearLayout frequencyLayout;
        public LinearLayout done_layout;


        public ViewHolder(View habitView) {
            super(habitView);

            habitNameLabel = (TextView) habitView.findViewById(R.id.habit_name);
            habitReasonLabel = (TextView) habitView.findViewById(R.id.habit_reason);
            frequencyLayout = (LinearLayout) habitView.findViewById(R.id.habitLayout);
            done_layout = (LinearLayout) habitView.findViewById(R.id.done_layout);


        }}

    public TodayAdapter(ArrayList<Habit> habits, Activity context) {
        this.habits = habits;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TodayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View habitView = LayoutInflater.from(parent.getContext()).inflate(R.layout.today_item, parent, false);
        TodayAdapter.ViewHolder viewHolder = new TodayAdapter.ViewHolder(habitView);

        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final Habit habit = habits.get(position);

        // TODO remove this later - just a temporary fix since the items in the database don't contain a frequency
        if (habit.getFrequency() == null){
            habit.setFrequency(new ArrayList<Integer>(Collections.nCopies(7, 0)));
        }

        holder.habitNameLabel.setText(habit.getName());
        holder.habitReasonLabel.setText(habit.getReason());

        // change the frequency button backgrounds for this habit item
        ArrayList<Integer> frequency = habit.getFrequency();

        for (int counter = 0; counter < frequency.size(); counter++) {
            if (frequency.get(counter) == 1){
                Button button = (Button)holder.frequencyLayout.findViewWithTag(Integer.toString(counter+1));
                button.setBackgroundResource(R.drawable.gradient);
            }
        }

        holder.done_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Habit habit = habits.get(position);

                Intent intent = new Intent(context, AddHabitEventActivity.class);
                intent.putExtra("habit", habit);
                context.startActivity(intent);

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HabitStatsActivity.class);

                intent.putExtra("habit_id",habit.getId());
              
                context.startActivity(intent);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return habits.size();
    }

}