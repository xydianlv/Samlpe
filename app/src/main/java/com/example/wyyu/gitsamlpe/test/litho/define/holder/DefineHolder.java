package com.example.wyyu.gitsamlpe.test.litho.define.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.litho.custom.widget.LithoContainer;
import com.example.wyyu.gitsamlpe.test.litho.define.component.ContentTest;
import com.example.wyyu.gitsamlpe.test.litho.define.component.FooterRoot;
import com.example.wyyu.gitsamlpe.test.litho.define.component.HeaderTest;
import com.example.wyyu.gitsamlpe.test.litho.define.data.DefineData;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.LithoView;

/**
 * Created by wyyu on 2019-10-09.
 **/

public class DefineHolder extends RecyclerView.ViewHolder {

    public static final int LAYOUT = R.layout.layout_litho_define_holder;

    private LithoContainer header;
    private LithoContainer footer;
    private LithoContainer content;

    private ContentTest contentTest;
    private HeaderTest headerTest;
    private FooterRoot footerRoot;

    public DefineHolder(@NonNull View itemView) {
        super(itemView);

        initView(itemView);
        initComponent();
        addView(itemView.getContext());
    }

    private void initView(@NonNull View itemView) {
        header = itemView.findViewById(R.id.litho_define_header);
        footer = itemView.findViewById(R.id.litho_define_footer);
        content = itemView.findViewById(R.id.litho_define_content);
    }

    private void initComponent() {
        contentTest = new ContentTest();
        footerRoot = new FooterRoot();
        headerTest = new HeaderTest();
    }

    private void addView(Context context) {
        header.removeAllViews();
        footer.removeAllViews();
        content.removeAllViews();

        ComponentContext componentContext = new ComponentContext(context);

        header.addView(LithoView.create(componentContext, headerTest));
        content.addView(LithoView.create(componentContext, contentTest));
        footer.addView(LithoView.create(componentContext, footerRoot));
    }

    public void cacheValue(DefineData defineData) {
        footerRoot.setDefineData(defineData);
        headerTest.setDefineData(defineData);
        contentTest.setDefineData(defineData);
    }
}
