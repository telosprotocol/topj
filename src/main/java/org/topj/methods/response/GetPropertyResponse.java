package org.topj.methods.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class GetPropertyResponse {

    @JSONField(name = "property_value")
    private List<String> propertyValue;

    public List<String> getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(List<String> propertyValue) {
        this.propertyValue = propertyValue;
    }
}
