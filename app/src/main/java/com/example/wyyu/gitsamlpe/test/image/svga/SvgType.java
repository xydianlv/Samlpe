package com.example.wyyu.gitsamlpe.test.image.svga;

import com.example.wyyu.gitsamlpe.R;

public enum SvgType {

	SMALL_CLOSE("带圈小关闭", R.drawable.ic_close_s_24, R.drawable.ic_close_s_28, R.drawable.ic_close_s_32, R.drawable.ic_close_s_o),
	MID_CLOSE("带圈中关闭", R.drawable.ic_close_m_40, R.drawable.ic_close_m_48, R.drawable.ic_close_m_o),
	SMALL_S_CLOSE("小关闭", R.drawable.ic_close_sp_14, R.drawable.ic_close_sp_16, R.drawable.ic_close_sp_20, R.drawable.ic_close_sp_24, R.drawable.ic_close_sp_o),
	MID_S_CLOSE("中关闭", R.drawable.ic_close_mp_20, R.drawable.ic_close_mp_24, R.drawable.ic_close_mp_28, R.drawable.ic_close_mp_32, R.drawable.ic_close_mp_o),
	BIG_S_CLOSE("大关闭", R.drawable.ic_close_bp_24, R.drawable.ic_close_bp_28, R.drawable.ic_close_bp_32, R.drawable.ic_close_bp_40, R.drawable.ic_close_bp_o),
	SMALL_SKIP("小跳转", R.drawable.ic_skip_s_14, R.drawable.ic_skip_s_16, R.drawable.ic_skip_s_20, R.drawable.ic_skip_s_24, R.drawable.ic_skip_s_o),
	MID_SKIP("中跳转", R.drawable.ic_skip_m_16, R.drawable.ic_skip_m_20, R.drawable.ic_skip_m_24, R.drawable.ic_skip_m_28, R.drawable.ic_skip_m_32, R.drawable.ic_skip_m_o),
	BIG_SKIP("大跳转", R.drawable.ic_skip_b_24, R.drawable.ic_skip_b_28, R.drawable.ic_skip_b_32, R.drawable.ic_skip_b_40, R.drawable.ic_skip_b_o);


	public final String titleText;
	public final int[] imageArray;

	SvgType(String titleText, int... imageArray) {
		this.titleText = titleText;
		this.imageArray = imageArray;
	}
}
