package com.jerryai.commons.tool.range;

import com.jerryai.commons.tool.pattern.DefaultRangeFormatConfig;
import com.jerryai.commons.tool.pattern.RangeFormatConfigConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractRangeListTest {

    @Test
    public void testMust() {
        try {
            AbstractRangeList.must(true, "");
        } catch (Exception e) {
            fail("should be no exception");
        }

        try {
            AbstractRangeList.must(false, "msg");
            fail("should not go here");
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    public void testCheckParameter() {
        try {
            AbstractRangeList.checkParameter(new int[] {1, 1});
            AbstractRangeList.checkParameter(new int[] {1, 2});
        } catch (Exception e) {
            fail("should be no exception");
        }

        try {
            AbstractRangeList.checkParameter(new int[] {1, -1});
            fail("should not go here");
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    public void testOnEmptyRange() {
        AbstractRangeList rangeList = create();
        try {
            rangeList.onEmptyRange(1, "test");
            rangeList.setEmptyRangeConsumer((i, s) -> {});
            rangeList.onEmptyRange(1, "test");
        } catch (Throwable t) {
            fail("should not throw exception");
        }

        try {
            rangeList.setEmptyRangeConsumer((i, s) -> {throw new IllegalArgumentException(s + " " + i);});
            rangeList.onEmptyRange(1, "test");
            fail("should throw exception");
        } catch (IllegalArgumentException t) {
            assertEquals("test 1", t.getMessage());
        } catch (Throwable t) {
            fail("should not go here");
        }
    }

    @Test
    public void testIntersects() {
        // case 1 r1 [10,20), r2 [10,30) or r2 [11,20)
        assertTrue(AbstractRangeList.intersects(10,20, 10,30));
        assertTrue(AbstractRangeList.intersects(10,20, 11,20));
        // case 2 r1 [10,20), r2 [15,30) or r2 [15, 18)
        assertTrue(AbstractRangeList.intersects(10,20, 15,30));
        assertTrue(AbstractRangeList.intersects(10,20, 15,18));
        // case 3 r1 [10,20), r2 [1,11) or [1,30)
        assertTrue(AbstractRangeList.intersects(10,20, 1,11));
        assertTrue(AbstractRangeList.intersects(10,20, 1,30));
        // false case
        assertFalse(AbstractRangeList.intersects(10,20, 1,10));
        assertFalse(AbstractRangeList.intersects(10,20, 1,9));
        assertFalse(AbstractRangeList.intersects(10,20, 20,21));
        assertFalse(AbstractRangeList.intersects(10,20, 22,25));
    }

    @Test
    public void testCanMerge() {
        // case 1 r1 [10,20), r2 [10,30) or r2 [11,20)
        assertTrue(AbstractRangeList.canMerge(10,20, 10,30));
        assertTrue(AbstractRangeList.canMerge(10,20, 11,20));
        // case 2 r1 [10,20), r2 [15,30) or r2 [15, 18)
        assertTrue(AbstractRangeList.canMerge(10,20, 15,30));
        assertTrue(AbstractRangeList.canMerge(10,20, 15,18));
        // case 3 r1 [10,20), r2 [1,11) or [1,30)
        assertTrue(AbstractRangeList.canMerge(10,20, 1,11));
        assertTrue(AbstractRangeList.canMerge(10,20, 1,30));
        // case 4 r1 [10,20), r2 [1,10) or [20,30)
        assertTrue(AbstractRangeList.canMerge(10,20, 1,10));
        assertTrue(AbstractRangeList.canMerge(10,20, 20,30));
        // false case
        assertFalse(AbstractRangeList.canMerge(10,20, 1,9));
        assertFalse(AbstractRangeList.canMerge(10,20, 22,25));
    }

    @Test
    public void testRangeFormatConfig() {
        AbstractRangeList defaultRangeList = create();

        assertEquals(RangeFormatConfigConstants.DEFAULT_RANGE_PATTERN, defaultRangeList.rangeFormatConfig.getRangePattern());
        assertEquals(RangeFormatConfigConstants.DEFAULT_RANGE_SEPARATOR, defaultRangeList.rangeFormatConfig.getRangeSeparator());

        defaultRangeList.setRangeFormatConfig(DefaultRangeFormatConfig.getInstance());
        assertEquals(RangeFormatConfigConstants.DEFAULT_RANGE_PATTERN,defaultRangeList.rangeFormatConfig.getRangePattern());
        assertEquals(RangeFormatConfigConstants.DEFAULT_RANGE_SEPARATOR, defaultRangeList.rangeFormatConfig.getRangeSeparator());

        defaultRangeList.setRangeFormatConfig(new DefaultRangeFormatConfig("abc", "d"));
        assertEquals("abc",defaultRangeList.rangeFormatConfig.getRangePattern());
        assertEquals("d",defaultRangeList.rangeFormatConfig.getRangeSeparator());
    }

    private AbstractRangeList create() {
       return new AbstractRangeList() {
            /**
             * @param range see add/remove method
             */
            @Override
            protected void processAdd(int[] range) {

            }

            /**
             * @param range see add/remove method
             */
            @Override
            protected void processRemove(int[] range) {

            }

           @Override
           protected String formatRangeList(String rangePattern, String rangeSeparator) {
               return null;
           }
        };
    }
}
