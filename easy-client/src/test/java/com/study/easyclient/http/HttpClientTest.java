package com.study.easyclient.http;

/**
 * @author jixu
 */
public class HttpClientTest {

    /*private static final HttpClient client;
    static {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        HttpInnerClientFactory factory = new HttpInnerClientFactory(asyncHttpClient, 10, TimeUnit.SECONDS);
        Cluster cluster = new StaticCluster("profiler1.yidian.com:7080");
        LoadBalancer<HttpInnerClient> loadBalancer = new RoundRobinBalancer<>(factory);
        client = new HttpClientImpl(cluster, loadBalancer);
    }

    @Test
    public void testGet() throws Exception {
        String uri = "/popularity?key=gender--male";

        HttpResponse response = client.get(uri, null);
        assertEquals(response.statusCode, 200);

        response = client.apply(new HttpRequest(HttpMethod.GET, uri));
        assertEquals(response.statusCode, 200);
    }

    @Test
    public void testGetAsync() throws Exception {
        String uri = "/popularity?key=gender--male";
        CompletableFuture<HttpResponse> responseFuture = client.getAsync(uri);
        int result = responseFuture.handle((ok, ex) -> {
            if (ok != null && ok.statusCode == 200) {
                return 0;
            } else {
                return -1;
            }
        }).get(10, TimeUnit.SECONDS);

        assertEquals(result, 0);
    }*/
}
