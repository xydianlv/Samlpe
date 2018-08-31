package com.example.wyyu.gitsamlpe.test.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.wyyu.gitsamlpe.R;
import com.example.wyyu.gitsamlpe.util.UIUtils;

/**
 * Created by wyyu on 2018/8/28.
 **/

public class FragmentBottom extends DialogFragment {

    public static FragmentBottom getFragment() {
        return new FragmentBottom();
    }

    public static boolean isShowing;

    @BindView(R.id.fragment_bottom_recycler) RecyclerView recyclerView;
    @BindView(R.id.fragment_bottom_root) View rootView;
    @BindView(R.id.fragment_bottom_quit) View quitView;

    private Animation animationOut;
    private Animation animationIn;

    private Unbinder unbinder;
    private Context context;

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @NonNull public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        return dialog;
    }

    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_bottom_test, container, false);

        unbinder = ButterKnife.bind(this, contentView);
        isShowing = true;
        return contentView;
    }

    @Override public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initBasicValue();
        initRecyclerView();
        rootView.startAnimation(animationIn);
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getDialog() == null || getDialog().getWindow() == null) {
            return;
        }

        Window window = getDialog().getWindow();

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING
            | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        window.setBackgroundDrawable(new ColorDrawable());

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.gravity = Gravity.BOTTOM;
        window.setAttributes(layoutParams);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = window.getDecorView();
            int sysUIFlag = decorView.getSystemUiVisibility();
            sysUIFlag |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(sysUIFlag);
            decorView.setPadding(0, UIUtils.getStatusHeightByDimen(getContext()), 0, 0);
        }
    }

    @Override public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override public void onCancel(DialogInterface dialog) {

        Log.e("FragmentBottom", "onCancel");
    }

    @Override public void onDismiss(DialogInterface dialog) {

        Log.e("FragmentBottom", "onDismiss");
        isShowing = false;

        if (dismissListener != null) {
            dismissListener.onDismiss();
        }
    }

    private void initBasicValue() {
        animationOut = AnimationUtils.loadAnimation(getContext(), R.anim.from_top_to_bottom);
        animationIn = AnimationUtils.loadAnimation(getContext(), R.anim.from_bottom_to_top);

        quitView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                hide();
            }
        });
        animationOut.setAnimationListener(new Animation.AnimationListener() {
            @Override public void onAnimationStart(Animation animation) {

            }

            @Override public void onAnimationEnd(Animation animation) {
                dismiss();
            }

            @Override public void onAnimationRepeat(Animation animation) {

            }
        });

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && isShowing) {
                    hide();
                    isShowing = false;
                    return true;
                }
                return false;
            }
        });
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new BottomListAdapter());
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 10);
    }

    void hide() {
        rootView.startAnimation(animationOut);
    }

    private OnDismissListener dismissListener;

    void setOnDismissListener(OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }

    public interface OnDismissListener {

        void onDismiss();
    }
}
