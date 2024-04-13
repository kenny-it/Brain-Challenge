package com.speed.brainchallenge.globalscore;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.speed.brainchallenge.R;

import java.util.ArrayList;
public class GlobalScoreListItemAdapter extends RecyclerView.Adapter<GlobalScoreListItemAdapter.ItemViewHolder> {
    private static final String TAG = "ListItemAdapter";
    private ArrayList<GlobalScoreListItem> itemList;

    public GlobalScoreListItemAdapter(ArrayList<GlobalScoreListItem> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public GlobalScoreListItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "Create ViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.global_list_item,parent,false);
        return new ItemViewHolder(view);
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView rank;
        private TextView username;
        private TextView score;
        private TextView time;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            rank = itemView.findViewById(R.id.rank);
            username = itemView.findViewById(R.id.username);
            score = itemView.findViewById(R.id.score);
            time = itemView.findViewById(R.id.time);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull GlobalScoreListItemAdapter.ItemViewHolder holder, int position) {
        GlobalScoreListItem item = itemList.get(position);
        holder.rank.setText(String.valueOf(item.getRank()));
        holder.username.setText(item.getUsername());
        holder.score.setText(String.valueOf(item.getScore()));
        holder.time.setText(item.getTime());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
