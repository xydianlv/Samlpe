package com.example.wyyu.gitsamlpe.test.tangram;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.test.tangram.bean.CellDefine;
import com.example.wyyu.gitsamlpe.test.tangram.bean.DefineBean;
import com.tmall.wireless.tangram.MVHelper;
import com.tmall.wireless.tangram.TangramEngine;
import com.tmall.wireless.tangram.structure.BaseCell;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wyyu on 2019-09-25.
 **/

public class CelListModel extends ViewModel {

    // 组件加载引擎
    private TangramEngine engine;

    public CelListModel() {

    }

    void bindValue(TangramEngine tangramEngine) {
        this.engine = tangramEngine;
    }

    void loadList(@NonNull OnLoadListListener loadListListener) {
        Observable.unsafeCreate((Observable.OnSubscribe<List<BaseCell>>) subscriber -> {
            try {
                subscriber.onNext(loadCellList());
            } catch (JSONException e) {
                e.printStackTrace();
                subscriber.onError(e);
            }
            subscriber.onCompleted();
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(cellList -> loadListListener.onSuccess(0, cellList), throwable -> {
                loadListListener.onFailure();
                ULog.show(throwable.getMessage());
            });
    }

    private List<BaseCell> loadCellList() throws JSONException {
        MVHelper resolver = engine == null ? null : engine.getService(MVHelper.class);
        if (resolver == null) {
            return null;
        }
        List<BaseCell> cellList = new ArrayList<>();
        for (int index = 0; index < 32; index++) {
            DefineBean defineBean = new DefineBean();
            defineBean.index = index;

            cellList.add(CellDefine.buildPostToCell(defineBean, resolver, ""));
        }
        return cellList;
    }

    public interface OnLoadListListener {

        void onSuccess(int index, List<BaseCell> cellList);

        void onFailure();
    }
}
