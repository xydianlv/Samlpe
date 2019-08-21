package com.example.wyyu.gitsamlpe.test.tangram;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import butterknife.BindView;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.image.matisse.FrescoLoader;
import com.example.wyyu.gitsamlpe.test.tangram.cell.ArrayBuilder;
import com.example.wyyu.gitsamlpe.test.tangram.bean.CellBean;
import com.example.wyyu.gitsamlpe.test.tangram.cell.CellLayoutType;
import com.example.wyyu.gitsamlpe.test.tangram.bean.TestBean;
import com.example.wyyu.gitsamlpe.util.UIUtils;
import com.tmall.wireless.tangram.TangramBuilder;
import com.tmall.wireless.tangram.TangramEngine;
import com.tmall.wireless.tangram.util.IInnerImageSetter;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wyyu on 2019-08-15.
 **/

public class ActivityDefault extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityDefault.class));
    }

    @BindView(R.id.tangram_test_list) RecyclerView recyclerView;

    private TangramEngine engine;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tangram_test);

        initActivity();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        if (engine != null) {
            engine.destroy();
        }
    }

    private void initActivity() {
        initToolbar("Default", 0xffffffff, 0xff84919b);
        initBasicValue();
        initList();

        loadList();
    }

    private void initBasicValue() {
        TangramBuilder.init(this, new IInnerImageSetter() {
            @Override public <IMAGE extends ImageView> void doLoadImageUrl(@NonNull IMAGE view,
                @Nullable String url) {
                FrescoLoader.newFrescoLoader().uri(Uri.parse(url)).into(view);
            }
        }, ImageView.class);

        engine = TangramBuilder.newInnerBuilder(this).build();
    }

    private void initList() {

        engine.registerCell(TestBean.CELL_TYPE, TestBean.CELL_LAYOUT);

        engine.bindView(recyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (engine != null) {
                    engine.onScrolled();
                }
            }
        });
    }

    private void loadList() {
        Observable.unsafeCreate(new Observable.OnSubscribe<JSONArray>() {
            @Override public void call(Subscriber<? super JSONArray> subscriber) {
                try {
                    subscriber.onNext(getJsonValue());
                } catch (JSONException e) {
                    subscriber.onError(e);
                } finally {
                    subscriber.onCompleted();
                }
            }
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<JSONArray>() {
                @Override public void call(JSONArray jsonArray) {
                    if (engine != null) {
                        engine.setData(jsonArray);
                    }
                }
            }, new Action1<Throwable>() {
                @Override public void call(Throwable throwable) {
                    ULog.show(throwable.getMessage());
                }
            });
    }

    private JSONArray getJsonValue() throws JSONException {

        ArrayBuilder builderA = new ArrayBuilder(CellLayoutType.ONE_COLUMN);
        builderA.addAllCell(new ArrayList<CellBean>(getBeanList(2)));

        ArrayBuilder builderB = new ArrayBuilder(CellLayoutType.STICKY);
        builderB.addAllCell(new ArrayList<CellBean>(getBeanList(1)));

        ArrayBuilder builderC = new ArrayBuilder(CellLayoutType.TWO_COLUMN);
        builderC.addAllCell(new ArrayList<CellBean>(getBeanList(2)));

        ArrayBuilder builderD = new ArrayBuilder(CellLayoutType.THREE_COLUMN);
        builderD.addAllCell(new ArrayList<CellBean>(getBeanList(3)));

        ArrayBuilder builderE = new ArrayBuilder(CellLayoutType.FOUR_COLUMN);
        builderE.addAllCell(new ArrayList<CellBean>(getBeanList(4)));

        ArrayBuilder builderF = new ArrayBuilder(CellLayoutType.BANNER);
        JSONObject jsonStyle = new JSONObject();
        jsonStyle.put("pageWidth", UIUtils.dpToPx(200.0f));
        jsonStyle.put("pageHeight", UIUtils.dpToPx(100.0f));
        builderF.style(jsonStyle);
        builderF.addAllCell(new ArrayList<CellBean>(getBeanList(4)));

        ArrayBuilder builderG = new ArrayBuilder(CellLayoutType.FLOAT);
        builderG.addAllCell(new ArrayList<CellBean>(getBeanList(1)));

        ArrayBuilder builderX = new ArrayBuilder(CellLayoutType.WATER_FALL);
        builderX.addAllCell(new ArrayList<CellBean>(getBeanList(2)));

        ArrayBuilder builderY = new ArrayBuilder(CellLayoutType.ONE_PLUS);
        builderY.addAllCell(new ArrayList<CellBean>(getBeanList(2)));

        ArrayBuilder builderS = new ArrayBuilder(CellLayoutType.ONE_COLUMN);
        builderS.addAllCell(new ArrayList<CellBean>(getBeanList(8)));

        ArrayBuilder builderZ = new ArrayBuilder(CellLayoutType.SCROLL_FIX);
        builderZ.addAllCell(new ArrayList<CellBean>(getBeanList(1)));

        JSONArray array = new JSONArray();
        array.put(builderA.toObject());
        array.put(builderB.toObject());
        array.put(builderC.toObject());
        array.put(builderD.toObject());
        array.put(builderE.toObject());
        array.put(builderF.toObject());
        array.put(builderX.toObject());
        array.put(builderY.toObject());
        array.put(builderS.toObject());
        array.put(builderZ.toObject());

        return array;
    }

    private List<TestBean> getBeanList(int size) {
        List<TestBean> beanList = new ArrayList<>(6);
        for (int index = 0; index < size; index++) {
            TestBean bean = new TestBean();
            bean.index = index;
            beanList.add(bean);
        }
        return beanList;
    }
}
