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

import cn.csu.software.wechat.adapter.FriendChatInfoAdapter;
import cn.csu.software.wechat.R;
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
public class TabMessageFragment extends Fragment {
    private static final String TAG = TabMessageFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;

    private LinearLayoutManager mLayoutManager;

    private FriendChatInfoAdapter mAdapter;

    private Context mContext;

    private View mView;

    private TextView mTabNameTextView;

    private static String mTabName;

    private FriendChatInfoDatabaseHelper mDatabaseHelper;

    private List<FriendChatInfo> mFriendChatInfoList;

    public static TabMessageFragment newInstance(String content){
        mTabName = content;
        return new TabMessageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mView = inflater.inflate(R.layout.fragment_tab_message, container, false);
        initView();
        initDatabaseHelper();
        readSQLite();
        return mView;
    }

    private void initView() {
        mTabNameTextView = mView.findViewById(R.id.tv_tab_name);
        mTabNameTextView.setText(mTabName);
        mAdapter = new FriendChatInfoAdapter(mContext);
        mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = mView.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onPause() {
        LogUtil.e(TAG, "close database");
        mDatabaseHelper.closeLink();
        mDatabaseHelper.close();
        super.onPause();
    }

    private void initDatabaseHelper() {
        mDatabaseHelper = FriendChatInfoDatabaseHelper.getInstance(mContext,
            ConstantData.DATABASE_CREATE_VISION_SECOND_TIME);
        mDatabaseHelper.openReadLink();
        mDatabaseHelper.openWriteLink();
    }

    private void readSQLite() {
        if (mDatabaseHelper == null) {
            LogUtil.e(TAG, "database helper is null");
            return;
        }
        mFriendChatInfoList = mDatabaseHelper.query(ConstantData.GLOBAL_QUERY_CONDITION);
        if (mFriendChatInfoList.size() == 0) {
            initData();
        } else {
            for (FriendChatInfo friendChatInfo : mFriendChatInfoList) {
                mAdapter.addItem(friendChatInfo);
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
            mAdapter.addItem(friendChatInfo);
        }
    }
}
