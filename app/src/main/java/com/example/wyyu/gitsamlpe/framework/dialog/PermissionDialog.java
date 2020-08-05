package com.example.wyyu.gitsamlpe.framework.dialog;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wyyu on 2018/8/25.
 **/

public class PermissionDialog {

    public static final int REQ_CODE_REQUEST_SETTING = 119;

    private FullScreenDialogTest.Builder dialogBuilder;
    private View contentView;

    // 权限名称
    private HashMap<String, String> permissionNameMap;
    // 权限说明
    private HashMap<String, String> permissionDesMap;

    @SuppressLint("InflateParams")
    public PermissionDialog(final Activity activity, List<String> permissionList) {
        initView(activity, permissionList);
        initValue();
    }

    @SuppressLint("InflateParams")
    private void initView(final Activity activity, List<String> permissionList) {
        contentView =
            LayoutInflater.from(activity).inflate(R.layout.layout_check_permission_dialog, null);

        PermissionListAdapter listAdapter = new PermissionListAdapter(activity, permissionList);
        RecyclerView recyclerView = contentView.findViewById(R.id.check_dialog_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(listAdapter);

        contentView.findViewById(R.id.check_dialog_button_open)
            .setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    goSetting(activity);
                }
            });

        dialogBuilder = new FullScreenDialogTest.Builder(activity);
    }

    private void initValue() {
        permissionNameMap = new HashMap<>();
        permissionDesMap = new HashMap<>();

        permissionNameMap.put(Manifest.permission.READ_PHONE_STATE, "获取手机信息");
        permissionNameMap.put(Manifest.permission.ACCESS_FINE_LOCATION, "获取位置信息");
        permissionNameMap.put(Manifest.permission.ACCESS_COARSE_LOCATION, "获取位置信息");
        permissionNameMap.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, "存储");
        permissionNameMap.put(Manifest.permission.READ_EXTERNAL_STORAGE, "存储");
        permissionNameMap.put(Manifest.permission.RECORD_AUDIO, "录音");
        permissionNameMap.put(Manifest.permission.CAMERA, "录像");

        permissionDesMap.put(Manifest.permission.READ_PHONE_STATE, "开启后推荐内容会更符合您的口味哦, 同时提高账户安全系数");
        permissionDesMap.put(Manifest.permission.ACCESS_FINE_LOCATION, "为您推荐更适合您的内容");
        permissionDesMap.put(Manifest.permission.ACCESS_COARSE_LOCATION, "为您推荐更适合您的内容");
        permissionDesMap.put(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            "浏览图片和视频缓存需要本地读写，方便浏览并节约流量");
        permissionDesMap.put(Manifest.permission.READ_EXTERNAL_STORAGE,
            "浏览图片和视频缓存需要本地读写，方便浏览并节约流量");
        permissionDesMap.put(Manifest.permission.RECORD_AUDIO, "如需录音需先开启手机麦克风使用权限哦~");
        permissionDesMap.put(Manifest.permission.CAMERA, "如需拍摄图片和视频，需先开启手机相机使用权限哦~");
    }

    private void goSetting(Activity activity) {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(
                Uri.parse("package:" + activity.getPackageName()));
            activity.startActivityForResult(intent, REQ_CODE_REQUEST_SETTING);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Intent intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
            activity.startActivityForResult(intent, REQ_CODE_REQUEST_SETTING);
        }
    }

    public void show() {
        dialogBuilder.show(contentView);
    }

    public void hide() {
        dialogBuilder.hide();
    }

    private class PermissionListAdapter extends RecyclerView.Adapter {

        private List<String> permissionList;
        private Activity activity;

        PermissionListAdapter(Activity activity, List<String> permissionList) {
            this.activity = activity;
            this.permissionList = permissionList;
        }

        @NonNull @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PermissionItemHolder(LayoutInflater.from(activity)
                .inflate(R.layout.layout_permission_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            String name = permissionNameMap.get(permissionList.get(position));
            String des = permissionDesMap.get(permissionList.get(position));

            ((PermissionItemHolder) holder).cacheItem(name + "：" + des);
        }

        @Override public int getItemCount() {
            return permissionList == null ? 0 : permissionList.size();
        }

        private class PermissionItemHolder extends RecyclerView.ViewHolder {

            private TextView text;

            PermissionItemHolder(View itemView) {
                super(itemView);

                text = itemView.findViewById(R.id.permission_item_text);
            }

            void cacheItem(String permission) {
                text.setText(permission);
            }
        }
    }
}
