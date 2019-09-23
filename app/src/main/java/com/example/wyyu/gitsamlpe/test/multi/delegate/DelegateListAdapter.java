package com.example.wyyu.gitsamlpe.test.multi.delegate;

import com.example.wyyu.gitsamlpe.test.multi.delegate.base.ListDelegationAdapter;
import com.example.wyyu.gitsamlpe.test.multi.delegate.data.ItemTest;
import com.example.wyyu.gitsamlpe.test.multi.delegate.delegate.TypeADelegate;
import com.example.wyyu.gitsamlpe.test.multi.delegate.delegate.TypeBDelegate;
import com.example.wyyu.gitsamlpe.test.multi.delegate.delegate.TypeCDelegate;
import java.util.List;

/**
 * Created by wyyu on 2019-09-23.
 **/

class DelegateListAdapter extends ListDelegationAdapter<List<ItemTest>> {

    DelegateListAdapter(List<ItemTest> itemTestList) {
        delegatesManager.addDelegate(new TypeADelegate());
        delegatesManager.addDelegate(new TypeBDelegate());
        delegatesManager.addDelegate(new TypeCDelegate());

        setItems(itemTestList);
    }
}
