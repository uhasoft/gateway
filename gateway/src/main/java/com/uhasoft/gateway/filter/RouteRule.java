package com.uhasoft.gateway.filter;

public class RouteRule{

        private String clientCondition;
        private String serverCondition;

        public RouteRule(){

        }

        public RouteRule(String clientCondition, String serverCondition){
            this.clientCondition = clientCondition;
            this.serverCondition = serverCondition;
        }

        public String getClientCondition() {
            return clientCondition;
        }

        public void setClientCondition(String clientCondition) {
            this.clientCondition = clientCondition;
        }

        public String getServerCondition() {
            return serverCondition;
        }

        public void setServerCondition(String serverCondition) {
            this.serverCondition = serverCondition;
        }

        public String toString(){
            return clientCondition + ":" + serverCondition;
        }
    }