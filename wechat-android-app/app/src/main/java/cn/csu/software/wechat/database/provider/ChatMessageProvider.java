/*
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.database.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Objects;

import cn.csu.software.wechat.database.content.ChatMessageContent;
import cn.csu.software.wechat.database.helper.ChatMessageDatabaseHelper;
import cn.csu.software.wechat.util.LogUtil;

/**
 * 聊天消息 provider
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-19
 */
public class ChatMessageProvider extends ContentProvider {
    private static final String TAG = ChatMessageProvider.class.getSimpleName();

    private static final int CHAT_MESSAGE = 1;

    private ChatMessageDatabaseHelper mDatabaseHelper;

    public static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMatcher.addURI(ChatMessageContent.AUTHORITIES, "/chat_message", CHAT_MESSAGE);
    }
    @Override
    public boolean onCreate() {
        mDatabaseHelper = ChatMessageDatabaseHelper.getInstance(getContext(), 1);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        LogUtil.d(TAG, "provider query");
        Cursor cursor = null;
        if (mUriMatcher.match(uri) == CHAT_MESSAGE) {
            SQLiteDatabase sqLiteDatabase = mDatabaseHelper.getReadableDatabase();
            cursor = sqLiteDatabase.query(ChatMessageContent.TABLE_NAME, projection, selection,
                    selectionArgs, null, null, sortOrder);
            cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Uri newUri = uri;
        if (mUriMatcher.match(uri) == CHAT_MESSAGE) {
            SQLiteDatabase sqLiteDatabase = mDatabaseHelper.getWritableDatabase();
            long rowId = sqLiteDatabase.insert(ChatMessageContent.TABLE_NAME, null, contentValues);
            if (rowId > 0) {
                newUri = ContentUris.withAppendedId(ChatMessageContent.CONTENT_URI, rowId);
                Objects.requireNonNull(getContext()).getContentResolver().notifyChange(newUri, null);
            }
            sqLiteDatabase.close();
        }
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        if (mUriMatcher.match(uri) == CHAT_MESSAGE) {
            SQLiteDatabase sqLiteDatabase = mDatabaseHelper.getWritableDatabase();
            count = sqLiteDatabase.delete(ChatMessageContent.TABLE_NAME, selection, selectionArgs);
            sqLiteDatabase.close();
        }
        return count;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
