package client2;

import java.util.Objects;

public class RequestLog {

    private long startTime;
    private String requestType;
    private long latency;
    private int responseCode;

    public RequestLog(long startTime, String requestType, long latency, int responseCode) {
        this.startTime = startTime;
        this.requestType = requestType;
        this.latency = latency;
        this.responseCode = responseCode;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public long getLatency() {
        return latency;
    }

    public void setLatency(long latency) {
        this.latency = latency;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestLog)) {
            return false;
        }
        RequestLog that = (RequestLog) o;
        return startTime == that.startTime && latency == that.latency
            && responseCode == that.responseCode && Objects.equals(requestType,
            that.requestType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, requestType, latency, responseCode);
    }

    @Override
    public String toString() {
        return "client2.RequestLog{" +
            "startTime=" + startTime +
            ", requestType='" + requestType + '\'' +
            ", latency=" + latency +
            ", responseCode=" + responseCode +
            '}';
    }
}
