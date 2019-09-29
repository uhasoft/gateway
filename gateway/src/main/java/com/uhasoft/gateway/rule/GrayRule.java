package com.uhasoft.gateway.rule;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;
import com.uhasoft.gateway.util.SpelUtil;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Component
public class GrayRule extends AbstractLoadBalancerRule {

    @Override
    public Server choose(Object key) {

        List<Server> servers = this.getLoadBalancer().getReachableServers();
        if (servers.isEmpty()) {
            return null;
        }
        if (key == null) {
            return randomChoose(servers);
        }
        return grayChoose(servers, (String)key);
    }

    private Server randomChoose(List<Server> servers) {
        if(servers.isEmpty()){
            return null;
        }
        int randomIndex = new Random().nextInt(servers.size());
        return servers.get(randomIndex);
    }

    private Server grayChoose(List<Server> servers, String condition) {
        List<Server> qualifiedServers = servers.stream().filter(server -> {
            StandardEvaluationContext serverContext = new StandardEvaluationContext();
            serverContext.setVariable("s", ((NacosServer)server).getMetadata());
            return SpelUtil.eval(condition, serverContext);
        }).collect(Collectors.toList());

        System.out.println("\nQualified Servers: ");
        qualifiedServers.forEach(server -> System.out.print(server.getPort() + ","));

        return randomChoose(qualifiedServers);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig config) {

    }

}
