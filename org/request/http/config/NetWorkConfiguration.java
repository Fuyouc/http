package org.request.http.config;

public class NetWorkConfiguration {
    private int readTimeout;
    private int connectionTimeout;
    private boolean useCaches;

    public NetWorkConfiguration() {
    }

    public NetWorkConfiguration(int readTimeout, int connectionTimeout, boolean useCaches) {
        this.readTimeout = readTimeout;
        this.connectionTimeout = connectionTimeout;
        this.useCaches = useCaches;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setUseCaches(boolean useCaches) {
        this.useCaches = useCaches;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public boolean isUseCaches() {
        return useCaches;
    }
}
