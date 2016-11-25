package com.haife.soucesproject.material_animation;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.View;

import com.haife.soucesproject.material_animation.databinding.ActivityTranstion3Binding;

public class TranstionActivity3 extends BaseDetailActivity {
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindData();
        setupWindowAnimation();
        setupLayout();
        setToolBar();
    }

    private void bindData() {
        ActivityTranstion3Binding binding = DataBindingUtil.setContentView(this, R.layout.activity_transtion3);
        Sample sample = (Sample) getIntent().getExtras().getSerializable(EXTRA_SAMPLE);
        type = getIntent().getExtras().getInt(EXTRA_TYPE);
        binding.setTranstion3Sample(sample);
    }

    private void setupWindowAnimation() {
        Transition transition;
        if (type == TYPE_PROGRAMMATICALLY) {
            /*也可通过下面buildEnterTransition()实现*/
            transition = TransitionInflater.from(this).inflateTransition(R.transition.slide_from_right);
        } else {
            transition = TransitionInflater.from(this).inflateTransition(R.transition.slide_from_bottom);
        }
        getWindow().setEnterTransition(transition);
    }

    private void setupLayout() {
        findViewById(R.id.exit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });
    }
    private Visibility buildEnterTransition() {
        Slide slide = new Slide();
        slide.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        slide.setSlideEdge(Gravity.RIGHT);
        return slide;
    }
}
