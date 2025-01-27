package com.MountreachSolution.pamlabourshedulingandroidapp.Labour;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.MountreachSolution.pamlabourshedulingandroidapp.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import android.widget.Filter;
import android.widget.Filterable;

public class WorkPostingAdapter extends RecyclerView.Adapter<WorkPostingAdapter.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<String> id, name, mobileno, address, work, wages, starttime, endtime, workdate;
    private ArrayList<String> idFiltered, nameFiltered, mobilenoFiltered, addressFiltered, workFiltered, wagesFiltered, starttimeFiltered, endtimeFiltered, workdateFiltered;

    public WorkPostingAdapter(Context context, ArrayList<String> id, ArrayList<String> name, ArrayList<String> mobileno,
                              ArrayList<String> address, ArrayList<String> work, ArrayList<String> wages,
                              ArrayList<String> starttime, ArrayList<String> endtime, ArrayList<String> workdate) {
        this.context = context;
        this.id = id;
        this.name = name;
        this.mobileno = mobileno;
        this.address = address;
        this.work = work;
        this.wages = wages;
        this.starttime = starttime;
        this.endtime = endtime;
        this.workdate = workdate;

        // Initialize filtered lists
        this.idFiltered = new ArrayList<>(id);
        this.nameFiltered = new ArrayList<>(name);
        this.mobilenoFiltered = new ArrayList<>(mobileno);
        this.addressFiltered = new ArrayList<>(address);
        this.workFiltered = new ArrayList<>(work);
        this.wagesFiltered = new ArrayList<>(wages);
        this.starttimeFiltered = new ArrayList<>(starttime);
        this.endtimeFiltered = new ArrayList<>(endtime);
        this.workdateFiltered = new ArrayList<>(workdate);
    }

    @NonNull
    @Override
    public WorkPostingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.postdesgin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkPostingAdapter.ViewHolder holder, int position) {
        holder.tvwork.setText(workFiltered.get(position));
        holder.tvname.setText(nameFiltered.get(position));
        holder.tvnumber.setText(mobilenoFiltered.get(position));
        holder.tvaddress.setText(addressFiltered.get(position));
        holder.tvstarttime.setText(starttimeFiltered.get(position));
        holder.tvendtime.setText(endtimeFiltered.get(position));
        holder.tvdate.setText(workdateFiltered.get(position));
        holder.tvwasge.setText(wagesFiltered.get(position));

        String id = idFiltered.get(position); // Save the data in a String variable

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,WorkDetails.class);
                i.putExtra("workid",id);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return idFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString().toLowerCase();
                if (query.isEmpty()) {
                    idFiltered = new ArrayList<>(id);
                    nameFiltered = new ArrayList<>(name);
                    mobilenoFiltered = new ArrayList<>(mobileno);
                    addressFiltered = new ArrayList<>(address);
                    workFiltered = new ArrayList<>(work);
                    wagesFiltered = new ArrayList<>(wages);
                    starttimeFiltered = new ArrayList<>(starttime);
                    endtimeFiltered = new ArrayList<>(endtime);
                    workdateFiltered = new ArrayList<>(workdate);
                } else {
                    ArrayList<String> filteredIds = new ArrayList<>();
                    ArrayList<String> filteredWork = new ArrayList<>();
                    ArrayList<String> filteredName = new ArrayList<>();
                    ArrayList<String> filteredMobileno = new ArrayList<>();
                    ArrayList<String> filteredAddress = new ArrayList<>();
                    ArrayList<String> filteredWages = new ArrayList<>();
                    ArrayList<String> filteredStarttime = new ArrayList<>();
                    ArrayList<String> filteredEndtime = new ArrayList<>();
                    ArrayList<String> filteredWorkdate = new ArrayList<>();

                    for (int i = 0; i < work.size(); i++) {
                        if (work.get(i).toLowerCase().contains(query) ||
                                wages.get(i).toLowerCase().contains(query) ||
                                workdate.get(i).toLowerCase().contains(query)) {
                            filteredIds.add(id.get(i));
                            filteredWork.add(work.get(i));
                            filteredName.add(name.get(i));
                            filteredMobileno.add(mobileno.get(i));
                            filteredAddress.add(address.get(i));
                            filteredWages.add(wages.get(i));
                            filteredStarttime.add(starttime.get(i));
                            filteredEndtime.add(endtime.get(i));
                            filteredWorkdate.add(workdate.get(i));
                        }
                    }

                    idFiltered = filteredIds;
                    workFiltered = filteredWork;
                    nameFiltered = filteredName;
                    mobilenoFiltered = filteredMobileno;
                    addressFiltered = filteredAddress;
                    wagesFiltered = filteredWages;
                    starttimeFiltered = filteredStarttime;
                    endtimeFiltered = filteredEndtime;
                    workdateFiltered = filteredWorkdate;
                }

                FilterResults results = new FilterResults();
                results.values = idFiltered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView ivimage;
        TextView tvwork, tvname, tvnumber, tvaddress, tvstarttime, tvendtime, tvdate, tvwasge;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.carsView);

            tvwork = itemView.findViewById(R.id.tv_work_description);
            tvname = itemView.findViewById(R.id.tvname);
            tvnumber = itemView.findViewById(R.id.tvMobileno);
            tvaddress = itemView.findViewById(R.id.tvaddress);
            tvstarttime = itemView.findViewById(R.id.tvstartTime);
            tvendtime = itemView.findViewById(R.id.tvendtime);
            tvdate = itemView.findViewById(R.id.tvDate);
            tvwasge = itemView.findViewById(R.id.tvwasgae);
        }
    }
}
