package com.secondchance.ui.pendingnominate;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.secondchance.R;
import com.secondchance.SecondChanceApplication;
import com.secondchance.ui.home.adapter.home;
import com.secondchance.ui.home.adapter.homeadapter;
import com.secondchance.ui.pendingnominate.adapter.PendingnominateAdapter;
import com.secondchance.ui.pendingnominate.adapter.PendingnominateModel;
import com.secondchance.utils.DividerItemDecoration;
import com.secondchance.utils.StorageUtil;

import java.util.ArrayList;

public class PendingNominateFragment extends Fragment implements View.OnClickListener {
    SecondChanceApplication secondChanceApplication;
    StorageUtil mStore;
    ArrayList<String> usernameList;
    ArrayList<String> imageList;
    ArrayList<String> dobList;
    RecyclerView mRecyclerView;
    PendingnominateAdapter pendingnominateAdapter;
    LinearLayoutManager mLayoutManager;
    Button send_btn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.pending_nominate_layout, container, false);

        secondChanceApplication = (SecondChanceApplication) getActivity().getApplication();
        mStore = StorageUtil.getInstance(getActivity());
        mRecyclerView = root.findViewById(R.id.mRecyclerView);
        send_btn = root.findViewById(R.id.send_btn);
        send_btn.setOnClickListener(this);
        setPendingNominateListView();
        return root;
    }

    private void setPendingNominateListView() {

        usernameList = new ArrayList<>();
        usernameList.add("Shristi Subhakaran");
        usernameList.add("Shristi Subhakaran");
        dobList = new ArrayList<>();
        dobList.add("22 Sept 2020");
        dobList.add("22 Sept 2020");
        imageList = new ArrayList<>();
        imageList.add("https://youthwhatsapp.files.wordpress.com/2016/03/8589130459022-cute-little-baby-girl-wallpaper-hd-download.jpg");
        imageList.add("https://youthwhatsapp.files.wordpress.com/2016/03/8589130459022-cute-little-baby-girl-wallpaper-hd-download.jpg");

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Drawable dividerDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.divider_drawable);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        pendingnominateAdapter = new PendingnominateAdapter(new ArrayList<PendingnominateModel>());
        prepareContentForMsg();

    }

    private void prepareContentForMsg() {

        ArrayList<PendingnominateModel> usernameListarra = new ArrayList<>();
        for (int i = 0; i < usernameList.size(); i++) {
            usernameListarra.add(new PendingnominateModel(getActivity(), usernameList, dobList, imageList, "MESSAGE"));
        }
        pendingnominateAdapter.addItems(usernameListarra);
        mRecyclerView.setAdapter(pendingnominateAdapter);
        pendingnominateAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if(id== R.id.send_btn)
        {

        }
    }
}
