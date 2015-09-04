package com.cristaliza.nacional.global;

import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.cristaliza.nacional.R;

public abstract class FragmentReplacer {

    public static void popSupBackStack(final FragmentActivity _activity) {
        _activity.getSupportFragmentManager().popBackStack();
    }

    public static int getSupBackStackEntryCount(final FragmentActivity _activity) {
        return _activity.getSupportFragmentManager().getBackStackEntryCount();
    }

    public static void clearSupBackStack(final FragmentActivity _activity){
        _activity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public static void addFragment(final FragmentActivity _activity, final Fragment _fragment) {
        _activity.getSupportFragmentManager().beginTransaction()
                .add(R.id.container, _fragment)
                .addToBackStack(null)
                .commit();
    }

    public static void replaceFragmentWithStack(final FragmentActivity _activity, final Fragment _fragment) {
        _activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, _fragment)
                .setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out)
                .addToBackStack(_fragment.getClass().getName())
                .commit();
    }

}
