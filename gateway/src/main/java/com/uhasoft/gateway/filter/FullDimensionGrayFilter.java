package com.uhasoft.gateway.filter;

import com.uhasoft.gateway.util.SpelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Component
public class FullDimensionGrayFilter extends LoadBalancerClientFilter {

    @Autowired
    private RouteRuleList ruleList;

    public FullDimensionGrayFilter(LoadBalancerClient loadBalancer, LoadBalancerProperties properties) {
        super(loadBalancer, properties);
    }

    @Override
    protected ServiceInstance choose(ServerWebExchange exchange) {
        if (this.loadBalancer instanceof RibbonLoadBalancerClient) {
            RibbonLoadBalancerClient client = (RibbonLoadBalancerClient) this.loadBalancer;
            String serviceId = ((URI) exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR)).getHost();
            Map<String, String> headers = new HashMap<>(ruleList.getHeaderKeys().size());
            //HttpHeaders是一个非常庞大的对象，不适合放入SpEL上下文，组装一个我们自己的Map作为客户端对象
            HttpHeaders httpHeaders = exchange.getRequest().getHeaders();
            for(String key : ruleList.getHeaderKeys()){
                headers.put(key, httpHeaders.getFirst(key));
            }
            StandardEvaluationContext context = new StandardEvaluationContext();
            context.setVariable("c", headers);//SpEL上下文的关键信息，即客户端对象，此处只使用了header
            //下面的遍历，用于确定适用的规则
            for(RouteRule rule : ruleList.getRules()){
                if(SpelUtil.eval(rule.getClientCondition(), context)){
                    ServiceInstance instance = client.choose(serviceId, rule.getServerCondition());
                    if(instance != null){
                        //这里的判断很重要，如果按照规则找不到任何实例，应该继续循环查找下一个匹配规则，而不是直接返回找不到服务实例。
                        return instance;
                    }
                }
            }
            return null;
        }
        return super.choose(exchange);
    }


}
