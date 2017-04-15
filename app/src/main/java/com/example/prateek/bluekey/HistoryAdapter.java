package com.example.prateek.bluekey;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by prateek on 12/2/17.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<History> historyList;

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, timeStamp, lockname;
        public CircleImageView profileImage;
        public ImageView actionImage;

        public HistoryViewHolder(View view){
            super(view);
            userName = (TextView) view.findViewById(R.id.username);
            timeStamp = (TextView) view.findViewById(R.id.timestamp);
            profileImage = (CircleImageView) view.findViewById(R.id.profile_image);
            actionImage = (ImageView) view.findViewById(R.id.action_image);
            lockname = (TextView) view.findViewById(R.id.lockName);
        }
    }

    public HistoryAdapter(List<History> historyList) {
        this.historyList = historyList;
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        History history = historyList.get(position);
        holder.userName.setText(history.getUserName());
        holder.timeStamp.setText((CharSequence) history.getTimestamp());
        holder.profileImage.setImageResource(R.drawable.user_image);
        if (history.getAction().equals("1")) {
            holder.actionImage.setImageResource(R.drawable.lock);
        } else holder.actionImage.setImageResource(R.drawable.unlock);
        holder.lockname.setText(history.getLockname());
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_list_row, parent, false);

        return new HistoryViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }
}
