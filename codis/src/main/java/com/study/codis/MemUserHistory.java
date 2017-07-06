package com.study.codis;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * Created by tianyuzhi on 17/7/6.
 */
@Data
@Builder
public class MemUserHistory {
    private String userid;
    private Set<String> docIds;
}
