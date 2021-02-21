package com.example.worktime.adapters;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worktime.R;
import com.example.worktime.models.WorkplaceModel;

import java.util.ArrayList;
import java.util.List;


public class OvertimeListAdapter extends RecyclerView.Adapter<OvertimeListAdapter.OvertimeListHolder> {
    private List<String> overtimeList = new ArrayList<>();
    private OnOvertimeListner mOnOvertimeListner;
    private ImageButton imageButton;

    public OvertimeListAdapter(ArrayList<String> overtimeListList, OnOvertimeListner onOvertimeListner){
        this.overtimeList = overtimeList;
        this.mOnOvertimeListner = onOvertimeListner;
    }

    @NonNull
    @Override
    public OvertimeListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.overtime_list_item,parent,false);
        return new OvertimeListHolder(itemView, mOnOvertimeListner);
    }

    @Override
    public void onBindViewHolder(@NonNull OvertimeListHolder holder, int position) {
        String currentWP = overtimeList.get(position);
        holder.overtimeET.setText(currentWP);

       /* holder.addObButtonIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        holder.removeObButtonIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("remove");
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return overtimeList.size();
    }

    public void setOvertimeList(List<String> workplaceList){
        this.overtimeList = workplaceList;
        notifyDataSetChanged();
    }

    class OvertimeListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private EditText overtimeET;
        private ImageButton removeObButtonIB;
        private ImageButton addObButtonIB;
        private OnOvertimeListner onOvertimeListner;
        public OvertimeListHolder(View itemView, OnOvertimeListner onOvertimeListner) {
            super(itemView);
            overtimeET = itemView.findViewById(R.id.wpOvertimeListItemET);
            this.onOvertimeListner = onOvertimeListner;
            removeObButtonIB = itemView.findViewById(R.id.wpOvertimeListViewItemRemoveIB);
            addObButtonIB = itemView.findViewById(R.id.wpOvertimeListViewItemAddIB);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onOvertimeListner.onOvertimeClick(getAdapterPosition());
        }
    }

    public interface OnOvertimeListner{
        void onOvertimeClick(int position);
    }

}
