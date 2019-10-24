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
import cn.csu.software.wechat.bean.FriendChatInfo;
import cn.csu.software.wechat.constant.ConstantData;
import cn.csu.software.wechat.util.BitmapUtil;
import cn.csu.software.wechat.util.FileProcessUtil;
import cn.csu.software.wechat.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息界面 recycle view adapter
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-19
 */
public class FriendChatInfoAdapter extends RecyclerView.Adapter {
    private static final String TAG = FriendChatInfoAdapter.class.getSimpleName();

    private List<FriendChatInfo> mFriendChatInfoList = new ArrayList<>();

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
            friendChatInfoHolder.mFriendNameTextView.setText(mFriendChatInfoList.get(position).getFriendName());
            friendChatInfoHolder.mFriendMessageTextView.setText(mFriendChatInfoList.get(position).getFriendLastMessage());
            Bitmap bitmap = FileProcessUtil.getBitmap(mContext, mFriendChatInfoList.get(position).getFriendAvatarPath());
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
                    intent.putExtra(ConstantData.EXTRA_RECEIVER_NAME, mFriendChatInfoList.get(position).getFriendName());
                    intent.putExtra(ConstantData.EXTRA_AVATAR_PATH, mFriendChatInfoList.get(position).getFriendAvatarPath());
                    intent.setClassName(ConstantData.PACKAGE_NAME, ConstantData.ACTIVITY_CLASS_NAME_CHAT);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mFriendChatInfoList.size();
    }


    public void refreshItems(List<FriendChatInfo> friendChatInfoList) {
        LogUtil.i(TAG, "refreshItems %s", friendChatInfoList);
        mFriendChatInfoList.clear();
        mFriendChatInfoList.addAll(friendChatInfoList);
        notifyDataSetChanged();
    }


    public void addItem(FriendChatInfo friendChatInfo) {
        mFriendChatInfoList.add(friendChatInfo);
        notifyDataSetChanged();
    }

    public class FriendChatInfoHolder extends RecyclerView.ViewHolder {
        private TextView mFriendNameTextView;

        private ImageView mFriendAvatarImageView;

        private TextView mFriendMessageTextView;

        private View mFriendItemView;

        public FriendChatInfoHolder(View itemView) {
            super(itemView);
            mFriendItemView = itemView;
            mFriendNameTextView = itemView.findViewById(R.id.tv_friend_name);
            mFriendAvatarImageView = itemView.findViewById(R.id.iv_friend_avatar);
            mFriendMessageTextView = itemView.findViewById(R.id.tv_friend_message);
        }
    }
}
