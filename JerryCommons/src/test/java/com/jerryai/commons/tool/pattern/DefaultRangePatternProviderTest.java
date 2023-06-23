package com.jerryai.commons.tool.pattern;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DefaultRangePatternProviderTest {

    @Test
    public void testGetDefault() {
        RangeFormatConfig provider = DefaultRangeFormatConfig.getInstance();
        assertEquals(RangeFormatConfigConstants.DEFAULT_RANGE_PATTERN, provider.getRangePattern());
        assertEquals(RangeFormatConfigConstants.DEFAULT_RANGE_SEPARATOR, provider.getRangeSeparator());
    }

}
