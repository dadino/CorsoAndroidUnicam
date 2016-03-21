package it.unicam.corsoandroid.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import it.unicam.corsoandroid.model.entities.TodoItem;

public class TodoItemHelper {

	public static long insert(Context context, TodoItem item) {
		TodoDbHelper mDbHelper = new TodoDbHelper(context);
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		final ContentValues values = item.toContentValues();
		final long insert = db.insert(TodoItemColumns.TABLE_NAME, null, values);
		db.close();
		return insert;
	}

	public static List<TodoItem> query(Context context) {
		TodoDbHelper mDbHelper = new TodoDbHelper(context);
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		String[] projection =
				{TodoItemColumns._ID, TodoItemColumns.COLUMN_NAME_TEXT, TodoItemColumns
						.COLUMN_NAME_DONE};

		String sortOrder = TodoItemColumns._ID + " DESC";

		Cursor c = db.query(TodoItemColumns.TABLE_NAME, projection, null, null, null, null,
				sortOrder);

		final List<TodoItem> todoItems = TodoItem.fromCursor(c);
		c.close();
		db.close();
		return todoItems;
	}

	public static int delete(Context context, long itemId) {
		TodoDbHelper mDbHelper = new TodoDbHelper(context);
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		String selection = TodoItemColumns._ID + "= ?";
		String[] selectionArgs = {String.valueOf(itemId)};
		final int delete = db.delete(TodoItemColumns.TABLE_NAME, selection, selectionArgs);
		db.close();
		return delete;
	}

	public static int update(Context context, TodoItem item) {
		TodoDbHelper mDbHelper = new TodoDbHelper(context);
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		String selection = TodoItemColumns._ID + "= ?";
		String[] selectionArgs = {String.valueOf(item.getId())};
		final int update = db.update(TodoItemColumns.TABLE_NAME, item.toContentValues(), selection,
				selectionArgs);
		db.close();
		return update;
	}
}
