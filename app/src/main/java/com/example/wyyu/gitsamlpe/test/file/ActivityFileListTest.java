package com.example.wyyu.gitsamlpe.test.file;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.CustomFrameLayout;
import com.example.wyyu.gitsamlpe.framework.activity.FullScreenActivity;
import com.example.wyyu.gitsamlpe.util.file.StorageUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wyyu on 2018/3/8.
 **/

public class ActivityFileListTest extends FullScreenActivity
    implements PresenterGetFile.ILocalFileListView {

    private static final String TITLE = "吾即大灾变！！！";

    private CustomFrameLayout customFrameLayout;
    private TopGuideView topGuideView;

    private PresenterGetFile presenterGetFile;
    private FileViewAdapter viewAdapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list_test);

        initFileListTest();
    }

    private void initFileListTest() {

        initToolBar();

        initCustomLayout();

        initGuideView();

        initRecyclerView();

        initPresenter();

        initBottomView();
    }

    private void initToolBar() {

        Toolbar toolbar = findViewById(R.id.file_list_toolbar);

        toolbar.setBackgroundColor(0xff84919b);

        toolbar.setTitleTextColor(0xffffffff);

        toolbar.setTitle(TITLE);
    }

    private void initCustomLayout() {

        customFrameLayout = findViewById(R.id.file_list_custom);

        customFrameLayout.setList(
            new int[] { R.id.file_list_content, R.id.file_list_empty, R.id.file_list_loading });
    }

    private void initGuideView() {

        topGuideView = findViewById(R.id.file_list_guide_view);

        topGuideView.setOnGuideItemClickListener(new TopGuideView.OnGuideItemClickListener() {
            @Override public boolean onClick(LocalFileBean fileBean) {
                if (presenterGetFile != null) {
                    presenterGetFile.backLocalFolder(fileBean);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    private void initRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.file_list_view);
        viewAdapter = new FileViewAdapter(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(viewAdapter);
    }

    private void initBottomView() {

        findViewById(R.id.file_search).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

            }
        });

        findViewById(R.id.file_sort).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                showSortDialog();
            }
        });

        final TextView textView = findViewById(R.id.file_function);
        textView.setText(presenterGetFile.isShowHidden() ? "隐藏" : "显示");

        textView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (presenterGetFile.isShowHidden()) {
                    presenterGetFile.showHiddenFile(false);
                    textView.setText("显示");
                } else {
                    presenterGetFile.showHiddenFile(true);
                    textView.setText("隐藏");
                }
            }
        });
    }

    private void showSortDialog() {

        String[] strings = new String[] { "按时间排序", "按名称排序", "按大小排序" };

        new AlertDialog.Builder(this).setTitle("选择排序方式")
            .setSingleChoiceItems(strings, 0, new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {

                }
            })
            .setNegativeButton("升序", new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {

                }
            })
            .setPositiveButton("降序", new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {

                }
            })
            .show();
    }

    private void initPresenter() {

        presenterGetFile = new PresenterGetFile(this);

        viewAdapter.setPresenterGetFile(presenterGetFile);

        presenterGetFile.initPresenter();
    }

    @Override public void setFileList(LocalFileBeanList localFileBeanList) {
        viewAdapter.setLocalFileBeanList(localFileBeanList.getLocalFileBeanList());
    }

    @Override public void refreshGuestView(List<LocalFileBean> localFileBeanList) {
        topGuideView.setTopGuideList(localFileBeanList);
    }

    @Override public void showLoading() {
        customFrameLayout.showAppointedLayout(R.id.file_list_loading);
    }

    @Override public void showEmpty() {
        customFrameLayout.showAppointedLayout(R.id.file_list_empty);
    }

    @Override public void showList() {
        customFrameLayout.showAppointedLayout(R.id.file_list_content);
    }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        return (presenterGetFile != null && presenterGetFile.backLastFolder()) || super.onKeyDown(
            keyCode, event);
    }

    private static class FileViewAdapter extends RecyclerView.Adapter {

        private PresenterGetFile presenterGetFile;

        private List<LocalFileBean> localFileBeanList;
        private Context context;

        FileViewAdapter(Context context) {
            this.context = context;
        }

        void setLocalFileBeanList(List<LocalFileBean> localFileBeanList) {
            this.localFileBeanList = localFileBeanList;
            notifyDataSetChanged();
        }

        void setPresenterGetFile(PresenterGetFile presenterGetFile) {
            this.presenterGetFile = presenterGetFile;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FileViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_file_list_item, parent, false));
        }

        @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((FileViewHolder) holder).setItemData(localFileBeanList.get(position));
        }

        @Override public int getItemCount() {
            return localFileBeanList == null ? 0 : localFileBeanList.size();
        }

        private class FileViewHolder extends RecyclerView.ViewHolder {

            private ImageView fileImage;
            private TextView fileTitle;
            private TextView fileInfo;
            private TextView fileHeader;

            FileViewHolder(View itemView) {
                super(itemView);

                fileImage = itemView.findViewById(R.id.file_item_icon);
                fileTitle = itemView.findViewById(R.id.file_item_title);
                fileInfo = itemView.findViewById(R.id.file_item_info);
                fileHeader = itemView.findViewById(R.id.file_item_header);
            }

            void setItemData(LocalFileBean localFileBean) {

                fileTitle.setText(localFileBean.getFileName());
                fileHeader.setText(getFileHeader(localFileBean.getFile()));

                if (localFileBean.isDir()) {
                    setDirInfo(localFileBean);
                } else {
                    setFileInfo(localFileBean);
                }
            }

            @SuppressLint("SetTextI18n") private void setDirInfo(final LocalFileBean fileBean) {

                String fileChangeDate = StorageUtil.getFileDateTime(fileBean.getChangeTime());

                String fileCount =
                    "" + (fileBean.getFile().list() != null ? fileBean.getFile().list().length : 0);

                fileImage.setImageResource(R.mipmap.folder);
                fileInfo.setText(fileCount + "项，" + fileChangeDate);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        if (presenterGetFile != null) presenterGetFile.visitLocalFolder(fileBean);
                    }
                });
            }

            @SuppressLint("SetTextI18n") private void setFileInfo(LocalFileBean fileBean) {

                String fileChangeDate = StorageUtil.getFileDateTime(fileBean.getChangeTime());

                String fileSize = StorageUtil.formatFileSize(context, fileBean.getFileSize());

                fileImage.setImageResource(R.mipmap.file);
                fileInfo.setText(fileSize + "，" + fileChangeDate);

                itemView.setClickable(false);
            }

            private String getFileHeader(File file) {
                byte[] fileHeader=new byte[16];
                try {
                    FileInputStream inputStream = new FileInputStream(file);
                    inputStream.read(fileHeader,0,16);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return Arrays.toString(fileHeader);
                //return bytesToHexString(fileHeader);
            }

            private String bytesToHexString(byte[] src) {
                StringBuilder stringBuilder = new StringBuilder();
                if (src == null || src.length <= 0) {
                    return null;
                }
                for (byte aSrc : src) {
                    int v = aSrc & 0xFF;
                    String hv = Integer.toHexString(v);
                    if (hv.length() < 2) {
                        stringBuilder.append(0);
                    }
                    stringBuilder.append(hv);
                }
                return stringBuilder.toString();
            }
        }
    }
}
