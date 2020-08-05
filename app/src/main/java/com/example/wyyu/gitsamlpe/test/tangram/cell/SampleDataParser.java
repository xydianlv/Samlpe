package com.example.wyyu.gitsamlpe.test.tangram.cell;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import com.tmall.wireless.tangram.core.service.ServiceManager;
import com.tmall.wireless.tangram.dataparser.DataParser;
import com.tmall.wireless.tangram.dataparser.concrete.Card;
import com.tmall.wireless.tangram.op.ParseComponentsOp;
import com.tmall.wireless.tangram.op.ParseGroupsOp;
import com.tmall.wireless.tangram.op.ParseSingleComponentOp;
import com.tmall.wireless.tangram.op.ParseSingleGroupOp;
import com.tmall.wireless.tangram.structure.BaseCell;
import io.reactivex.ObservableTransformer;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by wyyu on 2019-08-27.
 **/

public class SampleDataParser extends DataParser<JSONObject, JSONArray, Card, BaseCell> {

    @NonNull @Override
    public List<Card> parseGroup(@Nullable JSONArray data, ServiceManager serviceManager) {
        Log.e("SampleDataParserTest", "parseGroup");
        return null;
    }

    @NonNull @Override
    public List<BaseCell> parseComponent(@Nullable JSONArray data, ServiceManager serviceManager) {
        Log.e("SampleDataParserTest", "parseComponent");
        return null;
    }

    @NonNull @Override public List<BaseCell> parseComponent(@Nullable JSONArray data, Card parent,
        ServiceManager serviceManager) {
        Log.e("SampleDataParserTest", "parseComponent");
        return null;
    }

    @NonNull @Override
    public Card parseSingleGroup(@Nullable JSONObject data, ServiceManager serviceManager) {
        Log.e("SampleDataParserTest", "parseSingleGroup");
        return null;
    }

    @NonNull @Override public BaseCell parseSingleComponent(@Nullable JSONObject data, Card parent,
        ServiceManager serviceManager) {
        Log.e("SampleDataParserTest", "parseSingleComponent");
        return null;
    }

    @NonNull @Override
    public ObservableTransformer<ParseGroupsOp, List<Card>> getGroupTransformer() {
        return null;
    }

    @NonNull @Override
    public ObservableTransformer<ParseComponentsOp, List<BaseCell>> getComponentTransformer() {
        return null;
    }

    @NonNull @Override
    public ObservableTransformer<ParseSingleGroupOp, Card> getSingleGroupTransformer() {
        return null;
    }

    @NonNull @Override
    public ObservableTransformer<ParseSingleComponentOp, BaseCell> getSingleComponentTransformer() {
        return null;
    }
}
