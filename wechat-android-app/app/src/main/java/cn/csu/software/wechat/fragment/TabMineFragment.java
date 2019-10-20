/*
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.csu.software.wechat.R;

/**
 * 我的界面
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-19
 */
public class TabMineFragment extends Fragment {
    // TODO: 19-10-20 后续开发我的界面
    private View mView;

    public static TabMineFragment newInstance(String content){
        Bundle arguments = new Bundle();
        arguments.putString("content", content);
        TabMineFragment tabContentFragment = new TabMineFragment();
        tabContentFragment.setArguments(arguments);
        return tabContentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_tab_mine, container, false);
        return mView;
    }
}
