package com.yastart.papaya.adapters;

import android.content.Context;
import android.view.LayoutInflater;

import com.yastart.papaya.Model.Book;

import java.util.List;

/**
 * Created by Egor on 04.04.2015.
 */
public class ProfileBooksListAdapter {

        Context context;
        protected List<Book> items;
        LayoutInflater inflater;
/*
        public ListDialogMenuAdapter(Context context, List<DialogMenuItem> items){
            this.context = context;
            this.items = items;
            this.inflater = LayoutInflater.from(context);
        }

        public int getCount(){
            return items.size();
        }
        public DialogMenuItem getItem(int position){
            return items.get(position);
        }
        public long getItemId(int position){
            return items.get(position).getDrawableId();
        }

        private class ViewHolder{
            TextView txtTitle;
            ImageView image;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = this.inflater.inflate(R.layout.dialog_list_item, parent, false);
                holder.txtTitle = (TextView)convertView.findViewById(R.id.msgDialogMenu);
                holder.image = (ImageView)convertView.findViewById(R.id.imgDialogMenu);

                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder)convertView.getTag();
            }
            DialogMenuItem item = items.get(position);
            holder.txtTitle.setText(item.getTitle());

            holder.image.setImageResource(item.getDrawableId());

            return convertView;
        }*/
}
