package com.workshopproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.workshopproject.dbhelpers.WorkshopDAO;
import com.workshopproject.workshop_lifecycle.WorkshopMain;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
        if (isFirstRun)
        {
            WorkshopDAO workshopDAO = new WorkshopDAO(this);
            workshopDAO.createWorkshop("Web Development","This workshop is to give insight " +
                    "of web developement","13 sep - 17 sep","2500");
            workshopDAO.createWorkshop("Android Development","This workshop is to give insight " +
                    "of android developement","30 sep - 3 sep","4000");
            workshopDAO.createWorkshop("Big Data","This workshop is to give insight " +
                    "of Big Data using hadoop","3 sep - 6 sep","4500");
            workshopDAO.createWorkshop("Ethical Hacking","This workshop is to give insight " +
                    "of hacking in a ethical way","8 sep - 12 sep","3000");
            workshopDAO.createWorkshop("IOT","This workshop is to give insight " +
                    "of Internet of Things","19 sep - 22 sep","5000");
            workshopDAO.createWorkshop("Machine Learning","This workshop is to give insight " +
                    "of Machine Learning using various libraries","26 sep - 29 sep","6000");
            SharedPreferences.Editor editor = wmbPreference.edit();
            editor.putBoolean("FIRSTRUN", false);
            editor.commit();
            Intent intent = new Intent(this, SignUpActivity.class);
            finish();
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, WorkshopMain.class);
            finish();
            startActivity(intent);
        }
    }


}
