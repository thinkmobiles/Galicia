package com.cristaliza.alimentacion.global;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.cristaliza.alimentacion.R;

public abstract class FragmentReplacer {

    public static final void popSupBackStack(final FragmentActivity _activity) {

        _activity.getSupportFragmentManager().popBackStack();
    }

    public static final void popSuperBackStack(final FragmentActivity _activity) {
        _activity.getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public static final int getBackStackEntryCount(final FragmentActivity _activity) {
        return _activity.getFragmentManager().getBackStackEntryCount();
    }

    public static final int getSupBackStackEntryCount(final FragmentActivity _activity) {
        return _activity.getSupportFragmentManager().getBackStackEntryCount();
    }

    public static final void clearBackStack(final FragmentActivity _activity){
        _activity.getFragmentManager().popBackStack(null, _activity.getFragmentManager().POP_BACK_STACK_INCLUSIVE);
    }

    public static final void clearSupBackStack(final FragmentActivity _activity){
        _activity.getSupportFragmentManager().popBackStack(null, _activity.getFragmentManager().POP_BACK_STACK_INCLUSIVE);
    }

//    public static final void replaceTopNavigationFragment(final FragmentActivity _activity,
//                                                          final Fragment _fragment) {
//         _activity.getSupportFragmentManager().beginTransaction()
//                .replace(R.id.container, _fragment)
//                .commit();
//    }

    public static final void replaceTopNavigationFragment(final FragmentActivity _activity,
                                                          final android.support.v4.app.Fragment _fragment) {
        _activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, _fragment)
                .commit();
    }
    public static final void replaceFragmentWithoutBackStack(final FragmentActivity _activity,
                                                             final android.support.v4.app.Fragment _fragment) {
        if ( _activity.getSupportFragmentManager().getFragments() == null ||
                !((Object) _activity.getSupportFragmentManager().getFragments().get(0)).getClass().getName().
                        equals(((Object)_fragment).getClass().getName())) {
            _activity.getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            _activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, _fragment)
                    .commit();

        }
    }

    public static final void replaceCurrentFragment(final FragmentActivity _activity,
                                                    final Fragment _fragment) {
        _activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, _fragment)
                .addToBackStack(null)
                .commit();

    }

//    public static final void replaceFragmentWithAnim(final FragmentActivity _activity,
//                                                     final Fragment _fragment) {
//        _activity.getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                .setCustomAnimations(R.anim.anim_in, R.anim.anim_out)//,
////                        R.anim.anim_out,R.anim.anim_in)
//                .replace(R.id.container, _fragment)
//                .addToBackStack(null).commit();
////        manageBackButton(_activity, true);
//    }

    public static final void addFragment(final FragmentActivity _context,
                                         final Fragment _fragment) {

        _context.getSupportFragmentManager().beginTransaction()
                .add(R.id.container, _fragment)
                .addToBackStack(null)
                .commit();
    }

    public static final void replaceFragmentWithAnim(final FragmentActivity _activity,
                                                     final android.support.v4.app.Fragment _fragment) {
        _activity.getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setCustomAnimations(R.anim.anim_in, R.anim.anim_out)//,
//                        R.anim.anim_out,R.anim.anim_in)
                .replace(R.id.container, _fragment)
                .addToBackStack(null).commit();
//        manageBackButton(_activity, true);
    }

//

    public static final void replaceFragmentWithStack(final FragmentActivity _activity, final android.support.v4.app.Fragment _fragment) {
        _activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, _fragment)
                .setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out)
                .addToBackStack(_fragment.getClass().getName())
                .commit();
    }

}
