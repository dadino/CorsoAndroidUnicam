package it.unicam.corsoandroid;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import it.unicam.corsoandroid.model.entities.TodoItem;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder> {

	private final LayoutInflater layoutInflater;
	private       List<TodoItem> items;
	private       ItemListener   listener;


	public TodoAdapter(Context context) {
		layoutInflater = LayoutInflater.from(context);
		setHasStableIds(true);
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
	public void onBindViewHolder(TodoHolder holder, final int position) {
		TodoItem item = items.get(position);
		holder.setClickListener(new ClickListener() {
			@Override
			public void onClick(View v, int position, boolean isLongClick) {
				if (listener != null) listener.onItemClicked(getItem(position));
			}

			@Override
			public void onDoneCheckChanged(int position, boolean done) {
				if (listener != null) listener.onItemDoneClicked(getItem(position), done);
			}
		});

		if (item.isDone()) {
			holder.text.setText(item.getText());
			holder.text.setPaintFlags(holder.text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		} else {
			holder.text.setText(item.getText());
		}

		holder.done.setChecked(item.isDone());
	}

	@Override
	public long getItemId(int position) {
		final TodoItem item = getItem(position);
		return item != null ? item.getId() : 0;
	}

	@Override
	public int getItemCount() {
		if (items == null) return 0;
		return items.size();
	}

	private TodoItem getItem(int position) {
		return items != null ? items.get(position) : null;
	}

	public void setListener(ItemListener listener) {
		this.listener = listener;
	}

	public interface ItemListener {

		void onItemClicked(TodoItem item);
		void onItemDoneClicked(TodoItem item, boolean done);
	}

	public interface ClickListener {

		void onClick(View v, int position, boolean isLongClick);
		void onDoneCheckChanged(int position, boolean done);
	}


	public class TodoHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
			View.OnLongClickListener,
			CompoundButton.OnCheckedChangeListener {

		private TextView      text;
		private CheckBox      done;
		private ClickListener clickListener;

		public TodoHolder(View itemView) {
			super(itemView);
			text = (TextView) itemView.findViewById(R.id.item_text);
			done = (CheckBox) itemView.findViewById(R.id.item_done);

			itemView.setOnClickListener(this);
			itemView.setOnLongClickListener(this);

			done.setOnCheckedChangeListener(this);
		}

		public void setClickListener(ClickListener clickListener) {
			this.clickListener = clickListener;
		}

		@Override
		public void onClick(View v) {
			if (clickListener != null) clickListener.onClick(v, getLayoutPosition(), false);
		}

		@Override
		public boolean onLongClick(View v) {
			if (clickListener != null) clickListener.onClick(v, getLayoutPosition(), true);
			return true;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (clickListener != null) clickListener.onDoneCheckChanged(getLayoutPosition(),
					isChecked);
		}
	}
}
