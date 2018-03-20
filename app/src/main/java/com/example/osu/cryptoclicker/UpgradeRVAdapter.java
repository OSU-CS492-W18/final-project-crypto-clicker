package com.example.osu.cryptoclicker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Alex on 3/20/2018.
 */

public class UpgradeRVAdapter extends RecyclerView.Adapter<UpgradeRVAdapter.ViewHolder> {

    ArrayList<String> mUpgrades;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public UpgradeRVAdapter(){

    }

    public void updateData(ArrayList<String> data){
        mUpgrades = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if(mUpgrades != null){
            return mUpgrades.size();
        } else {
            return 0;
        }
    }
}
