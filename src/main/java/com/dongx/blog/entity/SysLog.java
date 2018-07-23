package com.dongx.blog.entity;

import java.io.Serializable;
import java.util.Date;

public class SysLog implements Serializable {
    
    private String id;

    private String operateUser;

    private Date operateTime;

    private String operateIp;

    private String requestUrl;

    private String requestMethod;

    private String requestArgs;

    private String responseData;

    private String responseTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestArgs() {
        return requestArgs;
    }

    public void setRequestArgs(String requestArgs) {
        this.requestArgs = requestArgs;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    private SysLog(String id, String operateUser, Date operateTime, String operateIp, String requestUrl, String requestMethod, String requestArgs, String responseData, String responseTime) {
        this.id = id;
        this.operateUser = operateUser;
        this.operateTime = operateTime;
        this.operateIp = operateIp;
        this.requestUrl = requestUrl;
        this.requestMethod = requestMethod;
        this.requestArgs = requestArgs;
        this.responseData = responseData;
        this.responseTime = responseTime;
    }

    public static class SysLogBuilder {

        private String id;

        private String operateUser;

        private Date operateTime;

        private String operateIp;

        private String requestUrl;

        private String requestMethod;

        private String requestArgs;

        private String responseData;

        private String responseTime;

        public SysLogBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public SysLogBuilder setOperateUser(String operateUser) {
            this.operateUser = operateUser;
            return this;
        }

        public SysLogBuilder setOperateTime(Date operateTime) {
            this.operateTime = operateTime;
            return this;
        }

        public SysLogBuilder setOperateIp(String operateIp) {
            this.operateIp = operateIp;
            return this;
        }

        public SysLogBuilder setRequestUrl(String requestUrl) {
            this.requestUrl = requestUrl;
            return this;
        }

        public SysLogBuilder setRequestMethod(String requestMethod) {
            this.requestMethod = requestMethod;
            return this;
        }

        public SysLogBuilder setRequestArgs(String requestArgs) {
            this.requestArgs = requestArgs;
            return this;
        }

        public SysLogBuilder setResponseData(String responseData) {
            this.responseData = responseData;
            return this;
        }

        public SysLogBuilder setResponseTime(String responseTime) {
            this.responseTime = responseTime;
            return this;
        }
        
        public SysLog build() {
            return  new SysLog(id, operateUser, operateTime, operateIp, requestUrl, requestMethod, requestArgs, responseData, responseTime);
        }
    }
}