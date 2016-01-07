package com.example.weimin.android;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class Animation extends Activity {

    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        image = (ImageView) findViewById(R.id.am);
        TranslateAnimation at = new TranslateAnimation(10, 100, 10, 150);
//        TranslateAnimation ta=new TranslateAnimation()
        at.setDuration(2000);
        at.setRepeatCount(2);
        at.setRepeatMode(android.view.animation.Animation.REVERSE);
        image.startAnimation(at);
        AnimationSet set = new AnimationSet(false);//属性动画一起飞
        set.addAnimation(at);
        set.start();
    }
    public void attributeAnimation() {



        //旋转 缩放透明均使用改Api  只是构造方法有出入 ，函数重载 ，
        ObjectAnimator am = ObjectAnimator.ofFloat(image, "TranslationX", 10, 100);
        am.setDuration(5000);
        am.setRepeatCount(2);
        am.setRepeatMode(ValueAnimator.REVERSE);
        AnimatorSet set=new AnimatorSet();
        set.playSequentially(am,am);  //序列化播放  顺序添加一些列参数
        set.playTogether();
        am.start();

    }
}
