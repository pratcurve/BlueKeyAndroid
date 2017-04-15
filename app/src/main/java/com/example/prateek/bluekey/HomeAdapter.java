package com.example.prateek.bluekey;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by prateek on 22/2/17.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private List<Home> homeList;
    private Context context;

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView lockName;
        public ImageView lockActionStatus;

        public HomeViewHolder(View view) {
            super(view);
            lockName = (TextView) view.findViewById(R.id.lockname);
            lockActionStatus = (ImageView) view.findViewById(R.id.action_status_image);
        }

        @Override
        public void onClick(View v) {
            Log.d("onCLick", String.valueOf(getAdapterPosition()));
//            setVisililityTrue()
        }
    }

    public HomeAdapter (List<Home> homeList, Context context) {
        this.homeList = homeList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(final HomeAdapter.HomeViewHolder holder, final int position) {
        final Home home = homeList.get(position);
        holder.lockName.setText(home.getLockName());
        if (home.getLockStatus().equals("1")) {
            holder.lockActionStatus.setImageResource(R.drawable.lock);
        } else {
            holder.lockActionStatus.setImageResource(R.drawable.unlock);
        }

        holder.lockActionStatus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newStatus;
                        if (home.getLockStatus().equals("1")) {
                            holder.lockActionStatus.setImageResource(R.drawable.unlock);
                            newStatus = "0";
                            home.setLockStatus(newStatus);
                            Toast.makeText(context, "Unlocking Door! Please wait.", Toast.LENGTH_SHORT).show();
                            SaveLock saveLock = new SaveLock(home.getLockName(), home.getLockAddress(), newStatus, context);
                            saveLock.saveToFile();
                        } else {
                            holder.lockActionStatus.setImageResource(R.drawable.lock);
                            newStatus = "1";
                            Toast.makeText(context, "Locking Door! Please wait.", Toast.LENGTH_SHORT).show();
                            SaveLock saveLock = new SaveLock(home.getLockName(), home.getLockAddress(), newStatus, context);
                            saveLock.saveToFile();
                            home.setLockStatus(newStatus);
//                            notifyItemChanged(position);
                        }
                        notifyDataSetChanged();
                    }
                }
        );
    }

    @Override
    public HomeAdapter.HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_list_row, parent, false);

        return new HomeAdapter.HomeViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }
}
