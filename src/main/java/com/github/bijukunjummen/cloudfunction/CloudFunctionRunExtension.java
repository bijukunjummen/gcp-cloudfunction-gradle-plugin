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
    private static String DEFAULT_INVOKER_VERSION = "1.2.1";

    public CloudFunctionRunExtension(Project project) {
        getPort().convention(DEFAULT_PORT);
        getInvokerVersion().convention(DEFAULT_INVOKER_VERSION);
        getTestMode().convention(Boolean.FALSE);
    }

    /**
     * GCP Cloud Function target method
     *
     * @return the name of the target method
     */
    public abstract Property<String> getTarget();

    /**
     * Port to expose the function on
     *
     * @return port number
     */
    public abstract Property<Integer> getPort();

    /**
     * Version of the invoker function to use
     *
     * @return
     */
    public abstract Property<String> getInvokerVersion();

    /**
     *
     * @return
     */
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
