package it.unicam.corsoandroid.model.db;

import android.provider.BaseColumns;

public class TodoItemColumns implements BaseColumns {

	public static final  String TABLE_NAME         = "todo_item";
	public static final  String COLUMN_NAME_TEXT   = "todo_text";
	public static final  String COLUMN_NAME_DONE   = "todo_done";
	public static final  String SQL_DELETE_ENTRIES =
			"DROP TABLE IF EXISTS " + TodoItemColumns.TABLE_NAME;
	private static final String TEXT_TYPE          = " TEXT";
	private static final String TEXT_INT           = " INTEGER";
	private static final String COMMA_SEP          = ",";
	public static final  String CREATE_TABLE       = "CREATE TABLE " + TABLE_NAME + " " +
	                                           "(" +
	                                           _ID + " INTEGER PRIMARY KEY," +
	                                           COLUMN_NAME_TEXT + TEXT_TYPE +
	                                           COMMA_SEP +
	                                           COLUMN_NAME_DONE + TEXT_INT +
	                                           " )";
}