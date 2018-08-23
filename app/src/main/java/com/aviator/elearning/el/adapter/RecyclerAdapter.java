package com.aviator.elearning.el.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aviator.elearning.R;
import com.aviator.elearning.el.models.MyModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Aviator on 2/15/2018.
 */
@SuppressWarnings("ALL")

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {

    View.OnClickListener listener;
    Context context;
    List<MyModel> modelList;

    public RecyclerAdapter(View.OnClickListener listener, Context context, List<MyModel> modelList) {
        this.listener = listener;
        this.context = context;
        this.modelList = modelList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_player_model, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        MyModel myModel=modelList.get(position);
        // VideoModel model = ListDataGenerater.datas.get(position);
        holder.txtDesc.setText(myModel.getDescription());//("Just Video " + position);
        holder.txtLikes.setText(myModel.getNumLikes());
        Picasso.with(context).load(R.mipmap.book)//model.coverImage
                .config(Bitmap.Config.RGB_565)
                .placeholder(R.drawable.shape_place_holder)
                .into(holder.cover);
        // model.position = position;
        myModel.position=position;
        holder.playArea.setTag(myModel);
        holder.playArea.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return modelList.size();//ListDataGenerater.datas.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView txtDesc,txtLikes;
        public ImageView cover;
        public View playArea;
        public AppCompatImageView imgLike,imgShare;

        public ItemViewHolder(View itemView) {
            super(itemView);
            txtDesc = (TextView) itemView.findViewById(R.id.txtDesc);
            txtLikes= (TextView) itemView.findViewById(R.id.txtLikes);
            cover = (ImageView) itemView.findViewById(R.id.img_cover);
            playArea = itemView.findViewById(R.id.layout_play_area);
            imgLike= (AppCompatImageView) itemView.findViewById(R.id.imgLike);
            imgShare=(AppCompatImageView) itemView.findViewById(R.id.imgShare);
        }
    }
}