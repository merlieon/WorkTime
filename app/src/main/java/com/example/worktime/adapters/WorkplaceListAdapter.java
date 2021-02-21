package com.example.worktime.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worktime.R;
import com.example.worktime.models.TimeReportModel;
import com.example.worktime.models.WorkplaceModel;

import java.util.ArrayList;
import java.util.List;


public class WorkplaceListAdapter extends RecyclerView.Adapter<WorkplaceListAdapter.WorkplaceListHolder> {
    private List<WorkplaceModel> workplaceList = new ArrayList<>();
    private OnWorkPlaceListner mOnWorkPlaceListner;

    public WorkplaceListAdapter(ArrayList<WorkplaceModel> workplaceList, OnWorkPlaceListner onWorkPlaceListner){
        this.workplaceList = workplaceList;
        this.mOnWorkPlaceListner = onWorkPlaceListner;
    }

    @NonNull
    @Override
    public WorkplaceListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workplace_list_item,parent,false);
        return new WorkplaceListHolder(itemView, mOnWorkPlaceListner);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkplaceListHolder holder, int position) {
        WorkplaceModel currentWP = workplaceList.get(position);
        holder.companyNameTV.setText(currentWP.getCompanyName());
        holder.companyAddressTV.setText(currentWP.getCompanyAddress());
    }

    @Override
    public int getItemCount() {
        return workplaceList.size();
    }

    public void setWorkplaceList(List<WorkplaceModel> workplaceList){
        this.workplaceList = workplaceList;
        notifyDataSetChanged();
    }

    class WorkplaceListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView companyNameTV;
        private TextView companyAddressTV;
        private OnWorkPlaceListner onWorkPlaceListner;
        public WorkplaceListHolder(View itemView, OnWorkPlaceListner onWorkPlaceListner) {
            super(itemView);
            companyNameTV = itemView.findViewById(R.id.wpCompanyNameListItemTV);
            companyAddressTV = itemView.findViewById(R.id.wpCompanyAddressListItemTV);
            this.onWorkPlaceListner = onWorkPlaceListner;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            onWorkPlaceListner.onWorkplaceClick(getAdapterPosition());
        }
    }

    public interface OnWorkPlaceListner{
        void onWorkplaceClick(int position);
    }
}
