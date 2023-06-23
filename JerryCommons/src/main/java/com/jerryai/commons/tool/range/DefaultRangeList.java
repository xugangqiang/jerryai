package com.jerryai.commons.tool.range;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;

public class DefaultRangeList extends AbstractRangeList{

    private final TreeMap<Integer, Integer> rangeSegments = new TreeMap<>();

    public DefaultRangeList() {
        this(null);
    }

    /**
     *
     * @param consumerForEmptyRange the consumer when range specified in add/remove is empty, could be null
     */
    public DefaultRangeList(BiConsumer<Integer, String> consumerForEmptyRange) {
        super(consumerForEmptyRange);
    }

    /**
     * Time complexity: O(N) on average, where N is the total count of ranges.
     * Worst case is O(N*logN) - all existing ranges should be merged with the new range.
     * Not thread safe to work concurrently with processRemove.
     *
     * @param range see add/remove method
     */
    protected void processAdd(int[] range) {
        Map.Entry<Integer, Integer> first = null;
        Map.Entry<Integer, Integer> last = null;
        Iterator<Map.Entry<Integer, Integer>> it = rangeSegments.entrySet().iterator();
        while (it.hasNext()) { // find all intersected ranges and remove
            Map.Entry<Integer, Integer> temp = it.next();
            if (canMerge(temp.getKey(), temp.getValue(), range[0], range[1])) {
                if (first == null) {
                    first = temp;
                }
                last = temp;
                it.remove();
            } else if (last != null) {
                break;
            }
        }

        int[] param = new int[] {range[0], range[1]};
        if (first != null) { // add back beginning range that reminds
            // first - [1,10), range - [5,20) -> range - [1,20)
            param[0] = Math.min(param[0], first.getKey());
        }

        if (last != null) { // add back last range that reminds
            // last - [15,30), range - [5,20) -> last - [20,30)
            param[1] = Math.max(param[1], last.getValue());
        }
        rangeSegments.put(param[0], param[1]);
    }

    /**
     * Time complexity: O(N) on average, where N is the total count of ranges.
     * Worst case is O(N*logN) - all existing ranges should be merged with the new range.
     * Not thread safe to work concurrently with processAdd.
     *
     * @param range see add/remove method
     */
    protected void processRemove(int[] range) {
        Map.Entry<Integer, Integer> first = null;
        Map.Entry<Integer, Integer> last = null;
        Iterator<Map.Entry<Integer, Integer>> it = rangeSegments.entrySet().iterator();
        while (it.hasNext()) { // find all intersected ranges and remove
            Map.Entry<Integer, Integer> temp = it.next();
            if (intersects(temp.getKey(), temp.getValue(), range[0], range[1])) {
                if (first == null) {
                    first = temp;
                }
                last = temp;
                it.remove();
            } else if (last != null) {
                break;
            }
        }

        if (first != null) { // add back beginning range that reminds
            // first - [1,10), range - [5,20) -> first - [1,5)
            if (first.getKey() < range[0]) {
                rangeSegments.put(first.getKey(), range[0]);
            }
        }

        if (last != null) { // add back last range that reminds
            // last - [15,30), range - [5,20) -> last - [20,30)
            if (last.getValue() > range[1]) {
                rangeSegments.put(range[1], last.getValue());
            }
        }
    }

    public String toStringWithPattern(String pattern, String separator) {
        return formatRangeList(pattern, separator);
    }

    @Override
    protected String formatRangeList(String rangePattern, String rangeSeparator) {
        StringBuilder sbd = new StringBuilder();
        Iterator<Map.Entry<Integer, Integer>> rangeIterator = this.rangeSegments.entrySet().iterator();
        while(rangeIterator.hasNext()) {
            Map.Entry<Integer, Integer> r = rangeIterator.next();
            final String range = String.format(rangePattern,r.getKey(), r.getValue());
            if (!sbd.isEmpty()) {
                sbd.append(rangeSeparator);
            }
            sbd.append(range);
        }
        return sbd.toString();
    }
}
