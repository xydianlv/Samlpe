package com.example.wyyu.gitsamlpe.test.database;

import androidx.lifecycle.ViewModel;
import android.content.Context;
import androidx.annotation.NonNull;
import com.example.wyyu.gitsamlpe.framework.ULog;
import com.example.wyyu.gitsamlpe.framework.message.Message;
import com.example.wyyu.gitsamlpe.framework.message.MsgSender;
import com.example.wyyu.gitsamlpe.framework.message.MsgType;
import com.example.wyyu.gitsamlpe.framework.toast.UToast;
import java.util.List;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wyyu on 2018/11/12.
 **/

public class DataListModel extends ViewModel {

    public DataListModel() {

    }

    void loadDataList(@NonNull final LoadListener loadListener) {
        Observable.unsafeCreate(new Observable.OnSubscribe<List<DataBean>>() {
            @Override public void call(Subscriber<? super List<DataBean>> subscriber) {
                List<DataBean> dataBeanList = DataManager.getDataList();
                if (dataBeanList == null || dataBeanList.isEmpty()) {
                    subscriber.onError(new Throwable());
                } else {
                    subscriber.onNext(dataBeanList);
                }
                subscriber.onCompleted();
            }
        })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(new Action1<List<DataBean>>() {
                @Override public void call(List<DataBean> dataBeanList) {
                    loadListener.loadSuccess(dataBeanList);
                }
            }, new Action1<Throwable>() {
                @Override public void call(Throwable throwable) {
                    loadListener.loadFailure();
                }
            });
    }

    void saveNewData(final Context context, final String text) {
        Observable.unsafeCreate(new Observable.OnSubscribe<Integer>() {
            @Override public void call(Subscriber<? super Integer> subscriber) {
                DataBean dataBean = new DataBean();
                dataBean.id = System.currentTimeMillis();
                dataBean.name = text;
                dataBean.userJson = dataBean.toJsonObject();

                subscriber.onNext(DataManager.saveData(dataBean));
                subscriber.onCompleted();
            }
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<Integer>() {
                @Override public void call(Integer integer) {
                    UToast.showShort(context, "数据个数 ：" + integer);
                    MsgSender.getMsgSender().sendMessage(MsgType.DATA_CHANGE, new Message());
                }
            }, new Action1<Throwable>() {
                @Override public void call(Throwable throwable) {
                    UToast.showShort(context, "存储失败");
                    ULog.show(throwable.getMessage());
                }
            });
    }

    public interface LoadListener {

        void loadSuccess(List<DataBean> dataBeanList);

        void loadFailure();
    }
}
