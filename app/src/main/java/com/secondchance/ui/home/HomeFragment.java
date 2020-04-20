package com.secondchance.ui.home;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.secondchance.R;
import com.secondchance.ui.home.adapter.PageViewModel;
import com.secondchance.ui.home.adapter.SectionsPagerAdapter;
import com.secondchance.ui.home.adapter.home;
import com.secondchance.ui.home.adapter.homeadapter;
import com.secondchance.utils.DividerItemDecoration;
import com.secondchance.utils.StorageUtil;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment implements View.OnClickListener {
    ArrayList<String> usernameList;
    ArrayList<String> imageList;
    ArrayList<String> dobList;
    LinearLayout linear_login_layout;
    LinearLayout registration_layout;
    StorageUtil mStore;
    LinearLayoutManager mLayoutManager;

    RecyclerView mRecyclerView;
    homeadapter homeadapter;
    Button people_btn;
    View root;
    LinearLayout linear_msg_layout;
    LinearLayout linear_people_layout;
    RecyclerView mRecyclerView_people;
    Button add_people_btn;
    Button new_msg_btn;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModel pageViewModel;
    String useraction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        initialization();
        setScreens();


        linear_msg_layout.setVisibility(View.GONE);
        linear_people_layout.setVisibility(View.VISIBLE);
        setPeopleListView();


//                if (mStore.getString("USERACTIVITY") != null && mStore.getString("USERACTIVITY").equalsIgnoreCase("REGISTER")) {
//            linear_login_layout.setVisibility(View.GONE);
//            registration_layout.setVisibility(View.VISIBLE);
//        } else if (mStore.getString("USERACTIVITY") != null && mStore.getString("USERACTIVITY").equalsIgnoreCase("LOGIN")) {
//            linear_login_layout.setVisibility(View.VISIBLE);
//            registration_layout.setVisibility(View.GONE);
//        } else {
//            linear_login_layout.setVisibility(View.VISIBLE);
//            registration_layout.setVisibility(View.GONE);
//        }


        return root;
    }

    private void initialization() {
        mStore = StorageUtil.getInstance(getActivity());

        mRecyclerView = root.findViewById(R.id.mRecyclerView);
        mRecyclerView_people = root.findViewById(R.id.mRecyclerView_people);
        registration_layout = root.findViewById(R.id.registration_layout);
        linear_login_layout = root.findViewById(R.id.linear_login_layout);
        linear_msg_layout = root.findViewById(R.id.linear_msg_layout);
        linear_people_layout = root.findViewById(R.id.linear_people_layout);
        add_people_btn = root.findViewById(R.id.add_people_btn);
        add_people_btn.setOnClickListener(this);
        new_msg_btn = root.findViewById(R.id.new_msg_btn);
        new_msg_btn.setOnClickListener(this);
        people_btn = root.findViewById(R.id.people_btn);
        people_btn.setOnClickListener(this);

    }

    private void setScreens() {

        //        if (mStore.getString("USERACTIVITY") != null && mStore.getString("USERACTIVITY").equalsIgnoreCase("REGISTER")) {
//            linear_login_layout.setVisibility(View.GONE);
//            registration_layout.setVisibility(View.VISIBLE);
//        } else if (mStore.getString("USERACTIVITY") != null && mStore.getString("USERACTIVITY").equalsIgnoreCase("LOGIN")) {
//            linear_login_layout.setVisibility(View.VISIBLE);
//            registration_layout.setVisibility(View.GONE);
//        } else {
//            linear_login_layout.setVisibility(View.VISIBLE);
//            registration_layout.setVisibility(View.GONE);
//        }
    }


    private void setMsgListView() {

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
        homeadapter = new homeadapter(new ArrayList<home>());
        prepareContentForMsg();

    }

    private void setPeopleListView() {

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
        mRecyclerView_people.setLayoutManager(mLayoutManager);

        mRecyclerView_people.setItemAnimator(new DefaultItemAnimator());
        Drawable dividerDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.divider_drawable);
        mRecyclerView_people.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        homeadapter = new homeadapter(new ArrayList<home>());
        prepareContentForPeople();

    }


    private void prepareContentForMsg() {

        ArrayList<home> usernameListarra = new ArrayList<>();
        for (int i = 0; i < usernameList.size(); i++) {
            usernameListarra.add(new home(getActivity(), usernameList, dobList, imageList, "MESSAGE"));
        }
        homeadapter.addItems(usernameListarra);
        mRecyclerView.setAdapter(homeadapter);
        homeadapter.notifyDataSetChanged();

    }

    private void prepareContentForPeople() {

        ArrayList<home> usernameListarra = new ArrayList<>();
        for (int i = 0; i < usernameList.size(); i++) {
            usernameListarra.add(new home(getActivity(), usernameList, dobList, imageList, "PEOPLE"));
        }
        homeadapter.addItems(usernameListarra);
        mRecyclerView_people.setAdapter(homeadapter);
        homeadapter.notifyDataSetChanged();

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.people_btn) {
            Navigation.findNavController(v).navigate(R.id.nav_addpeople);

        } else if (id == R.id.add_people_btn) {
//            AddpeopleFragment faqFragment = new AddpeopleFragment();
//            getFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.nav_host_fragment,
//                            faqFragment).commit();

            Navigation.findNavController(v).navigate(R.id.nav_addpeople);
        }
    }

    public static HomeFragment newInstance(int index) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }


}
