package com.haife.soucesproject.material_animation;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by haife
 * on 2016/11/23.
 */

public class TransitionHelper {

    public static Pair<View, String>[] createSafeTransitionParticipants(@NonNull Activity activity, boolean includeStatusBar, @Nullable Pair... otherParticipants) {
        View deco = activity.getWindow().getDecorView();
        View status = null;
        if (includeStatusBar) {
            status = deco.findViewById(android.R.id.statusBarBackground);
        }
        View navBar = deco.findViewById(android.R.id.navigationBarBackground);
        List<Pair> pairst = new ArrayList<>(3);
        addNonNullViewToTransitionParticipants(status, pairst);
        addNonNullViewToTransitionParticipants(navBar, pairst);
        if (otherParticipants != null && !(otherParticipants.length == 1 && otherParticipants[0] == null)) {
            pairst.addAll(Arrays.asList(otherParticipants));
        }
        return pairst.toArray(new Pair[pairst.size()]);
    }

    private static void addNonNullViewToTransitionParticipants(View view, List<Pair> paticipants) {
        if (view == null) {
            return;
        }
        paticipants.add(new Pair(view, view.getTransitionName()));
    }
}
