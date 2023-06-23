package com.jerryai.commons.tool.range;

import java.util.function.BiConsumer;

public final class RangeListFactory {

    private RangeListFactory() {

    }

    /**
     *
     * @return RangeList the default implementation
     */
    public static RangeList getDefaultRangeList() {
        return new DefaultRangeList();
    }

    /**
     *
     * @param emptyRangeConsumer could be null, the consumer will be invoked when meets empty range (begin == end) during add/remove operation.
     * @return RangeList the default implementation with the specified emptyRangeConsumer
     */
    public static RangeList getDefaultRangeList(BiConsumer<Integer, String> emptyRangeConsumer) {
        return new DefaultRangeList(emptyRangeConsumer);
    }
}
