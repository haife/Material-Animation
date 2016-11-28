package com.haife.soucesproject.material_animation;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Gravity;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Sample> samples;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupWindowAnimations();
        setupSamples();
        setupToolbar();
        setuplayout();
    }


    private void setupWindowAnimations() {
        Transition slideTracition = TransitionInflater.from(this).inflateTransition(R.transition.slide_from_left);
//        Slide slideTracition = new Slide();
//        slideTracition.setSlideEdge(Gravity.LEFT);
//        slideTracition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        getWindow().setEnterTransition(slideTracition);
        getWindow().setExitTransition(slideTracition);
    }


    private void setupSamples() {
        samples = Arrays.asList(new Sample(ContextCompat.getColor(this, R.color.sample_red), "Transitions"),
                new Sample(ContextCompat.getColor(this, R.color.sample_blue), "Shared Elements"),
                new Sample(ContextCompat.getColor(this, R.color.sample_green), "View animations"),
                new Sample(ContextCompat.getColor(this, R.color.sample_yellow), "Circular Reveal Animation"));
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setuplayout() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sample_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SamplesRecyclerAdapter adapter = new SamplesRecyclerAdapter(this, samples);
        recyclerView.setAdapter(adapter);
    }

}
