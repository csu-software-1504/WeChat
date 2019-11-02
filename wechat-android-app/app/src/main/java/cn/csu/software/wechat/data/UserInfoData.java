package cn.csu.software.wechat.data;

import android.content.Context;

import java.io.File;
import java.util.List;

import cn.csu.software.wechat.entity.UserInfo;
import cn.csu.software.wechat.constant.ConstantData;
import cn.csu.software.wechat.database.helper.UserInfoDatabaseHelper;
import cn.csu.software.wechat.util.LogUtil;

public class UserInfoData {
    private static final String TAG = UserInfoData.class.getSimpleName();

    private static UserInfoDatabaseHelper sDatabaseHelper;

    private static List<UserInfo> sUserInfoList;

    public static void initDatabaseHelper(Context context) {
        sDatabaseHelper = UserInfoDatabaseHelper.getInstance(context,
                ConstantData.DATABASE_CREATE_VISION_SECOND_TIME);
        sDatabaseHelper.openReadLink();
        sDatabaseHelper.openWriteLink();
    }

    public static void queryAllFriendChatInfo() {
        if (sDatabaseHelper == null) {
            LogUtil.e(TAG, "mChatMessageDatabaseHelper is null");
            return;
        }
        sUserInfoList = sDatabaseHelper.query("1=1");
        if (sUserInfoList.size() == 0) {
            LogUtil.i(TAG, "init user info");
            for (int i = 0; i < ConstantData.EXAMPLE_FRIEND_NAME.length; i++) {
                UserInfo userInfo = new UserInfo();
                userInfo.setAccount(ConstantData.EXAMPLE_FRIEND_NAME[i].hashCode());
                userInfo.setUsername(ConstantData.EXAMPLE_FRIEND_NAME[i]);
                userInfo.setRemark(ConstantData.EXAMPLE_FRIEND_NAME[i]);
                userInfo.setEmail(ConstantData.EXAMPLE_FRIEND_NAME[i].hashCode() + "@qq.com");
                userInfo.setSex(0);
                userInfo.setAvatarPath(ConstantData.FILES_DIR + File.separator
                    + ConstantData.PHOTO_DIRECTORY + File.separator + ConstantData.AVATAR_DIRECTORY
                    + File.separator + ConstantData.EXAMPLE_AVATAR_NAME[i] + ConstantData.EXAMPLE_EXTENSION_NAME);
                sUserInfoList.add(userInfo);
                sDatabaseHelper.insert(userInfo);
            }
        }
    }

    private static void addFriendChatInfo(UserInfo userInfo) {
        if (sDatabaseHelper == null) {
            LogUtil.e(TAG, "mChatMessageDatabaseHelper is null");
            return;
        }
        sDatabaseHelper.insert(userInfo);
    }

    private static void updateFriendChatInfo(UserInfo userInfo) {
        if (sDatabaseHelper == null) {
            LogUtil.e(TAG, "mChatMessageDatabaseHelper is null");
            return;
        }
        if (sDatabaseHelper.update(userInfo) == -1) {
            LogUtil.e(TAG, "update error");
        }
    }

    public static List<UserInfo> getChatMessagesList() {
        return sUserInfoList;
    }

    public static void setChatMessagesList(List<UserInfo> chatMessagesList) {
        UserInfoData.sUserInfoList = chatMessagesList;
    }
}
