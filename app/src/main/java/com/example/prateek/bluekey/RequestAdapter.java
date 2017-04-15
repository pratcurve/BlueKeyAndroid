package com.example.prateek.bluekey;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by prateek on 16/2/17.
 */

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    private List<Request> requestList;

    public class RequestViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, timestamp, RMN;
        public CircleImageView profileImage;
        public Button acceptButton, denyButton;

        public RequestViewHolder(View view) {
            super(view);
            userName = (TextView) view.findViewById(R.id.username);
            RMN = (TextView) view.findViewById(R.id.RMN);
            timestamp = (TextView) view.findViewById(R.id.timestamp);
            profileImage = (CircleImageView) view.findViewById(R.id.profile_image);
            acceptButton = (Button) view.findViewById(R.id.accept_button);
            denyButton = (Button) view.findViewById(R.id.deny_button);
        }
    }

    public RequestAdapter (List<Request> requestList){
        this.requestList = requestList;
    }

    @Override
    public void onBindViewHolder(RequestViewHolder holder, int position) {
        Request request = requestList.get(position);
        holder.userName.setText(request.getUserName());
        holder.profileImage.setImageResource(R.drawable.user_image);
        holder.timestamp.setText(request.getTimestamp());
        holder.RMN.setText(request.getRMN());
    }

    @Override
    public RequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_list_row, parent, false);

        return new RequestAdapter.RequestViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }
}
