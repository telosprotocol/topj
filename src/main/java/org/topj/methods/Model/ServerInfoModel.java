package org.topj.methods.Model;

import com.alibaba.fastjson.annotation.JSONField;
import org.graalvm.compiler.core.common.type.ArithmeticOpTable;

import java.util.List;

public class ServerInfoModel {

    @JSONField(name = "address")
    private Address address;

    @JSONField(name = "port")
    private Port port;

    public String getEdgeUrl(String type){
        if (address == null){
            return "";
        }
        String hostDetail = getHost(address.getEdge());
        String portDetail = getPort(port.getEdge(), type);
        return new StringBuilder(type).append("://").append(hostDetail).append(":").append(portDetail).toString();
    }

    private String getHost(List<String> hostList){
        if (hostList == null || hostList.size() == 0){
            return "";
        }
        int hostSize = hostList.size();
        Double random = Math.floor(Math.random() * hostSize);
        String host = address.getEdge().get(random.intValue());
        return host;
    }

    private String getPort(PortDetail portDetail, String type){
        if (portDetail == null){
            return "";
        }
        if (type == null || type == ""){
            type = "http";
        }
        switch (type){
            case "http":
                return portDetail.getHttp();
            case "ws":
                return portDetail.getWs();
            case "grpc":
                return portDetail.getGrpc();
            default:
                return "";
        }
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }
}

class Address{
    @JSONField(name = "edge")
    List<String> edge;

    public List<String> getEdge() {
        return edge;
    }

    public void setEdge(List<String> edge) {
        this.edge = edge;
    }
}

class Port{
    @JSONField(name = "edge")
    private PortDetail edge;

    public PortDetail getEdge() {
        return edge;
    }

    public void setEdge(PortDetail edge) {
        this.edge = edge;
    }
}

class PortDetail{
    @JSONField(name = "http")
    private String http;

    @JSONField(name = "ws")
    private String ws;

    @JSONField(name = "grpc")
    private String grpc;

    public String getHttp() {
        return http;
    }

    public void setHttp(String http) {
        this.http = http;
    }

    public String getWs() {
        return ws;
    }

    public void setWs(String ws) {
        this.ws = ws;
    }

    public String getGrpc() {
        return grpc;
    }

    public void setGrpc(String grpc) {
        this.grpc = grpc;
    }
}
