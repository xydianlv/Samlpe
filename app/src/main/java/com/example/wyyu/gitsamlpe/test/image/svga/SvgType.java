package com.example.wyyu.gitsamlpe.test.image.svga;

import com.example.wyyu.gitsamlpe.R;

public enum SvgType {

	SMALL_CLOSE("带圈关闭", R.drawable.ic_close_s_24);


	public final String titleText;
	public final int[] imageArray;

	SvgType(String titleText, int... imageArray) {
		this.titleText = titleText;
		this.imageArray = imageArray;
	}
}
