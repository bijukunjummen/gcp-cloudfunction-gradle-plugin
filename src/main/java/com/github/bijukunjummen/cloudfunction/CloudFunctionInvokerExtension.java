package com.github.bijukunjummen.cloudfunction;

import org.gradle.api.Project;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;

/**
 * Holds the properties associated with Cloud Invoker
 *
 * @author Biju Kunjummen
 */
public class CloudFunctionInvokerExtension {
    private static Integer DEFAULT_PORT = 8080;
    private static String DEFAULT_INVOKER_VERSION = "1.1.0";
    private Property<String> target;
    private Property<Integer> port;
    private Property<String> invokerVersion;

    private Property<Boolean> testMode;

    public CloudFunctionInvokerExtension(Project project) {
        ObjectFactory objectFactory = project.getObjects();
        this.port = objectFactory.property(Integer.class).convention(DEFAULT_PORT);
        this.target = objectFactory.property(String.class);
        this.invokerVersion = objectFactory.property(String.class).convention(DEFAULT_INVOKER_VERSION);
        this.testMode = objectFactory.property(Boolean.class).convention(Boolean.FALSE);
    }

    public Property<String> getTarget() {
        return target;
    }

    public Property<Integer> getPort() {
        return port;
    }

    public Property<String> getInvokerVersion() {
        return invokerVersion;
    }

    public Property<Boolean> getTestMode() {
        return testMode;
    }

    @Override
    public String toString() {
        return "CloudFunctionInvokerExtension{" +
                "target=" + target.getOrElse("Not set..") +
                ", port=" + port.get() +
                ", invokerVersion=" + invokerVersion.get() +
                '}';
    }
}