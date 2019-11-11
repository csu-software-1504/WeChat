package cn.csu.software.wechat.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.csu.software.wechat.entity.ChatMessage;
import cn.csu.software.wechat.entity.SocketData;
import cn.csu.software.wechat.entity.UserInfo;
import cn.csu.software.wechat.constant.ConstantData;
import cn.csu.software.wechat.data.ChatMessageData;
import cn.csu.software.wechat.data.FriendChatInfoData;
import cn.csu.software.wechat.database.helper.UserInfoDatabaseHelper;
import cn.csu.software.wechat.socket.SocketClient;
import cn.csu.software.wechat.util.LogUtil;

public class SocketService extends Service implements SocketClient.SocketClientListener {
    private static final String TAG = SocketService.class.getSimpleName();

    private SocketClient mSocketClient;

    private ExecutorService mThreadPool;

    private Context mContext;

    private UserInfoDatabaseHelper mDatabaseHelper;

    private SendMessageBinder mSendMessageBinder = new SendMessageBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mSendMessageBinder;
    }

    @Override
    public void onCreate() {
        LogUtil.i(TAG, "Socket Service onCreate");
        mContext = this;
        startSocket();
        super.onCreate();
    }

    private void sendBroadCast(ChatMessage chatMessage) {
        Intent intent = new Intent(ConstantData.RECEIVED_MESSAGE_BROADCAST);
        intent.putExtra(ConstantData.EXTRA_CHAT_MESSAGE, chatMessage);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private void startSocket() {
        mSocketClient = new SocketClient();
        mSocketClient.setSocketClientListener(this);
        mThreadPool = Executors.newFixedThreadPool(2);
        mThreadPool.execute(mSocketClient);
    }

    @Override
    public void onSocketClientListener(SocketData socketData) {
        LogUtil.i(TAG, "socket data: %s", socketData.toString());
        mDatabaseHelper = UserInfoDatabaseHelper.getInstance(mContext, ConstantData.DATABASE_CREATE_VISION_SECOND_TIME);
        long mSendTime = System.currentTimeMillis();
        UserInfo senderUserInfo = mDatabaseHelper.queryByAccount(socketData.getSenderAccount());
        UserInfo receiverUserInfo = mDatabaseHelper.queryByAccount(socketData.getReceiverAccount());
        ChatMessage chatMessage = new ChatMessage(senderUserInfo.getAccount(), receiverUserInfo.getAccount(),
            senderUserInfo.getUsername(), receiverUserInfo.getUsername(), senderUserInfo.getAvatarPath(),
            0, socketData.getMessageType(), mSendTime, socketData.getTextMessage(), "", "");
        if (socketData.getMessageType() == 0) {
            senderUserInfo.setLastMessage(socketData.getTextMessage());
        }
        senderUserInfo.setLastMessageSendTime(mSendTime);
        FriendChatInfoData.addUserInfo(senderUserInfo);
        ChatMessageData.addChatMessage(chatMessage, senderUserInfo.getAccount());
        sendBroadCast(chatMessage);
    }

    public class SendMessageBinder extends Binder {
        public void sendMessage(final ChatMessage chatMessage, final UserInfo userInfo) {
            mThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                try {
                    FriendChatInfoData.addUserInfo(userInfo);
                    ChatMessageData.addChatMessage(chatMessage, userInfo.getAccount());

                    SocketData socketData = new SocketData(chatMessage.getReceiverAccount(),
                        chatMessage.getReceiverAccount(), chatMessage.getChatMessageType(),
                        chatMessage.getChatMessageText(), new byte[0]);
                    mSocketClient.sendMessage(socketData);
                } catch (IOException e) {
                    LogUtil.i(TAG, "send message error, %s", e);
                }
                }
            });
        }
    }
}
