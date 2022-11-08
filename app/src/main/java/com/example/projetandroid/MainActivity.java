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
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100 );
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100 );
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
                Toast.makeText(getApplicationContext(), "Recording on prepared", Toast.LENGTH_LONG).show();
                try {
                    myAudioRecorder.prepare();

                    myAudioRecorder.start();

                } catch (IllegalStateException ise) {
                    //Android Catch
                } catch (IOException ioe) {
                    //Input/Output catch
                }
                Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
                record.setEnabled(false);
                stop.setEnabled(true);


            });
            stop.setOnClickListener(view -> {
                try {
                    myAudioRecorder.stop();
                    myAudioRecorder.reset();
                    myAudioRecorder.release();

                }catch(IllegalStateException ise){
                    //tt
                }
                record.setEnabled(true);
                stop.setEnabled(false);
                play.setEnabled(true);


                Toast.makeText(getApplicationContext(), "Audio recorder ouais", Toast.LENGTH_LONG).show();
            });

            play.setOnClickListener(view -> {
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.reset();
                Toast.makeText(getApplicationContext(), "Ecoute ca wesh", Toast.LENGTH_LONG).show();
                try {
                    mediaPlayer.setDataSource(outputFile);
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                    mediaPlayer.release();


                } catch (Exception e) {
                    //tmtc
                }

            });
    }

}