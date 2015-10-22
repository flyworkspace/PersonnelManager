package com.flyworkspace.person.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyworkspace.person.R;
import com.flyworkspace.person.activity.DetailedActivity;
import com.flyworkspace.person.model.PersonInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinpengfei on 15-10-12.
 */
public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.ViewHolder> {

    private List<PersonInfo> mDataset = new ArrayList<>();
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCompanyName;
        private TextView tvContactName;
        private TextView tvNickname;
        private TextView tvSex;
        private TextView tvContactPhone;
        private TextView tvAddress;
        private TextView tvRange;
        private TextView tvCategory;
        private LinearLayout mLLayout;

        public ViewHolder(View v) {
            super(v);
            tvCompanyName = (TextView) v.findViewById(R.id.textview_company_name);
            tvContactName = (TextView) v.findViewById(R.id.textview_contact_name);
            tvNickname = (TextView) v.findViewById(R.id.textview_nickname);
            tvSex = (TextView) v.findViewById(R.id.textview_sex);
            tvContactPhone = (TextView) v.findViewById(R.id.textview_contact_phone);
            tvAddress = (TextView) v.findViewById(R.id.textview_add);
            tvRange = (TextView) v.findViewById(R.id.textview_range);
            tvCategory = (TextView) v.findViewById(R.id.textview_category);
            mLLayout = (LinearLayout) v.findViewById(R.id.llayout_content);
        }
    }

    public PersonListAdapter(Context context) {
        this.mContext = context;
    }

    public void setList(List<PersonInfo> dataset) {
        mDataset.clear();
        mDataset.addAll(dataset);
    }

    public void addList(List<PersonInfo> dataset) {
        mDataset.addAll(dataset);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.persion_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final PersonInfo personInfo = mDataset.get(position);
        holder.tvCompanyName.setText(personInfo.getCompanyName());
        holder.tvContactName.setText(personInfo.getContactName());
        holder.tvNickname.setText(personInfo.getNickname());
        holder.tvSex.setText(mContext.getResources().getStringArray(R.array.spinner_sex)[personInfo.getSex()]);
        holder.tvContactPhone.setText(personInfo.getContactPhone());
        holder.tvAddress.setText(personInfo.getAddress());
        holder.tvRange.setText(personInfo.getRange());
        holder.tvCategory.setText(personInfo.getCategory());

        holder.mLLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailedActivity.class);
                intent.putExtra("person_info_id", personInfo.getId());
                mContext.startActivity(intent);
            }
        });
    }
}