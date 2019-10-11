package com.example.wyyu.gitsamlpe.test.litho.define.component;

import com.example.wyyu.gitsamlpe.test.litho.define.data.DefineData;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;

/**
 * Created by wyyu on 2019-10-10.
 **/

public class FooterRoot extends Component {

    private FooterTest footerTest;

    public FooterRoot() {
        super("FooterRoot");
        footerTest = new FooterTest();
    }

    @Override public FooterRoot makeShallowCopy() {
        FooterRoot footerCopy = (FooterRoot) super.makeShallowCopy();
        footerCopy.footerTest = footerTest;
        return footerCopy;
    }

    @Override protected Component onCreateLayout(ComponentContext componentContext) {
        return footerTest.onCreateLayout(componentContext);
    }

    public void setDefineData(DefineData defineData) {
        footerTest.setDefineData(defineData);
    }
}
