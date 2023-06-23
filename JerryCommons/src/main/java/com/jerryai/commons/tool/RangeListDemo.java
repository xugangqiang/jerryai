package com.jerryai.commons.tool;

import com.jerryai.commons.tool.pattern.DefaultRangeFormatConfig;
import com.jerryai.commons.tool.range.DefaultRangeList;
import com.jerryai.commons.tool.range.RangeList;
import com.jerryai.commons.tool.range.RangeListFactory;

public class RangeListDemo {

    public static void main(String[] args) {
        // get default implementation
        RangeList rangeList = RangeListFactory.getDefaultRangeList();
        // use it
        rangeList.add(new int[] {1, 100});
        System.out.println(rangeList);

        rangeList.remove(new int[] {5, 10});
        System.out.println(rangeList);

        // get default implementation with log when meets empty range
        RangeList rangeList2 = RangeListFactory.getDefaultRangeList((i,s) -> {System.out.println(s + ", meets empty range, value:" + i);});
        // use it
        rangeList2.add(new int[] {1, 1});
        System.out.println(rangeList2);

        // get default implementation with log when meets empty range
        RangeList rangeList3 = RangeListFactory.getDefaultRangeList((i,s) -> {System.out.println(s + ", meets empty range, value:" + i);});
        // specify a different range format config to use
        rangeList3.setRangeFormatConfig(new DefaultRangeFormatConfig("[%s-%s)", "---"));
        // use it
        rangeList3.add(new int[] {1, 1000});
        System.out.println(rangeList3);

        rangeList3.remove(new int[] {2, 10});
        rangeList3.remove(new int[] {20, 100});
        rangeList3.remove(new int[] {200, 999});
        System.out.println(rangeList3);
        // print with a different format config
        DefaultRangeList defaultRangeList = new DefaultRangeList();
        defaultRangeList.add(new int[] {1,2});
        defaultRangeList.add(new int[] {11,22});
        System.out.println(defaultRangeList);
        System.out.println(defaultRangeList.toStringWithPattern("[%s->%s)", ","));
    }

}
