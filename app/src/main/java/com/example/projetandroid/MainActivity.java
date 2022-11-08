package com.example.projetandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button stop;
    private Button record;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, 100 );
        }


            Button play = findViewById(R.id.play);
            stop = findViewById(R.id.stop);
            record = findViewById(R.id.record);

            stop.setEnabled(false);
            play.setEnabled(false);

            String outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";

            MediaRecorder myAudioRecorder = new MediaRecorder();
            myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            myAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            myAudioRecorder.setOutputFile(outputFile);

            record.setOnClickListener(view -> {
                try {
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                } catch (IllegalStateException ise) {
                    //Android Catch
                } catch (IOException ioe) {
                    //Input/Output catch
                }

                record.setEnabled(false);
                stop.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
            });
            stop.setOnClickListener(view -> {

                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder=null;
                record.setEnabled(true);
                stop.setEnabled(false);
                play.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Audio recorder ouais", Toast.LENGTH_LONG).show();
            });

            play.setOnClickListener(view -> {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(outputFile);
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                    Toast.makeText(getApplicationContext(), "Ecoute ca wesh", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    //tmtc
                }

            });
    }

}