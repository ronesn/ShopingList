package il.ac.shenkar.ronesn;

import android.content.Context;
import android.view.*;
import android.widget.*;


public class ItemListAdapter extends BaseAdapter{


    private Context context;
    private LayoutInflater inflater;
    private ItemListSingleton taskListModel;

    public ItemListAdapter(android.content.Context context) {
        this.context = context;
        this.taskListModel = ItemListSingleton.getInstance();
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public ItemDetails getItem(int i) {
        return taskListModel.getItem(i);
    }

    @Override
     public long getItemId(int i) {
        return getItem(i).getId();
    }
    @Override
    public int getCount() {
        return taskListModel.getCount();
    }

    @Override
    public boolean isEmpty(){
        return taskListModel.isEmpty();
    }

    @Override
    public boolean hasStableIds (){
        return true;
    }

    private final View.OnClickListener doneButtonOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            int position = (Integer) view.getTag();
            taskListModel.removeItem(getItem(position));
            notifyDataSetChanged();
        }
    };

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        TaskRowViewHolder holder;

        if (convertView==null){
            convertView = this.inflater.inflate(R.layout.item_view, null);
            holder = new TaskRowViewHolder();
            holder.descriptionTextView = (TextView) convertView.findViewById(R.id.name);
            holder.quantityTextView = (TextView) convertView.findViewById(R.id.quantity);
            holder.doneButton = (Button) convertView.findViewById(R.id.doneButton);
            holder.doneButton.setOnClickListener(doneButtonOnClickListener);
            convertView.setTag(holder);
        } else {
            holder = (TaskRowViewHolder) convertView.getTag();
        }

        holder.descriptionTextView.setText(getItem(position).toString());
        holder.quantityTextView.setText(getItem(position).getQuantity()+" ");
        holder.doneButton.setTag(position);

        return convertView;
    }

    static class TaskRowViewHolder {
        TextView descriptionTextView;
        TextView quantityTextView;
        Button doneButton;
    }


}