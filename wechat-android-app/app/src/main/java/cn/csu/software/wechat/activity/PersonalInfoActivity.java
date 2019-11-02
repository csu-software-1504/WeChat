/*
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.csu.software.wechat.R;
import cn.csu.software.wechat.entity.UserInfo;
import cn.csu.software.wechat.constant.ConstantData;
import cn.csu.software.wechat.util.BitmapUtil;
import cn.csu.software.wechat.util.FileProcessUtil;

/**
 * 消息界面
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-19
 */
public class PersonalInfoActivity extends Activity implements View.OnClickListener {
    private static final String TAG = PersonalInfoActivity.class.getSimpleName();

    private RelativeLayout mSendMessageRelativeLayout;

    private Context mContext;

    private UserInfo mUserInfo;

    private TextView mNameTextView;

    private TextView mAccountTextView;

    private ImageView mAvatarImageView;

    private String mFriendName;

    private String mAvatarPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_personal_info);
        initIntent();
        initView();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rl_send_message) {
            Intent intent = new Intent();
            intent.putExtra(ConstantData.EXTRA_USER_INFO, mUserInfo);
            intent.setClassName(ConstantData.PACKAGE_NAME, ConstantData.ACTIVITY_CLASS_NAME_CHAT);
            mContext.startActivity(intent);
        }
    }

    private void initView() {
        mSendMessageRelativeLayout = findViewById(R.id.rl_send_message);
        mSendMessageRelativeLayout.setOnClickListener(this);
        mNameTextView = findViewById(R.id.tv_name);
        mNameTextView.setText(mUserInfo.getUsername());
        mAccountTextView = findViewById(R.id.tv_account);
        mAccountTextView.setText(String.valueOf(mUserInfo.getAccount()));
        mAvatarImageView = findViewById(R.id.iv_personal_avatar);
        Bitmap bitmap = FileProcessUtil.getBitmap(mContext, mUserInfo.getAvatarPath());
        if (bitmap != null) {
            Bitmap avatarBitmap = BitmapUtil.zoomImg(bitmap, ConstantData.AVATAR_SIZE_MINE, ConstantData.AVATAR_SIZE_MINE);
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(mContext.getResources(), avatarBitmap);
            roundedBitmapDrawable.setCornerRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                ConstantData.AVATAR_CIRCLE_SIZE, mContext.getResources().getDisplayMetrics()));
            mAvatarImageView.setImageDrawable(roundedBitmapDrawable);
        }
    }

    private void initIntent() {
        Intent intent = getIntent();
        Object object = intent.getSerializableExtra(ConstantData.EXTRA_USER_INFO);
        if (object instanceof UserInfo) {
            mUserInfo = (UserInfo) object;
        }
    }
}
