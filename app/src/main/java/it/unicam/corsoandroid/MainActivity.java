package it.unicam.corsoandroid;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import it.unicam.corsoandroid.model.db.TodoItemHelper;
import it.unicam.corsoandroid.model.entities.TodoItem;

public class MainActivity extends AppCompatActivity implements TodoAdapter.ItemListener {

	private TodoAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addItem();
			}
		});

		RecyclerView list = (RecyclerView) findViewById(R.id.list);
		adapter = new TodoAdapter(this);
		adapter.setListener(this);
		list.setAdapter(adapter);
		list.setLayoutManager(new LinearLayoutManager(this));

	}

	@Override
	protected void onResume() {
		super.onResume();
		loadItems();
	}

	private void addItem() {
		final EditText editText = new EditText(this);
		final AlertDialog dialog = createAlertDialog(editText);

		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s == null || s.length() == 0) dialog.getButton(DialogInterface.BUTTON_POSITIVE)
				                                        .setEnabled(false);
				else dialog.getButton(DialogInterface.BUTTON_POSITIVE)
				           .setEnabled(true);
			}

			@Override
			public void afterTextChanged(Editable s) {}
		});

		dialog.show();
		dialog.getButton(DialogInterface.BUTTON_POSITIVE)
		      .setEnabled(false);
	}

	private AlertDialog createAlertDialog(final EditText editText) {
		return new AlertDialog.Builder(this).setView(editText)
		                                    .setPositiveButton("OK",
				                                    new DialogInterface.OnClickListener() {
					                                    @Override
					                                    public void onClick(DialogInterface dialog,
					                                                        int which) {
						                                    final String text = editText.getText()
						                                                                .toString()
						                                                                .trim();
						                                    if (!TextUtils.isEmpty(text))
							                                    createItem(text);
						                                    dialog.dismiss();
					                                    }
				                                    })
		                                    .setNegativeButton("Cancella",
				                                    new DialogInterface.OnClickListener() {
					                                    @Override
					                                    public void onClick(DialogInterface dialog,
					                                                        int which) {
						                                    dialog.dismiss();
					                                    }
				                                    })
		                                    .setCancelable(true)
		                                    .create();
	}

	private void createItem(String text) {
		TodoItemHelper.insert(this, new TodoItem(text));
		loadItems();
	}

	private void loadItems() {
		List<TodoItem> items = TodoItemHelper.query(this);
		adapter.setItems(items);
	}

	@Override
	public void onItemClicked(TodoItem item) {
		TodoDetailActivity.show(this, item.getId());
	}
}
