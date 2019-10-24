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
import cn.csu.software.wechat.bean.FriendChatInfo;
import cn.csu.software.wechat.constant.ConstantData;
import cn.csu.software.wechat.database.helper.FriendChatInfoDatabaseHelper;
import cn.csu.software.wechat.util.LogUtil;

import java.io.File;
import java.util.List;

/**
 * 消息界面
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-19
 */
public class TabFriendFragment extends Fragment {
    private static final String TAG = TabMessageFragment.class.getSimpleName();

    private RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;

    private FriendInfoAdapter adapter;

    private Context mContext;

    private View mView;

    private TextView mTabNameTextView;

    private static String mTabName;

    private FriendChatInfoDatabaseHelper mDatabaseHelper;

    private List<FriendChatInfo> mFriendChatInfoList;

    public static TabFriendFragment newInstance(String content){
        Bundle arguments = new Bundle();
        arguments.putString("content", content);
        mTabName = content;
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
        mTabNameTextView.setText(mTabName);
        adapter = new FriendInfoAdapter(mContext);
        recyclerView = mView.findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        initDatabaseHelper();
        readSQLite();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return mView;
    }

    private void initDatabaseHelper() {
        mDatabaseHelper = FriendChatInfoDatabaseHelper.getInstance(mContext,
            ConstantData.DATABASE_CREATE_VISION_SECOND_TIME);
        mDatabaseHelper.openReadLink();
        mDatabaseHelper.openWriteLink();
    }

    private void readSQLite() {
        if (mDatabaseHelper == null) {
            LogUtil.e(TAG, "mDatabaseHelper is null");
            return;
        }
        mFriendChatInfoList = mDatabaseHelper.query(ConstantData.GLOBAL_QUERY_CONDITION);
        if (mFriendChatInfoList.size() == 0) {
            initData();
        } else {
            for (FriendChatInfo friendChatInfo : mFriendChatInfoList) {
                adapter.addItem(friendChatInfo);
            }
        }
    }

    private void initData() {
        for (int i = 0; i < ConstantData.EXAMPLE_FRIEND_NAME.length; i++) {
            FriendChatInfo friendChatInfo = new FriendChatInfo();
            friendChatInfo.setFriendName(ConstantData.EXAMPLE_FRIEND_NAME[i]);
            friendChatInfo.setFriendLastMessage(ConstantData.EXAMPLE_LAST_MESSAGE_HEADER
                + ConstantData.EXAMPLE_FRIEND_NAME[i]);
            friendChatInfo.setFriendAvatarPath(mContext.getFilesDir().getPath()+ File.separator
                + ConstantData.PHOTO_DIRECTORY + File.separator + ConstantData.AVATAR_DIRECTORY
                + File.separator + ConstantData.EXAMPLE_AVATAR_NAME[i] + ConstantData.EXAMPLE_EXTENSION_NAME);
            mDatabaseHelper.insert(friendChatInfo);
            adapter.addItem(friendChatInfo);
        }
    }
}
