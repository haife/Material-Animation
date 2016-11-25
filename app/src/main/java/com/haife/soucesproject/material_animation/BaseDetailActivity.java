package com.haife.soucesproject.material_animation;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class BaseDetailActivity extends AppCompatActivity {
    static final String EXTRA_SAMPLE = "sample";
    static final String EXTRA_TYPE = "type";
    static final int TYPE_PROGRAMMATICALLY = 0;
    static final int TYPE_XML = 1;


    void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @SuppressWarnings("unchecked")
    void transitionTo(Intent intent) {
        Pair<View, String>[] pair = TransitionHelper.createSafeTransitionParticipants(this, true);
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair);
        startActivity(intent, compat.toBundle());
    }


}
