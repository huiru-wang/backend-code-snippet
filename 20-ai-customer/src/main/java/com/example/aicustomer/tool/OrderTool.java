package com.example.aicustomer.tool;

import com.alibaba.fastjson2.JSONObject;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class OrderTool {

    @Tool(name = "queryOrderDetails")
    public String queryOrderDetails() {
        JSONObject orderDetails = new JSONObject();
        orderDetails.put("order_id", "orderId123");
        orderDetails.put("pay_amount", "123");
        orderDetails.put("discount", "");
        orderDetails.put("gmt_created", "2025-01-21");
        orderDetails.put("gmt_updated", "2025-01-24");
        return orderDetails.toJSONString();
    }
}
