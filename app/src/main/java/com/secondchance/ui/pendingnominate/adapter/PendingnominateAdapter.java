package com.secondchance.ui.pendingnominate.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.secondchance.R;
import com.secondchance.ui.home.adapter.BaseViewHolder;
import com.secondchance.ui.home.adapter.home;
import com.secondchance.ui.home.adapter.homeadapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PendingnominateAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private homeadapter.Callback mCallback;
    private List<PendingnominateModel> mainList;


    public PendingnominateAdapter(List<PendingnominateModel> mainList) {
        this.mainList = mainList;

    }


    public void setCallback(homeadapter.Callback callback) {
        mCallback = callback;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new PendingnominateAdapter.ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_adapter_layout, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new PendingnominateAdapter.EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mainList != null && mainList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (mainList != null && mainList.size() > 0) {
            return mainList.size();
        } else {
            return 1;
        }
    }

    public void addItems(List<PendingnominateModel> sportList) {
        mainList.clear();
        mainList.addAll(sportList);
        notifyDataSetChanged();
    }

    public interface Callback {
        void onEmptyViewRetryClick();
    }

    public class ViewHolder extends BaseViewHolder {


        @BindView(R.id.edit_btn)
        TextView edit_btn;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.people_image)
        ImageView profile_pic;
        @BindView(R.id.radio)
        MaterialRadioButton radio;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            profile_pic = itemView.findViewById(R.id.people_image);
            name = itemView.findViewById(R.id.name);
            edit_btn = itemView.findViewById(R.id.edit_btn);
            radio = itemView.findViewById(R.id.radio);


        }

        protected void clear() {
            name.setText("");
        }

        @SuppressLint("SetTextI18n")
        public void onBind(int position) {
            super.onBind(position);

            PendingnominateModel pendingnominateModel = mainList.get(position);

            name.setText(pendingnominateModel.getUsernameList().get(position));
            Picasso.with(pendingnominateModel.activity).load(pendingnominateModel.getImageList().get(position)).into(profile_pic);


        }
    }

    public class EmptyViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_message)
        TextView messageTextView;
        @BindView(R.id.buttonRetry)
        TextView buttonRetry;

        EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            buttonRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onEmptyViewRetryClick();
                }
            });
        }

        @Override
        protected void clear() {


        }

    }
}
