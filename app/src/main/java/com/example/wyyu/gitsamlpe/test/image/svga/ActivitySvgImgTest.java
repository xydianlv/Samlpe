package com.example.wyyu.gitsamlpe.test.image.svga;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.framework.activity.ToolbarActivity;

public class ActivitySvgImgTest extends ToolbarActivity {

	public static void open(Context context) {
		context.startActivity(new Intent(context, ActivitySvgImgTest.class));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_svg_img_test);

		initActivity();
	}

	private void initActivity() {
		initToolbar("SvgImageTest");

		RecyclerView recyclerView = findViewById(R.id.list);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(new ListAdapter());
	}
}
