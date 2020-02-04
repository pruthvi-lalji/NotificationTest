package com.example.notificationtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {


    public static final String CHANNEL_ID = "Main 1";
    private static final String CHANNEL_NAME = "Main 2";
    private static final String CHANNEL_DESC = "Notification";
    public static TextView textView;
    public static TextView textView1;
    private String token;
    public static EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        textView = findViewById(R.id.textViewToken);
        textView1 = findViewById(R.id.info);
        name = findViewById(R.id.nameText);

    }



    public void tokenGen(View v){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if(task.isSuccessful()){
                            token = task.getResult().getToken();
                            textView.setText("Token: " + token);
                            sendingData(token);
                        }else {
                            textView.setText(task.getException().getMessage());
                            textView1.append("Error");

                        }




                    }
                });
    }


    private void sendingData(String token){
        String user = name.getText().toString();
        String user_token = token;
        String type = "token";
        BackgroundTask backgroundTask  = new BackgroundTask(this);
        backgroundTask.execute(type,user, user_token);
    }




}
