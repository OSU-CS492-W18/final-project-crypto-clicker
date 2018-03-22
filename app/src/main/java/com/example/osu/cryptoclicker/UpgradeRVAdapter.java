package com.example.osu.cryptoclicker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Alex on 3/20/2018.
 */

public class UpgradeRVAdapter extends RecyclerView.Adapter<UpgradeRVAdapter.ViewHolder> {

    private ArrayList<String> mUpgrades;
    private OnUpgradeClickListener mClickListener;

    public interface OnUpgradeClickListener{
        void onUpgradeClick(String upgrade);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mTVLeft, mTVRight;

        public ViewHolder(View itemView) {
            super(itemView);

            mTVLeft = itemView.findViewById(R.id.tv_upgrade_rv1);
            mTVRight = itemView.findViewById(R.id.tv_upgrade_rv2);

            mTVLeft.setOnClickListener(this);
            mTVRight.setOnClickListener(this);
        }

        //parameters need to change
        public void bind(String upgradeLeft){
            mTVLeft.setText(upgradeLeft);
            //mTVRight.setText(upgradeRight);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onUpgradeClick(mUpgrades.get(getAdapterPosition()));
        }
    }

    public UpgradeRVAdapter(OnUpgradeClickListener clickListener){
        mClickListener = clickListener;
    }

    public void updateData(ArrayList<String> data){
        mUpgrades = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.upgrade_rv, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mUpgrades.get(position));
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
