/*
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import cn.csu.software.wechat.R;
import cn.csu.software.wechat.adapter.ChatMessageAdapter;
import cn.csu.software.wechat.bean.ChatMessage;
import cn.csu.software.wechat.constant.ConstantData;
import cn.csu.software.wechat.database.helper.ChatMessageDatabaseHelper;
import cn.csu.software.wechat.socket.SocketClient;
import cn.csu.software.wechat.util.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 消息界面
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-19
 */
public class ChatActivity extends Activity implements TextWatcher, View.OnClickListener, SocketClient.SocketClientListener {
    private static final String TAG = ChatActivity.class.getSimpleName();

    /**
     * time, my name, type暂时还没实现，后续修改
     */
    private static final String TIME = "2019";

    private static final String MY_NAME = "me";

    private RecyclerView mRecyclerView;

    private Context mContext;

    private ChatMessageAdapter mChatMessageAdapter;

    private EditText mEditText;

    private ImageButton mMoreButton;

    private ImageButton mExpressionButton;

    private TextView mReceiverNameTextView;

    private ImageButton mVoiceButton;

    private Button mSendButton;

    private String mMessage;

    private LinearLayoutManager layoutManager;

    private ChatMessageDatabaseHelper mDatabaseHelper;

    private ArrayList<ChatMessage> mChatMessagesList;

    private SocketClient mSocketClient;

    private ExecutorService mThreadPool;

    private String mReceiverName;

    private String mAvatarPath;

    @SuppressLint("HandlerLeak")
    private Handler mMainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                LogUtil.i(TAG, "message: %s", msg);
                ChatMessage chatMessage = new ChatMessage();
                String message = msg.getData().getString(ConstantData.BUNDLE_KEY_MESSAGE);
                chatMessage.setSenderName(mReceiverName);
                chatMessage.setReceiverName(MY_NAME);
                chatMessage.setAvatarPath(mAvatarPath);
                chatMessage.setChatMessageType(ChatMessage.TEXT_TYPE);
                chatMessage.setSendTime(TIME);
                chatMessage.setChatMessageText(message);
                chatMessage.setChatMessagePhotoPath(message);
                chatMessage.setChatMessageVideoPath(message);
                mDatabaseHelper.insert(chatMessage);
                addItem(chatMessage);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initIntent();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatabaseHelper = ChatMessageDatabaseHelper.getInstance(mContext,
            ConstantData.DATABASE_CREATE_VISION_SECOND_TIME);
        mDatabaseHelper.openReadLink();
        mDatabaseHelper.openWriteLink();
        readSQLite();
        initData();
        startSocket();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 关闭数据库连接
        mDatabaseHelper.closeLink();
        mSocketClient.close();
    }

    private void initIntent() {
        Intent intent = this.getIntent();
        if (intent == null || intent.getExtras() == null) {
            return;
        }
        mReceiverName = intent.getStringExtra(ConstantData.EXTRA_RECEIVER_NAME);
        mAvatarPath = intent.getStringExtra(ConstantData.EXTRA_AVATAR_PATH);
    }

    private void readSQLite() {
        if (mDatabaseHelper == null) {
            LogUtil.e(TAG, "mChatMessageDatabaseHelper is null");
        }
        String condition = String.format("receiver_name='%s' or sender_name='%s'", mReceiverName, mReceiverName);
        mChatMessagesList = mDatabaseHelper.query(condition);
    }

    private void startSocket() {
        mSocketClient = new SocketClient();
        mSocketClient.setSocketClientListener(this);
        mThreadPool = Executors.newCachedThreadPool();
        mThreadPool.execute(mSocketClient);
    }

    private void initView() {
        setContentView(R.layout.activity_chat);
        mChatMessageAdapter = new ChatMessageAdapter(mContext);
        mRecyclerView = findViewById(R.id.recycler_chat);
        mRecyclerView.setAdapter(mChatMessageAdapter);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mSendButton = findViewById(R.id.bt_send);
        mSendButton.setOnClickListener(this);
        mMoreButton = findViewById(R.id.bt_more);
        mExpressionButton = findViewById(R.id.bt_expression);
        mEditText = findViewById(R.id.et_input);
        mEditText.addTextChangedListener(this);
        mReceiverNameTextView = findViewById(R.id.tv_receiver_name);
        mReceiverNameTextView.setText(mReceiverName);
    }

    private void addItem(ChatMessage chatMessage) {
        mChatMessageAdapter.addItem(chatMessage);
    }

    private void initData() {
        if (mChatMessagesList == null) {
            return;
        }
        for (ChatMessage chatMessage : mChatMessagesList) {
            mChatMessageAdapter.addItem(chatMessage);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
        mMoreButton.setVisibility(View.GONE);
        mExpressionButton.setVisibility(View.GONE);
        mSendButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        mMessage = mEditText.getText().toString();
    }

    @Override
    public void onClick(View view) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSenderName(MY_NAME);
        chatMessage.setReceiverName(mReceiverName);
        String myAvatarPath = mContext.getFilesDir().getPath()+ File.separator
            + ConstantData.PHOTO_DIRECTORY + File.separator + ConstantData.AVATAR_DIRECTORY
            + File.separator + ConstantData.MY_AVATAR_NAME + ConstantData.EXAMPLE_EXTENSION_NAME;
        chatMessage.setAvatarPath(myAvatarPath);
        chatMessage.setChatMessageType(ChatMessage.TEXT_TYPE);
        chatMessage.setSendTime(TIME);
        chatMessage.setChatMessageText(mMessage);
        chatMessage.setChatMessagePhotoPath(mMessage);
        chatMessage.setChatMessageVideoPath(mMessage);
        mDatabaseHelper.insert(chatMessage);
        addItem(chatMessage);
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mSocketClient.sendMessage(mMessage);
                } catch (IOException e) {
                    LogUtil.i(TAG, "send message error");
                }
            }
        });
        mEditText.setText("");
    }

    @Override
    public void onSocketClientListener(String msg) {
        Message message = mMainHandler.obtainMessage(0);
        Bundle bundle = new Bundle();
        bundle.putString(ConstantData.BUNDLE_KEY_MESSAGE, msg);
        message.setData(bundle);
        mMainHandler.sendMessage(message);
    }
}
