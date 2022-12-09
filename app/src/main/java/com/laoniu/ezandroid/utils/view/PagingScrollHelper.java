package com.laoniu.ezandroid.utils.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 实现RecycleView分页滚动的工具类
 */
public class PagingScrollHelper {

    View view = null;
    RecyclerView mRecyclerView = null;

    private MyOnScrollListener mOnScrollListener = new MyOnScrollListener();

    private MyOnFlingListener mOnFlingListener = new MyOnFlingListener();
    private int offsetY = 0;
    private int offsetX = 0;

    int startY = 0;
    int startX = 0;

    ORIENTATION mOrientation = ORIENTATION.VERTICAL;

    private OnLoadMoreListener mOnLoadMoreListener;

    enum ORIENTATION {
        HORIZONTAL, VERTICAL, NULL
    }


    /**
     * 其它需求的参数
     */
    public void setParams() {

    }

    public void setUpRecycleView(RecyclerView recycleView, OnLoadMoreListener mOnLoadMoreListener) {
        if (recycleView == null) {
            throw new IllegalArgumentException("recycleView must be not null");
        }
        this.mOnLoadMoreListener = mOnLoadMoreListener;
        mRecyclerView = recycleView;
        //处理滑动
        recycleView.setOnFlingListener(mOnFlingListener);
        //设置滚动监听，记录滚动的状态，和总的偏移量
        recycleView.addOnScrollListener(mOnScrollListener);
        //记录滚动开始的位置
        recycleView.setOnTouchListener(mOnTouchListener);
        //获取滚动的方向
        updateLayoutManger();

    }

    private void initParams() {
        mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //获取itemView的高度
                RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(view);
                int itemHeight = childViewHolder.itemView.getMeasuredHeight();
                Log.e("zwk", "itemHeight=" + itemHeight);
            }
        });
    }

    public void clear() {
        if (null == mRecyclerView) {
            return;
        }
        mRecyclerView.setOnFlingListener(null);
        mRecyclerView.removeOnScrollListener(mOnScrollListener);
    }

    /**
     * 更新LayoutManger
     */
    public void updateLayoutManger() {
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager != null) {
            if (layoutManager.canScrollVertically()) {
                mOrientation = ORIENTATION.VERTICAL;
            } else if (layoutManager.canScrollHorizontally()) {
                mOrientation = ORIENTATION.HORIZONTAL;
            } else {
                mOrientation = ORIENTATION.NULL;
            }
            if (mAnimator != null) {
                mAnimator.cancel();
            }
            startX = 0;
            startY = 0;
            offsetX = 0;
            offsetY = 0;
        }
    }

    ValueAnimator mAnimator = null;
    int newOffset = 0;

    public class MyOnFlingListener extends RecyclerView.OnFlingListener {

        @Override
        public boolean onFling(int velocityX, int velocityY) {
            if (mOrientation == ORIENTATION.NULL) {
                return false;
            }
            //获取开始滚动时所在页面的index
            int p = getStartPageIndex();

            //记录滚动开始和结束的位置
            int endPoint = 0;
            int startPoint = 0;

            //如果是垂直方向
            if (mOrientation == ORIENTATION.VERTICAL) {
                startPoint = offsetY;

                if (velocityY < 0) {
                    p--;
                } else if (velocityY > 0) {
                    p++;
                }
                //更具不同的速度判断需要滚动的方向
                //注意，此处有一个技巧，就是当速度为0的时候就滚动会开始的页面，即实现页面复位
                endPoint = p * mRecyclerView.getHeight();
            } else {
                startPoint = offsetX;
                if (velocityX < 0) {
                    p--;
                } else if (velocityX > 0) {
                    p++;
                }
                endPoint = p * mRecyclerView.getWidth();
            }
//            if (endPoint < 0) {
//                endPoint = 0;
//            }
            //使用动画处理滚动
            if (mAnimator == null) {
                mAnimator = new ValueAnimator().ofInt(startPoint, endPoint);

                mAnimator.setDuration(300);
                mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Object obj = animation.getAnimatedValue();
                        if (obj != null) {
                            int nowPoint = (int) obj;
                            if (mOrientation == ORIENTATION.VERTICAL) {
                                int dy = nowPoint - offsetY;
                                //这里通过RecyclerView的scrollBy方法实现滚动。
                                mRecyclerView.scrollBy(0, dy);
                                if (offsetY < 0) {
                                    startX = 0;
                                    startY = 0;
                                    offsetX = 0;
                                    offsetY = 0;
                                }
                            } else {
                                int dx = nowPoint - offsetX;
                                mRecyclerView.scrollBy(dx, 0);
                                if (offsetX < 0) {
                                    startX = 0;
                                    startY = 0;
                                    offsetX = 0;
                                    offsetY = 0;
                                }
                            }
                        }
                    }
                });
                mAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //回调监听
                        if (null != mOnPageChangeListener) {
                            mOnPageChangeListener.onPageChange(getPageIndex());
                        }
                        doLoadMore();
                    }
                });
            } else {
                mAnimator.cancel();
                mAnimator.setIntValues(startPoint, endPoint);
                Log.e("zwk", "mAnimator.setIntValues------startPoint=" + startPoint
                        + ",endPoint=" + endPoint);
            }

            mAnimator.start();

            return true;
        }
    }

    private boolean mIsSlidingUp = false;

    private void doLoadMore() {
        if (null == mOnLoadMoreListener) {
            return;
        }
        //加载更多
        LinearLayoutManager manager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        //获取最后一个完全显示的itemPosition
        int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
        int itemCount = manager.getItemCount();
        // 判断是否滑动到了最后一个item，并且是向上滑动
        if (lastItemPosition == (itemCount - 1) && mIsSlidingUp) {
            //加载更多
            mOnLoadMoreListener.onLoadMore();
        }

    }

    public class MyOnScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            //newState==0表示滚动停止，此时需要处理回滚
            if (newState == 0 && mOrientation != ORIENTATION.NULL) {
                boolean move;
                int vY = 0;
                int vX = 0;
                if (mOrientation == ORIENTATION.VERTICAL) {
                    int absY = Math.abs(offsetY - startY);
                    //如果滑动的距离超过屏幕的一半表示需要滑动到下一页
                    move = absY > recyclerView.getHeight() / 2;
                    vY = 0;
                    if (move) {
                        vY = offsetY - startY < 0 ? -1000 : 1000;
                    }
                } else {
                    int absX = Math.abs(offsetX - startX);
                    move = absX > recyclerView.getWidth() / 2;
                    if (move) {
                        vX = offsetX - startX < 0 ? -1000 : 1000;
                    }
                }
                mOnFlingListener.onFling(vX, vY);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            //滚动结束记录滚动的偏移量
            offsetY += dy;
            offsetX += dx;

            // 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
            mIsSlidingUp = dy > 0;
        }
    }

    private MyOnTouchListener mOnTouchListener = new MyOnTouchListener();

    public class MyOnTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //手指按下的时候记录开始滚动的坐标
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                startY = offsetY;
                startX = offsetX;
            }
            return false;
        }
    }

    private int getPageIndex() {
        int p = 0;
        if (mOrientation == ORIENTATION.VERTICAL) {
            int height = mRecyclerView.getHeight();
            if (height != 0) {
                p = offsetY / height;
            }
        } else {
            int width = mRecyclerView.getWidth();
            if (width != 0) {
                p = offsetX / width;
            }
        }
        return p;
    }

    private int getStartPageIndex() {
        int p = 0;
        if (mOrientation == ORIENTATION.VERTICAL) {
            int height = mRecyclerView.getHeight();
            if (height != 0) {
                p = startY / height;
            }
        } else {
            int width = mRecyclerView.getWidth();
            if (width != 0) {
                p = startX / width;
            }
        }
        return p;
    }

    OnPageChangeListener mOnPageChangeListener;

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mOnPageChangeListener = listener;
    }

    public interface OnPageChangeListener {

        void onPageChange(int index);
    }

}
