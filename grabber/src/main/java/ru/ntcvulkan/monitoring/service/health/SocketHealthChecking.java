package ru.ntcvulkan.monitoring.service.health;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public abstract class SocketHealthChecking implements HealthIndicator {

    protected static Logger logger = LoggerFactory.getLogger(SocketHealthChecking.class);

    private final static Long TIMEOUT = TimeUnit.SECONDS.toMillis(3);

    protected String host;

    protected int port;

    @Override
    public Health health() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), TIMEOUT.intValue());
            return Health.up().build();
        } catch (final Exception e) {
            logger.error("Connection error", e);
            return Health.outOfService().withDetail("error", "Connection error").build();
        }
    }
}
