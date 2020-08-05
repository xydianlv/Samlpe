package com.example.wyyu.gitsamlpe.test.bigimage;

import android.app.Activity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.bigimage.data.LocationData;
import com.example.wyyu.gitsamlpe.test.bigimage.data.OnPositionChangeListener;
import com.example.wyyu.gitsamlpe.test.bigimage.data.PositionObservable;

/**
 * Created by wyyu on 2018/5/7.
 **/

public class ActivityImageListFun extends ToolbarActivity implements OnPositionChangeListener {

    private static final int[] IMAGE_ARRAY = new int[] {
        R.mipmap.image_test_1, R.mipmap.image_test_2, R.mipmap.image_test_3, R.mipmap.image_test_4,
        R.mipmap.image_test_5
    };

    private RecyclerView recyclerView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list_fun);

        PositionObservable.getInstance().attach(this);
        initImageListFun();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        PositionObservable.getInstance().detach(this);
    }

    @Override public void onChange(final int position) {

        recyclerView.scrollToPosition(position);
    }

    private void initImageListFun() {
        initBasicView();
        initRecyclerView();
    }

    private void initBasicView() {

        initToolbar("Image", 0xffffffff, 0xff84919b);

    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.image_fun_list);
        recyclerView.setAdapter(new ImageListAdapter(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(null);
    }

    private static class ImageListAdapter extends RecyclerView.Adapter {

        private Activity activity;

        ImageListAdapter(Activity activity) {
            this.activity = activity;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ImageViewHolder(LayoutInflater.from(activity)
                .inflate(R.layout.layout_fun_image_list_item, parent, false));
        }

        @Override public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            ((ImageViewHolder) holder).setItemData(IMAGE_ARRAY[position],
                holder.getAdapterPosition());
        }

        @Override public int getItemCount() {
            return IMAGE_ARRAY.length;
        }

        private class ImageViewHolder extends RecyclerView.ViewHolder {

            private ImageView image;

            ImageViewHolder(View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.fun_list_item_image);
            }

            void setItemData(final int imageId, final int position) {
                image.setImageResource(imageId);

                image.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View view) {

                        int[] number = new int[2];
                        view.getLocationOnScreen(number);

                        LocationData locationData =
                            new LocationData(number[0], number[1], number[0] + view.getWidth(),
                                number[1] + view.getHeight());

                        ActivityBigImageDetail.open(activity, locationData, IMAGE_ARRAY, position);
                    }
                });
            }
        }
    }
}
