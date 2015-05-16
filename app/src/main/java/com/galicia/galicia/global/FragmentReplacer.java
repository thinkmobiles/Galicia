package com.galicia.galicia.global;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.galicia.galicia.R;


/**
 * Created by Виталий on 09/10/2014.
 */
public abstract class FragmentReplacer {

    public static final void popBackStack(final FragmentActivity _activity) {
        _activity.getFragmentManager().popBackStack();
    }

    public static final void popSupBackStack(final FragmentActivity _activity) {
        _activity.getSupportFragmentManager().popBackStack();
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

    public static final void replaceTopNavigationFragment(final Activity _activity,
                                                          final Fragment _fragment) {
         _activity.getFragmentManager().beginTransaction()
                .replace(R.id.container, _fragment)
                .commit();
    }

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

    public static final void replaceCurrentFragment(final Activity _activity,
                                                    final Fragment _fragment) {
        _activity.getFragmentManager().beginTransaction()
                .replace(R.id.container, _fragment)
                .addToBackStack(null)
                .commit();

    }

    public static final void replaceFragmentWithAnim(final FragmentActivity _activity,
                                                     final Fragment _fragment) {
        _activity.getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setCustomAnimations(R.anim.anim_in, R.anim.anim_out)//,
//                        R.anim.anim_out,R.anim.anim_in)
                .replace(R.id.container, _fragment)
                .addToBackStack(null).commit();
//        manageBackButton(_activity, true);
    }

    public static final void addFragment(final Activity _context,
                                         final Fragment _fragment) {

        _context.getFragmentManager().beginTransaction()
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

//    public static final void manageBackButton(final FragmentActivity _activity, final boolean _doNeedToShow) {
//        if (_activity instanceof MainFragmentActivity) {

//        }
//    }
//
//    public static final void manageLikeButton(final FragmentActivity _activity, final boolean _doNeedToShow) {
//        if (_activity instanceof MainFragmentActivity) {
//            ((MainFragmentActivity) _activity).showLikeButton(_doNeedToShow);
//        }
//    }
//    public static final void clearBackStack(final FragmentActivity _activity) {
//        _activity.getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//    }


//    ////////////////////////////MY Methods

    public static final void replaceFragmentWithStack(final FragmentActivity _activity, final android.support.v4.app.Fragment _fragment) {
        _activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, _fragment)
                .addToBackStack(_fragment.getClass().getName())
                .commit();
    }

}
