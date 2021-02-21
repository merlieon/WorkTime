package com.example.worktime.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worktime.R;
import com.example.worktime.models.TimeReportModel;

import java.util.ArrayList;
import java.util.List;


public class CalendarListAdapter extends RecyclerView.Adapter<CalendarListAdapter.CalendarListHolder>  {
    private List<TimeReportModel> mTimeReportList = new ArrayList<>();
    private OnCalendarListner mOnCalendarListner;

    public CalendarListAdapter(ArrayList<TimeReportModel> timeReportList, OnCalendarListner onCalendarListner){
        this.mOnCalendarListner = onCalendarListner;
        this.mTimeReportList = timeReportList;
    }

    @NonNull
    @Override
    public CalendarListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calendar_view_list_item,parent,false);
        return new CalendarListHolder(itemView, mOnCalendarListner);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarListHolder holder, int position) {
        TimeReportModel currentTR = mTimeReportList.get(position);
        holder.companyNameTV.setText("Företag: " + currentTR.getCompanyName());
        holder.hoursTV.setText("Tid: " + String.valueOf(currentTR.getHours()));
        holder.salaryTV.setText("Lön: " + String.valueOf(currentTR.getSalary()));

    }

    @Override
    public int getItemCount() {
        return mTimeReportList.size();
    }

    public void setCalendartList(List<TimeReportModel> timeReportList){
        this.mTimeReportList = timeReportList;
        notifyDataSetChanged();
    }

    class CalendarListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView companyNameTV;
        private TextView hoursTV;
        private TextView salaryTV;
        OnCalendarListner onCalendarListner;
        public CalendarListHolder(View itemView, OnCalendarListner onCalendarListner) {
            super(itemView);
            companyNameTV = itemView.findViewById(R.id.calendarCompanyNameListItemTV);
            hoursTV = itemView.findViewById(R.id.calendarHoursListItemTV);
            salaryTV = itemView.findViewById(R.id.calendarSalaryListItemTV);
            this.onCalendarListner = onCalendarListner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCalendarListner.OnCalendarClick(getAdapterPosition());
        }
    }

    public interface OnCalendarListner{
        void OnCalendarClick(int position);
    }
}
