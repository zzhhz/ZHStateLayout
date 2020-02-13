package com.zzh.sl.empty;


import android.support.v7.widget.RecyclerView;

public class RecyclerAdapterEmptyStrategy extends SourceCountEmptyStrategy<RecyclerView.Adapter> {
    public RecyclerAdapterEmptyStrategy(RecyclerView.Adapter source) {
        super(source);
    }

    public RecyclerAdapterEmptyStrategy(RecyclerView.Adapter source, int emptyCount) {
        super(source, emptyCount);
    }

    @Override
    protected int getCount() {
        return getSource().getItemCount();
    }
}
