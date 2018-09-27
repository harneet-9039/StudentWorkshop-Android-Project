package com.workshopproject.workshop_lifecycle;

/**
 * Created by HP on 25-09-2018.
 */


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import com.workshopproject.DFragment;
import com.workshopproject.LoginActivity;
import com.workshopproject.R;
import com.workshopproject.dashboard_lifecycle.DashboardMain;
import com.workshopproject.dbhelpers.WorkshopDAO;
import com.workshopproject.db_models.Workshop;
import com.workshopproject.getter_setters.Workshops;



public class WorkshopMain extends AppCompatActivity {

    String[] WorkshopName;

    List<String> workshops_view = null;
    private RecyclerView recycler_view;

    private ArrayList<Workshops> AllRowData = null;
    private List<Workshop> workshops = null;
    private RecyclerView.LayoutManager  Layout;

    private WorkshopAdapter Adapter = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workshop_view);

            setTitle("Workshops");
            set_RecyclerView_Details();
            addRowData();
            Json_Data_Web_Call();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        MenuItem item = menu.findItem(R.id.Logout);
        if (isEmpty()) {
            item.setVisible(false);
        } else {
            item.setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.Dashboard)
        {
            Intent intent = new Intent(this, DashboardMain.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.Logout)
        {
            SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    public boolean isEmpty() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        boolean isEmailEmpty = sharedPreferences.getString("Email", "").isEmpty();

        return isEmailEmpty;
    }
    private void addRowData()
    {

        Adapter = new
                WorkshopAdapter(WorkshopMain.this,AllRowData);

        recycler_view.setAdapter(Adapter);
    }


    private void set_RecyclerView_Details()
    {
        if(AllRowData!=null)
        AllRowData.clear();

        AllRowData = new ArrayList<>();
        recycler_view = findViewById(R.id.scrollableview);
        recycler_view.removeAllViewsInLayout();
        recycler_view.setHasFixedSize(true);
        Layout = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(Layout);



    }

    public void Json_Data_Web_Call()
    {
                    workshops_view = new ArrayList<String>();
                    Workshops RowData;
                    WorkshopDAO workshopDAO = new WorkshopDAO(this);
                    if(workshops!=null)
                    workshops.clear();

                     workshops = workshopDAO.getAllWorkshop();
                    int length = workshops.size();
                    if(length>6)
                        length=6;
                    WorkshopName = new String[length];
                    AllRowData.clear();

                    for ( int i = 0 ; i < length ; i++)
                    {
                        RowData = new Workshops();

                        try
                        {
                            Workshop row = (Workshop) workshops.get(i);
                            RowData.setWorkshopName(row.getName());
                            RowData.setIntro(row.getmIntro());
                            RowData.setTiming(row.getmTimings());
                            RowData.setFee("Rs. "+row.getmFee());
                            workshops_view.add(row.getName());

                        }
                        catch (Exception e) {
                            Log.e("Error: " , String.valueOf(e));
                        }

                            AllRowData.add(RowData);
                    }

        Adapter = new
                WorkshopAdapter(WorkshopMain.this,AllRowData);
        recycler_view.setAdapter(Adapter);
        Adapter.setOnItemClickListener(new WorkshopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String name) {

                int position = 0;
                for(int i=0;i<workshops_view.size();i++)
                {
                    if(workshops_view.get(i).contentEquals(name))
                    {
                        position = i;
                        break;
                    }

                }

                FragmentManager fm = getSupportFragmentManager();
                DFragment editNameDialogFragment = DFragment.newInstance(workshops.get(position),workshops_view.get(position));
                editNameDialogFragment.show(fm, "dialogfragment");

            }
        });


            }

}
