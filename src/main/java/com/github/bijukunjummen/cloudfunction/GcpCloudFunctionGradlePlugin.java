package com.github.bijukunjummen.cloudfunction;

import com.github.bijukunjummen.cloudfunction.task.CloudFunctionRunTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency;
import org.gradle.api.plugins.JavaPluginExtension;

/**
 * Plugin to register various tasks associated with Gcp Cloud function
 *
 * @author Biju Kunjummen
 */
public class GcpCloudFunctionGradlePlugin implements Plugin<Project> {
    private static final String CLOUDFUNCTION_RUN_EXTENSION_NAME = "cloudFunctionRun";
    public static final String INVOKER_GROUP = "com.google.cloud.functions.invoker";
    public static final String INVOKER_MODULE = "java-function-invoker";

    public void apply(Project project) {
        JavaPluginExtension javaPluginExtension = project.getExtensions().findByType(JavaPluginExtension.class);
        if (javaPluginExtension == null) {
            throw new RuntimeException("Cloudfunction Plugin requires Java Plugin as a Pre-requisite");
        }
        CloudFunctionRunExtension cloudFunctionRunExtension = project.getExtensions()
                .create(CLOUDFUNCTION_RUN_EXTENSION_NAME, CloudFunctionRunExtension.class, project);
        ConfigurationContainer configurations = project.getConfigurations();

        // Adds function invoker as a dependency
        Configuration invokerConfiguration = configurations.create(Constants.INVOKER_CONFIGURATION_NAME);
        invokerConfiguration.getDependencies()
                .add(new DefaultExternalModuleDependency(
                        INVOKER_GROUP, INVOKER_MODULE, cloudFunctionRunExtension.getInvokerVersion().get()));

        // Register the task to be able to start the invoker
        project.getTasks()
                .register(Constants.CLOUD_FUNCTION_RUN_TASK, CloudFunctionRunTask.class,
                        task -> task.setInvokerExtension(cloudFunctionRunExtension));
    }
}
