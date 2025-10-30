package org.jahanzaib.personalsite.config;

import static ch.qos.logback.classic.Level.ERROR;
import static org.springframework.util.StringUtils.hasText;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ch.qos.logback.core.AppenderBase;
import lombok.Setter;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

public class AwsLogAppender extends AppenderBase<ILoggingEvent> {

    @Setter private String topicArn;
    @Setter private String snsRegion;
    @Setter private int maxMessageLength;

    private SnsClient snsClient;

    @Override
    public void start() {
        if (hasText(topicArn)) {
            snsClient = SnsClient.builder().region(Region.of(snsRegion)).build();
        }
        super.start();
    }

    @Override
    protected void append(ILoggingEvent event) {
        if (snsClient == null || !event.getLevel().isGreaterOrEqual(ERROR)) {
            return;
        }
        snsClient.publish(
                PublishRequest.builder()
                        .topicArn(topicArn)
                        .subject("App ERROR: " + event.getLoggerName())
                        .message(buildMessage(event))
                        .build());
    }

    private String buildMessage(ILoggingEvent event) {
        var messageBuilder =
                new StringBuilder()
                        .append(String.format("Logger: %s\n", event.getLoggerName()))
                        .append(String.format("Thread: %s\n", event.getThreadName()))
                        .append(String.format("Message: %s\n", event.getFormattedMessage()));

        var throwableProxy = event.getThrowableProxy();
        if (throwableProxy != null) {
            messageBuilder.append(
                    String.format(
                            "\nStacktrace: %s\n", ThrowableProxyUtil.asString(throwableProxy)));
        }
        var message = messageBuilder.toString();
        if (message.length() > maxMessageLength) {
            message = message.substring(0, maxMessageLength) + "\n...[truncated]";
        }
        return message;
    }

    @Override
    public void stop() {
        if (snsClient != null) {
            snsClient.close();
        }
        super.stop();
    }
}
