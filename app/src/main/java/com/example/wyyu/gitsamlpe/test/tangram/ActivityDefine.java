package com.example.wyyu.gitsamlpe.test.tangram;

import androidx.lifecycle.Observer;
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
import com.example.wyyu.gitsamlpe.test.tangram.bean.CellLoad;
import com.example.wyyu.gitsamlpe.test.tangram.bean.DefineBean;
import com.example.wyyu.gitsamlpe.test.tangram.bean.LoadBean;
import com.example.wyyu.gitsamlpe.test.tangram.cell.ArrayBuilder;
import com.example.wyyu.gitsamlpe.test.tangram.cell.CellLayoutType;
import com.example.wyyu.gitsamlpe.test.tangram.support.DefineClickSupport;
import com.example.wyyu.gitsamlpe.test.tangram.support.EventLoadMore;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.tmall.wireless.tangram.TangramBuilder;
import com.tmall.wireless.tangram.TangramEngine;
import com.tmall.wireless.tangram.util.IInnerImageSetter;
import org.json.JSONArray;
import org.json.JSONException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wyyu on 2019-08-21.
 **/

public class ActivityDefine extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityDefine.class));
    }

    @BindView(R.id.tangram_define_list) RecyclerView recyclerView;

    private TangramEngine engine;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tangram_define);

        initActivity();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        if (engine != null) {
            engine.destroy();
        }
    }

    private void initActivity() {
        initToolbar("TangramDefine", 0xffffffff, 0xff84919b);
        initBasicValue();
        initList();

        loadList();

        LiveEventBus.get()
            .with(EventLoadMore.EVENT, EventLoadMore.class)
            .observe(this, new Observer<EventLoadMore>() {
                @Override public void onChanged(@Nullable EventLoadMore event) {
                    append();
                }
            });
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

        engine.registerCell(CellDefine.CELL_TYPE, CellDefine.class, CellDefine.CELL_LAYOUT);
        engine.registerCell(CellLoad.CELL_TYPE, CellLoad.class, CellLoad.CELL_LAYOUT);

        engine.addSimpleClickSupport(new DefineClickSupport());

        // 预加载
        engine.setPreLoadNumber(5);

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
        Observable.unsafeCreate(new Observable.OnSubscribe<JSONArray>() {
            @Override public void call(Subscriber<? super JSONArray> subscriber) {
                try {
                    subscriber.onNext(loadArray());
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

                }
            });
    }

    private JSONArray loadArray() throws JSONException {
        ArrayBuilder builder = new ArrayBuilder(CellLayoutType.ONE_COLUMN);
        for (int index = 0; index < 18; index++) {
            DefineBean bean = new DefineBean();
            bean.index = index;
            builder.addCell(bean.toJsonObject());
        }

        builder.addCell(new LoadBean().toJsonObject());

        JSONArray array = new JSONArray();
        array.put(builder.toObject());

        return array;
    }

    private void append() {

    }
}
