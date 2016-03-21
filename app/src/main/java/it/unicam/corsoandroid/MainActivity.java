package it.unicam.corsoandroid;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
		TodoDetailActivity.create(this);
	}


	private void loadItems() {
		List<TodoItem> items = TodoItemHelper.query(this);
		adapter.setItems(items);
	}

	@Override
	public void onItemClicked(TodoItem item) {
		TodoDetailActivity.show(this, item.getId());
	}

	@Override
	public void onItemDoneClicked(TodoItem item, boolean done) {
		if (item.isDone() == done) return;
		item.setDone(done);
		TodoItemHelper.update(this, item);
		loadItems();
	}
}
