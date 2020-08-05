package com.example.wyyu.gitsamlpe.test.multi.delegate.base;

import androidx.annotation.NonNull;
import java.util.List;

/**
 * Created by wyyu on 2019-09-23.
 **/

public class ListDelegationAdapter<T extends List<?>> extends AbsDelegationAdapter<T> {

    public ListDelegationAdapter() {
        super();
    }

    public ListDelegationAdapter(@NonNull AdapterDelegatesManager<T> delegatesManager) {
        super(delegatesManager);
    }

    public ListDelegationAdapter(@NonNull AdapterDelegate<T>... delegates) {
        super(delegates);
    }

    @Override public int getItemCount() {
        return items == null ? 0 : items.size();
    }
}
