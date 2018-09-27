package com.workshopproject.dashboard_lifecycle;

/**
 * Created by HP on 26-09-2018.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import com.workshopproject.LoginActivity;
import com.workshopproject.R;
import com.workshopproject.dbhelpers.WorkshopDAO;
import com.workshopproject.db_models.Workshop;
import com.workshopproject.getter_setters.Workshops;


public class DashboardMain extends AppCompatActivity implements View.OnClickListener {

    private List<String> workshops_view = null;
    private RecyclerView Dashboard_View;

    private ArrayList<Workshops> AllRowData = null;
    private List<Workshop> dashboardData = null;
    private RecyclerView.LayoutManager  Layout;

    private DashboardAdapter Adapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_view);
        setTitle("Workshops Applied");
        set_RecyclerView_Details();
        addRowData();
        Json_Data_Web_Call();

    }

    private void addRowData()
    {

        Adapter = new
                DashboardAdapter(DashboardMain.this,AllRowData);

        Dashboard_View.setAdapter(Adapter);
    }


    private void set_RecyclerView_Details()
    {
        if(AllRowData!=null)
            AllRowData.clear();

        AllRowData = new ArrayList<>();
        Dashboard_View = findViewById(R.id.scrollableview);
        Dashboard_View.removeAllViewsInLayout();
        Dashboard_View.setHasFixedSize(true);
        Layout = new LinearLayoutManager(this);
        Dashboard_View.setLayoutManager(Layout);
    }

    public void Json_Data_Web_Call()
    {
        workshops_view = new ArrayList<String>();
        Workshops RowData;
        WorkshopDAO workshopDAO = new WorkshopDAO(this);
        if(dashboardData!=null)
            dashboardData.clear();
        if(isEmpty()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        SharedPreferences sharedPreferences = getSharedPreferences("LoginDetails",
                Context.MODE_PRIVATE);
        String Id = sharedPreferences.getString("Email",null);
        dashboardData = workshopDAO.getAllWorkshopById(Id);
        int length = dashboardData.size();
        if(length>6)
            length=6;

        AllRowData.clear();
        for ( int i = 0 ; i < length ; i++)
        {
            RowData = new Workshops();

            try
            {
                Workshop row = (Workshop) dashboardData.get(i);
                RowData.setWorkshopName(row.getName());
                RowData.setIntro(row.getmIntro());
                RowData.setTiming(row.getmTimings());

            }
            catch (Exception e) {
                Log.e("Error: " , String.valueOf(e));
            }

            AllRowData.add(RowData);
        }


    }

    public boolean isEmpty() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        boolean isEmailEmpty = sharedPreferences.getString("Email", "").isEmpty();
        boolean isPasswordEmpty = sharedPreferences.getString("Password", "").isEmpty();
        return isEmailEmpty || isPasswordEmpty;
    }


    @Override
    public void onClick(View v) {

    }
}
