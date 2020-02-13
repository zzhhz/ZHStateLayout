package com.zzh.sl.empty;

/**
 * 显示无内容状态策略接口
 */
public interface HStateEmptyStrategy {
    boolean isDestroyed();

    Result getResult();

    enum Result {
        Empty,
        Content,
        None,
    }
}
