package com.haife.soucesproject.material_animation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SharedElementFragment2 extends Fragment {
    private static final String EXTRA_SAMPLE = "sample";

    public static SharedElementFragment2 newInstance(Sample sample) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_SAMPLE, sample);
        SharedElementFragment2 sharedElementFragment2 = new SharedElementFragment2();
        sharedElementFragment2.setArguments(bundle);
        return sharedElementFragment2;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shared_element_fragment2, container, false);
        Sample sample = (Sample) getArguments().getSerializable(EXTRA_SAMPLE);
        ImageView squareBlue = (ImageView) view.findViewById(R.id.square_blue);
        DrawableCompat.setTint(squareBlue.getDrawable(), sample.color);
        return view;
    }

}
