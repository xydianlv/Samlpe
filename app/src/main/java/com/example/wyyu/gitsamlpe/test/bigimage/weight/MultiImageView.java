package com.example.wyyu.gitsamlpe.test.bigimage.weight;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.bigimage.ActivityBigImageDetail;
import com.example.wyyu.gitsamlpe.test.bigimage.data.LocationData;
import com.example.wyyu.gitsamlpe.test.bigimage.data.LocationObservable;
import com.example.wyyu.gitsamlpe.test.bigimage.data.OnPositionChangeListener;
import com.example.wyyu.gitsamlpe.test.bigimage.data.PositionObservable;
import java.util.List;

/**
 * Created by wyyu on 2018/5/9.
 **/

public class MultiImageView extends RecyclerView implements OnPositionChangeListener {

    public MultiImageView(@NonNull Context context) {
        super(context);
        initMultiImageView();
    }

    public MultiImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initMultiImageView();
    }

    public MultiImageView(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initMultiImageView();
    }

    private MultiImageAdapter adapter;

    private void initMultiImageView() {
        setPadding(18, 18, 0, 0);

        adapter = new MultiImageAdapter(getContext());

        setLayoutManager(new GridLayoutManager(getContext(), 3));
        setAdapter(adapter);

        PositionObservable.getInstance().attach(this);
    }

    public void setImageIdList(List<Integer> imageIdList) {
        if (imageIdList == null || imageIdList.isEmpty()) {
            return;
        }
        if (imageIdList.size() > 9) {
            imageIdList = imageIdList.subList(0, 9);
        }
        adapter.setImageIdList(imageIdList);
    }

    @Override public void onChange(int position) {
        adapter.notifyItemChanged(position);
    }

    private static class MultiImageAdapter extends Adapter {

        private List<Integer> imageIdList;
        private Context context;

        MultiImageAdapter(Context context) {
            this.context = context;
        }

        void setImageIdList(List<Integer> imageIdList) {
            this.imageIdList = imageIdList;
            notifyDataSetChanged();
        }

        @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ImageViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_multi_image_item, parent, false));
        }

        @Override public void onBindViewHolder(ViewHolder holder, int position) {
            ((ImageViewHolder) holder).setData(imageIdList.get(position), position);

            final ImageView imageView = ((ImageViewHolder) holder).imageView;

            imageView.post(new Runnable() {
                @Override public void run() {
                    int[] number = new int[2];
                    imageView.getLocationOnScreen(number);

                    LocationData locationData =
                        new LocationData(number[0], number[1], number[0] + imageView.getWidth(),
                            number[1] + imageView.getHeight());

                    LocationObservable.getInstance().positionUpdate(locationData);
                }
            });
        }

        @Override public int getItemCount() {
            return imageIdList == null ? 0 : imageIdList.size();
        }

        private class ImageViewHolder extends ViewHolder {

            private MyImageView imageView;

            ImageViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.image_view);
            }

            void setData(int imageId, final int position) {
                imageView.setImageResource(imageId);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View view) {

                        int[] number = new int[2];
                        view.getLocationOnScreen(number);

                        LocationData locationData =
                            new LocationData(number[0], number[1], number[0] + view.getWidth(),
                                number[1] + view.getHeight());

                        int[] imageArray = new int[imageIdList.size()];

                        for (int index = 0; index < imageIdList.size(); index++) {
                            imageArray[index] = imageIdList.get(index);
                        }
                        ActivityBigImageDetail.open(context, locationData, imageArray, position);
                    }
                });
            }
        }
    }
}
