package com.uhasoft.gateway.filter;

import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@NacosConfigurationProperties(prefix = "gray", dataId = "gateway", autoRefreshed = true)
@Component
public class RouteRuleList {

    private List<String> headerKeys;

    private List<RouteRule> rules;

    public List<String> getHeaderKeys() {
        return headerKeys;
    }

    public void setHeaderKeys(List<String> headerKeys) {
        this.headerKeys = headerKeys;
    }

    public List<RouteRule> getRules() {
        return rules;
    }

    public void setRules(List<RouteRule> rules) {
        this.rules = rules;
    }
}