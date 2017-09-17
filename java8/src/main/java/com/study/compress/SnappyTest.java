package com.study.compress;

import org.xerial.snappy.Snappy;

import java.io.IOException;

/**
 * Created by tianyuzhi on 17/7/19.
 */
public class SnappyTest {

    public static void main(String[] args) throws IOException {
        String str = "{\"enableCodis\":true,\"expireTimeInSeconds\":180,\"zkHostAndPort\":\"10.120.18.40:2181,10.120.18.39:2181,10.120.14.43:2181\",\"zkPath\":\"/zk/codis/db_codis-video2video/proxy\",\"zkSessionTimeoutMs\":30000,\"enableGetFromThriftServer\":true,\"dataCompressed\":false,\"jedisPoolConfig\":{\"maxTotal\":1024,\"maxIdle\":5,\"minIdle\":0,\"lifo\":true,\"fairness\":false,\"maxWaitMillis\":5000,\"minEvictableIdleTimeMillis\":60000,\"softMinEvictableIdleTimeMillis\":1800000,\"numTestsPerEvictionRun\":-1,\"evictionPolicyClassName\":\"org.apache.commons.pool2.impl.DefaultEvictionPolicy\",\"testOnCreate\":false,\"testOnBorrow\":true,\"testOnReturn\":false,\"testWhileIdle\":true,\"timeBetweenEvictionRunsMillis\":30000,\"blockWhenExhausted\":true,\"jmxEnabled\":true,\"jmxNamePrefix\":\"pool\"}}";
        System.out.println(new String(Snappy.compress(str.getBytes("UTF-8"))));
        System.out.println(new String(Snappy.uncompress(Snappy.compress(str.getBytes()))));


        System.out.println("#########");
        byte[] compressedBytes = Snappy.compress(str);
        String compressed = new String(compressedBytes);
        byte[] compressedGet = compressed.getBytes();
        System.out.println(compressed.length() + ":" + compressedBytes.length + ":" + compressedGet.length);
        for (int i = 0; i < compressedBytes.length; i ++) {
            if (compressedBytes[i] != compressedGet[i]) {
                System.out.println("not same at " + i + ":" + compressedBytes[i] + ":" + compressedGet[i]);
            }
        }


//
//        byte[] uncompressedBytes = Snappy.uncompress(compressedBytes);
//        String uncompressed = new String( Snappy.uncompress(compressed.getBytes("UTF-8")) );
//        System.out.println(str);
//        System.out.println(new String(compressedBytes));
//        System.out.println(new String(uncompressedBytes));
//        System.out.println(compressed);
       // System.out.println(uncompressed);
    }
}
