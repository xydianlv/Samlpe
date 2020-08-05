package com.example.wyyu.gitsamlpe.test.tangram;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.image.matisse.FrescoLoader;
import com.example.wyyu.gitsamlpe.test.tangram.bean.CellDefine;
import com.example.wyyu.gitsamlpe.test.tangram.cell.CellPreLoad;
import com.tmall.wireless.tangram.TangramBuilder;
import com.tmall.wireless.tangram.TangramEngine;
import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.util.IInnerImageSetter;
import java.util.List;

/**
 * Created by wyyu on 2019-09-25.
 **/

public class ActivityCell extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityCell.class));
    }

    @BindView(R.id.tangram_cell_list) RecyclerView recyclerView;

    private TangramEngine engine;
    private CelListModel model;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tangram_cell);

        initToolbar("TangramCell", 0xffffffff, 0xff84919b);
        initActivity();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        if (engine != null) {
            engine.destroy();
        }
    }

    private void initActivity() {
        initValue();
        initList();

        loadList();
    }

    private void initValue() {
        TangramBuilder.init(this, new IInnerImageSetter() {
            @Override public <IMAGE extends ImageView> void doLoadImageUrl(@NonNull IMAGE view,
                @Nullable String url) {
                FrescoLoader.newFrescoLoader().uri(Uri.parse(url)).into(view);
            }
        }, ImageView.class);
        engine = TangramBuilder.newInnerBuilder(this).build();

        model = ViewModelProviders.of(this).get(CelListModel.class);
        model.bindValue(engine);
    }

    private void initList() {
        engine.registerCell(CellDefine.CELL_TYPE, CellDefine.class, CellDefine.getCellLayout());
        engine.registerCell(CellPreLoad.CELL_TYPE, CellPreLoad.class, CellPreLoad.getCellLayout());

        engine.bindView(recyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (engine != null) {
                    engine.onScrolled();
                }
            }
        });
    }

    private void loadList() {
        model.loadList(new CelListModel.OnLoadListListener() {
            @Override public void onSuccess(int index, List<BaseCell> cellList) {
                if (engine != null) {
                    engine.setData(CellUtils.getPreLoadArray());
                    engine.insertWith(index, cellList);
                }
            }

            @Override public void onFailure() {

            }
        });
    }
}
