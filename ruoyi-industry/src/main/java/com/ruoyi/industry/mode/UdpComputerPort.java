package com.ruoyi.industry.mode;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "udp")
@PropertySource("classpath:properties/UdpComputerPort.properties")
public class UdpComputerPort {

    private JSONObject portJson;

    public JSONObject getPortJson() {
        return portJson;
    }

    public void setPortJson(JSONObject portJson) {
        this.portJson = portJson;
    }
}
