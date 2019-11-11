/*
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import cn.csu.software.wechat.entity.UserInfo;
import cn.csu.software.wechat.constant.ConstantData;
import cn.csu.software.wechat.util.LogUtil;

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

    private Button mNormalButton;

    private Button mLocalButton;

    private Button mOrderButton;

    private Context mContext;

    private View mView;

    private TextView mTabNameTextView;

    private static String mTabName;

    private BroadcastReceiver receiver;

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
        mNormalButton = mView.findViewById(R.id.normal_broadcast);
        mNormalButton.setOnClickListener(this);
        mLocalButton = mView.findViewById(R.id.local_broadcast);
        mLocalButton.setOnClickListener(this);
        mOrderButton = mView.findViewById(R.id.order_broadcast);
        mOrderButton.setOnClickListener(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("cn.csu.software.normal.broadcast");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                LogUtil.e(TAG, "BroadcastService接收到了广播");
                Intent intent1 = new Intent();
                intent1.putExtra(ConstantData.EXTRA_USER_INFO, new UserInfo());
                intent1.setClassName(ConstantData.PACKAGE_NAME, intent.getStringExtra("activity"));
                context.startActivity(intent);
            }
        };
        mContext.registerReceiver(receiver, intentFilter);
        return mView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.normal_broadcast) {
            LogUtil.i(TAG, "send normal broadcast");
            Intent intent = new Intent("com.example.normal.receiver");
            intent.putExtra("activity", ConstantData.ACTIVITY_CLASS_NAME_CHAT);
            mContext.sendBroadcast(intent);
        } else if (view.getId() == R.id.order_broadcast){
            Intent intent = new Intent("cn.csu.software.order.broadcast");
            intent.putExtra("activity", ConstantData.ACTIVITY_CLASS_NAME_PERSONAL_INFO);
            mContext.sendOrderedBroadcast(intent, null);
        } else {
            Intent intent = new Intent("cn.csu.software.local.broadcast");
            intent.putExtra("activity", ConstantData.ACTIVITY_CLASS_NAME_PERSONAL_INFO);
            mContext.sendBroadcast(intent);
        }
    }

    class LowBroadcast extends BroadcastReceiver {
        private final String TAG = LowBroadcast.class.getSimpleName();
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtil.i(TAG, "low receiver the broadcast");
//        Intent intent1 = new Intent();
//        intent.setClassName(ConstantData.PACKAGE_NAME, intent.getStringExtra("activity"));
//        context.startActivity(intent);
        }
    }
}
