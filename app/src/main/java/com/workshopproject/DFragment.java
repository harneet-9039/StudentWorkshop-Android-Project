package com.workshopproject; /**
 * Created by HP on 26-09-2018.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import android.widget.Toast;
import com.workshopproject.db_models.Workshop;
import com.workshopproject.dbhelpers.ApplicationDAO;


public class DFragment extends DialogFragment {

    static Workshop workshop = null;
    ApplicationDAO applicationDAO = null;
    public DFragment() {
        // Empty constructor required for DialogFragment
    }

    public static DFragment newInstance(Workshop workshops, String title) {
        DFragment frag = new DFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        workshop = workshops;
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(workshop.getmIntro());
        alertDialogBuilder.setPositiveButton("Apply",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(isEmpty())
                {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    getActivity().getSupportFragmentManager().popBackStack();

                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                    String Email = sharedPreferences.getString("Email", "");
                    if (CheckExistence(Email, workshop.getId())) {
                        applicationDAO = new ApplicationDAO(getContext());
                        boolean Result = applicationDAO.createUserApplication(Email, workshop.getId());
                        if (Result)
                            Toast.makeText(getContext(), "Application Accepted", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });


        return alertDialogBuilder.create();
    }

    public boolean isEmpty() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        boolean isEmailEmpty = sharedPreferences.getString("Email", "").isEmpty();
        boolean isPasswordEmpty = sharedPreferences.getString("Password", "").isEmpty();
        return isEmailEmpty || isPasswordEmpty;
    }

    public boolean CheckExistence(String Email, long id)
    {
        applicationDAO = new ApplicationDAO(getContext());
        boolean res = applicationDAO.Check(Email,id);
        if(res) return false;
        else return true;

    }



}