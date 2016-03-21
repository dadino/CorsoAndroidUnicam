package it.unicam.corsoandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Switch;

import it.unicam.corsoandroid.model.db.TodoItemHelper;
import it.unicam.corsoandroid.model.entities.TodoItem;

public class TodoDetailActivity extends AppCompatActivity {

	private long     itemId;
	private TodoItem item;
	private EditText itemText;
	private Switch   itemDone;

	public static void show(Context context, long itemId) {
		Intent intent = new Intent(context, TodoDetailActivity.class);
		intent.putExtra("itemId", itemId);
		context.startActivity(intent);
	}

	public static void create(Context context) {
		Intent intent = new Intent(context, TodoDetailActivity.class);
		intent.putExtra("itemId", -1);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_detail);
		if (getActionBar() != null) {
			getActionBar().setHomeButtonEnabled(true);
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		itemText = (EditText) findViewById(R.id.todo_item_text);
		itemDone = (Switch) findViewById(R.id.todo_item_done);

		if (getIntent() != null && getIntent().getExtras() != null) {
			itemId = getIntent().getExtras()
			                    .getLong("itemId", -1);

			if (itemId == -1) {
				itemId = TodoItemHelper.insert(this, new TodoItem());
			}

			item = TodoItemHelper.query(this, itemId);
			updateUi();
		}
	}

	private void updateUi() {
		if (item == null) return;
		itemText.setText(item.getText());
		itemDone.setChecked(item.isDone());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.item_detail_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_save_item:
				saveItem();
				break;
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				break;
		}
		return true;
	}

	private void updateItem() {
		item.setText(itemText.getText()
		                     .toString());
		item.setDone(itemDone.isChecked());
	}

	private void saveItem() {
		updateItem();
		TodoItemHelper.update(this, item);
		finish();
	}
}
