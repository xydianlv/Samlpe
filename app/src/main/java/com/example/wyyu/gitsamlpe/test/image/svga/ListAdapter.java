package com.example.wyyu.gitsamlpe.test.image.svga;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private final List<SvgType> svgList = new ArrayList<>();

	public ListAdapter() {
		svgList.add(SvgType.SMALL_CLOSE);
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new SvgItemHolder(LayoutInflater.from(parent.getContext()).inflate(SvgItemHolder.LAYOUT, parent, false));
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		((SvgItemHolder) holder).bindItem(svgList.get(position));
	}

	@Override
	public int getItemCount() {
		return svgList.size();
	}

	private static class SvgItemHolder extends RecyclerView.ViewHolder {

		private static final int LAYOUT = R.layout.layout_svg_item;

		private final TextView titleView;
		private final LinearLayout container;

		public SvgItemHolder(@NonNull View itemView) {
			super(itemView);

			titleView = itemView.findViewById(R.id.title);
			container = itemView.findViewById(R.id.container);
		}

		public void bindItem(SvgType svgType) {
			titleView.setText(svgType.titleText);
			container.removeAllViews();
			for (int resId : svgType.imageArray) {
				ImageView imageView = new ImageView(itemView.getContext());
				imageView.setImageResource(resId);
				imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

				ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
				container.addView(imageView, params);

				View divide = new View(itemView.getContext());
				ViewGroup.LayoutParams divideParams = new LinearLayout.LayoutParams(UIUtils.dpToPx(14.0f), 0);
				container.addView(divide, divideParams);
			}
		}
	}
}
