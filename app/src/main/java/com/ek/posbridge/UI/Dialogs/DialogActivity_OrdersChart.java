package com.ek.posbridge.UI.Dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.ArcMotion;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import com.ek.posbridge.Morph.MorphDialogToFab;
import com.ek.posbridge.Morph.MorphFabToDialog;
import com.ek.posbridge.Morph.MorphTransition;
import com.ek.posbridge.R;

public class DialogActivity_OrdersChart extends AppCompatActivity {

        private ViewGroup container;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dialog_orderschart);

            container =  findViewById(R.id.container);

            setupSharedEelementTransitions1();

            View.OnClickListener dismissListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            };
            container.setOnClickListener(dismissListener);
            container.findViewById(R.id.close).setOnClickListener(dismissListener);
        }

        public void setupSharedEelementTransitions1() {
            ArcMotion arcMotion = new ArcMotion();
//            arcMotion.setMinimumHorizontalAngle(50f);
//            arcMotion.setMinimumVerticalAngle(50f);

            Interpolator easeInOut = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in);

            MorphFabToDialog sharedEnter = new MorphFabToDialog();
            sharedEnter.setPathMotion(arcMotion);
            sharedEnter.setInterpolator(easeInOut);

            MorphDialogToFab sharedReturn = new MorphDialogToFab();
            sharedReturn.setPathMotion(arcMotion);
            sharedReturn.setInterpolator(easeInOut);

            if (container != null) {
                sharedEnter.addTarget(container);
                sharedReturn.addTarget(container);
            }
            getWindow().setSharedElementEnterTransition(sharedEnter);
            getWindow().setSharedElementReturnTransition(sharedReturn);
        }

        public void setupSharedEelementTransitions2() {
            ArcMotion arcMotion = new ArcMotion();
//            arcMotion.setMinimumHorizontalAngle(50f);
//            arcMotion.setMinimumVerticalAngle(50f);

            Interpolator easeInOut = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in);


            MorphTransition sharedEnter = new MorphTransition(ContextCompat.getColor(this, R.color.white),
                    ContextCompat.getColor(this, R.color.dialog_background_color), 100, getResources().getDimensionPixelSize(R.dimen.dialog_corners), true);
            sharedEnter.setPathMotion(arcMotion);
            sharedEnter.setInterpolator(easeInOut);

            MorphTransition sharedReturn = new MorphTransition(ContextCompat.getColor(this, R.color.white),
                    ContextCompat.getColor(this, R.color.colorPrimary), getResources().getDimensionPixelSize(R.dimen.dialog_corners), 100,  false);
            sharedReturn.setPathMotion(arcMotion);
            sharedReturn.setInterpolator(easeInOut);

            if (container != null) {
                sharedEnter.addTarget(container);
                sharedReturn.addTarget(container);
            }
            getWindow().setSharedElementEnterTransition(sharedEnter);
            getWindow().setSharedElementReturnTransition(sharedReturn);
        }

        @Override
        public void onBackPressed() {
            dismiss();
        }

        public void dismiss() {
            setResult(Activity.RESULT_CANCELED);
            finishAfterTransition();
        }

    }

