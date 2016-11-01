package com.study.metrics;

import com.google.common.collect.Maps;
import com.study.metrics.core.Meter;
import com.study.metrics.core.Gauge;
import com.study.metrics.core.Histogram;
import com.study.metrics.reporter.opentsdb.OpenTsdbClient;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author jixu
 */
public class OnDemandMetricsFactoryTest {
    private OnDemandMetricsFactory metricsFactory;

    @BeforeClass
    public void init() {
        OpenTsdbClient openTsdbClient = mock(OpenTsdbClient.class);
        Map<String, String> tags = Maps.newHashMap();
        metricsFactory = new OnDemandMetricsFactory(tags, openTsdbClient);
    }

    @Test
    public void test() {
        Histogram h1 = metricsFactory.getHistogram("histo");
        Histogram h2 = metricsFactory.getHistogram("histo");
        assertNotNull(h1);
        assertTrue(h1 == h2);

        Meter m1 = metricsFactory.getMeter("meter");
        Meter m2 = metricsFactory.getMeter("meter");
        assertNotNull(m1);
        assertTrue(m1 == m2);

        boolean success = metricsFactory.register(new Gauge<Integer>("gauge") {
            @Override
            public Integer getValue() {
                return 1;
            }
        });
        assertTrue(success);
        success = metricsFactory.register(new Gauge<Integer>("gauge") {
            @Override
            public Integer getValue() {
                return 2;
            }
        });
        assertFalse(success);
    }
}
