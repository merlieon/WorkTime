package com.example.worktime.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worktime.R;
import com.example.worktime.models.TimeReportModel;

import java.util.ArrayList;
import java.util.List;


public class TimeReportListAdapter extends RecyclerView.Adapter<TimeReportListAdapter.TimeReportListHolder>  {
    private List<TimeReportModel> mTimeReportList = new ArrayList<>();
    private OnTimeReportListner mOnTimeReportListner;

    public TimeReportListAdapter(ArrayList<TimeReportModel> timeReportList, OnTimeReportListner onTimeReportListner){
        this.mOnTimeReportListner = onTimeReportListner;
        this.mTimeReportList = timeReportList;
    }

    @NonNull
    @Override
    public TimeReportListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.time_report_list_item,parent,false);
        return new TimeReportListHolder(itemView, mOnTimeReportListner);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeReportListHolder holder, int position) {
        TimeReportModel currentTR = mTimeReportList.get(position);
        holder.workedDateTV.setText("Datum: " + currentTR.getWorkedDate());
        holder.hoursTV.setText("Tid: " + String.valueOf(currentTR.getHours()));
        holder.salaryTV.setText("Lön: " + String.valueOf(currentTR.getSalary()));

    }

    @Override
    public int getItemCount() {
        return mTimeReportList.size();
    }

    public void setTimeReportList(List<TimeReportModel> timeReportList){
        this.mTimeReportList = timeReportList;
        notifyDataSetChanged();
    }

    class TimeReportListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView workedDateTV;
        private TextView hoursTV;
        private TextView salaryTV;
        OnTimeReportListner onTimeReportListner;
        public TimeReportListHolder(View itemView, OnTimeReportListner onTimeReportListner) {
            super(itemView);
            workedDateTV = itemView.findViewById(R.id.trWorkedDateListItemTV);
            hoursTV = itemView.findViewById(R.id.trHoursListItemTV);
            salaryTV = itemView.findViewById(R.id.trSalaryListItemTV);
            this.onTimeReportListner = onTimeReportListner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onTimeReportListner.OnTimeReportClick(getAdapterPosition());
        }
    }

    public interface OnTimeReportListner{
        void OnTimeReportClick(int position);
    }
}
