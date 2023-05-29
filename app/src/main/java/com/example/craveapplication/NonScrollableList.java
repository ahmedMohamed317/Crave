package com.example.craveapplication;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.recyclerview.widget.RecyclerView;

public class NonScrollableList extends RecyclerView {

        private boolean scrollingEnabled = true;

        public NonScrollableList(Context context) {
            super(context);
        }

        public NonScrollableList(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public NonScrollableList(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        public boolean onTouchEvent(MotionEvent e) {
            return scrollingEnabled && super.onTouchEvent(e);
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent e) {
            return scrollingEnabled && super.onInterceptTouchEvent(e);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            if (scrollingEnabled) {
                getParent().requestDisallowInterceptTouchEvent(true);
                return super.dispatchTouchEvent(ev);
            } else {
                getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        }

        public void setScrollingEnabled(boolean scrollingEnabled) {
            this.scrollingEnabled = scrollingEnabled;
        }
    }

