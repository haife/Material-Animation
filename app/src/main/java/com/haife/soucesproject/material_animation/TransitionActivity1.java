package com.haife.soucesproject.material_animation;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Visibility;
import android.view.View;

import com.haife.soucesproject.material_animation.databinding.ActivityTransition1Binding;

public class TransitionActivity1 extends BaseDetailActivity {
    private Sample sample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindData();
        setupWindowAnimations();
        setupLayout();
        setToolBar();
    }

    private void bindData() {
        ActivityTransition1Binding binding = DataBindingUtil.setContentView(this, R.layout.activity_transition1);
        sample = (Sample) getIntent().getExtras().getSerializable(EXTRA_SAMPLE);
        binding.setTransition1Sample(sample);
    }

    private void setupWindowAnimations() {
        Visibility enterTranstion = buildEnterTransition();
        getWindow().setEnterTransition(enterTranstion);
    }

    private void setupLayout() {
        findViewById(R.id.sample1_button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TransitionActivity1.this, TransitionActivity2.class);
                i.putExtra(EXTRA_SAMPLE, sample);
                i.putExtra(EXTRA_TYPE, TYPE_PROGRAMMATICALLY);
                transitionTo(i);
            }
        });

        findViewById(R.id.sample1_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TransitionActivity1.this, TransitionActivity2.class);
                i.putExtra(EXTRA_SAMPLE, sample);
                i.putExtra(EXTRA_TYPE, TYPE_XML);
                transitionTo(i);
            }
        });

        findViewById(R.id.sample1_button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TransitionActivity1.this, TranstionActivity3.class);
                i.putExtra(EXTRA_SAMPLE, sample);
                i.putExtra(EXTRA_TYPE, TYPE_PROGRAMMATICALLY);
                transitionTo(i);
            }
        });

        findViewById(R.id.sample1_button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TransitionActivity1.this, TranstionActivity3.class);
                i.putExtra(EXTRA_SAMPLE, sample);
                i.putExtra(EXTRA_TYPE, TYPE_XML);
                transitionTo(i);
            }
        });

        findViewById(R.id.sample1_button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Visibility returnTransition = buildExitTransition();
                getWindow().setReturnTransition(returnTransition);
                finishAfterTransition();
            }
        });

        findViewById(R.id.sample1_button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });


    }

    private Visibility buildEnterTransition() {
        Fade enterTransition = new Fade();
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        return enterTransition;
    }

    private Visibility buildExitTransition() {
        Visibility enterTransition = new Slide();
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        return enterTransition;
    }
}
