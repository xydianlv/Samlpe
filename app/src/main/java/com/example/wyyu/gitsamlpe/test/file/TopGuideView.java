package com.example.wyyu.gitsamlpe.test.file;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyyu on 2018/3/8.
 **/

public class TopGuideView extends LinearLayout {

    private GuideViewAdapter viewAdapter;
    private LocalFileBean headFileBean;

    private RecyclerView recyclerView;
    private TextView rootName;

    public TopGuideView(Context context) {
        super(context);
        initTopGuideView();
    }

    public TopGuideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initTopGuideView();
    }

    public TopGuideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTopGuideView();
    }

    private void initTopGuideView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_top_guide_view, this);

        initHeadGuideInfo();

        initRecyclerView();
    }

    private void initHeadGuideInfo() {

        rootName = findViewById(R.id.local_file_root);

        findViewById(R.id.head_click_view).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (guideItemClickListener != null) guideItemClickListener.onClick(headFileBean);
            }
        });
    }

    private void initRecyclerView() {

        recyclerView = findViewById(R.id.top_guide_index);

        viewAdapter = new GuideViewAdapter(getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), HORIZONTAL, false));

        recyclerView.setAdapter(viewAdapter);

    }

    void setTopGuideList(List<LocalFileBean> localFileBeanList) {

        if (localFileBeanList == null || localFileBeanList.isEmpty()) return;

        List<LocalFileBean> fileBeanList = new ArrayList<>();

        for (int index = 0; index < localFileBeanList.size(); index++) {
            if (index == 0) {
                rootName.setText(localFileBeanList.get(index).getFileName());
                headFileBean = localFileBeanList.get(0);
            } else {
                fileBeanList.add(localFileBeanList.get(index));
            }
        }

        viewAdapter.setTextList(fileBeanList);
        recyclerView.scrollToPosition(fileBeanList.size() - 1);
    }

    private static class GuideViewAdapter extends RecyclerView.Adapter {

        private OnGuideItemClickListener guideItemClickListener;

        private List<LocalFileBean> fileBeanList;
        private Context context;

        GuideViewAdapter(Context context) {
            this.context = context;
        }

        void setTextList(List<LocalFileBean> fileBeanList) {
            this.fileBeanList = fileBeanList;
            notifyDataSetChanged();
        }

        void setGuideItemClickListener(OnGuideItemClickListener guideItemClickListener) {
            this.guideItemClickListener = guideItemClickListener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GuideViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_top_guide_item, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final LocalFileBean fileBean = fileBeanList.get(position);
            ((GuideViewHolder) holder).setItemData(fileBean);

            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (guideItemClickListener != null) guideItemClickListener.onClick(fileBean);
                }
            });
        }

        @Override
        public int getItemCount() {
            return fileBeanList == null ? 0 : fileBeanList.size();
        }

        private class GuideViewHolder extends RecyclerView.ViewHolder {

            private TextView textView;

            GuideViewHolder(View itemView) {
                super(itemView);

                textView = itemView.findViewById(R.id.guide_item_text);
            }

            void setItemData(LocalFileBean fileBean) {
                textView.setText(fileBean.getFileName());
            }
        }
    }


    private OnGuideItemClickListener guideItemClickListener;

    void setOnGuideItemClickListener(OnGuideItemClickListener guideItemClickListener) {
        viewAdapter.setGuideItemClickListener(guideItemClickListener);
        this.guideItemClickListener = guideItemClickListener;
    }

    interface OnGuideItemClickListener {
        boolean onClick(LocalFileBean fileBean);
    }
}
