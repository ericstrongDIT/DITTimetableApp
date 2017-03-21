package com.eric.dt211c.dittimetableapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class PopulateTimetable extends AppCompatActivity {

    //for background image
    private ImageView bgImage = null;

    public static HashMap <Integer,String[]> classes = new HashMap<Integer,String[]>();
    public String[] namesOfDays =
            { "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" };

    //MAIN BUTTON CLICK

    public ArrayList<String[]> list = new ArrayList<String[]>();


    Button mButton = null;
    TextView text1 = null;  //current class
    TextView text2 = null;  //current room
    TextView text3 = null;  //next class
    TextView text4 = null;  // next room
    TextView now1 = null;
    TextView next1 = null;


    //location to where the txt file is saved to on your android device
    public String path = Environment.getExternalStorageDirectory() + "/DITTimetableApp/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

          /*

            CHANGE BACKGROUND IMAGE DEPENDING ON WHAT DAY IT IS

         */
        bgImage = (ImageView)findViewById(R.id.mainbg);
        changeBackgroundImage();

        mButton = (Button)findViewById(R.id.click_main);
        text1 = (TextView)findViewById(R.id.currentclass);
        text2 = (TextView)findViewById(R.id.currentroom);
        text3 = (TextView)findViewById(R.id.nextclass);
        text4 = (TextView)findViewById(R.id.roomnext);
        now1 = (TextView)findViewById(R.id.now2);
        next1 = (TextView)findViewById(R.id.next2);
        Button url =(Button) findViewById(R.id.click_url);





        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                try {
                    ReadApp reader = new ReadApp(path + "/timetable.txt");
                    /*

                    CLASSES STRUCTURE IS RETURNED FROM FILE AND PUT INTO MEMORY
                     */
                    //getting file contents into memory
                    classes = reader.getContents();
                    Log.d("eric", "size of Classes is" + classes.size());

                    //for debugging reasons....
                    String contentToDisplay = reader.readFile();
                    Log.d("eric", contentToDisplay);


                }//end try
                catch (IOException e) {
                    //file not found
                    Toast.makeText(PopulateTimetable.this,"timetable.txt file not found. Please add this file to DITTimetable directory",
                            Toast.LENGTH_LONG).show();
                }//end catch






                String toCut=null;
                String buttonDisplay=null;
                String text1Display=null;
                String text2Display=null;
                String text3Display=null;
                String text4Display=null;
                String nowDisplay=null;
                String nextDisplay=null;


                int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                Date today = new Date();
                int hour = (int) today.getHours();
                int min = (int) today.getMinutes();
                int week = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);

                nowDisplay = ""+ hour + ".00";
                nextDisplay = ""+ (hour+1) + ".00";
                day = day - 1;


                switch (day)
                {
                    case 1:
                    {
                        // monday
                        // call mondayMap

                        if (hour < 8 || hour >= 18)
                        {
                            buttonDisplay = "no classes";
                            break;
                        }

                        toCut = populateTimeTable(day, hour,min,week);

                        String words[] = new String[4];
                        int i=0;
                        for (String word: toCut.split("-")) {
                            word = word.replace('_',' ');

                            words[i] = word;
                            i++;
                        }
                        text1Display= words[0];
                        text2Display= words[1];
                        text3Display= words[2];
                        text4Display= words[3];

                        //to get the remaining minutes per class
                        min = Math.abs(min - 60);
                        // off set of week 4, we start back in 4th week of the year
                        week = week - 3;

                        buttonDisplay = "Remaining Mins: " + min;




                    }
                    break;

                    case 2:
                    {
                        // tuesday
                        // call tueMap

                        if (hour < 8 || hour >= 18)
                        {

                            buttonDisplay = "no classes";
                            break;
                        }

                        toCut = populateTimeTable(day, hour,min,week);

                        String words[] = new String[4];
                        int i=0;
                        for (String word: toCut.split("-")) {
                            word = word.replace('_',' ');

                            words[i] = word;
                            i++;
                        }
                        text1Display= words[0];
                        text2Display= words[1];
                        text3Display= words[2];
                        text4Display= words[3];

                        //to get the remaining minutes per class
                        min = Math.abs(min - 60);
                        // off set of week 4, we start back in 4th week of the year
                        week = week - 3;

                        buttonDisplay = "Remaining Mins: " + min;


                    }
                    break;

                    case 3:
                    {
                        // wedneday
                        // call wedMap

                        if (hour < 9 || hour >= 18)
                        {
                            buttonDisplay = "no classes";

                            break;
                        }
                        toCut = populateTimeTable(day, hour,min,week);

                        String words[] = new String[4];
                        int i=0;
                        for (String word: toCut.split("-")) {
                            word = word.replace('_',' ');
                            words[i] = word;
                            i++;
                        }
                        text1Display= words[0];
                        text2Display= words[1];
                        text3Display= words[2];
                        text4Display= words[3];

                        //to get the remaining minutes per class
                        min = Math.abs(min - 60);
                        // off set of week 4, we start back in 4th week of the year
                        week = week - 3;

                        buttonDisplay = "Remaining Mins: " + min;

                    }
                    break;

                    case 4:
                    {
                        // thursday
                        // call thurMap

                        if (hour < 8 || hour >= 18)
                        {
                            buttonDisplay = "no classes";

                            break;
                        }
                        toCut = populateTimeTable(day, hour,min,week);

                        String words[] = new String[4];
                        int i=0;
                        for (String word: toCut.split("-")) {
                            word = word.replace('_',' ');
                            words[i] = word;
                            i++;
                        }
                        text1Display= words[0];
                        text2Display= words[1];
                        text3Display= words[2];
                        text4Display= words[3];

                        //to get the remaining minutes per class
                        min = Math.abs(min - 60);
                        // off set of week 4, we start back in 4th week of the year
                        week = week - 3;

                        buttonDisplay = "Remaining Mins: " + min;

                    }
                    break;

                    case 5:
                    {
                        // friday
                        // call friMap

                        if (hour < 8 || hour >= 18)
                        {
                            buttonDisplay = "no classes";

                            break;
                        }
                        toCut = populateTimeTable(day, hour,min,week);

                        String words[] = new String[4];
                        int i=0;
                        for (String word: toCut.split("-")) {
                            word = word.replace('_',' ');
                            words[i] = word;
                            i++;
                        }
                        text1Display= words[0];
                        text2Display= words[1];
                        text3Display= words[2];
                        text4Display= words[3];

                        //to get the remaining minutes per class
                        min = Math.abs(min - 60);
                        // off set of week 4, we start back in 4th week of the year
                        week = week - 3;

                        buttonDisplay = "Remaining Mins: " + min;


                    }
                    break;

                    default:
                    {


                        buttonDisplay = "No Classes";
                        text1Display="no classes";
                        text2Display="no classes";
                        text3Display="no classes";
                        text4Display="no classes";


                    }
                    break;

                }// end switch

                     /*
            * MAIN PART TO BE DISPLAYED
            * */

                mButton.setText(buttonDisplay);
                text1.setText(text1Display);
                text2.setText(text2Display);
                text3.setText(text3Display);
                text4.setText(text4Display);
                next1.setText(nextDisplay); //TIME NOW
                now1.setText(nowDisplay); // TIME MEXT
            }//end ONCLICK

            public String populateTimeTable(int flag, int key, int min,int week)
            {


                if (flag == 1)
                {
                    //each field must have 4 indexes - current class - room, next class = room

                    //mapMonday.putAll(classes);


                    //          class                       room

                    //to work for 12 hour clocks
                    if(key == 8 || key == 9 || key == 10 || key == 12 || key == 1 || key == 2 || key == 3 || key == 4 || key == 5
                            || key == 6 || key == 7){

                        return classes.get(key)[0] + "-" + classes.get(key)[1] + "-" +
                                classes.get(key+ 1)[0] + "-" + classes.get(key + 1)[1];

                    }


                    //to work for 24 hour clocks
                    return classes.get(key)[0] + "-" + classes.get(key)[1] + "-" +
                           classes.get(key+ 1)[0] + "-" + classes.get(key + 1)[1];
                }

                else if (flag == 2)
                {

                    //to work for 12 hour clocks
                    if(key == 8 || key == 9 || key == 10 || key == 12 || key == 1 || key == 2 || key == 3 || key == 4 || key == 5
                            || key == 6 || key == 7){

                        return classes.get(key)[0] + "-" + classes.get(key)[1] + "-" +
                                classes.get(key+ 1)[0] + "-" + classes.get(key + 1)[1];

                    }



                    //          class                       room
                    return classes.get(key)[0] + "-" + classes.get(key)[1] + "-" +
                            classes.get(key+ 1)[0] + "-" + classes.get(key + 1)[1];

                }

                else if (flag == 3)
                {
                    //to work for 12 hour clocks
                    if(key == 8 || key == 9 || key == 10 || key == 12 || key == 1 || key == 2 || key == 3 || key == 4 || key == 5
                            || key == 6 || key == 7){

                        return classes.get(key)[0] + "-" + classes.get(key)[1] + "-" +
                                classes.get(key+ 1)[0] + "-" + classes.get(key + 1)[1];

                    }



                    return classes.get(key)[0] + "-" + classes.get(key)[1] + "-" +
                            classes.get(key+ 1)[0] + "-" + classes.get(key + 1)[1];

                }

                else if (flag == 4)
                {
                    //to work for 12 hour clocks
                    if(key == 8 || key == 9 || key == 10 || key == 12 || key == 1 || key == 2 || key == 3 || key == 4 || key == 5
                            || key == 6 || key == 7){

                        return classes.get(key)[0] + "-" + classes.get(key)[1] + "-" +
                                classes.get(key+ 1)[0] + "-" + classes.get(key + 1)[1];

                    }


                    return classes.get(key)[0] + "-" + classes.get(key)[1] + "-" +
                            classes.get(key+ 1)[0] + "-" + classes.get(key + 1)[1];
                }

                else if (flag == 5)
                {
                    //to work for 12 hour clocks
                    if(key == 8 || key == 9 || key == 10 || key == 12 || key == 1 || key == 2 || key == 3 || key == 4 || key == 5
                            || key == 6 || key == 7){

                        return classes.get(key)[0] + "-" + classes.get(key)[1] + "-" +
                                classes.get(key+ 1)[0] + "-" + classes.get(key + 1)[1];

                    }


                    return classes.get(key)[0] + "-" + classes.get(key)[1] + "-" +
                            classes.get(key+ 1)[0] + "-" + classes.get(key + 1)[1];

                }

                else
                {

                    return "No Classes";
                }
            }// end




        }); // end of the enonimous inner class


        //MAIN BODY WORK FOR TIMETABLE APP


    }//end ONCREATE

    //ADDITIONAL FUNCTIONS
    //external url
    public void onurlClick(View view){
        goToUrl("http://www.dit.ie/timetables");
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    public void changeBackgroundImage(){

        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);



        if(day == 1){
            bgImage.setImageResource(R.drawable.bgsun);
        }//end if

        if(day == 2){
            bgImage.setImageResource(R.drawable.bgmon);
        }//end if

        if(day == 3){
            bgImage.setImageResource(R.drawable.bgtue);
        }//end if

        if(day == 4){
            bgImage.setImageResource(R.drawable.bgwed);
        }//end if

        if(day == 5){
            bgImage.setImageResource(R.drawable.bgthur);
        }//end if

        if(day == 6){
            bgImage.setImageResource(R.drawable.bgfri);
        }//end if

        if(day == 7){
            bgImage.setImageResource(R.drawable.bgsat);
        }//end if




    }



}//end class