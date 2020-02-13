package com.zzh.sl.empty;

public abstract class CountEmptyStrategy implements HStateEmptyStrategy {
    private final int mEmptyCount;

    public CountEmptyStrategy(int emptyCount) {
        if (emptyCount < 0)
            throw new IllegalArgumentException("emptyCount must >= 0");

        mEmptyCount = emptyCount;
    }

    @Override
    public boolean isDestroyed() {
        return false;
    }

    @Override
    public final HStateEmptyStrategy.Result getResult() {
        final int count = getCount();
        if (count < 0) {
            return Result.None;
        } else if (count <= mEmptyCount) {
            return Result.Empty;
        } else {
            return Result.Content;
        }
    }

    protected abstract int getCount();
}
