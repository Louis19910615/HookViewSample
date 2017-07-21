package louis.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by louis on 2017/7/21.
 * 疑问：
 * 1.如何比较两个动画的性能好坏？
 * 2.为什么用animation，与view直接invalidate()的区别？
 */

public class HookView extends View {

    //绘制圆弧的进度值
    private int progress = 0;
    //线1的x轴
    private int line1_x = 0;
    //线1的y轴
    private int line1_y = 0;
    //线2的x轴
    private int line2_x = 0;
    //线2的y轴
    private int line2_y = 0;

    public HookView(Context context) {
        super(context);
    }

    public HookView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HookView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //绘制

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        progress++;

        /**
         * 绘制圆弧
         */
        Paint paint = new Paint();
        //设置画笔颜色
        paint.setColor(getResources().getColor(R.color.arc_blue));
        //设置圆弧的宽度
        paint.setStrokeWidth(5);
        //设置圆弧为空心
        paint.setStyle(Paint.Style.STROKE);
        //消除锯齿
        paint.setAntiAlias(true);

        //获取圆心的x坐标
        int center = getWidth() / 2;
        int center1 = center - getWidth() / 5;
        //圆弧半径
        int radius = getWidth() / 2 - 5;

//        //定义的圆弧的形状和大小的界限
//        RectF rectF = new RectF(center - radius -1, center - radius -1 ,center + radius + 1, center + radius + 1);
//
//        //根据进度画圆弧
//        canvas.drawArc(rectF, 235, -360 * progress / 100, false, paint);
//
//        /**
//         * 绘制对勾
//         */
//        //先等圆弧画完，才话对勾
//        if(progress >= 100) {
        //画第一根线
        canvas.drawLine(center1, center, center1 + line1_x, center + line1_y, paint);
        //画第二根线
        canvas.drawLine(center1 + line1_x - 1, center + line1_y, center1 + line2_x, center + line2_y, paint);
//        }

        //每隔10毫秒界面刷新
//        postInvalidateDelayed(5);
    }


    public void startTickAnim () {
        invalidate();
        Animation tickAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                int radius = getWidth() / 2 - 5;
                if (interpolatedTime < 1 / 3.0f) {
                    line1_x = (int) (interpolatedTime * radius);
                    line1_y = (int) (interpolatedTime * radius);
                    line2_x = 0;
                    line2_y = 0;
                }

                if (interpolatedTime == 1 / 3.0f) {
                    line2_x = line1_x;
                    line2_y = line1_y;
                    line1_x = (int) (interpolatedTime * radius);
                    line1_y = (int) (interpolatedTime * radius);
                }

                if (interpolatedTime > 1 / 3.0f) {
                    line2_x = (int) (interpolatedTime * radius);
                    line2_y = (int) ((2 / 3.0f - interpolatedTime) * radius);

                }


                invalidate();

            }
        };
        tickAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        tickAnim.setDuration(1000);

        //无限循环动画
        tickAnim.setRepeatCount(Animation.INFINITE);
        tickAnim.setRepeatMode(Animation.RESTART);

        tickAnim.setStartOffset(100);
        startAnimation(tickAnim);
    }
}
