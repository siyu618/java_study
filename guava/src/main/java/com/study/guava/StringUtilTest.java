package com.study.guava;

import com.google.common.base.CaseFormat;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import java.util.Arrays;

/**
 * Created by tianyuzhi on 16/5/16.
 */
public class StringUtilTest {
    public static void main(String[] args) {
        StringUtilTest test = new StringUtilTest();
        test.testJoiner();
        test.testSplitter();
        test.testCharMatcher();
        test.testCaseFormat();
    }

    public void testJoiner() {
        System.out.println(
                Joiner.on(",")
                        .skipNulls()
                        .join(Arrays.asList(1, 2, 3, 4, 5, null, 6, 7)));

    }
    public void testSplitter() {
        System.out.println(
                Splitter.on(",")
                        .trimResults()
                        .omitEmptyStrings()
                        .split("the ,quick, , brown, fox"));
    }

    public void testCharMatcher() {
        System.out.println(CharMatcher.DIGIT.retainFrom("mahesh123"));
        System.out.println(CharMatcher.WHITESPACE.trimAndCollapseFrom("    Mahesh    Parashar " , ' '));
        System.out.println(CharMatcher.JAVA_DIGIT.replaceFrom("mahesh123", '*'));
        System.out.println(CharMatcher.DIGIT.or(CharMatcher.ANY.JAVA_LOWER_CASE).retainFrom("Mahesh123"));
    }

    public void testCaseFormat() {
        String data = "test_data";
        System.out.println(CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, "test-data"));
        System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "test_data"));
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, "test_data"));
    }

}
