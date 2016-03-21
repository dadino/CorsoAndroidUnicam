package it.unicam.corsoandroid.model.entities;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import it.unicam.corsoandroid.model.db.TodoItemColumns;

public class TodoItem {

	private long id = -1;
	private String  text;
	private boolean done;

	public TodoItem(String text) {
		this.text = text;
	}

	public TodoItem() {
	}

	private static TodoItem fromCursorSingle(Cursor c) {
		TodoItem item = new TodoItem();
		item.setId(c.getLong(c.getColumnIndex(TodoItemColumns._ID)));
		item.setText(c.getString(c.getColumnIndex(TodoItemColumns.COLUMN_NAME_TEXT)));
		item.setDone(c.getInt(c.getColumnIndex(TodoItemColumns.COLUMN_NAME_DONE)) == 1);
		return item;
	}

	public static List<TodoItem> fromCursor(Cursor c) {
		List<TodoItem> items = new ArrayList<>();
		if (c == null) return null;

		c.moveToPosition(-1);
		while (c.moveToNext()) {
			items.add(fromCursorSingle(c));
		}
		return items;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public ContentValues toContentValues() {
		ContentValues values = new ContentValues();
		if (getId() >= 0) values.put(TodoItemColumns._ID, getId());
		values.put(TodoItemColumns.COLUMN_NAME_TEXT, getText());
		values.put(TodoItemColumns.COLUMN_NAME_DONE, isDone() ? 1 : 0);
		return values;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
