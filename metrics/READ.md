Meter: 用来实现qps等计数类的metrics，最终数据的单位是event/sec.
     // 每个StaticChannel的query执行一次下面的语句
     MetricsFactoryUtil.getRegisteredFactory().getMeter("YourService.qps").mark();
Histogram: 用来统计latency等分布数据，也可以用来实现error_rate一类的数据.
     // latency的例子
     long tsBegin = System.currentTimeMillis();
     ...
     long tsEnd = System.currentTimeMillis();
     MetricsFactoryUtil.getRegisteredFactory().getHistogram("YourService.latency").update(tsEnd-tsBegin);
     // error_rate的例子
     try {
       ..
       MetricsFactoryUtil.getRegisteredFactory().getHistogram("YourService.error").update(0);
     } catch(Exception) {
       MetricsFactoryUtil.getRegisteredFactory().getHistogram("YourService.error").update(100);
     }
Gauge: 用来实现某些状态的监控，比如cache size.
     cache = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).maximumSize(10000).build();
     MetricsFactoryUtil.getRegisteredFactory().register(new Gauge("torsonews.UserNewsCandidatesCache.size") {
         @Override
         public Number getValue() {
             return cache.size();
         }
     });
配置
每个server配置文件的格式可能是不一样的,配置文件中需要server的tag信息以及opentsdb的地址.
     // config file example
     ...
     serving-metrics {
       opentsdb.address = "http://test1-2.kafka.yidian.com:14242/api/put"
       tags = {
         host = "serving1-9.yidian.com"
         colo = "lugu"
         component = "channel-serving"
       }
     }
读取config并注册MetricsFactory的代码只需要在server启动的时候运行一次,注册需要在使用metrics之前进行.
     // read config
     Config config = ConfigFactory.load();
     Config servingMetricsConfig;
     try {
         servingMetricsConfig = config.getConfig("serving-metrics");
     } catch (Exception e) {
         logger.info("serving-metrics config is not found, will not send serving metrics");
         return;
     }
     String openTsdbAddr = servingMetricsConfig.getString("opentsdb.address");
     Config tagsConfig = servingMetricsConfig.getConfig("tags");
     Map tags = Maps.newHashMap();
     for (Map.Entry entry : tagsConfig.entrySet()) {
         tags.put(entry.getKey(), entry.getValue().unwrapped().toString());
     }

     AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
     OpenTsdbClient openTsdbClient = new HttpOpenTsdbClient(asyncHttpClient, openTsdbAddr);
     MetricsFactory metricsFactory = new OnDemandMetricsFactory(tags, openTsdbClient);
     MetricsFactoryUtil.register(metricsFactory);