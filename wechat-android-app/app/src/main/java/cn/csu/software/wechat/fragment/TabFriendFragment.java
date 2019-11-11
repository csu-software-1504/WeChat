/*
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.csu.software.wechat.R;
import cn.csu.software.wechat.adapter.FriendInfoAdapter;
import cn.csu.software.wechat.entity.UserInfo;
import cn.csu.software.wechat.data.UserInfoData;

/**
 * 消息界面
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-19
 */
public class TabFriendFragment extends Fragment {
    private static final String TAG = TabMessageFragment.class.getSimpleName();

    private static String sTabName;

    private RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;

    private FriendInfoAdapter adapter;

    private Context mContext;

    private View mView;

    private TextView mTabNameTextView;

    public static TabFriendFragment newInstance(String content){
        Bundle arguments = new Bundle();
        arguments.putString("content", content);
        sTabName = content;
        TabFriendFragment tabContentFragment = new TabFriendFragment();
        tabContentFragment.setArguments(arguments);
        return tabContentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mView = inflater.inflate(R.layout.fragment_tab_friend, container, false);
        mTabNameTextView = mView.findViewById(R.id.tv_tab_name);
        mTabNameTextView.setText(sTabName);
        adapter = new FriendInfoAdapter(mContext);
        recyclerView = mView.findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        refreshData();
        return mView;
    }

    private void refreshData() {
        for (UserInfo userInfo : UserInfoData.getChatMessagesList()) {
            adapter.addItem(userInfo);
        }
    }
}
