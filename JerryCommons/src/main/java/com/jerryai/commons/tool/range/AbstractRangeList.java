package com.jerryai.commons.tool.range;

import com.jerryai.commons.tool.pattern.DefaultRangeFormatConfig;
import com.jerryai.commons.tool.pattern.RangeFormatConfig;

import java.util.function.BiConsumer;

public abstract class AbstractRangeList implements RangeList{

    private static final int RANGE_PARAM_LENGTH = 2;

    private BiConsumer<Integer, String> emptyRangeConsumer;

    // provides the pattern/separator used to format range list
    protected RangeFormatConfig rangeFormatConfig;

    public AbstractRangeList() {
        this(null);
    }

    /**
     *
     * @param consumerForEmptyRange the consumer when range specified in add/remove is empty, could be null
     */
    public AbstractRangeList(BiConsumer<Integer, String> consumerForEmptyRange) {
        this(consumerForEmptyRange, DefaultRangeFormatConfig.getInstance());
    }

    public AbstractRangeList(BiConsumer<Integer, String> consumerForEmptyRange, RangeFormatConfig provider) {
        this.setEmptyRangeConsumer(consumerForEmptyRange);
        this.setRangeFormatConfig(provider);
    }

    public void setEmptyRangeConsumer(BiConsumer<Integer, String> biConsumer) {
        emptyRangeConsumer = biConsumer;
    }

    public void setRangeFormatConfig(RangeFormatConfig provider) {
        this.rangeFormatConfig = provider;
    }

    /**
     * Add the specified range to the range list.
     *
     * @param range int[], where range[0](the first element) is the start position(inclusive)
     *              and range[1](the second element) is the end position(exclusive).
     *              range[1] must greater than or equals to range[0] - an IllegalArgumentException will be thrown if this is violated.
     *              For example: int[] {1,4} represents the range from 1(inclusive) to 4(exclusive), which means {1,2,3}.
     *              Please note: int[]{X,X} will not take effect since the range of X(inclusive - first element)
     *              and X(exclusive - second element) is empty.
     * @throws IllegalArgumentException if second element (range[1]) < first element (range[0]), like {2, 1}
     */
    @Override
    public void add(int[] range) {
        // step 1, validate the range parameter
        checkParameter(range);
        // step 2, if begin == end, no need to change
        if (range[0] == range[1]) {
            onEmptyRange(range[0], "in add method");
            return;
        }
        // step 3, process the add operation
        processAdd(range);
    }

    /**
     *
     * @param range see add/remove method
     */
    abstract protected void processAdd(int[] range);

    public static void checkParameter(int[] range) {
        must(range != null, "range is null");
        must(range.length == RANGE_PARAM_LENGTH, "range parameter length expected to be "
                + RANGE_PARAM_LENGTH + ", but it's " + range.length);
        must(range[0] <= range[1], "beginning of range:" + range[0]
                + " is greater than end of range:" + range[1]);
    }

    static void must(boolean condition, String msg) {
        if (!condition) {
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Trigger emptyRangeConsumer action when meets empty range in parameter of add/remove.
     * @param value the beginning range (also same as end of range)
     */
    protected void onEmptyRange(int value, String extra) {
        // avoid this.emptyRangeConsumer changed in between
        BiConsumer<Integer, String> consumerCopy = this.emptyRangeConsumer;
        if (consumerCopy != null) {
            consumerCopy.accept(value, extra);
        }
    }

    /**
     * Remove the specified range from the range list
     *
     * @param range int[], where range[0](the first element) is the start position(inclusive)
     *              and range[1](the second element) is the end position(exclusive).
     *              range[1] must greater than or equals to range[0] - an IllegalArgumentException will be thrown if this is violated.
     *              For example: int[] {1,4} represents the range from 1(inclusive) to 4(exclusive), which means {1,2,3}.
     *              Please note: int[]{X,X} will not take effect since the range of X(inclusive - first element)
     *              and X(exclusive - second element) is empty.
     */
    @Override
    public void remove(int[] range) {
        // step 1, validate the range parameter
        checkParameter(range);
        // step 2, if begin == end, no need to change
        if (range[0] == range[1]) {
            onEmptyRange(range[0], "in remove method");
            return;
        }
        // step 3, process the remove operation
        processRemove(range);
    }

    /**
     *
     * @param range see add/remove method
     */
    protected abstract void processRemove(int[] range);

    /**
     * check if two range can be merged.
     * "Intersects" means:
     * 1. r1 [10,20), r2 [10,30) or r2 [11,20)
     * 2. r1 [10,20), r2 [15,30) or r2 [15, 18)
     * 3. r1 [10,20), r2 [1,11) or [1,30)
     * 4. r1 [10,20), r2 [1,10) or r2 [20,30)
     * @param r1_0
     * @param r1_1
     * @param r2_0
     * @param r2_1
     * @return true if two ranges can be merged
     */
    public static boolean canMerge(int r1_0, int r1_1, int r2_0, int r2_1) {
        if (intersects(r1_0, r1_1, r2_0, r2_1)) {
            return true;
        }
        // r1 [10,20), r2 [20,30) or r2 [1,10)
        return r1_0 == r2_1 || r1_1 == r2_0;
    }

    /**
     * check if two range intersects.
     * "Intersects" means:
     * 1. r1 [10,20), r2 [10,30) or r2 [11,20)
     * 2. r1 [10,20), r2 [15,30) or r2 [15, 18)
     * 3. r1 [10,20), r2 [1,11) or [1,30)
     * @param r1_0
     * @param r1_1
     * @param r2_0
     * @param r2_1
     * @return true if two ranges intersects
     */
    public static boolean intersects(int r1_0, int r1_1, int r2_0, int r2_1) {
        if (r1_0 > r1_1) {
            throw new IllegalArgumentException("second element " + r1_1 +
                    " must not smaller than first element " + r1_0);
        }
        if (r2_0 > r2_1) {
            throw new IllegalArgumentException("second element " + r2_1 +
                    " must not smaller than first element " + r2_0);
        }
        // r1 [10,20), r2 [10,30) or r2 [11,20)
        if (r1_0 == r2_0 || r1_1 == r2_1) {
            return true;
        }
        // r1 [10,20), r2 [20,30) or r2 [1,10)
        if (r1_0 == r2_1 || r1_1 == r2_0) {
            return false;
        }
        // r1 [10,20), r2 [15,30) or r2 [15, 18) or r2 [40,50)
        if (r1_0 < r2_0) {
            return r2_0 < r1_1;
        }
        // r1 [10,20), r2 [1,5) or r2 [1,11)
        return r2_1 > r1_0;
    }

    @Override
    public String toString() {
        return formatRangeList(rangeFormatConfig.getRangePattern(), rangeFormatConfig.getRangeSeparator());
    }

    abstract protected String formatRangeList(String rangePattern, String rangeSeparator);
}
