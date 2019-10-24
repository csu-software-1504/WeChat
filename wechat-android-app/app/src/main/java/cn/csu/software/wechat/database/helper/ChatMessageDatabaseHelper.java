/**
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.database.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import cn.csu.software.wechat.bean.ChatMessage;
import cn.csu.software.wechat.util.LogUtil;

/**
 * 聊天消息 database helper
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-19
 */
public class ChatMessageDatabaseHelper extends SQLiteOpenHelper {
    // TODO: 19-10-20 后续数据库代码重构，暂时不加注释
    private static final String TAG = ChatMessageDatabaseHelper.class.getSimpleName();

    public static final String TABLE_NAME = "chat_message";

    private static final String DB_NAME = "chat_message.db";

    private static final int DB_VERSION = 1;

    private static ChatMessageDatabaseHelper mDatabaseHelper;

    private SQLiteDatabase mDatabase;

    private ChatMessageDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private ChatMessageDatabaseHelper(@Nullable Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    public static ChatMessageDatabaseHelper getInstance(Context context, int version) {
        if (version > 0 && mDatabaseHelper == null) {
            mDatabaseHelper = new ChatMessageDatabaseHelper(context, version);
        } else if (mDatabaseHelper == null) {
            mDatabaseHelper = new ChatMessageDatabaseHelper(context);
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
                + "sender_name VARCHAR NOT NULL," + "receiver_name VARCHAR NOT NULL,"
                + "avatar_path VARCHAR NOT NULL," + "chat_message_type INTEGER NOT NULL,"
                + "send_time VARCHAR NOT NULL," + "chat_message_text VARCHAR NOT NULL,"
                + "chat_message_photo_path VARCHAR NOT NULL," + "chat_message_video_path VARCHAR NOT NULL"
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
    public long insert(ChatMessage chatMessage) {
        ArrayList<ChatMessage> chatMessageList = new ArrayList<>();
        chatMessageList.add(chatMessage);
        return insert(chatMessageList);
    }

    // 往该表添加多条记录
    public long insert(ArrayList<ChatMessage> friendChatInfoArray) {
        long result = -1;
        for (int i = 0; i < friendChatInfoArray.size(); i++) {
            ChatMessage chatMessage = friendChatInfoArray.get(i);
            // 如果存在同名记录，则更新记录
            // 注意条件语句的等号后面要用单引号括起来
            // 如果存在同样的手机号码，则更新记录
            // 不存在唯一性重复的记录，则插入新记录
            ContentValues contentValues = new ContentValues();
            contentValues.put("sender_name", chatMessage.getSenderName());
            contentValues.put("receiver_name", chatMessage.getReceiverName());
            contentValues.put("avatar_path", chatMessage.getAvatarPath());
            contentValues.put("chat_message_type", chatMessage.getChatMessageType());
            contentValues.put("send_time", chatMessage.getSendTime());
            contentValues.put("chat_message_text", chatMessage.getChatMessageText());
            contentValues.put("chat_message_photo_path", chatMessage.getChatMessagePhotoPath());
            contentValues.put("chat_message_video_path", chatMessage.getChatMessageVideoPath());
            // 执行插入记录动作，该语句返回插入记录的行号
            result = mDatabase.insert(TABLE_NAME, "", contentValues);
            // 添加成功后返回行号，失败后返回-1
            LogUtil.i(TAG, "insert message: %s , result: %s", chatMessage.getChatMessageText(), result);

            if (result == -1) {
                return result;
            }
        }
        return result;
    }

    // 根据条件更新指定的表记录
    private int update(ChatMessage chatMessage, String condition) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("sender_name", chatMessage.getSenderName());
        contentValues.put("receiver_name", chatMessage.getReceiverName());
        contentValues.put("avatar_path", chatMessage.getAvatarPath());
        contentValues.put("chat_message_type", chatMessage.getChatMessageType());
        contentValues.put("send_time", chatMessage.getSendTime());
        contentValues.put("chat_message_text", chatMessage.getChatMessageText());
        contentValues.put("chat_message_photo_path", chatMessage.getChatMessagePhotoPath());
        contentValues.put("chat_message_video_path", chatMessage.getChatMessageVideoPath());
        // 执行更新记录动作，该语句返回记录更新的数目
        return mDatabase.update(TABLE_NAME, contentValues, condition, null);
    }

    /**
     * sender_name
     * receiver_name
     * avatar_path
     * chat_message_type
     * send_time
     * chat_message_text
     * chat_message_photo_path
     * chat_message_video_path
     */
    // 根据指定条件查询记录，并返回结果数据队列
    public ArrayList<ChatMessage> query(String condition) {
        String sql = String.format("select sender_name,receiver_name,avatar_path,chat_message_type," +
                "send_time,chat_message_text,chat_message_photo_path,chat_message_video_path " +
                "from %s where %s;", TABLE_NAME, condition);
        LogUtil.d(TAG, "query sql: " + sql);
        ArrayList<ChatMessage> chatMessageList = new ArrayList<>();
        // 执行记录查询动作，该语句返回结果集的游标
        Cursor cursor = mDatabase.rawQuery(sql, null);
        // 循环取出游标指向的每条记录
        while (cursor.moveToNext()) {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setSenderName(cursor.getString(0));
            chatMessage.setReceiverName(cursor.getString(1));
            chatMessage.setAvatarPath(cursor.getString(2));
            chatMessage.setChatMessageType(cursor.getInt(3));
            chatMessage.setSendTime(cursor.getString(4));
            chatMessage.setChatMessageText(cursor.getString(5));
            chatMessage.setChatMessagePhotoPath(cursor.getString(6));
            chatMessage.setChatMessageVideoPath(cursor.getString(7));
            chatMessageList.add(chatMessage);
        }
        cursor.close(); // 查询完毕，关闭游标
        return chatMessageList;
    }
}
