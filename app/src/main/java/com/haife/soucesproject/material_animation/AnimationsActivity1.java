package com.haife.soucesproject.material_animation;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.haife.soucesproject.material_animation.databinding.ActivityAnimations1Binding;

public class AnimationsActivity1 extends BaseDetailActivity {
    private Sample sample;
    private ImageView square;
    private ViewGroup viewRoot;
    private boolean sizeChange;
    private int savedWitdh;
    private boolean positionChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindLayout();
        setupWindowAnimation();
        setupLayout();
        setToolBar();
    }

    private void setupWindowAnimation() {
        getWindow().setReenterTransition(new Fade());
    }


    private void bindLayout() {
        ActivityAnimations1Binding binding = DataBindingUtil.setContentView(AnimationsActivity1.this, R.layout.activity_animations1);
        sample = (Sample) getIntent().getExtras().getSerializable(EXTRA_SAMPLE);
        binding.setAnimationSample(sample);
    }

    private void setupLayout() {
        square = (ImageView) findViewById(R.id.square_green);
        viewRoot = (ViewGroup) findViewById(R.id.sample3_root);
        findViewById(R.id.sample3_button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLayout();
            }
        });

        findViewById(R.id.sample3_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosition();
            }
        });

        findViewById(R.id.sample3_button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnimationsActivity1.this, AnimationsActivity2.class);
                i.putExtra(EXTRA_SAMPLE, sample);
                transitionTo(i);
            }
        });
    }


    private void changeLayout() {
        TransitionManager.beginDelayedTransition(viewRoot);
        ViewGroup.LayoutParams params = square.getLayoutParams();
        if (sizeChange) {
            params.width = savedWitdh;
        } else {
            savedWitdh = params.width;
            params.width = 200;
        }
        sizeChange = !sizeChange;
        square.setLayoutParams(params);
    }

    private void changePosition() {
        TransitionManager.beginDelayedTransition(viewRoot);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) square.getLayoutParams();
        if (positionChanged) {
            params.gravity = Gravity.CENTER;
        } else {
            params.gravity = Gravity.LEFT;
        }
        positionChanged = !positionChanged;
        square.setLayoutParams(params);
    }

}
