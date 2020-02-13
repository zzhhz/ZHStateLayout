package com.zzh.sl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.zzh.sl.empty.AdapterViewEmptyStrategy;
import com.zzh.sl.empty.CombineEmptyStrategy;
import com.zzh.sl.empty.HStateEmptyStrategy;
import com.zzh.sl.empty.RecyclerViewEmptyStrategy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * 调用{@link #autoEmpty()}方法之后，自动设置空布局策略
 */
public class HAutoEmptyStateLayout extends HStateLayout {
    public HAutoEmptyStateLayout(Context context) {
        super(context);
    }

    public HAutoEmptyStateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HAutoEmptyStateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private boolean mAutoEmpty = false;

    /**
     * 设置自动空布局策略
     */
    public void autoEmpty() {
        mAutoEmpty = true;
        applyAutoEmptyStrategy(getContentView());
    }

    @Override
    public void setEmptyStrategy(HStateEmptyStrategy strategy) {
        mAutoEmpty = false;
        super.setEmptyStrategy(strategy);
    }

    @Override
    protected void onContentViewChanged(View oldView, View newView) {
        super.onContentViewChanged(oldView, newView);
        applyAutoEmptyStrategy(newView);
    }

    private void applyAutoEmptyStrategy(View view) {
        if (!mAutoEmpty)
            return;

        if (view == null) {
            cancelAutoEmptyStrategy();
            return;
        }

        final List<HStateEmptyStrategy> listStrategy = new LinkedList<>();

        final List<View> list = getAllViews(view);
        for (View item : list) {
            if (item instanceof AdapterView) {
                listStrategy.add(new AdapterViewEmptyStrategy((AdapterView) item));
            } else if (item instanceof RecyclerView) {
                listStrategy.add(new RecyclerViewEmptyStrategy((RecyclerView) item));
            }
        }

        final int count = listStrategy.size();
        if (count <= 0) {
            cancelAutoEmptyStrategy();
            return;
        }

        if (count == 1) {
            super.setEmptyStrategy(new AutoEmptyStrategy(listStrategy.get(0)));
        } else {
            final HStateEmptyStrategy[] array = new HStateEmptyStrategy[count];
            super.setEmptyStrategy(new AutoEmptyStrategy(new CombineEmptyStrategy(listStrategy.toArray(array))));
        }
    }

    private void cancelAutoEmptyStrategy() {
        if (getEmptyStrategy() instanceof AutoEmptyStrategy)
            super.setEmptyStrategy(null);
    }

    private static class AutoEmptyStrategy implements HStateEmptyStrategy {
        private final HStateEmptyStrategy mStrategy;

        public AutoEmptyStrategy(HStateEmptyStrategy strategy) {
            if (strategy == null)
                throw new IllegalArgumentException("strategy is null when create " + AutoEmptyStrategy.class.getSimpleName());
            mStrategy = strategy;
        }

        @Override
        public boolean isDestroyed() {
            return mStrategy.isDestroyed();
        }

        @Override
        public Result getResult() {
            return mStrategy.getResult();
        }
    }

    private static List<View> getAllViews(View view) {
        if (view == null)
            throw new IllegalArgumentException("view is null when getAllViews()");

        final List<View> list = new ArrayList<>();

        list.add(view);
        if (view instanceof ViewGroup) {
            final ViewGroup viewGroup = (ViewGroup) view;
            final int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                final View child = viewGroup.getChildAt(i);
                if (child != null)
                    list.addAll(getAllViews(child));
            }
        }
        return list;
    }
}
