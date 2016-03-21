package it.unicam.corsoandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import it.unicam.corsoandroid.model.db.TodoItemHelper;
import it.unicam.corsoandroid.model.entities.TodoItem;

public class TodoDetailActivity extends AppCompatActivity {

	private long     itemId;
	private TodoItem item;
	private EditText itemText;

	public static void show(Context context, long itemId) {
		Intent intent = new Intent(context, TodoDetailActivity.class);
		intent.putExtra("itemId", itemId);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_detail);

		itemText = (EditText) findViewById(R.id.todo_item_text);

		if (getIntent() != null && getIntent().getExtras() != null) {
			itemId = getIntent().getExtras()
			                    .getLong("itemId");
			item = TodoItemHelper.query(this, itemId);
			updateUi();
		}
	}

	private void updateUi() {
		if (item == null) return;
		itemText.setText(item.getText());
	}
}
