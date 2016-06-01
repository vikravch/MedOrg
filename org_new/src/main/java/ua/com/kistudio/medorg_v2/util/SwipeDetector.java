package ua.com.kistudio.medorg_v2.util;

import android.view.MotionEvent;

public class SwipeDetector {

    private int swipe_distance;
    private int swipe_velocity;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    public SwipeDetector(int distance, int velocity) {
        super();
        this.swipe_distance = distance;
        this.swipe_velocity = velocity;
    }

    // Конструктор
    public SwipeDetector() {
        super();
        this.swipe_distance = SWIPE_MIN_DISTANCE;
        this.swipe_velocity = SWIPE_THRESHOLD_VELOCITY;
    }
    /*
        public boolean isNotSwipe(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            return  ! isSwipeLeft(e1, e2, velocityX) &&
                    ! isSwipeRight(e1, e2, velocityX) &&
                    ! isSwipeUp(e1, e2, velocityY) &&
                    ! isSwipeDown(e1, e2, velocityY);
        }
    */
    public boolean isSwipeDown(MotionEvent e1, MotionEvent e2, float velocityY) {
        return isSwipe(e2.getY(), e1.getY(), velocityY);
    }

    public boolean isSwipeUp(MotionEvent e1, MotionEvent e2, float velocityY) {
        return isSwipe(e1.getY(), e2.getY(), velocityY);
    }

    public boolean isSwipeLeft(MotionEvent e1, MotionEvent e2, float velocityX) {
        return isSwipe(e1.getX(), e2.getX(), velocityX);
    }

    public boolean isSwipeRight(MotionEvent e1, MotionEvent e2, float velocityX) {
        return isSwipe(e2.getX(), e1.getX(), velocityX);
    }

    private boolean isSwipeDistance(float coordinateA, float coordinateB) {
        return (coordinateA - coordinateB) > this.swipe_distance;
    }

    private boolean isSwipeSpeed(float velocity) {
        return Math.abs(velocity) > this.swipe_velocity;
    }

    private boolean isSwipe(float coordinateA, float coordinateB, float velocity) {
        return isSwipeDistance(coordinateA, coordinateB)
                && isSwipeSpeed(velocity);
    }
}

