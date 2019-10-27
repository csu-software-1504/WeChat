/*
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cn.csu.software.wechat.R;

/**
 * 消息界面
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-19
 */
public class TabCircleFragment extends Fragment implements View.OnClickListener {
    // TODO: 19-10-20 后续开发朋友圈界面 
    private static final String TAG = TabCircleFragment.class.getSimpleName();

    private static final String DOWNLOAD_PATH = "https://github.com/geeeeeeeeek/electronic-wechat/releases/download/V2.0/linux-ia32.tar.gz";

    private TextView mDownloadPathTextView;

    private Button mDownloadButton;

    private Context mContext;

    private View mView;

    private TextView mTabNameTextView;

    private static String mTabName;

    public static TabCircleFragment newInstance(String content){
        Bundle arguments = new Bundle();
        arguments.putString("content", content);
        mTabName = content;
        TabCircleFragment tabContentFragment = new TabCircleFragment();
        tabContentFragment.setArguments(arguments);
        return tabContentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mView = inflater.inflate(R.layout.fragment_tab_circle, container, false);
        mDownloadPathTextView = mView.findViewById(R.id.tv_download_path);
        mTabNameTextView = mView.findViewById(R.id.tv_tab_name);
        mTabNameTextView.setText(mTabName);
        mDownloadPathTextView.setText(DOWNLOAD_PATH);
        mDownloadButton = mView.findViewById(R.id.bt_download);
        mDownloadButton.setOnClickListener(this);
        return mView;
    }

    @Override
    public void onClick(View view) {
        /*DownloadUtil.get(mContext).download(DOWNLOAD_PATH, "download", new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                LogUtil.i(TAG, "download success");
            }
            @Override
            public void onDownloading(int progress) {
                LogUtil.i(TAG, "downloading");
            }
            @Override
            public void onDownloadFailed() {
                LogUtil.i(TAG,  "download failed");
            }
        });*/
    }
}
