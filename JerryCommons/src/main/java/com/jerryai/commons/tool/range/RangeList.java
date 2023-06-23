package com.jerryai.commons.tool.range;

import com.jerryai.commons.tool.pattern.RangeFormatConfig;

public interface RangeList {

    /**
     * Add the specified range to the range list.
     *
     * @param range int[], where range[0](the first element) is the start position(inclusive)
     *              and range[1](the second element) is the end position(exclusive).
     *              range[1] must greater than or equals to range[0] - an IllegalArgumentException will be thrown if this is violated.
     *              For example: int[] {1,4} represents the range from 1(inclusive) to 4(exclusive), which means {1,2,3}.
     *              Please note: int[]{X,X} will not take effect since the range of X(inclusive - first element)
     *              and X(exclusive - second element) is empty.
     */
    void add(int[] range);

    /**
     * Remove the specified range from the range list.
     *
     * @param range int[], where range[0](the first element) is the start position(inclusive)
     *              and range[1](the second element) is the end position(exclusive).
     *              range[1] must greater than or equals to range[0] - an IllegalArgumentException will be thrown if this is violated.
     *              For example: int[] {1,4} represents the range from 1(inclusive) to 4(exclusive), which means {1,2,3}.
     *              Please note: int[]{X,X} will not take effect since the range of X(inclusive - first element)
     *              and X(exclusive - second element) is empty.
     */
    void remove(int[] range);

    void setRangeFormatConfig(RangeFormatConfig provider);
}
