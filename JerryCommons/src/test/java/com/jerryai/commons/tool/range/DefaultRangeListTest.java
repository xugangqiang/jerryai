package com.jerryai.commons.tool.range;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultRangeListTest {

    @Test
    public void testAddThrowsIllegalArgumentException() {
        DefaultRangeList defaultRangeList = new DefaultRangeList();
        try {
            defaultRangeList.add(new int[]{2, 1});
            fail("should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
        // this should be ok
        defaultRangeList.add(new int[]{1, 2});
        defaultRangeList.add(new int[]{1, 1});
    }

    @Test
    public void testRemoveThrowsIllegalArgumentException() {
        DefaultRangeList defaultRangeList = new DefaultRangeList();
        try {
            defaultRangeList.remove(new int[]{2, 1});
            fail("should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
        // this should be ok
        defaultRangeList.remove(new int[]{1, 2});
        defaultRangeList.remove(new int[]{1, 1});
    }

    @Test
    public void testAdd() {
        DefaultRangeList defaultRangeList = new DefaultRangeList();
        assertEquals("", defaultRangeList.toString());

        defaultRangeList.add(new int[] {1, 5});
        assertEquals("[1,5)", defaultRangeList.toString());

        defaultRangeList.add(new int[] {10, 20});
        assertEquals("[1,5) [10,20)", defaultRangeList.toString());

        defaultRangeList.add(new int[] {20, 20});
        assertEquals("[1,5) [10,20)", defaultRangeList.toString());

        defaultRangeList.add(new int[] {20, 21});
        assertEquals("[1,5) [10,21)", defaultRangeList.toString());

        defaultRangeList.add(new int[] {3, 8});
        assertEquals("[1,8) [10,21)", defaultRangeList.toString());

        defaultRangeList.add(new int[] {30, 38});
        assertEquals("[1,8) [10,21) [30,38)", defaultRangeList.toString());

        defaultRangeList.add(new int[] {1, 30});
        assertEquals("[1,38)", defaultRangeList.toString());

        defaultRangeList.add(new int[] {38, 50});
        assertEquals("[1,50)", defaultRangeList.toString());

        defaultRangeList.add(new int[] {-1, 0});
        assertEquals("[-1,0) [1,50)", defaultRangeList.toString());

        defaultRangeList.add(new int[] {80, 100});
        assertEquals("[-1,0) [1,50) [80,100)", defaultRangeList.toString());

        defaultRangeList.add(new int[] {80, 100});
        assertEquals("[-1,0) [1,50) [80,100)", defaultRangeList.toString());

        defaultRangeList.add(new int[] {0, 90});
        assertEquals("[-1,100)", defaultRangeList.toString());

        defaultRangeList.add(new int[] {0, 90});
        assertEquals("[-1,100)", defaultRangeList.toString());

        defaultRangeList.add(new int[] {Integer.MAX_VALUE - 100, Integer.MAX_VALUE});
        assertEquals("[-1,100) [2147483547,2147483647)", defaultRangeList.toString());

        defaultRangeList.add(new int[] {300,400});
        assertEquals("[-1,100) [300,400) [2147483547,2147483647)", defaultRangeList.toString());
    }

    @Test
    public void testRemove() {
        DefaultRangeList defaultRangeList = new DefaultRangeList();

        defaultRangeList.add(new int[] {1, 8});
        defaultRangeList.add(new int[] {10, 21});

        defaultRangeList.remove(new int[]{10, 10});
        assertEquals("[1,8) [10,21)", defaultRangeList.toString());

        defaultRangeList.remove(new int[]{10, 11});
        assertEquals("[1,8) [11,21)", defaultRangeList.toString());

        defaultRangeList.remove(new int[]{15, 17});
        assertEquals("[1,8) [11,15) [17,21)", defaultRangeList.toString());

        defaultRangeList.remove(new int[]{3, 19});
        assertEquals("[1,3) [19,21)", defaultRangeList.toString());

        defaultRangeList.add(new int[]{5, 8});
        defaultRangeList.add(new int[]{9, 11});
        defaultRangeList.add(new int[]{11, 15});
        defaultRangeList.add(new int[] {Integer.MAX_VALUE - 100, Integer.MAX_VALUE});
        assertEquals("[1,3) [5,8) [9,15) [19,21) [2147483547,2147483647)", defaultRangeList.toString());

        defaultRangeList.remove(new int[]{-1, 20});
        assertEquals("[20,21) [2147483547,2147483647)", defaultRangeList.toString());
    }

    @Test
    public void testToString() {
        DefaultRangeList defaultRangeList = new DefaultRangeList();
        assertEquals("",defaultRangeList.toString());

        defaultRangeList.add(new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE});
        assertEquals("[-2147483648,2147483647)",defaultRangeList.toString());

        defaultRangeList.remove(new int[] {100, 200});
        assertEquals("[-2147483648,100) [200,2147483647)",defaultRangeList.toString());
    }

    @Test
    public void testFormatWithRange() {
        DefaultRangeList defaultRangeList = new DefaultRangeList();

        defaultRangeList.add(new int[] {0, 1});
        defaultRangeList.add(new int[] {10, 11});
        assertEquals("[0,1)---[10,11)",defaultRangeList.formatRangeList("[%s,%s)", "---"));
    }

    @Test
    public void testToStringWithPattern() {
        DefaultRangeList defaultRangeList = new DefaultRangeList();

        defaultRangeList.add(new int[] {0, 1});
        defaultRangeList.add(new int[] {10, 11});
        assertEquals("[0-1),[10-11)",defaultRangeList.toStringWithPattern("[%s-%s)", ","));
    }
}
