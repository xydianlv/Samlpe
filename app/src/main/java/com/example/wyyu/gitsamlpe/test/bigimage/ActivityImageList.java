package com.example.wyyu.gitsamlpe.test.bigimage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import java.io.Serializable;

/**
 * Created by wyyu on 2018/4/12.
 **/

public class ActivityImageList extends ToolbarActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        initImageList();
    }

    private void initImageList() {

        initToolbar("Image", 0xffffffff, 0xff84919b);

        initRecyclerView();
    }

    private void initRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.fun_test_recycler_view);
        ImageViewAdapter viewAdapter = new ImageViewAdapter(this);

        viewAdapter.setItemCount(120);

        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 50);
        recyclerView.setPadding(6, 0, 0, 0);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(viewAdapter);
    }

    private static class ImageViewAdapter extends RecyclerView.Adapter {

        private Context context;
        private int itemCount;

        ImageViewAdapter(Context context) {
            this.context = context;
        }

        void setItemCount(int itemCount) {
            this.itemCount = itemCount;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ImageViewHolder(
                LayoutInflater.from(context).inflate(R.layout.layout_image_list, parent, false));
        }

        @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ImageViewHolder) holder).setItemViewData();
        }

        @Override public int getItemCount() {
            return itemCount;
        }

        private class ImageViewHolder extends RecyclerView.ViewHolder {

            private ImageView imageView;

            ImageViewHolder(View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.image_list_item);
            }

            void setItemViewData() {

                imageView.setImageResource(R.mipmap.lock_normal);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {

                        // getLocationOnScreen() 获取 view 在屏幕中的 左上角 坐标
                        int[] number = new int[2];
                        v.getLocationOnScreen(number);

                        ImageLocationData locationData =
                            new ImageLocationData(number[0], number[1], number[0] + v.getWidth(),
                                number[1] + v.getHeight());
                        startCheckImageActivity(locationData);
                    }
                });
            }

            void startCheckImageActivity(ImageLocationData locationData) {
                Intent intent = new Intent(context, ActivityBigImage.class);
                intent.putExtra("LocationData", locationData);

                context.startActivity(intent);
            }
        }
    }

    public static class ImageLocationData implements Serializable {

        public int bottom;
        public int right;
        public int left;
        public int top;

        ImageLocationData(int left, int top, int right, int bottom) {

            this.bottom = bottom;
            this.right = right;
            this.left = left;
            this.top = top;
        }
    }
}
