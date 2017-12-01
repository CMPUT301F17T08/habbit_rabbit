package ca.ualberta.cmput301f17t08.habitrabbit;

/**
 * The defined adapter for history page
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    private  ArrayList<HabitEvent> habitEvents;
    public ArrayList<String> username;
    public Activity context;

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
    public FeedAdapter(ArrayList<String> username, ArrayList<HabitEvent> habitEvents, Activity context) {

        this.habitEvents = habitEvents; //get the habitsEvents list passed in
        this.username =  username;
        this.context = context;
    }
    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();//get the context
        LayoutInflater inflater = LayoutInflater.from(context);//initialize the layout inflater

        // Inflate the custom layout
        View feedView = inflater.inflate(R.layout.post, parent, false);

        // Return a new holder instance
        FeedAdapter.ViewHolder viewHolder = new FeedAdapter.ViewHolder(feedView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final FeedAdapter.ViewHolder viewHolder, final int position) {

        //change the text and appearance of each elements on the layout
        viewHolder.habitName.setText(habitEvents.get(position).getHabit().getName());
        viewHolder.Comment.setText(habitEvents.get(position).getComment());
        viewHolder.historyDate.setText(habitEvents.get(position).getDateCompleted().toString());
        viewHolder.numLike.setText(Integer.toString(habitEvents.get(position).getLikeCount())+" likes");
        viewHolder.userNameView.setText(habitEvents.get(position).getUsername());
        String username = LoginManager.getInstance().getCurrentUser().getUsername();

        //check if the current user has liked the feed before
        if (habitEvents.get(position).getLikes().contains(username)){
            viewHolder.likeButton.setBackgroundResource(R.drawable.black_like);
        }

        //get the image the user uploaded, set the image if exist
        Bitmap userImage = habitEvents.get(position).getPicture();
        if (userImage != null){
            viewHolder.imagePreview.setImageBitmap(userImage);
        }


        //functionality of likes to the post
        viewHolder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HabitEvent event = habitEvents.get(position);
                int oldLikeCount = event.getLikeCount();

                // mark the event as liked by this user
                String username = LoginManager.getInstance().getCurrentUser().getUsername();
                event.like(username);

                // display the updated like count
                int newLikeCount = event.getLikeCount();
                viewHolder.numLike.setText(Integer.toString(newLikeCount)+" likes");

                // change the colour of the like button based on if this was the like or dislike
                if (oldLikeCount < newLikeCount){
                    // event was liked
                    viewHolder.likeButton.setBackgroundResource (R.drawable.black_like);

                    DatabaseManager.getInstance().getUserData(username, new DatabaseManager.OnUserDataListener() {
                        @Override
                        public void onUserData(User user) {
                            final User eventOwner = user;
                            eventOwner.addLikedUser(LoginManager.getInstance().getCurrentUser());
                        }

                        @Override
                        public void onUserDataFailed(String message) {

                        }
                    });
                }
                else{
                    // event was disliked
                    viewHolder.likeButton.setBackgroundResource (R.drawable.like);
                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return habitEvents.size();
    }
}