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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private List<TodoItem> items = new ArrayList<>();
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
		list.setAdapter(adapter);

		list.setLayoutManager(new LinearLayoutManager(this));
	}

	//private List<TodoItem> fillList(){
	//	List<TodoItem> items = new ArrayList<>();
	//	for (int i = 0; i<100; i++){
	//		items.add(new TodoItem("Oggetto " + i));
	//	}
	//
	//	return items;
	//}

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
		items.add(new TodoItem(text));
		adapter.setItems(items);
	}
}
