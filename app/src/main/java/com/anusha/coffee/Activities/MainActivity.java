package com.anusha.coffee.Activities;

import com.anusha.coffee.Adapters.BadgeManager;
import com.anusha.coffee.Adapters.Gamification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

//import com.anusha.coffee.Adapters.CustomArFragment;
import com.anusha.coffee.Adapters.GamificationListener;
import com.anusha.coffee.Models.Message1;
import com.anusha.coffee.R;
import com.anusha.coffee.Models.User;
import com.anusha.coffee.Adapters.UsersAdapter;
import com.anusha.coffee.databinding.ActivityMainBinding;
//import com.google.ar.core.AugmentedFace;
//import com.google.ar.core.Frame;
//import com.google.ar.sceneform.rendering.ModelRenderable;
//import com.google.ar.sceneform.rendering.Renderable;
//import com.google.ar.sceneform.rendering.Texture;
//import com.google.ar.sceneform.ux.AugmentedFaceNode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GamificationListener {
//progressbar
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
//end

    //gamification
    private Gamification gamification;



    ActivityMainBinding binding;
    FirebaseDatabase database;
    ArrayList<User> users;
    UsersAdapter usersAdapter;

    ImageButton imageButton;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //progressbar
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        // Start long running operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 600) {
                    progressStatus += 1;

                    // Update the progress bar and display the current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });

                    try {
                        // Sleep for 1000 milliseconds.
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Close the application after the progress bar reaches 100%

                finish();
            }
        }).start();
    //for usage stats
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(intent);
// end
        //gamification
        // Initialize Gamification class
        gamification = new Gamification(this);

        Button rewardButton = findViewById(R.id.reward_button);
        binding.rewardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gamification.rewardUser("user123", 10);
            }
        });

        // Call rewardUser() method to reward the user




        //end



        database = FirebaseDatabase.getInstance();
        users = new ArrayList<>();

        usersAdapter = new UsersAdapter(this,users);
        binding.recyclerView.setAdapter(usersAdapter);

        database.getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren()) {
                    User user = snapshot1.getValue(User.class);
                    users.add(user);
                }
                usersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       int id = item.getItemId();
       if(id == R.id.search)
       {
           Intent intent = new Intent(MainActivity.this,ChatgptActivity.class);
           //Testing GamificationActivity.java
//           Intent intent = new Intent(MainActivity.this,GamificationActivity.class);

           startActivity(intent);
           return true;
       }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onRewardUser(String userId, long appUsage) {

        String badgeId = "badge001"; // or any other badge ID that you want to award
        gamification.awardBadge(userId,badgeId);
        Log.d("MainActivity", "User " + userId + " rewarded with " + appUsage + " app usage.");

        // Check if the user has earned a badge
        BadgeManager badgeManager = new BadgeManager(this);
        String badge = badgeManager.checkForBadge(userId, appUsage);
        if (badge != null) {
            // Show a toast message to the user
            String message = "Congratulations! You have earned the " + badge + " badge. Keep it up and try to reduce your app usage to earn even more reward points.";
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAwardBadge(String userId, String badgeId) {


        // Implement your own logic here when a user is awarded a badge
        Log.d("MainActivity", "User " + userId + " awarded badge " + badgeId);

    }
}