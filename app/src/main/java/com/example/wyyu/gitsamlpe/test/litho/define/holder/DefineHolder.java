package com.example.wyyu.gitsamlpe.test.litho.define.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.test.litho.custom.widget.LithoContainer;
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
    private FrameLayout footer;
    //private FrameLayout content;

    //private DefineContent defineContent;
    //private DefineHeader defineHeader;
    //private DefineFooter defineFooter;
    //private ContentTest contentTest;
    private HeaderTest headerTest;
    private FooterRoot footerRoot;

    public DefineHolder(@NonNull View itemView) {
        super(itemView);

        initView(itemView);
        initComponent(itemView.getContext());
        addView(itemView.getContext());
    }

    private void initView(@NonNull View itemView) {
        header = itemView.findViewById(R.id.litho_define_header);
        footer = itemView.findViewById(R.id.litho_define_footer);
        //content = itemView.findViewById(R.id.litho_define_content);
    }

    private void initComponent(Context context) {
        //defineContent = DefineContent.create(new ComponentContext(context)).build();
        //defineHeader = DefineHeader.create(new ComponentContext(context)).build();
        //defineFooter = DefineFooter.create(new ComponentContext(context)).build();

        //contentTest = new ContentTest();
        footerRoot = new FooterRoot();
        headerTest = new HeaderTest();
    }

    private void addView(Context context) {
        header.removeAllViews();
        footer.removeAllViews();
        //content.removeAllViews();

        ComponentContext componentContext = new ComponentContext(context);

        LithoView viewHeader = LithoView.create(componentContext, headerTest);
        //viewHeader.setOnPostDrawListener(() -> {
        //    if (newCache) {
        //        headerTest.setDefineData(defineData);
        //        newCache = false;
        //    }
        //});
        header.addView(viewHeader);
        //content.addView(LithoView.create(context, defineContent));
        //footer.addView(LithoView.create(context, defineFooter));

        //content.addView(contentTest.getContentView(new ComponentContext(context)));

        //header.addView(LithoView.create(componentContext, headerTest));
        //LithoView viewFooter = LithoView.create(componentContext, footerRoot);
        //viewFooter.setOnPostDrawListener(new LithoView.OnPostDrawListener() {
        //    @Override public void onPostDraw() {
        //        footerRoot.setDefineData(defineData);
        //    }
        //});
        //footer.addView(viewFooter);

        footer.addView(LithoView.create(componentContext, footerRoot));
    }

    public void cacheValue(DefineData defineData) {

        //defineHeader.setDefineData(defineData);
        //headerTest.setDefineData(defineData);
        footerRoot.setDefineData(defineData);

        //this.defineData = defineData;

        headerTest.setDefineData(defineData);

        //contentTest.setDefineData(defineData);
    }
}
