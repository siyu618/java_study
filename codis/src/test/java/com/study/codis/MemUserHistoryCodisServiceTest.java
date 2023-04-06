package com.study.codis;

import org.testng.annotations.Test;

import java.util.HashSet;


/**
 * Created by tianyuzhi on 17/7/6.
 */
public class MemUserHistoryCodisServiceTest {
    //@Test
    public void testSetAndGet() throws Exception {
        MemUserHistoryCodisService service = MemUserHistoryCodisService.getDefaultInstance();
        HashSet docIds = new HashSet();
        String userId = "12345";
        docIds.add("doci1");
        docIds.add("doci2");
        docIds.add("doci3");
        MemUserHistory memUserHistory = MemUserHistory.builder().userid(userId).docIds(docIds).build();
        service.set(userId, memUserHistory);
        System.out.println(service.get(userId));
    }

}