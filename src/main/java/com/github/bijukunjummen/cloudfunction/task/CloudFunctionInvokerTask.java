package com.github.bijukunjummen.cloudfunction.task;

import com.github.bijukunjummen.cloudfunction.CloudFunctionInvokerExtension;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.SourceSet;

import java.util.List;

public class CloudFunctionInvokerTask extends JavaExec {
    private CloudFunctionInvokerExtension invokerExtension;

    public CloudFunctionInvokerTask() {
        JavaPluginExtension javaPluginExtension = getProject().getExtensions().getByType(JavaPluginExtension.class);
        ConfigurationContainer configurations = getProject().getConfigurations();
        Configuration invokerConfiguration = configurations.create("invoker");
        invokerConfiguration.getDependencies()
                .add(new DefaultExternalModuleDependency(
                        "com.google.cloud.functions.invoker", "java-function-invoker", "1.1.0"));

        Configuration runtimeClasspathConfiguration = configurations.getAt("runtimeClasspath");
        SourceSet main = javaPluginExtension.getSourceSets().getByName("main");
        this.setClasspath(invokerConfiguration);
        this.getInputs().files(runtimeClasspathConfiguration, main.getOutput());
    }

    public void setInvokerExtension(CloudFunctionInvokerExtension invokerExtension) {
        this.invokerExtension = invokerExtension;
    }

    @Override
    public void exec() {
        String classpathFileCollection =
                this.getInputs().getFiles().getAsPath();
        System.out.println(classpathFileCollection);
        this.setArgs(
                List.of(
                        "--target", invokerExtension.getTarget().get(),
                        "--port", invokerExtension.getPort().get(),
                        "--classpath", classpathFileCollection));
        super.exec();
    }
}
