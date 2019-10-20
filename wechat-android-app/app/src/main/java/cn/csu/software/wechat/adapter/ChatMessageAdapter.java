/*
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.adapter;


import android.content.Context;
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
import cn.csu.software.wechat.bean.ChatMessage;
import cn.csu.software.wechat.constant.ConstantData;
import cn.csu.software.wechat.util.BitmapUtil;
import cn.csu.software.wechat.util.FileProcessUtil;
import cn.csu.software.wechat.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 聊天界面消息 recycle view adapter
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-19
 */
public class ChatMessageAdapter extends RecyclerView.Adapter {
    private static final String TAG = ChatMessageAdapter.class.getSimpleName();

    public final static int ITEM_TYPE_TEXT_LEFT = 0;

    public final static int ITEM_TYPE_TEXT_RIGHT = 1;

    private Context mContext;

    private LayoutInflater mInflater;

    List<ChatMessage> mChatMessageList = new ArrayList<>();

    public ChatMessageAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == ITEM_TYPE_TEXT_LEFT) {
            itemView = mInflater.inflate(R.layout.item_chat_left_text, parent, false);
            return new LeftTextViewHolder(itemView);
        } else {
            itemView = mInflater.inflate(R.layout.item_chat_right_text, parent, false);
            return new RightTextViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof LeftTextViewHolder) {
            LeftTextViewHolder leftTextViewHolder = (LeftTextViewHolder) viewHolder;
            leftTextViewHolder.mTextView.setText(mChatMessageList.get(position).getChatMessageText());
            Bitmap bitmap = FileProcessUtil.getBitmap(mContext, mChatMessageList.get(position).getAvatarPath());
            if (bitmap != null) {
                Bitmap avatarBitmap = BitmapUtil.zoomImg(bitmap, ConstantData.AVATAR_SIZE_MESSAGE, ConstantData.AVATAR_SIZE_MESSAGE);
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory
                    .create(mContext.getResources(), avatarBitmap);
                roundedBitmapDrawable.setCornerRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    ConstantData.AVATAR_CIRCLE_SIZE, mContext.getResources().getDisplayMetrics()));
                leftTextViewHolder.mAvatarImageView.setImageDrawable(roundedBitmapDrawable);
            }
        } else if (viewHolder instanceof RightTextViewHolder) {
            RightTextViewHolder rightTextViewHolder = (RightTextViewHolder) viewHolder;
            rightTextViewHolder.mTextView.setText(mChatMessageList.get(position).getChatMessageText());
            Bitmap bitmap = FileProcessUtil.getBitmap(mContext, mChatMessageList.get(position).getAvatarPath());
            if (bitmap != null) {
                Bitmap avatarBitmap = BitmapUtil.zoomImg(bitmap, ConstantData.AVATAR_SIZE_MESSAGE, ConstantData.AVATAR_SIZE_MESSAGE);
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory
                    .create(mContext.getResources(), avatarBitmap);
                roundedBitmapDrawable.setCornerRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    ConstantData.AVATAR_CIRCLE_SIZE, mContext.getResources().getDisplayMetrics()));
                rightTextViewHolder.mAvatarImageView.setImageDrawable(roundedBitmapDrawable);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mChatMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if ("me".equals(mChatMessageList.get(position).getSenderName())) {
            return ITEM_TYPE_TEXT_RIGHT;
        } else {
            return ITEM_TYPE_TEXT_LEFT;
        }
    }

    private void refreshItems(List<ChatMessage> list) {
        mChatMessageList.clear();
        mChatMessageList.addAll(list);
        notifyDataSetChanged();
    }

    public void addItem(ChatMessage message) {
        mChatMessageList.add(message);
        LogUtil.i(TAG, "message: %s", message.getChatMessageText());
        notifyDataSetChanged();
    }

    public class LeftTextViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        private ImageView mAvatarImageView;

        private View mItemView;

        public LeftTextViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mTextView = itemView.findViewById(R.id.tv_left_chat);
            mAvatarImageView = itemView.findViewById(R.id.iv_left_avatar);
        }
    }

    public class RightTextViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        private ImageView mAvatarImageView;

        private View mItemView;

        public RightTextViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mTextView = itemView.findViewById(R.id.tv_right_chat);
            mAvatarImageView = itemView.findViewById(R.id.iv_right_avatar);
        }
    }
}
