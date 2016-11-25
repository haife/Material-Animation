package com.haife.soucesproject.material_animation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SharedElementFragment1 extends Fragment {
    private static final String EXTRA_SAMPLE = "sample";

    public static SharedElementFragment1 newIntstance(Sample sample) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_SAMPLE, sample);
        SharedElementFragment1 elementFragment1 = new SharedElementFragment1();
        elementFragment1.setArguments(bundle);
        return elementFragment1;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shared_element_fragment1, container, false);
        final Sample sample = (Sample) getArguments().getSerializable(EXTRA_SAMPLE);

        final ImageView squareBlue = (ImageView) view.findViewById(R.id.square_blue);
        DrawableCompat.setTint(squareBlue.getDrawable(), sample.color);
        view.findViewById(R.id.sample2_button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNextFragment(sample, squareBlue, false);
            }
        });

        view.findViewById(R.id.sample2_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNextFragment(sample, squareBlue, true);
            }
        });


        return view;
    }

    private void addNextFragment(Sample sample, ImageView blue, boolean b) {
        SharedElementFragment2 elementFragment2 = SharedElementFragment2.newInstance(sample);

        Slide slide = new Slide();
        slide.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        slide.setSlideEdge(Gravity.RIGHT);

        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(getResources().getInteger(R.integer.anim_duration_medium));

        elementFragment2.setEnterTransition(slide);
        elementFragment2.setAllowEnterTransitionOverlap(b);
        elementFragment2.setAllowReturnTransitionOverlap(b);
        elementFragment2.setSharedElementEnterTransition(changeBounds);

        getFragmentManager().beginTransaction().replace(R.id.sample2_content, elementFragment2).addToBackStack(null).addSharedElement(blue, getString(R.string.square_blue_name)).commit();
    }

}
