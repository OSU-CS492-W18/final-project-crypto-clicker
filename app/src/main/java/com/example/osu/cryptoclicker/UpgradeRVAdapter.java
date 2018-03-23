package com.example.osu.cryptoclicker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Alex on 3/20/2018.
 */

public class UpgradeRVAdapter extends RecyclerView.Adapter<UpgradeRVAdapter.ViewHolder> {

    private ArrayList<Upgrade> mUpgrades;
    private OnUpgradeClickListener mClickListener;
    private Context mContext;
    private int mCurrUpgrade;

    public interface OnUpgradeClickListener{
        boolean onUpgradeClick(Upgrade upgrade);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mTV1, mTV2, mTV3;
        View mContainer;


        public ViewHolder(View itemView) {
            super(itemView);

            mTV1 = itemView.findViewById(R.id.tv_upgrade_rv1);
            mTV2 = itemView.findViewById(R.id.tv_upgrade_rv2);
            mTV3 = itemView.findViewById(R.id.tv_upgrade_rv3);

            mTV1.setOnClickListener(this);
            mTV2.setOnClickListener(this);

            mContainer = itemView;
        }

        //parameters need to change
        public void bind(Upgrade upgrade){
            mTV1.setText(upgrade.getName());
            mTV2.setText("Increase USD per click by " + String.valueOf((int)upgrade.getAmount()) + "%");
            mTV3.setText("$" + String.format("%.2f", upgrade.getCost()));

            if(mCurrUpgrade >= upgrade.getCount()){
                mContainer.setBackgroundColor(mContext.getResources().getColor(R.color.takenUpgrade));
            }
        }

        @Override
        public void onClick(View v) {
            boolean success = mClickListener.onUpgradeClick(mUpgrades.get(getAdapterPosition()));
            if(success) {
                mContainer.setBackgroundColor(mContext.getResources().getColor(R.color.grey));
            }
        }
    }

    public UpgradeRVAdapter(Context context, OnUpgradeClickListener clickListener, int currUpgrade){
        mClickListener = clickListener;
        mContext = context;
        mCurrUpgrade = currUpgrade;
    }

    public void updateData(ArrayList<Upgrade> data){
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
