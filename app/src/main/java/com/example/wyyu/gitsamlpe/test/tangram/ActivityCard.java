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
import com.example.wyyu.gitsamlpe.test.tangram.bean.CardBottomBean;
import com.example.wyyu.gitsamlpe.test.tangram.bean.CardMidBean;
import com.example.wyyu.gitsamlpe.test.tangram.bean.CardTopBean;
import com.example.wyyu.gitsamlpe.test.tangram.card.CardCellBottom;
import com.example.wyyu.gitsamlpe.test.tangram.card.CardCellMid;
import com.example.wyyu.gitsamlpe.test.tangram.card.CardCellTop;
import com.example.wyyu.gitsamlpe.test.tangram.card.CardTest;
import com.example.wyyu.gitsamlpe.test.tangram.support.CustomExposureSupport;
import com.example.wyyu.gitsamlpe.test.tangram.support.EventLoadMore;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.tmall.wireless.tangram.MVHelper;
import com.tmall.wireless.tangram.TangramBuilder;
import com.tmall.wireless.tangram.TangramEngine;
import com.tmall.wireless.tangram.dataparser.concrete.Card;
import com.tmall.wireless.tangram.dataparser.concrete.CardResolver;
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
 * Created by wyyu on 2019-08-22.
 **/

public class ActivityCard extends ToolbarActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, ActivityCard.class));
    }

    public static final int MAX_COUNT = 12;

    @BindView(R.id.tangram_card_list) RecyclerView recyclerView;

    private TangramBuilder.InnerBuilder builder;
    private TangramEngine engine;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tangram_card);

        initActivity();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        if (engine != null) {
            engine.destroy();
        }
    }

    private void initActivity() {
        initToolbar("TangramCard", 0xffffffff, 0xff84919b);
        initBasicValue();
        initList();

        //loadArray();
        loadList();

        LiveEventBus.get()
            .with(EventLoadMore.EVENT, EventLoadMore.class)
            .observe(this, new Observer<EventLoadMore>() {
                @Override public void onChanged(@Nullable EventLoadMore eventLoadMore) {
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

        builder = TangramBuilder.newInnerBuilder(this);
        //builder.setDataParser(new SampleDataParser());
    }

    private void initList() {

        builder.registerCell(CardCellTop.CELL_TYPE, CardCellTop.class, CardCellTop.CELL_LAYOUT);
        builder.registerCell(CardCellMid.CELL_TYPE, CardCellMid.class, CardCellMid.CELL_LAYOUT);
        builder.registerCell(CardCellBottom.CELL_TYPE, CardCellBottom.class,
            CardCellBottom.CELL_LAYOUT);
        builder.registerCard(CardTest.TYPE_CARD, CardTest.class);

        engine = builder.build();

        engine.addExposureSupport(new CustomExposureSupport());
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

    // 以 List<Card> 的方式加载列表
    private void loadList() {
        Observable.unsafeCreate(new Observable.OnSubscribe<List<Card>>() {
            @Override public void call(Subscriber<? super List<Card>> subscriber) {
                List<Card> cardTestList = new ArrayList<>(MAX_COUNT);
                try {
                    for (int index = 0; index < MAX_COUNT; index++) {
                        cardTestList.add(loadCard());
                    }
                    subscriber.onNext(cardTestList);
                } catch (JSONException e) {
                    subscriber.onError(e);
                    e.printStackTrace();
                } finally {
                    subscriber.onCompleted();
                }
            }
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<List<Card>>() {
                @Override public void call(List<Card> cardList) {
                    if (engine != null) {
                        engine.setData(cardList);
                    }
                }
            }, new Action1<Throwable>() {
                @Override public void call(Throwable throwable) {

                }
            });
    }

    private Card loadCard() throws JSONException {
        CardResolver cardResolver = engine.getService(CardResolver.class);
        MVHelper cellResolver = engine.getService(MVHelper.class);

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(new CardTopBean().toJsonObject());
        jsonArray.put(new CardMidBean().toJsonObject());
        jsonArray.put(new CardBottomBean().toJsonObject());

        JSONObject cardData = new JSONObject();
        cardData.put("items", jsonArray);

        Card cardTest = cardResolver.create(CardTest.TYPE_CARD);
        if (cardTest != null) {
            cardTest.serviceManager = engine;
            cardTest.parseWith(cardData, cellResolver);
            cardTest.stringType = CardTest.TYPE_CARD;
        }

        return cardTest;
    }

    private void append() {
        Observable.unsafeCreate(new Observable.OnSubscribe<List<Card>>() {
            @Override public void call(Subscriber<? super List<Card>> subscriber) {
                List<Card> cardTestList = new ArrayList<>(MAX_COUNT);
                try {
                    for (int index = 0; index < MAX_COUNT; index++) {
                        cardTestList.add(loadCard());
                    }
                    subscriber.onNext(cardTestList);
                } catch (JSONException e) {
                    subscriber.onError(e);
                    e.printStackTrace();
                } finally {
                    subscriber.onCompleted();
                }
            }
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<List<Card>>() {
                @Override public void call(List<Card> cardList) {
                    if (engine != null) {
                        engine.appendBatchWith(cardList);
                    }
                }
            }, new Action1<Throwable>() {
                @Override public void call(Throwable throwable) {

                }
            });
    }
}
