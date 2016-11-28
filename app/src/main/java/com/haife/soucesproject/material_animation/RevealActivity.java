package com.haife.soucesproject.material_animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haife.soucesproject.material_animation.databinding.ActivityRevealBinding;


/**
 * Created by haife
 * on 2016/11/28.
 */
public class RevealActivity extends BaseDetailActivity {
    private static final int DELAY = 100;
    private RelativeLayout bgViewGroup;
    private Toolbar toolbar;
    private Interpolator interpolator;
    private TextView body;
    private View btnRed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindData();
        setupWindowAnimations();
        setupLayout();
        setToolBar();
    }


    private void setupLayout() {
        bgViewGroup = (RelativeLayout) findViewById(R.id.reveal_root);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        body = ((TextView) findViewById(R.id.sample_body));
    }

    private void setupWindowAnimations() {
        interpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.decelerate_cubic);
        setupEnterAnimations();
        setupExitAnimations();

        findViewById(R.id.square_green).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revealGreen();
            }
        });
        btnRed = findViewById(R.id.square_red);
        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                revealRed();
            }
        });
    }

    private void revealRed() {
        final ViewGroup.LayoutParams params = btnRed.getLayoutParams();
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_with_arcmotion);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                animateRevealColor(bgViewGroup,R.color.sample_red);
                body.setText(R.string.reveal_body3);
                body.setTextColor(ContextCompat.getColor(RevealActivity.this, R.color.theme_red_background));
                btnRed.setLayoutParams(params);
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
        TransitionManager.beginDelayedTransition(bgViewGroup, transition);
        final RelativeLayout.LayoutParams relativeRarams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeRarams.addRule(RelativeLayout.CENTER_IN_PARENT);
        btnRed.setLayoutParams(relativeRarams);
    }

    private void revealGreen() {
        animateRevealColor(bgViewGroup, R.color.sample_green);
        body.setText(R.string.reveal_body2);
        body.setTextColor(ContextCompat.getColor(this, R.color.theme_green_background));
    }

    private void animateRevealColor(ViewGroup viewRoot, @ColorRes int color) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        animateRevealColorFromCoordinates(viewRoot, color, cx, cy);
    }

    private Animator animateRevealColorFromCoordinates(ViewGroup root, @ColorRes int color, int cx, int cy) {
        int finalRadius = Math.max(root.getWidth(), root.getHeight());
        final Animator animator = ViewAnimationUtils.createCircularReveal(root, cx, cy, 0, finalRadius);
        root.setBackgroundColor(ContextCompat.getColor(this, color));
        animator.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
        return animator;
    }

    private void setupEnterAnimations() {
        Transition transtion = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_with_arcmotion);
        getWindow().setSharedElementEnterTransition(transtion);
        transtion.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                hideTarget();
                animateRevealShow(toolbar);
                animateButtonIn();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    private void hideTarget() {
        findViewById(R.id.shared_target).setVisibility(View.GONE);
    }

    private void animateButtonIn() {
        for (int i = 0; i < bgViewGroup.getChildCount(); i++) {
            View child = bgViewGroup.getChildAt(i);
            child.animate()
                    .setStartDelay(100 + i * DELAY)
                    .setInterpolator(interpolator)
                    .alpha(1)
                    .scaleX(1)
                    .scaleY(1);
        }
    }

    private void animateRevealShow(ViewGroup toolbar) {
        int cx = (toolbar.getLeft() + toolbar.getRight()) / 2;
        int cy = (toolbar.getTop() + toolbar.getBottom()) / 2;
        int finalRadius = Math.max(toolbar.getWidth(), toolbar.getHeight());
        Animator reveal = ViewAnimationUtils.createCircularReveal(toolbar, cx, cy, 0, finalRadius);
        toolbar.setVisibility(View.VISIBLE);
        reveal.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        reveal.setInterpolator(new AccelerateInterpolator());
        reveal.start();
    }

    /**
     *
     */
    private void setupExitAnimations() {
        Fade returnTransition = new Fade();
        getWindow().setReturnTransition(returnTransition);
        returnTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        returnTransition.setStartDelay(getResources().getInteger(R.integer.anim_duration_medium));
        returnTransition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                transition.removeListener(this);
                animateButtonsOut();
                animateRevealHide(bgViewGroup);
            }


            @Override
            public void onTransitionEnd(Transition transition) {
            }

            @Override
            public void onTransitionCancel(Transition transition) {
            }

            @Override
            public void onTransitionPause(Transition transition) {
            }

            @Override
            public void onTransitionResume(Transition transition) {
            }
        });
    }

    private void animateRevealHide(final RelativeLayout viewRoot) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        int initialRadius = viewRoot.getWidth();

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, initialRadius, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                viewRoot.setVisibility(View.INVISIBLE);
            }
        });
        anim.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        anim.start();
    }

    private void animateButtonsOut() {
        for (int i = 0; i < bgViewGroup.getChildCount(); i++) {
            View child = bgViewGroup.getChildAt(i);
            child.animate()
                    .setStartDelay(i)
                    .setInterpolator(interpolator)
                    .alpha(0)
                    .scaleX(0f)
                    .scaleY(0f);
        }
    }

    private void bindData() {
        ActivityRevealBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_reveal);
        Sample sample = (Sample) getIntent().getExtras().getSerializable(EXTRA_SAMPLE);
        binding.setReveal1Sample(sample);
    }


}
