package it.unicam.corsoandroid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder> {

	private final LayoutInflater layoutInflater;
	private       List<TodoItem> items;


	public TodoAdapter(Context context) {
		layoutInflater = LayoutInflater.from(context);
	}

	public void setItems(List<TodoItem> items) {
		this.items = items;
		notifyDataSetChanged();
	}

	@Override
	public TodoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = layoutInflater.inflate(R.layout.item_todo, parent, false);
		return new TodoHolder(itemView);
	}

	@Override
	public void onBindViewHolder(TodoHolder holder, int position) {
		TodoItem item = items.get(position);
		holder.text.setText(item.text);
	}

	@Override
	public int getItemCount() {
		if (items == null) return 0;
		return items.size();
	}

	public class TodoHolder extends RecyclerView.ViewHolder {

		private TextView text;

		public TodoHolder(View itemView) {
			super(itemView);
			text = (TextView) itemView.findViewById(R.id.item_text);
		}
	}
}
