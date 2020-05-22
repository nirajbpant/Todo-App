package com.example.todomvvm.database;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TaskDao_Impl implements TaskDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TaskEntry> __insertionAdapterOfTaskEntry;

  private final EntityDeletionOrUpdateAdapter<TaskEntry> __deletionAdapterOfTaskEntry;

  private final EntityDeletionOrUpdateAdapter<TaskEntry> __updateAdapterOfTaskEntry;

  public TaskDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTaskEntry = new EntityInsertionAdapter<TaskEntry>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `task` (`id`,`description`,`priority`,`updated_at`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TaskEntry value) {
        stmt.bindLong(1, value.getId());
        if (value.getDescription() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getDescription());
        }
        stmt.bindLong(3, value.getPriority());
        final Long _tmp;
        _tmp = DateConverter.toTimeStamp(value.getUpdatedAt());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, _tmp);
        }
      }
    };
    this.__deletionAdapterOfTaskEntry = new EntityDeletionOrUpdateAdapter<TaskEntry>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `task` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TaskEntry value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfTaskEntry = new EntityDeletionOrUpdateAdapter<TaskEntry>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `task` SET `id` = ?,`description` = ?,`priority` = ?,`updated_at` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TaskEntry value) {
        stmt.bindLong(1, value.getId());
        if (value.getDescription() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getDescription());
        }
        stmt.bindLong(3, value.getPriority());
        final Long _tmp;
        _tmp = DateConverter.toTimeStamp(value.getUpdatedAt());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, _tmp);
        }
        stmt.bindLong(5, value.getId());
      }
    };
  }

  @Override
  public void insertTask(final TaskEntry task) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTaskEntry.insert(task);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteTask(final TaskEntry task) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfTaskEntry.handle(task);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final TaskEntry task) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfTaskEntry.handle(task);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<TaskEntry>> loadAllTasks() {
    final String _sql = "select * from task order by priority";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"task"}, false, new Callable<List<TaskEntry>>() {
      @Override
      public List<TaskEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<TaskEntry> _result = new ArrayList<TaskEntry>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final TaskEntry _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final Date _tmpUpdatedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            _tmpUpdatedAt = DateConverter.toDate(_tmp);
            _item = new TaskEntry(_tmpId,_tmpDescription,_tmpPriority,_tmpUpdatedAt);
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

  @Override
  public LiveData<TaskEntry> loadTAskById(final int taskId) {
    final String _sql = "Select * from task where id =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, taskId);
    return __db.getInvalidationTracker().createLiveData(new String[]{"task"}, false, new Callable<TaskEntry>() {
      @Override
      public TaskEntry call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final TaskEntry _result;
          if(_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            final Date _tmpUpdatedAt;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            _tmpUpdatedAt = DateConverter.toDate(_tmp);
            _result = new TaskEntry(_tmpId,_tmpDescription,_tmpPriority,_tmpUpdatedAt);
          } else {
            _result = null;
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
}
