package com.jerryai.commons.tool.pattern;

public class DefaultRangeFormatConfig implements RangeFormatConfig {

    private static final DefaultRangeFormatConfig INSTANCE =  new DefaultRangeFormatConfig(
            RangeFormatConfigConstants.DEFAULT_RANGE_PATTERN, RangeFormatConfigConstants.DEFAULT_RANGE_SEPARATOR);

    public static DefaultRangeFormatConfig getInstance() {
        return INSTANCE;
    }

    protected final String rangePattern;

    protected final String rangeSeparator;

    public DefaultRangeFormatConfig(String patternForRange, String separatorForRange) {
        rangePattern = patternForRange;
        rangeSeparator = separatorForRange;
    }

    @Override
    public String getRangeSeparator() {
        return this.rangeSeparator;
    }

    @Override
    public String getRangePattern() {
        return this.rangePattern;
    }
}
