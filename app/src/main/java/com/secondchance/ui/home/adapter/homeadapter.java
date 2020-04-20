package com.secondchance.ui.home.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.secondchance.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class homeadapter extends RecyclerView.Adapter<BaseViewHolder> {
    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private Callback mCallback;
    private List<home> mainList;


    public homeadapter(List<home> mainList) {
        this.mainList = mainList;

    }


    public void setCallback(Callback callback) {
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
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.homeadapter_layout, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new EmptyViewHolder(
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

    public void addItems(List<home> sportList) {
        mainList.clear();
        mainList.addAll(sportList);
        notifyDataSetChanged();
    }

    public interface Callback {
        void onEmptyViewRetryClick();
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.linear_msg)
        LinearLayout linear_msg;
        @BindView(R.id.username_msg)
        TextView username_msg;
        @BindView(R.id.dob_msg)
        TextView dob_msg;
        @BindView(R.id.image_msg)
        ImageView image_msg;
        @BindView(R.id.people_msg_btn)
        Button people_msg_btn;
        @BindView(R.id.edit_btn)
        TextView edit_btn;
        @BindView(R.id.people_username)
        TextView people_username;
        @BindView(R.id.people_image)
        ImageView people_image;
        @BindView(R.id.linear_people)
        LinearLayout linear_people;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            linear_msg = itemView.findViewById(R.id.linear_msg);
            username_msg = itemView.findViewById(R.id.username_msg);
            dob_msg = itemView.findViewById(R.id.dob_msg);
            image_msg = itemView.findViewById(R.id.image_msg);
            linear_people = itemView.findViewById(R.id.linear_people);
            people_image = itemView.findViewById(R.id.people_image);
            people_username = itemView.findViewById(R.id.people_username);
            edit_btn = itemView.findViewById(R.id.edit_btn);
            people_msg_btn = itemView.findViewById(R.id.people_msg_btn);


        }

        protected void clear() {
            username_msg.setText("");
        }

        @SuppressLint("SetTextI18n")
        public void onBind(int position) {
            super.onBind(position);

            home homemodel = mainList.get(position);
            if (homemodel.getUseraction().equalsIgnoreCase("MESSAGE")) {
                linear_msg.setVisibility(View.VISIBLE);
                linear_people.setVisibility(View.GONE);

                username_msg.setText(homemodel.getUsernameList().get(position));
                dob_msg.setText(homemodel.getDobList().get(position));
                Picasso.with(homemodel.activity).load(homemodel.getImageList().get(position)).into(image_msg);
            } else if (homemodel.getUseraction().equalsIgnoreCase("PEOPLE")) {
                linear_msg.setVisibility(View.GONE);
                linear_people.setVisibility(View.VISIBLE);
                people_username.setText(homemodel.getUsernameList().get(position));
                Picasso.with(homemodel.activity).load(homemodel.getImageList().get(position)).into(people_image);
            }


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
