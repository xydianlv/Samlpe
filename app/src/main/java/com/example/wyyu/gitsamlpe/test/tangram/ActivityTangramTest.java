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
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;
import com.example.wyyu.gitsamlpe.test.image.matisse.FrescoLoader;
import com.example.wyyu.gitsamlpe.test.tangram.bean.TestBean;
import com.example.wyyu.gitsamlpe.test.tangram.layout.ChildLayoutA;
import com.example.wyyu.gitsamlpe.test.tangram.layout.ChildLayoutB;
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

public class ActivityTangramTest extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityTangramTest.class));
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
        initToolbar("Tangram", 0xffffffff, 0xff84919b);
        initBasicValue();
        initList();

        if (engine != null) {
            try {
                JSONArray jsonArray = getJsonValue();
                engine.setData(jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //loadList();
    }

    private void initBasicValue() {
        TangramBuilder.init(this, new IInnerImageSetter() {
            @Override public <IMAGE extends ImageView> void doLoadImageUrl(@NonNull IMAGE view,
                @Nullable String url) {
                FrescoLoader.newFrescoLoader().uri(Uri.parse(url)).into(view);
            }
        }, ImageView.class);

        TangramBuilder.InnerBuilder builder = TangramBuilder.newInnerBuilder(this);

        builder.registerCell("1", ChildLayoutB.class);
        builder.registerCell("10", ChildLayoutA.class);

        engine = builder.build();
    }

    private void initList() {

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
        Observable.unsafeCreate(new Observable.OnSubscribe<List<TestBean>>() {
            @Override public void call(Subscriber<? super List<TestBean>> subscriber) {
                List<TestBean> beanList = new ArrayList<>(16);
                for (int index = 0; index < 16; index++) {
                    TestBean bean = new TestBean();
                    bean.index = index;
                    beanList.add(bean);
                }
                subscriber.onNext(beanList);
                subscriber.onCompleted();
            }
        })
            .observeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<List<TestBean>>() {
                @Override public void call(List<TestBean> testBeans) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("type", 100);
                        jsonObject.put("items", testBeans);

                        JSONArray jsonArray = new JSONArray();
                        jsonArray.put(jsonObject);

                        if (engine != null) {
                            engine.setData(jsonArray);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Action1<Throwable>() {
                @Override public void call(Throwable throwable) {

                }
            });
    }

    private JSONArray getJsonValue() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (int index = 0; index < 16; index++) {
            JSONObject object = new JSONObject();
            object.put("text", index);
            object.put("type", index < 8 ? 10 : 1);
            jsonArray.put(object);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "container-oneColumn");
        jsonObject.put("items", jsonArray);

        JSONArray array = new JSONArray();
        array.put(jsonObject);

        return array;
    }

    private JSONArray getJsonData() throws JSONException {
        JSONArray jsonArray = new JSONArray();

        //List<TestBean> beanList = new ArrayList<>(16);
        //for (int index = 0; index < 16; index++) {
        //    TestBean bean = new TestBean();
        //    bean.index = index;
        //    beanList.add(bean);
        //}
        JSONArray typeAArray = new JSONArray();
        for (int index = 0; index < 5; index++) {
            JSONObject object = new JSONObject();
            object.put("index", index);
            typeAArray.put(object);
        }
        JSONObject jsonObjectA = new JSONObject();
        jsonObjectA.put("type", 100);
        jsonObjectA.put("items", typeAArray);
        //jsonObjectA.put("style", "StyleA");

        JSONArray typeBArray = new JSONArray();
        for (int index = 0; index < 5; index++) {
            JSONObject object = new JSONObject();
            object.put("text", index);
            typeBArray.put(object);
        }
        JSONObject jsonObjectB = new JSONObject();
        jsonObjectB.put("type", 10);
        jsonObjectB.put("items", typeBArray);
        //jsonObjectB.put("style", "StyleB");

        jsonArray.put(jsonObjectB);
        jsonArray.put(jsonObjectA);

        return jsonArray;
    }
}
