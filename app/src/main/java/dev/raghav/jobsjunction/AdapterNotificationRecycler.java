package dev.raghav.jobsjunction;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class AdapterNotificationRecycler extends RecyclerView.Adapter<AdapterNotificationRecycler.ViewHolder> {

    private static final String TAG = "AdapterNotificationRecycler";

    private ArrayList<String> notification;


    @NonNull
    @Override
    public AdapterNotificationRecycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);

        return new ViewHolder(itemView);

    }

    public static Context mContext;
    public AdapterNotificationRecycler(ArrayList<String> notification) {
        notification = notification;


    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNotificationRecycler.ViewHolder holder, int position) {
        holder.notification.setText(notification.get(position).toString());

    }

    @Override
    public int getItemCount() {
        return notification.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView notification;

        public ViewHolder(View itemView) {
            super(itemView);
            notification = (TextView) itemView.findViewById(R.id.notification_test);
        }
    }
}
