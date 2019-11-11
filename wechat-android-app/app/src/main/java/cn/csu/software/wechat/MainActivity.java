/*
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import cn.csu.software.wechat.constant.ConstantData;
import cn.csu.software.wechat.data.ChatMessageData;
import cn.csu.software.wechat.data.FriendChatInfoData;
import cn.csu.software.wechat.data.UserInfoData;
import cn.csu.software.wechat.fragment.TabMessageFragment;
import cn.csu.software.wechat.fragment.TabCircleFragment;
import cn.csu.software.wechat.fragment.TabFriendFragment;
import cn.csu.software.wechat.fragment.TabMineFragment;
import cn.csu.software.wechat.fragment.adapter.WeChatFragmentPagerAdapter;
import cn.csu.software.wechat.service.SocketService;
import cn.csu.software.wechat.util.BitmapUtil;
import cn.csu.software.wechat.util.FileProcessUtil;
import cn.csu.software.wechat.util.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * 主页面
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-19
 */
public class MainActivity extends AppCompatActivity implements TabLayout.BaseOnTabSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TabLayout mTabLayout;

    private ViewPager mViewPager;

    private WeChatFragmentPagerAdapter mPagerAdapter;

    private Context mContext;

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        createFileDirectory();
        initView();
        initTab();
        initData();
        startSocketService();
    }

    private void initData() {
        UserInfoData.initDatabaseHelper(mContext);
        UserInfoData.queryAllFriendChatInfo();
        FriendChatInfoData.initDatabaseHelper(mContext);
        FriendChatInfoData.queryAllFriendChatInfo();
        ChatMessageData.initDatabaseHelper(mContext);
        ChatMessageData.queryChatMessage();
    }

    private void startSocketService() {
        Intent intent = new Intent(mContext, SocketService.class);
        startService(intent);
    }

    private void initView() {
        mTabLayout = findViewById(R.id.tl_tablayout);
        mViewPager = findViewById(R.id.vp_viewpager);
    }

    private void initTab() {
        mFragments.add(TabMessageFragment.newInstance(getString(R.string.tab_name_message)));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab_name_message));

        mFragments.add(TabFriendFragment.newInstance(getString(R.string.tab_name_friend)));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab_name_friend));

        mFragments.add(TabCircleFragment.newInstance(getString(R.string.tab_name_circle)));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab_name_circle));

        mFragments.add(TabMineFragment.newInstance(getString(R.string.tab_name_mine)));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab_name_mine));

        mTabLayout.setupWithViewPager(mViewPager, false);
        mPagerAdapter = new WeChatFragmentPagerAdapter(mFragments, getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);

        Objects.requireNonNull(mTabLayout.getTabAt(0)).setIcon(R.mipmap.icon_message_select);
        Objects.requireNonNull(mTabLayout.getTabAt(1)).setIcon(R.mipmap.icon_friend_normal);
        Objects.requireNonNull(mTabLayout.getTabAt(2)).setIcon(R.mipmap.icon_circle_normal);
        Objects.requireNonNull(mTabLayout.getTabAt(3)).setIcon(R.mipmap.icon_mine_normal);

        mTabLayout.addOnTabSelectedListener(this);
    }

    private void createFileDirectory() {
        LogUtil.i(TAG, "create directory");
        String photoPath = mContext.getFilesDir().getPath() + File.separator + ConstantData.PHOTO_DIRECTORY;
        if (!FileProcessUtil.isExist(photoPath)) {
            if (FileProcessUtil.createDirectory(photoPath)) {
                LogUtil.i(TAG, "create photo directory success");
            }
        }
        String avatarPath = mContext.getFilesDir().getPath() + File.separator + ConstantData.PHOTO_DIRECTORY
            + File.separator + ConstantData.AVATAR_DIRECTORY;
        LogUtil.i(TAG, mContext.getFilesDir().getPath());
        if (!FileProcessUtil.isExist(avatarPath)) {
            if (FileProcessUtil.createDirectory(avatarPath)) {
                LogUtil.i(TAG, "create avatar directory success");
                copyAvatar();
            }
        }
    }

    private void copyAvatar() {
        LogUtil.i(TAG, "copy avatar");
        for (String name : ConstantData.EXAMPLE_AVATAR_NAME) {
            String avatarSourcePath = ConstantData.PHOTO_DIRECTORY + File.separator + ConstantData.AVATAR_DIRECTORY
                + File.separator + name + ConstantData.EXAMPLE_EXTENSION_NAME;
            String avatarTargetPath = mContext.getFilesDir().getPath() + File.separator + ConstantData.PHOTO_DIRECTORY
                + File.separator + ConstantData.AVATAR_DIRECTORY + File.separator + name + ConstantData.EXAMPLE_EXTENSION_NAME;
            Bitmap bitmap = FileProcessUtil.getAssetsFileBitmap(mContext, avatarSourcePath);
            if (bitmap != null) {
                BitmapUtil.saveImg(avatarTargetPath, bitmap);
            }
        }

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                tab.setIcon(R.mipmap.icon_message_select);
                break;
            case 1:
                tab.setIcon(R.mipmap.icon_friend_select);
                break;
            case 2:
                tab.setIcon(R.mipmap.icon_circle_select);
                break;
            case 3:
                tab.setIcon(R.mipmap.icon_mine_select);
                break;
            default:
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                tab.setIcon(R.mipmap.icon_message_normal);
                break;
            case 1:
                tab.setIcon(R.mipmap.icon_friend_normal);
                break;
            case 2:
                tab.setIcon(R.mipmap.icon_circle_normal);
                break;
            case 3:
                tab.setIcon(R.mipmap.icon_mine_normal);
                break;
            default:
                break;
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
