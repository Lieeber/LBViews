package studentsdemo.lieeber.com.lbview.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import studentsdemo.lieeber.com.lbview.views.ColorPickerView;
import studentsdemo.lieeber.com.lbview.views.ColorPickerView.OnSeekColorListener;
import studentsdemo.lieeber.com.lbview.R;


/**
 * Created by lieeber on 16/8/14.
 */

public class SheetUtil {
    private ViewGroup contentView;
    private View sheetContent;
    private static SheetUtil sheetUtil = new SheetUtil();
    private Activity activity;
    private View llSheet;
    private View viewSheetBg;
    private ColorPickerView colorPickerView;
    private View colorView;
    private SeekBar seekBar;
    private TextView tvProgress;

    public static SheetUtil getInstance() {
        return sheetUtil;
    }

    public void addSheet(Activity activity, int originColor, float originProgress) {
        this.activity = activity;
        contentView = (ViewGroup) activity.findViewById(android.R.id.content);
        sheetContent = LayoutInflater.from(activity).inflate(R.layout.setting_dialog, contentView, false);
        sheetContent.setVisibility(View.GONE);
        if (contentView.findViewById(R.id.view_sheet_bg) == null) {
            contentView.addView(sheetContent);
        }

        viewSheetBg = sheetContent.findViewById(R.id.view_sheet_bg);
        llSheet = sheetContent.findViewById(R.id.ll_sheet);
        colorPickerView = (ColorPickerView) sheetContent.findViewById(R.id.color_picker);
        colorView = sheetContent.findViewById(R.id.colorView);
        seekBar = (SeekBar) sheetContent.findViewById(R.id.seek_bar);
        tvProgress = (TextView) sheetContent.findViewById(R.id.tv_progress);


        if (originColor == -1) {
            sheetContent.findViewById(R.id.rl_color_picker).setVisibility(View.GONE);
        }
        seekBar.setProgress((int) originProgress);
        tvProgress.setText((int) originProgress + "%");
        colorView.setBackgroundColor(originColor);
        colorPickerView.setOnSeekColorListener(new OnSeekColorListener() {
            @Override public void onSeekColorListener(int color) {
                colorView.setBackgroundColor(color);
                if (seekBarChangeListener != null) {
                    seekBarChangeListener.onColorChange(color);
                }
            }
        });

        viewSheetBg.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                disMissSheet();
            }
        });

        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                System.out.println(progress);
                if (seekBarChangeListener != null) {
                    seekBarChangeListener.onProgress(progress);
                }
                tvProgress.setText(progress + "%");

            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    public void showSheet() {
        sheetContent.setVisibility(View.VISIBLE);
        sheetContent.bringToFront();

        llSheet.setTranslationY(activity.getResources().getDisplayMetrics().heightPixels);
        ValueAnimator alphaAnimator = ValueAnimator.ofFloat(0, 1);
        alphaAnimator.setInterpolator(new DecelerateInterpolator());
        alphaAnimator.setDuration(300);
        alphaAnimator.addUpdateListener(new AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                viewSheetBg.setAlpha(alpha);
            }
        });
        alphaAnimator.start();
        ValueAnimator translationAniamtor = ValueAnimator.ofFloat(activity.getResources().getDisplayMetrics().heightPixels, 0);
        translationAniamtor.setInterpolator(new DecelerateInterpolator());
        translationAniamtor.setDuration(300);
        translationAniamtor.addUpdateListener(new AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                float translationY = (float) animation.getAnimatedValue();
                llSheet.setTranslationY(translationY);
            }
        });
        translationAniamtor.start();

    }

    public void disMissSheet() {
        if (viewSheetBg != null && llSheet != null) {
            disMissSheet(viewSheetBg, llSheet);
        }
    }

    private void disMissSheet(final View viewSheetBg, final View llSheet) {
        ValueAnimator alphaAnimator = ValueAnimator.ofFloat(1, 0);
        alphaAnimator.setInterpolator(new DecelerateInterpolator());
        alphaAnimator.setDuration(300);
        alphaAnimator.addUpdateListener(new AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                viewSheetBg.setAlpha(alpha);
            }
        });
        alphaAnimator.start();

        ValueAnimator translationAniamtor = ValueAnimator.ofFloat(0, activity.getResources().getDisplayMetrics().heightPixels);
        translationAniamtor.setInterpolator(new DecelerateInterpolator());
        translationAniamtor.setDuration(300);
        translationAniamtor.addUpdateListener(new AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                float translationY = (float) animation.getAnimatedValue();
                llSheet.setTranslationY(translationY);
            }
        });
        translationAniamtor.start();
        translationAniamtor.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                contentView.removeView(sheetContent);
            }
        });
    }

    public boolean isShowingSheet() {
        if (contentView == null) {
            return false;
        }
        if (contentView.findViewById(R.id.view_sheet_bg) == null) {
            return false;
        } else {
            return true;
        }
    }


    public interface SeekBarChangeListener {
        void onProgress(float progress);

        void onColorChange(int color);
    }

    private SeekBarChangeListener seekBarChangeListener;

    public void setSeekBarChangeListener(SeekBarChangeListener seekBarChangeListener) {
        this.seekBarChangeListener = seekBarChangeListener;
    }
}
