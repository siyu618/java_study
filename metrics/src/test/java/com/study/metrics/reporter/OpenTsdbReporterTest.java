package com.study.metrics.reporter;

import com.codahale.metrics.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.study.metrics.reporter.opentsdb.OpenTsdbClient;
import com.study.metrics.reporter.opentsdb.OpenTsdbMetric;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class OpenTsdbReporterTest {

    private static class TestingOpenTsdbClient implements OpenTsdbClient {
        private final ObjectMapper objectMapper = new ObjectMapper();
        private String result;
        @Override
        public boolean send(List<OpenTsdbMetric> metrics) {
            try {
                result = objectMapper.writeValueAsString(metrics);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return true;
        }

        public String getResult() {
            return result;
        }
    }

    @Test
    public void testReport() {
        MetricRegistry mockRegistry = mock(MetricRegistry.class);
        OpenTsdbClient openTsdbClient = new TestingOpenTsdbClient();

        Map<String, String> tags = Maps.newHashMap();
        tags.put("host", "serving1-1.yidian.com");
        OpenTsdbReporter reporter = OpenTsdbReporter.forRegistry(mockRegistry).withTags(tags).build(openTsdbClient);

        SortedMap<String, Histogram> histograms = Maps.newTreeMap();
        Histogram histogram = new Histogram(new ExponentiallyDecayingReservoir());
        histograms.put("histogram", histogram);

        SortedMap<String, Meter> meters = Maps.newTreeMap();
        Meter meter = new Meter();
        meters.put("meter", meter);

        SortedMap<String, Gauge> gauges = Maps.newTreeMap();
        Gauge gauge = new Gauge() {
            @Override
            public Integer getValue() {
                return 1;
            }
        };
        gauges.put("gauge", gauge);

        reporter.report(gauges, null, histograms, meters, null);

        assertNotNull(((TestingOpenTsdbClient) openTsdbClient).getResult());

    }
}
