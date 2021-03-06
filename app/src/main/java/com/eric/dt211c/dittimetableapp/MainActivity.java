package com.eric.dt211c.dittimetableapp;

/*
*
* Author: Eric Strong DT211C/3
* Module: Android - Mobile Software Development
* Description: The DIT timetable app will read from a customisable file into datastructures in order to help the student
 * find their classrooms. An SQLlite Database will also be used to persist notes, that the student will take during classes. The
 * will be prioritized by preference. Other activities include Browser and email activites to various useful student tools
*/


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    //database to be used when the app launches up to populate the arraylist for persistence

    private Database db = new Database(this);
    private Button timetableBtn = null;
    private Button viewNotesBtn = null;
    private Button myNotesBtn = null;
    private Button aboutBtn = null;
    private Button classesBtn = null;
    private Button webCoursesBtn = null;
    public boolean running = true;

    public static String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DITTimetableApp/timetable.txt";
    public static File fileCheck = new File(filePath);


    //used to ensure that when the app is launched at least once then the popup dialog box will be
    //incremented so it doesnt pop up every time. note using shared preferences
    public static int numberOfStarts = 0;
    public static final String STARTS = "number of starts";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //oncreate is invoked when the apps activity is launched


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //This will create the directory structure within the phone
        File directory1 = new File(Environment.getExternalStorageDirectory(), "DITTimetableApp");
        Log.d("eric", "create directory in " + Environment.getExternalStorageDirectory().getAbsolutePath());

        if (!directory1.exists()) {
            if (!directory1.mkdirs()) {
                Log.d("eric", "failed to create directory");
            }
        }

        //This will create the timetable file template within the directory within the phone if not already created
        File file = new File(directory1, "timetable.txt"); // create the file
        if (!file.exists())
            try {
                //file.createNewFile();
                String testData = fileStructure();
                ;
                FileOutputStream f = new FileOutputStream(file, false);
                f.write(testData.getBytes());
                f.close();
            } catch (IOException e) {
                Log.d("eric", "file did not create");
            }

        //popup when the app is launched.
        //Shared preferences will ensure that this is only run on the first 3 times the application is launched
        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
        int number = 0;
        numberOfStarts = sp.getInt(STARTS, number);

        if (numberOfStarts < 2) {

            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

            alert.setTitle("Welcome to My Next Class App");

            alert.setMessage("1. Go To Settings > Apps > My Next Class > Permissions > Enable Access to Storage.\n 2. Customize your Schedule. Would you like" +
                    " to view a video on how to format this file?");

            alert.setNegativeButton("No", null);


            //this will create an intent to the about page
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    running = false;
                    //this is what happens when YES is clicked
                    startActivity(new Intent(MainActivity.this, AboutPage.class));

                }
            });
            alert.setIcon(R.drawable.clock2);
            alert.show();

            //incremented so it doenst show every time
            numberOfStarts++;


        }//end running


        SharedPreferences.Editor ed = sp.edit();
        ed.putInt(STARTS, numberOfStarts);
        ed.commit();

        //retrieving saved data from database
        MyNotesActivity.taskList = db.getTasks();

        //switching to timetable screen and activity
        timetableBtn = (Button) findViewById(R.id.goToTimetable);

        timetableBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), PopulateTimetable.class);

                //checks if the file is in the directory before opening up timetable intent
                // String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DITTimetableApp/timetable.txt";
                //File fileCheck = new File(filePath);
                if (fileCheck.exists()) {
                    startActivity(i);
                }//end file checker

                else {
                    Toast.makeText(MainActivity.this, "Please ensure settings>apps>MY NEXT CLASS> Permissions > Storage > ON has been enabled",
                            Toast.LENGTH_LONG).show();
                }


            }
        });

        //switching to MyNotes screen and activity
        myNotesBtn = (Button) findViewById(R.id.goToMyNotes);

        myNotesBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), MyNotesActivity.class);
                startActivity(i);
            }
        });

        //switching to ViewNotes screen and activity
        viewNotesBtn = (Button) findViewById(R.id.goToViewNotes);

        viewNotesBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ViewNotesActivity.class);
                startActivity(i);
            }
        });

        //switching to About screen and activity
        aboutBtn = (Button) findViewById(R.id.goToAbout);

        aboutBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), AboutPage.class);
                startActivity(i);
            }
        });

        //switching to view Classes and activity
        classesBtn = (Button) findViewById(R.id.goToClasses);

        classesBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ViewClassesActivity.class);


                if (fileCheck.exists()) {
                    startActivity(i);
                }//end file checker

                else {
                    Toast.makeText(MainActivity.this, "Please ensure settings>apps>MY NEXT CLASS> Permissions > Storage > ON has been enabled",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        //switching to webCourses and activity
        webCoursesBtn = (Button) findViewById(R.id.goToWebCourses);

        webCoursesBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                goToUrl("https://dit-bb.blackboard.com/webapps/bb-auth-provider-shibboleth-BBLEARN/execute/shibbolethLogin?returnUrl=https%3A//dit-bb.blackboard.com/webapps/portal/frameset.jsp&authProviderId=_102_1d");
            }
        });


        //switching to exam papers
        webCoursesBtn = (Button) findViewById(R.id.goToExamPapers);

        webCoursesBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Toast.makeText(MainActivity.this, "login: Student Password: ThunderRoad",
                        Toast.LENGTH_LONG).show();
                goToUrl("http://student.dit.ie/exampapers/KT/2017_College_of_Science/");
            }
        });


    }//end main onCreate

    private void goToUrl(String url) {
        //browser activity
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    public static String fileStructure() {
        //this function is used to create the file structure in which is auto created for the student. with dummy data

        String s = "";

        for (int i = 1; i <= 7; i++) {
            for (int j = 8; j <= 21; j++) {
                s += String.format("%d,%d,Free,Free_class,\n", i, j); // these values are all the same and intended to be edited
                /*
                for future project iteration, webscraping could be used here to access the timetable server and scrape the data from the
                web page
                 */
                Log.d("Eric", s);

            }
        }

        return s;
    }
}//end class MainActivity
