package il.ac.shenkar.ronesn;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ItemListAdapter extends BaseAdapter{


    private Context context;
    private LayoutInflater inflater;
    private List<ItemDetails> ItemListModel;
    private List<String> listNames;
    boolean flag;//true-list ,false- names of thr lists

    public ItemListAdapter(android.content.Context context, String listName ) {
        this.context = context;
        if(listName==null)
            flag=false;
        else flag=true;
        adaptList(listName);
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public ItemDetails getItem(int i) {

        if (ItemListModel.size()==0)
            return null;
        return ItemListModel.get(ItemListModel.size()-i-1);
    }

    @Override
     public long getItemId(int i) {
        if(!flag)
            return i;
        return getItem(i).getId();
    }
    @Override
    public int getCount() {
        if (flag)
            return ItemListModel.size();
        else
            return listNames.size();
    }

    @Override
    public boolean isEmpty(){
        return ItemListModel.isEmpty();
    }

    @Override
    public boolean hasStableIds (){
        return true;
    }

    private final View.OnClickListener doneButtonOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            ItemDetails removeItem=getItem((Integer)view.getTag());
            ListSingleton.removeItem(removeItem);
            adaptList(removeItem.getListName());
            notifyDataSetChanged();
        }
    };
    private final View.OnClickListener addToCartOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            ItemDetails item=getItem((Integer)view.getTag());
            item.cart=!item.cart;
            //adaptList(removeItem.getListName());
            notifyDataSetChanged();
        }
    };
    private final View.OnClickListener removeButtonOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {

            ListSingleton.removeList((Integer) view.getTag());
            adaptList(null);
            notifyDataSetChanged();
        }
    };
    private final View.OnClickListener LoadButtonOnClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, new_list.class);
            Bundle b = new Bundle();
            String listName=ListSingleton.getName((Integer) view.getTag());
            b.putString("key", listName);

            intent.putExtras(b);
            context.startActivity(intent);
            notifyDataSetChanged();
        }
    };

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        ItemRowViewHolder holder;

        if (convertView==null){
            holder = new ItemRowViewHolder();
            if(!flag){//names of list
                convertView = this.inflater.inflate(R.layout.list_view, null);
                holder.descriptionTextView = (TextView) convertView.findViewById(R.id.name);
                holder.descriptionTextView.setOnClickListener(LoadButtonOnClickListener);
                holder.doneButton = (Button) convertView.findViewById(R.id.removeButton);
                holder.doneButton.setOnClickListener(removeButtonOnClickListener);
                holder.descriptionTextView.setText(getListName(position));
            }
            else{ //shopping list
                convertView = this.inflater.inflate(R.layout.item_view, null);
                holder.descriptionTextView = (TextView) convertView.findViewById(R.id.name);
                holder.quantityTextView = (TextView) convertView.findViewById(R.id.quantity);
                holder.doneButton = (Button) convertView.findViewById(R.id.doneButton);
                holder.doneButton.setOnClickListener(doneButtonOnClickListener);
                holder.addToCartBtn = (Button) convertView.findViewById(R.id.addToCart);
                holder.addToCartBtn.setOnClickListener(addToCartOnClickListener);

            }
            convertView.setTag(holder);
        }
        else
            holder = (ItemRowViewHolder) convertView.getTag();

        if(flag){
            String str=getItem(position).toString();
            if(getItem(position).cart){
                holder.descriptionTextView.setPaintFlags(holder.descriptionTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
               /*try{holder.addToCartBtn.setBackgroundDrawable(Drawable.createFromPath("R.drawable.remove_from_cart"));}
                catch (Exception e)
                {e.printStackTrace();}*/
            }
            else{
                holder.descriptionTextView.setPaintFlags(holder.descriptionTextView.getPaintFlags() &(~ Paint.STRIKE_THRU_TEXT_FLAG));
            }
            holder.descriptionTextView.setText(str);
            holder.quantityTextView.setText(getItem(position).getQuantity() + " ");
            holder.addToCartBtn.setTag(position);
        }
        else{
            holder.descriptionTextView.setText(getListName(position));

            }
        holder.descriptionTextView.setTag(position);
        holder.doneButton.setTag(position);
        return convertView;
    }

    private String getListName(int position) {
        return listNames.get(listNames.size()-position-1);
    }

    static class ItemRowViewHolder {
        TextView descriptionTextView;
        TextView quantityTextView;
        Button doneButton;
        Button addToCartBtn;
    }
    private void adaptList(String listName)
    {
        ListSingleton lS=ListSingleton.getInstance(context);

        if(listName!=null){
            ItemListModel=lS.getItemsInList(listName);
            Collections.sort(ItemListModel, new Comparator<ItemDetails>() {
                @Override
                public int compare(ItemDetails lhs, ItemDetails rhs) {
                    return lhs.compareTo(rhs);
                }
            });
        }
        else
            this.listNames=lS.getListNames();
    }

}