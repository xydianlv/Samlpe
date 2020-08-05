package com.example.wyyu.gitsamlpe.framework;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.wyyu.gitsamlpe.R;

/**
 * Created by wyyu on 2018/12/11.
 **/

public class SubReviewLayout extends LinearLayout {

    public SubReviewLayout(Context context) {
        this(context, null);
    }

    public SubReviewLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SubReviewLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSubReviewLayout();
    }

    private TextView subContent;

    private void initSubReviewLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_sub_review_layout, this);

        subContent = findViewById(R.id.sub_review_content);
    }

    //public void setSubReview(CommentBean commentBean) {
    //    if (subContent == null || commentBean == null || commentBean.subReview == null) {
    //        setVisibility(View.GONE);
    //        return;
    //    }
    //    setVisibility(View.VISIBLE);
    //
    //    CommentBean review = commentBean.subReview;
    //
    //    SpannableString spannableString = loadSpanFromContent(review);
    //
    //    setNameClickSpan(spannableString, commentBean.subReview.nickName,
    //        commentBean.subReview.mid);
    //
    //    int nameLength = TextUtils.isEmpty(review.nickName) ? 0 : review.nickName.length();
    //    setTypeImageSpan(spannableString, review.reviewerType, nameLength, nameLength + 1);
    //
    //    subContent.setText(spannableString);
    //}
    //
    //private SpannableString loadSpanFromContent(CommentBean review) {
    //    if (review == null) {
    //        return null;
    //    }
    //    String reviewPre = (TextUtils.isEmpty(review.nickName) ? " " : review.nickName) + " ：";
    //    String reviewMid;
    //    if (review.commentVideos != null && !review.commentVideos.isEmpty()) {
    //        reviewMid = "[视频]";
    //    } else if (review.serverImages != null && !review.serverImages.isEmpty()) {
    //        reviewMid = "[图片]";
    //    } else if (review.audio != null) {
    //        reviewMid = "[语音]";
    //    } else {
    //        reviewMid = "";
    //    }
    //    return new SpannableString(reviewPre + reviewMid + review.reviewContent);
    //}
    //
    //private void setNameClickSpan(SpannableString span, String name, final long memberId) {
    //    if (TextUtils.isEmpty(name)) {
    //        return;
    //    }
    //    ClickableSpan nameSpan = new ClickableSpan() {
    //        @Override public void onClick(View widget) {
    //            MemberActivity.open(getContext(), null, memberId);
    //        }
    //
    //        @Override public void updateDrawState(@NonNull TextPaint ds) {
    //            super.updateDrawState(ds);
    //            ds.setColor(getContext().getResources().getColor(R.color.color_427eb3));
    //            ds.setUnderlineText(false);
    //            ds.clearShadowLayer();
    //        }
    //    };
    //
    //    span.setSpan(nameSpan, 0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    //}
    //
    //private void setTypeImageSpan(SpannableString span, int reviewerType, int start, int end) {
    //
    //    Drawable drawable;
    //
    //    switch (reviewerType) {
    //        case 1:
    //            drawable = getContext().getResources().getDrawable(R.drawable.icon_role_louzhu);
    //            if (drawable != null) {
    //                drawable.setBounds(0, 0, drawable.getMinimumWidth(),
    //                    drawable.getMinimumHeight());
    //            }
    //            break;
    //        case 2:
    //            drawable = getContext().getResources().getDrawable(R.drawable.icon_role_guanfang);
    //            if (drawable != null) {
    //                drawable.setBounds(0, 0, drawable.getMinimumWidth(),
    //                    drawable.getMinimumHeight());
    //            }
    //            break;
    //        default:
    //            return;
    //    }
    //    span.setSpan(new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE), start, end,
    //        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    //}
}