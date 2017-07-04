package com.study.exam.game;

import lombok.Builder;
import lombok.Data;


/**
 * Created by tianyuzhi on 17/7/3.
 */
@Data
@Builder
public class RuleResult {
    private boolean match;
    private String value;
    public static RuleResult NOT_MATCHED = new RuleResult(false, null);
}
