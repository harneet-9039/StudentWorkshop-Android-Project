package com.workshopproject.dashboard_lifecycle;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.workshopproject.R;

import java.util.List;

import com.workshopproject.getter_setters.Workshops;


public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    private final Context context;
    private List<Workshops> data;

    public DashboardAdapter(Context context, List<Workshops> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_workshop_view, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Workshops RowData = data.get(position);
        holder.Intro.setText(RowData.getIntro());
        holder.fee.setText(RowData.getFee());
        holder.WorkshopName.setText(RowData.getWorkshopName());
        holder.timing.setText(RowData.getTiming());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView timing;
        TextView fee;
        TextView WorkshopName;
        TextView Intro;


        private ViewHolder(View itemView) {
            super(itemView);
            timing = itemView.findViewById( R.id.timing);
            fee = itemView.findViewById( R.id.Fee);
            WorkshopName = itemView.findViewById( R.id.res_name);
            Intro = itemView.findViewById( R.id.Intro);

        }
    }
}