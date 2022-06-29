package com.github.bijukunjummen.cloudfunction;

import org.gradle.api.Project;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;

public class CloudFunctionInvokerExtension {
    private static Integer DEFAULT_PORT = 8080;
    private Property<String> target;
    private Property<Integer> port;

    public CloudFunctionInvokerExtension(Project project) {
        ObjectFactory objectFactory = project.getObjects();
        this.port = objectFactory.property(Integer.class).convention(DEFAULT_PORT);
        this.target = objectFactory.property(String.class);
    }

    public Property<String> getTarget() {
        return target;
    }

    public Property<Integer> getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "CloudFunctionInvokerExtension{" +
                "target=" + target +
                ", port=" + port +
                '}';
    }
}
