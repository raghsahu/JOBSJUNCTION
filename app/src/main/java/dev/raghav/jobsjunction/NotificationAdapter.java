package dev.raghav.jobsjunction;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{

    private static final String TAG = "NotificationAdapter";
    private ArrayList<NotifiModel> notifiilist;
    public Context context;
    String resId = "";

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nText,nTittle,nDate, ctext;
        CardView cardeview;
        int pos;

        public ViewHolder(View view) {
            super(view);

            nText = (TextView)view.findViewById(R.id.nText);
            ctext=(TextView) view.findViewById(R.id.cText);
            nTittle = (TextView)view.findViewById(R.id.nTittle);
            nDate = (TextView)view.findViewById(R.id.nDate);
            cardeview = (CardView)view.findViewById(R.id.cardeview);
        }
    }

    public static Context mContext;

    public NotificationAdapter(Context mContext, ArrayList<NotifiModel> noti_list) {
        context = mContext;
        notifiilist = noti_list;

    }

    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_row, parent, false);

        return new NotificationAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final NotificationAdapter.ViewHolder viewHolder, final int position) {
        final NotifiModel notifiModel = notifiilist.get(position);
        viewHolder.nTittle.setText(notifiModel.getTitle());
        viewHolder.nText.setText(notifiModel.getNotification());
        viewHolder.nDate.setText(convertTime(notifiModel.getDate()));
        viewHolder.ctext.setText(notifiModel.getCompany());
        viewHolder.cardeview.setTag(viewHolder);
        viewHolder.pos = position;

    }

    @Override
    public int getItemCount() {
        return notifiilist.size();
    }

    private String convertTime(String time) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = null;

        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String convertedDate = format1.format(date);

        return convertedDate;
    }

}
