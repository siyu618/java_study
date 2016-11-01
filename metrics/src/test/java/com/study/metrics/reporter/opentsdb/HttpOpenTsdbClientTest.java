package com.study.metrics.reporter.opentsdb;

import com.beust.jcommander.internal.Maps;
import com.google.common.collect.Lists;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author jixu
 */
public class HttpOpenTsdbClientTest {

    @Test
    public void testSuccess() throws Exception {
        AsyncHttpClient.BoundRequestBuilder mockBuilder = mock(AsyncHttpClient.BoundRequestBuilder.class);
        AsyncHttpClient mockClient = mock(AsyncHttpClient.class);
        ListenableFuture<Response> mockFuture = mock(ListenableFuture.class);
        Response mockResponse = mock(Response.class);

        when(mockClient.preparePost(anyString())).thenReturn(mockBuilder);
        when(mockBuilder.setBody(anyString())).thenReturn(mockBuilder);
        when(mockBuilder.execute()).thenReturn(mockFuture);
        when(mockFuture.get()).thenReturn(mockResponse);
        when(mockResponse.getStatusCode()).thenReturn(204);

        HttpOpenTsdbClient client = new HttpOpenTsdbClient(mockClient, "");
        Map<String, String> tags = Maps.newHashMap();
        tags.put("host", "localhost");
        List<OpenTsdbMetric> list = Lists.newArrayList(new OpenTsdbMetric("test", 1426508286L, 1L, tags));
        boolean result = client.send(list);
        assertTrue(result);
    }

    @Test
    public void testFail() throws Exception {
        AsyncHttpClient.BoundRequestBuilder mockBuilder = mock(AsyncHttpClient.BoundRequestBuilder.class);
        AsyncHttpClient mockClient = mock(AsyncHttpClient.class);
        ListenableFuture<Response> mockFuture = mock(ListenableFuture.class);
        Response mockResponse = mock(Response.class);

        when(mockClient.preparePost(anyString())).thenReturn(mockBuilder);
        when(mockBuilder.setBody(anyString())).thenReturn(mockBuilder);
        when(mockBuilder.execute()).thenReturn(mockFuture);
        when(mockFuture.get()).thenReturn(mockResponse);
        when(mockResponse.getStatusCode()).thenReturn(500);

        HttpOpenTsdbClient client = new HttpOpenTsdbClient(mockClient, "");
        Map<String, String> tags = Maps.newHashMap();
        tags.put("host", "localhost");
        List<OpenTsdbMetric> list = Lists.newArrayList(new OpenTsdbMetric("test", 1426508286L, 1L, tags));
        boolean result = client.send(list);
        assertFalse(result);
    }
}
