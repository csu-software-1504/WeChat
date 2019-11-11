package cn.csu.software.wechat.data;

import android.content.Context;

import java.util.List;

import cn.csu.software.wechat.entity.UserInfo;
import cn.csu.software.wechat.constant.ConstantData;
import cn.csu.software.wechat.database.content.FriendChatInfoContent;
import cn.csu.software.wechat.database.helper.FriendChatInfoDatabaseHelper;
import cn.csu.software.wechat.util.LogUtil;

public class FriendChatInfoData {
    private static final String TAG = FriendChatInfoData.class.getSimpleName();

    private static FriendChatInfoDatabaseHelper sDatabaseHelper;

    private static List<UserInfo> sUserInfoList;

    public static void initDatabaseHelper(Context context) {
        sDatabaseHelper = FriendChatInfoDatabaseHelper.getInstance(context,
                ConstantData.DATABASE_CREATE_VISION_SECOND_TIME);
        sDatabaseHelper.openReadLink();
        sDatabaseHelper.openWriteLink();
    }

    public static void queryAllFriendChatInfo() {
        if (sDatabaseHelper == null) {
            LogUtil.e(TAG, "mChatMessageDatabaseHelper is null");
            return;
        }
        String condition = String.format("1=1 order by %s %s",
            FriendChatInfoContent.LAST_MESSAGE_SEND_TIME, FriendChatInfoContent.SORT_ORDER_DESC);
        sUserInfoList = sDatabaseHelper.query(condition);
    }

    private static void addFriendChatInfo(UserInfo userInfo) {
        if (sDatabaseHelper == null) {
            LogUtil.e(TAG, "mChatMessageDatabaseHelper is null");
            return;
        }
        if (sDatabaseHelper.insert(userInfo) == -1) {
            LogUtil.e(TAG, "update error");
        }
        LogUtil.i(TAG, "update success");
    }

    private static void updateFriendChatInfo(UserInfo userInfo) {
        if (sDatabaseHelper == null) {
            LogUtil.e(TAG, "mChatMessageDatabaseHelper is null");
            return;
        }
        if (sDatabaseHelper.update(userInfo) == -1) {
            LogUtil.e(TAG, "update error");
        }
        LogUtil.i(TAG, "update success");
    }

    public static void addUserInfo(UserInfo userInfo) {
        boolean isContain = false;
        for (int i = 0; i < sUserInfoList.size(); i++) {
            if (sUserInfoList.get(i).getAccount() == userInfo.getAccount()) {
                sUserInfoList.remove(i);
                isContain = true;
                break;
            }
        }
        sUserInfoList.add(0, userInfo);
        if (isContain) {
            updateFriendChatInfo(userInfo);
        } else {
            addFriendChatInfo(userInfo);
        }
    }

    public static List<UserInfo> getUserInfoList() {
        return sUserInfoList;
    }

    public static void setUserInfoList(List<UserInfo> userInfoList) {
        FriendChatInfoData.sUserInfoList = userInfoList;
    }
}
