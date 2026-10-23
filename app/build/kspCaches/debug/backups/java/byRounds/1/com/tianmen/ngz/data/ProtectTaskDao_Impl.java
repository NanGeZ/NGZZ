package com.tianmen.ngz.data;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ProtectTaskDao_Impl implements ProtectTaskDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ProtectTask> __insertionAdapterOfProtectTask;

  public ProtectTaskDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProtectTask = new EntityInsertionAdapter<ProtectTask>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `ProtectTask` (`id`,`inputPath`,`outputPath`,`packageName`,`status`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProtectTask entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getInputPath());
        statement.bindString(3, entity.getOutputPath());
        statement.bindString(4, entity.getPackageName());
        statement.bindString(5, entity.getStatus());
        statement.bindLong(6, entity.getCreatedAt());
      }
    };
  }

  @Override
  public Object insert(final ProtectTask task, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfProtectTask.insertAndReturnId(task);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ProtectTask>> observeAll() {
    final String _sql = "SELECT * FROM ProtectTask ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"ProtectTask"}, new Callable<List<ProtectTask>>() {
      @Override
      @NonNull
      public List<ProtectTask> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfInputPath = CursorUtil.getColumnIndexOrThrow(_cursor, "inputPath");
          final int _cursorIndexOfOutputPath = CursorUtil.getColumnIndexOrThrow(_cursor, "outputPath");
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<ProtectTask> _result = new ArrayList<ProtectTask>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProtectTask _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpInputPath;
            _tmpInputPath = _cursor.getString(_cursorIndexOfInputPath);
            final String _tmpOutputPath;
            _tmpOutputPath = _cursor.getString(_cursorIndexOfOutputPath);
            final String _tmpPackageName;
            _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new ProtectTask(_tmpId,_tmpInputPath,_tmpOutputPath,_tmpPackageName,_tmpStatus,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
