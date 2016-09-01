package com.kejiang.yuandl.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kejiang.yuandl.R;

import java.math.BigDecimal;

/**
 * Created by hedge_hog on 2015/6/11.
 * <p/>
 * add halfstar show
 * <p/>
 * Correction clickEvent from Xml
 */
public class RatingBar extends LinearLayout {
    private boolean mClickable;
    private int starCount;
    private OnRatingChangeListener onRatingChangeListener;
    private float starImageSize;
    private float starStep;
    private Drawable starEmptyDrawable;
    private Drawable starFillDrawable;
    private Drawable starHalfDrawable;
    private final StepSize stepSize;

    public void setStarHalfDrawable(Drawable starHalfDrawable) {
        this.starHalfDrawable = starHalfDrawable;
    }


    public void setOnRatingChangeListener(OnRatingChangeListener onRatingChangeListener) {
        this.onRatingChangeListener = onRatingChangeListener;
    }

    public void setmClickable(boolean clickable) {
        this.mClickable = clickable;
    }

    public void setStarFillDrawable(Drawable starFillDrawable) {
        this.starFillDrawable = starFillDrawable;
    }

    public void setStarEmptyDrawable(Drawable starEmptyDrawable) {
        this.starEmptyDrawable = starEmptyDrawable;
    }

    public void setStarImageSize(float starImageSize) {
        this.starImageSize = starImageSize;
    }


    /**
     * @param context
     * @param attrs
     */
    public RatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        starImageSize = mTypedArray.getDimension(R.styleable.RatingBar_starImageSize, 20);
        starStep = mTypedArray.getFloat(R.styleable.RatingBar_starStep, 1.0f);
        stepSize = StepSize.fromStep(mTypedArray.getInt(R.styleable.RatingBar_stepSize, 1));
        starCount = mTypedArray.getInteger(R.styleable.RatingBar_starCount, 5);
        starEmptyDrawable = mTypedArray.getDrawable(R.styleable.RatingBar_starEmpty);
        starFillDrawable = mTypedArray.getDrawable(R.styleable.RatingBar_starFill);
        starHalfDrawable = mTypedArray.getDrawable(R.styleable.RatingBar_starHalf);
        mClickable = mTypedArray.getBoolean(R.styleable.RatingBar_clickable, true);
        mTypedArray.recycle();
        for (int i = 0; i < starCount; ++i) {
            final ImageView imageView = getStarImageView(context, attrs);
            imageView.setImageDrawable(starEmptyDrawable);
            imageView.setOnClickListener(
                    new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mClickable) {

                                //浮点数的整数部分
                                int fint = (int) starStep;
                                BigDecimal b1 = new BigDecimal(Float.toString(starStep));
                                BigDecimal b2 = new BigDecimal(Integer.toString(fint));
                                //浮点数的小数部分
                                float fPoint = b1.subtract(b2).floatValue();
                                if (fPoint == 0) {
                                    fint -= 1;
                                }

                                if (indexOfChild(v) > fint) {
                                    setStar(indexOfChild(v) + 1);
                                } else if (indexOfChild(v) == fint) {
                                    if(stepSize==StepSize.Full){
                                        return;
                                    }
                                    if (imageView.getDrawable().getCurrent().getConstantState().equals(starHalfDrawable.getConstantState())) {
                                        setStar(indexOfChild(v) + 1);
                                    } else {
                                        setStar(indexOfChild(v) + 0.5f);
                                    }
                                } else {
                                    setStar(indexOfChild(v) + 1f);
                                }

                            }

                        }
                    }
            );
            addView(imageView);
        }
        setStar(starStep);
    }

    /**
     * @param context
     * @param attrs
     * @return
     */
    private ImageView getStarImageView(Context context, AttributeSet attrs) {
        ImageView imageView = new ImageView(context);
        ViewGroup.LayoutParams para = new ViewGroup.LayoutParams(
                Math.round(starImageSize),
                Math.round(starImageSize)
        );
        imageView.setLayoutParams(para);
        imageView.setPadding(0, 0, 5, 0);
        imageView.setImageDrawable(starEmptyDrawable);
        imageView.setMaxWidth(10);
        imageView.setMaxHeight(10);
        return imageView;

    }


    /**
     * setting start
     *
     * @param rating
     */

    public void setStar(float rating) {

        if (onRatingChangeListener != null) {
            onRatingChangeListener.onRatingChange(rating);
        }
        this.starStep = rating;
        //浮点数的整数部分
        int fint = (int) rating;
        BigDecimal b1 = new BigDecimal(Float.toString(rating));
        BigDecimal b2 = new BigDecimal(Integer.toString(fint));
        //浮点数的小数部分
        float fPoint = b1.subtract(b2).floatValue();

        //drawfullstar
        for (int i = 0; i < fint; ++i) {
            ((ImageView) getChildAt(i)).setImageDrawable(starFillDrawable);
        }
        for (int i = fint; i < starCount; i++) {
            ((ImageView) getChildAt(i)).setImageDrawable(starEmptyDrawable);
        }

        if (fPoint > 0) {
            ((ImageView) getChildAt(fint)).setImageDrawable(starHalfDrawable);
        }
    }

//  /**
//     * setting start
//     *
//     * @param starCount
//     */
//
//    public void setStar(float starCount) {
//
//        //浮点数的整数部分
//        int fint = (int) starCount;
//        BigDecimal b1 = new BigDecimal(Float.toString(starCount));
//        BigDecimal b2 = new BigDecimal(Integer.toString(fint));
//        //浮点数的小数部分
//        float fPoint = b1.subtract(b2).floatValue();
//        Logger.d("fint=" + fint);
//        Logger.d("fPoint=" + fPoint);
//
//        starCount = fint > this.starCount ? this.starCount : fint;
//        starCount = starCount < 0 ? 0 : starCount;
//
//        //drawfullstar
//        for (int i = 0; i < starCount; ++i) {
//            ((ImageView) getChildAt(i)).setImageDrawable(starFillDrawable);
//        }
//
//        //drawhalfstar
//        if (fPoint > 0) {
//            ((ImageView) getChildAt(fint)).setImageDrawable(starHalfDrawable);
//
//            //drawemptystar
//            for (int i = this.starCount - 1; i >= starCount + 1; --i) {
//                ((ImageView) getChildAt(i)).setImageDrawable(starEmptyDrawable);
//            }
//
//
//        } else {
//            //drawemptystar
//            for (int i = this.starCount - 1; i >= starCount; --i) {
//                ((ImageView) getChildAt(i)).setImageDrawable(starEmptyDrawable);
//            }
//
//        }
//
//
//    }
//

    /**
     * change stat listener
     */
    public interface OnRatingChangeListener {

        void onRatingChange(float RatingCount);

    }

}
