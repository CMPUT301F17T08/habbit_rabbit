package ca.ualberta.cmput301f17t08.habitrabbit;

/**
 * Created by yuxuanzhao on 2017-11-07.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class historyAdapter extends RecyclerView.Adapter<historyAdapter.ViewHolder> {
    private  ArrayList<HabitEvent> habitEvents;
    public String username;

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView habitName;
        public TextView numLike;
        public TextView Comment;
        public TextView userNameView;
        public Button likeButton;
        public TextView historyDate;
        public ImageView imagePreview;


        public ViewHolder(View historyView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(historyView);

            habitName = historyView.findViewById(R.id.habit_name);
            numLike = historyView.findViewById(R.id.num_like);
            likeButton = historyView.findViewById(R.id.like_button);
            Comment = historyView.findViewById(R.id.comment);
            userNameView = historyView.findViewById(R.id.post_username);
            historyDate = historyView.findViewById(R.id.post_time);
            imagePreview = historyView.findViewById(R.id.post_image);
        }
    }
    public historyAdapter(String username, ArrayList<HabitEvent> habitEvents) {
        this.habitEvents = habitEvents; //get the habitsEvents list passed in
        this.username =  username;//get the username passed in from activity class
    }
    @Override
    public historyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();//get the context
        LayoutInflater inflater = LayoutInflater.from(context);//initialize the layout inflater

        // Inflate the custom layout
        View historyView = inflater.inflate(R.layout.post, parent, false);

        // Return a new holder instance
        historyAdapter.ViewHolder viewHolder = new historyAdapter.ViewHolder(historyView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(historyAdapter.ViewHolder viewHolder, final int position) {
        //change the text and appearance of each elements on the layout
        viewHolder.habitName.setText(habitEvents.get(position).getHabit().getName());
        viewHolder.Comment.setText(habitEvents.get(position).getComment());
        viewHolder.historyDate.setText(habitEvents.get(position).getDateCompleted().toString());
        viewHolder.numLike.setText(Integer.toString(habitEvents.get(position).getLikeCount())+" likes");
        viewHolder.userNameView.setText(username);
        //get the image the user uploaded, set the image if exist
        Bitmap userImage = habitEvents.get(position).getPicture();
        if (userImage != null){
            viewHolder.imagePreview.setImageBitmap(userImage);
        }
        //functionality of likes to the post
        viewHolder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                habitEvents.get(position).like(LoginManager.getInstance().getCurrentUser().getUsername());
            }
        });
    }
    @Override
    public int getItemCount() {
        return habitEvents.size();
    }
}