/*
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.csu.software.wechat.R;
import cn.csu.software.wechat.entity.UserInfo;
import cn.csu.software.wechat.constant.ConstantData;
import cn.csu.software.wechat.util.BitmapUtil;
import cn.csu.software.wechat.util.FileProcessUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 消息界面 recycle view adapter
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-19
 */
public class FriendChatInfoAdapter extends RecyclerView.Adapter {
    private static final String TAG = FriendChatInfoAdapter.class.getSimpleName();

    private List<UserInfo> mUserInfoList = new ArrayList<>();

    private Context mContext;

    public FriendChatInfoAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(mContext).inflate(R.layout.item_friend_chat_info, parent, false);
        return new FriendChatInfoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof FriendChatInfoHolder) {
            FriendChatInfoHolder friendChatInfoHolder = (FriendChatInfoHolder) holder;
            friendChatInfoHolder.mFriendNameTextView.setText(mUserInfoList.get(position).getUsername());
            friendChatInfoHolder.mFriendMessageTextView.setText(mUserInfoList.get(position).getLastMessage());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sendTime = sdf.format(new Date(mUserInfoList.get(position).getLastMessageSendTime()));
            friendChatInfoHolder.mSendTimeTextView.setText(String.valueOf(sendTime));
            Bitmap bitmap = FileProcessUtil.getBitmap(mContext, mUserInfoList.get(position).getAvatarPath());
            if (bitmap != null) {
                Bitmap avatarBitmap = BitmapUtil.zoomImg(bitmap, ConstantData.AVATAR_SIZE_MESSAGE, ConstantData.AVATAR_SIZE_MESSAGE);
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory
                    .create(mContext.getResources(), avatarBitmap);
                roundedBitmapDrawable.setCornerRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    ConstantData.AVATAR_CIRCLE_SIZE, mContext.getResources().getDisplayMetrics()));
                friendChatInfoHolder.mFriendAvatarImageView.setImageDrawable(roundedBitmapDrawable);
            }
            friendChatInfoHolder.mFriendItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra(ConstantData.EXTRA_USER_INFO, mUserInfoList.get(position));
                    intent.setClassName(ConstantData.PACKAGE_NAME, ConstantData.ACTIVITY_CLASS_NAME_CHAT);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mUserInfoList.size();
    }


    public void refreshItems(List<UserInfo> userInfoList) {
        mUserInfoList.clear();
        mUserInfoList.addAll(userInfoList);
        notifyDataSetChanged();
    }


    public void addItem(UserInfo userInfo) {
        mUserInfoList.add(userInfo);
        notifyDataSetChanged();
    }

    public class FriendChatInfoHolder extends RecyclerView.ViewHolder {
        private TextView mFriendNameTextView;

        private ImageView mFriendAvatarImageView;

        private TextView mFriendMessageTextView;

        private TextView mSendTimeTextView;

        private View mFriendItemView;

        public FriendChatInfoHolder(View itemView) {
            super(itemView);
            mFriendItemView = itemView;
            mFriendNameTextView = itemView.findViewById(R.id.tv_friend_name);
            mFriendAvatarImageView = itemView.findViewById(R.id.iv_friend_avatar);
            mFriendMessageTextView = itemView.findViewById(R.id.tv_friend_message);
            mSendTimeTextView = itemView.findViewById(R.id.tv_send_time);
        }
    }
}
