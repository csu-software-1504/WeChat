package cn.csu.software.wechat.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.csu.software.wechat.entity.ChatMessage;
import cn.csu.software.wechat.entity.UserInfo;
import cn.csu.software.wechat.constant.ConstantData;
import cn.csu.software.wechat.database.content.ChatMessageContent;
import cn.csu.software.wechat.database.helper.ChatMessageDatabaseHelper;
import cn.csu.software.wechat.util.LogUtil;

public class ChatMessageData {
    private static final String TAG = ChatMessageData.class.getSimpleName();

    private static Map<Integer, List<ChatMessage>> sChatMessageMap = new HashMap<>();

    private static ChatMessageDatabaseHelper sDatabaseHelper;

    public static void initDatabaseHelper(Context context) {
        sDatabaseHelper = ChatMessageDatabaseHelper.getInstance(context,
                ConstantData.DATABASE_CREATE_VISION_SECOND_TIME);
        sDatabaseHelper.openReadLink();
        sDatabaseHelper.openWriteLink();
    }

    public static void queryChatMessage() {
        if (sDatabaseHelper == null) {
            LogUtil.e(TAG, "mChatMessageDatabaseHelper is null");
        }
        for (UserInfo userInfo : FriendChatInfoData.getUserInfoList()) {
            int account =  userInfo.getAccount();
            String condition = String.format("%s=%s or %s=%s", ChatMessageContent.SENDER_ACCOUNT, account, ChatMessageContent.RECEIVER_ACCOUNT, account);
            List<ChatMessage> chatMessageList = sDatabaseHelper.query(condition);
            if (chatMessageList.size() > 0) {
                LogUtil.i(TAG, "chatMessageList length : %s", chatMessageList.get(0).getSenderAccount());
                sChatMessageMap.put(account, chatMessageList);
            }
        }
        String condition = String.format("1=1");
        List<ChatMessage> chatMessageList = sDatabaseHelper.query(condition);
        for (ChatMessage chatMessage : chatMessageList) {
            LogUtil.i(TAG, "chatMessage : %s, %s", chatMessage.getSenderAccount(), chatMessage.getReceiverAccount());
        }
        LogUtil.i(TAG, "chatMessageList length : %s", chatMessageList.size());
        LogUtil.i(TAG, "length : %s", sChatMessageMap.size());
    }

    private static void addChatMessage(ChatMessage chatMessage) {
        if (sDatabaseHelper == null) {
            LogUtil.e(TAG, "mChatMessageDatabaseHelper is null");
            return;
        }
        if (sDatabaseHelper.insert(chatMessage) == -1) {
            LogUtil.e(TAG, "insert error");
        } else {
            LogUtil.i(TAG, "insert success");
        }
    }

    public static void addChatMessage(ChatMessage chatMessage, int account) {
        if (!sChatMessageMap.containsKey(account)) {
            List<ChatMessage> chatMessageList = new ArrayList<>();
            chatMessageList.add(chatMessage);
            sChatMessageMap.put(account, chatMessageList);
        } else {
            List<ChatMessage> chatMessageList = sChatMessageMap.get(account);
            if (chatMessageList != null) {
                chatMessageList.add(chatMessage);
            }
        }
        addChatMessage(chatMessage);
    }

    public static Map<Integer, List<ChatMessage>> getChatMessageMap() {
        return sChatMessageMap;
    }

    public static void setChatMessageMap(Map<Integer, List<ChatMessage>> chatMessageMap) {
        ChatMessageData.sChatMessageMap = chatMessageMap;
    }
}
