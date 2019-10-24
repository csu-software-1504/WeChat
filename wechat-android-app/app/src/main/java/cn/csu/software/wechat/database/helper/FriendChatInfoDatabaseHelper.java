/*
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.database.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import cn.csu.software.wechat.bean.FriendChatInfo;
import cn.csu.software.wechat.util.LogUtil;

import java.util.ArrayList;

/**
 * 好友消息 database helper
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-19
 */
public class FriendChatInfoDatabaseHelper extends SQLiteOpenHelper {
    // TODO: 19-10-20 后续数据库代码重构，暂时不加注释
    private static final String TAG = FriendChatInfoDatabaseHelper.class.getSimpleName();

    public static final String TABLE_NAME = "friend_chat_info";

    private static final String DB_NAME = "friend_chat_info.db";

    private static final int DB_VERSION = 1;

    private static FriendChatInfoDatabaseHelper mDatabaseHelper;

    private SQLiteDatabase mDatabase;

    private FriendChatInfoDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private FriendChatInfoDatabaseHelper(@Nullable Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    public static FriendChatInfoDatabaseHelper getInstance(Context context, int version) {
        if (version > 0 && mDatabaseHelper == null) {
            mDatabaseHelper = new FriendChatInfoDatabaseHelper(context, version);
        } else if (mDatabaseHelper == null) {
            mDatabaseHelper = new FriendChatInfoDatabaseHelper(context);
        }
        return mDatabaseHelper;
    }

    /**
     * 打开数据库的读连接
     *
     * @return SQLiteDatabase
     */
    public SQLiteDatabase openReadLink() {
        if (mDatabase == null || !mDatabase.isOpen()) {
            mDatabase = mDatabaseHelper.getReadableDatabase();
        }
        return mDatabase;
    }

    /**
     * 打开数据库的写连接
     *
     * @return SQLiteDatabase
     */
    public SQLiteDatabase openWriteLink() {
        if (mDatabase == null || !mDatabase.isOpen()) {
            mDatabase = mDatabaseHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    /**
     * 关闭数据库连接
     */
    public void closeLink() {
        if (mDatabase != null && mDatabase.isOpen()) {
            mDatabase.close();
            mDatabase = null;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        LogUtil.i(TAG, "onCreate");
        String drop_sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        LogUtil.i(TAG, "drop_sql:" + drop_sql);
        sqLiteDatabase.execSQL(drop_sql);
        String create_sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + "_id INTEGER PRIMARY KEY  AUTOINCREMENT NOT NULL,"
                + "friend_name VARCHAR NOT NULL," + "friend_avatar_path VARCHAR NOT NULL,"
                + "friend_message VARCHAR NOT NULL"
                + ");";
        LogUtil.d(TAG, "create_sql:" + create_sql);
        sqLiteDatabase.execSQL(create_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        LogUtil.d(TAG, "onUpgrade oldVersion=" + oldVersion + ", newVersion=" + newVersion);
        if (newVersion > 1) {
            //Android的ALTER命令不支持一次添加多列，只能分多次添加
            String alter_sql = "ALTER TABLE " + TABLE_NAME;
            LogUtil.d(TAG, "alter_sql:" + alter_sql);
            sqLiteDatabase.execSQL(alter_sql);
            alter_sql = "ALTER TABLE " + TABLE_NAME;
            LogUtil.d(TAG, "alter_sql:" + alter_sql);
            sqLiteDatabase.execSQL(alter_sql);
        }
    }

    // 根据指定条件删除表记录
    public int delete(String condition) {
        // 执行删除记录动作，该语句返回删除记录的数目
        return mDatabase.delete(TABLE_NAME, condition, null);
    }

    // 删除该表的所有记录
    public int deleteAll() {
        // 执行删除记录动作，该语句返回删除记录的数目
        return mDatabase.delete(TABLE_NAME, "1=1", null);
    }

    // 往该表添加一条记录
    public long insert(FriendChatInfo friendChatInfo) {
        ArrayList<FriendChatInfo> friendChatInfoList = new ArrayList<>();
        friendChatInfoList.add(friendChatInfo);
        return insert(friendChatInfoList);
    }

    // 往该表添加多条记录
    public long insert(ArrayList<FriendChatInfo> friendChatInfoArray) {
        long result = -1;
        for (int i = 0; i < friendChatInfoArray.size(); i++) {
            FriendChatInfo friendChatInfo = friendChatInfoArray.get(i);
            // 如果存在同名记录，则更新记录
            // 注意条件语句的等号后面要用单引号括起来
            // 如果存在同样的手机号码，则更新记录
            // 不存在唯一性重复的记录，则插入新记录
            ContentValues contentValues = new ContentValues();
            contentValues.put("friend_name", friendChatInfo.getFriendName());
            contentValues.put("friend_avatar_path", friendChatInfo.getFriendAvatarPath());
            contentValues.put("friend_message", friendChatInfo.getFriendLastMessage());
            // 执行插入记录动作，该语句返回插入记录的行号
            result = mDatabase.insert(TABLE_NAME, "", contentValues);
            // 添加成功后返回行号，失败后返回-1
            LogUtil.i(TAG, "insert message: %s , result: %s", friendChatInfo.getFriendLastMessage(), result);

            if (result == -1) {
                return result;
            }
        }
        return result;
    }

    // 根据条件更新指定的表记录
    private int update(FriendChatInfo friendChatInfo, String condition) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("friend_name", friendChatInfo.getFriendName());
        contentValues.put("friend_avatar_path", friendChatInfo.getFriendAvatarPath());
        contentValues.put("friend_message", friendChatInfo.getFriendLastMessage());
        // 执行更新记录动作，该语句返回记录更新的数目
        return mDatabase.update(TABLE_NAME, contentValues, condition, null);
    }

    // 根据指定条件查询记录，并返回结果数据队列
    public ArrayList<FriendChatInfo> query(String condition) {
        String sql = String.format("select friend_name,friend_avatar_path,friend_message " +
                "from %s where %s;", TABLE_NAME, condition);
        LogUtil.d(TAG, "query sql: " + sql);
        ArrayList<FriendChatInfo> friendChatInfoList = new ArrayList<>();
        // 执行记录查询动作，该语句返回结果集的游标
        Cursor cursor = mDatabase.rawQuery(sql, null);
        // 循环取出游标指向的每条记录
        while (cursor.moveToNext()) {
            FriendChatInfo friendChatInfo = new FriendChatInfo();
            friendChatInfo.setFriendName(cursor.getString(0));
            friendChatInfo.setFriendAvatarPath(cursor.getString(1));
            friendChatInfo.setFriendLastMessage(cursor.getString(2));
            friendChatInfoList.add(friendChatInfo);
        }
        cursor.close(); // 查询完毕，关闭游标
        return friendChatInfoList;
    }
}
