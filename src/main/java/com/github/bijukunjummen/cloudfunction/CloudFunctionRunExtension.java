package com.github.bijukunjummen.cloudfunction;

import org.gradle.api.Project;
import org.gradle.api.provider.Property;

/**
 * Holds the properties associated with local running
 * of a GCP Cloud Function
 *
 * @author Biju Kunjummen
 */
public abstract class CloudFunctionRunExtension {
    private static Integer DEFAULT_PORT = 8080;
    private static String DEFAULT_INVOKER_VERSION = "1.1.0";

    public CloudFunctionRunExtension(Project project) {
        getPort().convention(DEFAULT_PORT);
        getInvokerVersion().convention(DEFAULT_INVOKER_VERSION);
        getTestMode().convention(Boolean.FALSE);
    }

    public abstract Property<String> getTarget();

    public abstract Property<Integer> getPort();

    public abstract Property<String> getInvokerVersion();

    public abstract Property<Boolean> getTestMode();

    @Override
    public String toString() {
        return """
                cloudFunction {
                target = "%s"
                port = %d
                invokerVersion = "%s"
                }""".formatted(getTarget().getOrElse("Not Set"), getPort().get(), getInvokerVersion().get());
    }
}
