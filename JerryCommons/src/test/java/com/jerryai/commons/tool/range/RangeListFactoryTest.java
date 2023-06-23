package com.jerryai.commons.tool.range;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RangeListFactoryTest {

    @Test
    public void testGetDefaultRangeList() {
        RangeList list = RangeListFactory.getDefaultRangeList();
        assertTrue(DefaultRangeList.class.isInstance(list));
        assertEquals("", list.toString());
    }

    @Test
    public void testGetDefaultRangeListWithConsumer() {
        RangeList list = RangeListFactory.getDefaultRangeList((i,s) -> {});
        assertTrue(DefaultRangeList.class.isInstance(list));
        assertEquals("", list.toString());

        try {
            list.add(new int[]{1, 1});
            assertEquals("", list.toString());
        } catch (Throwable t) {
            fail("should not ge here");
        }
    }

}
