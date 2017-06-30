package studentsdemo.lieeber.com.lbview.viewgroups;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lieeber on 2017/6/30.
 * 自定义流式布局
 */

public class LBFlowLayout extends ViewGroup {

    public LBFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);


        // 如果是warp_content情况下，记录宽和高
        int width = 0;
        int height = 0;

        int lineWidth = 0;
        int lineHeight = 0;
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            // 测量每一个child的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int cWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int cHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if (lineWidth + cWidth > sizeWidth) {
                width = Math.max(lineWidth, cWidth);
                height += lineHeight;
                //如果单个控件的宽度或者高度比ViewGroup的高和宽还要多，重新测量子控件
                if (cWidth > sizeWidth) {
                    measureChild(child, MeasureSpec.makeMeasureSpec(sizeWidth - lp.leftMargin, modeWidth), heightMeasureSpec);
                }
                lineHeight = cHeight;
                lineWidth = cWidth;
            } else {
                lineWidth += cWidth;
                lineHeight = Math.max(cHeight, lineHeight);
                //如果单个控件的宽度或者高度比ViewGroup的高和宽还要多，重新测量子控件
                if (cHeight > sizeHeight) {
                    measureChild(child, widthMeasureSpec, MeasureSpec.makeMeasureSpec(sizeHeight - lp.bottomMargin, modeHeight));
                }
            }
            if (i == cCount - 1) {
                height += lineHeight;
                width = Math.max(lineWidth, width);
            }

            setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth : width, (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight : height);
        }
    }

    @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = getWidth();
        int height = getHeight();
        int left = 0;
        int lineHeight = 0;
        int cCount = getChildCount();
        int top = 0;
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            // 测量每一个child的宽和高
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int cWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int cHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if (left + cWidth > width) {
                left = 0;
                top += lineHeight;
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();
                //如果单个控件的宽度或者高度比ViewGroup的高和宽还要多，就需要做下面的处理
                if (rc > width - lp.rightMargin) {
                    rc = width - lp.rightMargin;
                }

                if (bc > height - lp.bottomMargin) {
                    bc = height - lp.bottomMargin;
                }
                child.layout(lc, tc, rc, bc);

                lineHeight = cHeight;
                left = cWidth;
            } else {
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                child.layout(lc, tc, rc, bc);

                left += cWidth;
                lineHeight = Math.max(cHeight, lineHeight);
            }
        }
    }


    @Override protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }
}
