package org.techtown.image_load;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    ArrayList<String> urls;
    Context context;

    //constructor
    public RecyclerViewAdapter(ArrayList ImgUrl, Context context_)
    {
        this.urls = ImgUrl;
        this.context = context_;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView image;

        public ViewHolder(View v)
        {
            super(v);
            image = v.findViewById(R.id.img);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url= urls.get(getAdapterPosition());
                    Toast.makeText(context,url,Toast.LENGTH_SHORT).show();

                }
            });
        }


        public ImageView getImage(){ return this.image;}
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        //이미지를 로드 api인 Glide 사용
        Glide.with(this.context)
                .load(urls.get(position))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .into(holder.getImage());

    }


    @Override
    public int getItemCount()
    {
        return urls.size();
    }


}