package com.github.bijukunjummen.cloudfunction.task;

import com.github.bijukunjummen.cloudfunction.CloudFunctionExtension;
import com.github.bijukunjummen.cloudfunction.Constants;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.SourceSet;

import java.util.List;

/**
 * Task responsible for starting the Google Cloud function invoker, a local instance of the running function
 * which can be integration tested against
 *
 * @author Biju Kunjummen
 */
public class CloudFunctionRunTask extends JavaExec {
    public static final String INVOKER_MAIN_CLASS = "com.google.cloud.functions.invoker.runner.Invoker";
    public static final String RUNTIME_CLASSPATH = "runtimeClasspath";
    public static final String MAIN_SOURCESET = "main";
    private CloudFunctionExtension invokerExtension;

    public CloudFunctionRunTask() {
        getMainClass().set(INVOKER_MAIN_CLASS);
        JavaPluginExtension javaPluginExtension = getProject().getExtensions().getByType(JavaPluginExtension.class);
        ConfigurationContainer configurations = getProject().getConfigurations();
        Configuration invokerConfiguration = configurations.getAt(Constants.INVOKER_CONFIGURATION_NAME);
        Configuration runtimeClasspathConfiguration = configurations.getAt(RUNTIME_CLASSPATH);
        SourceSet main = javaPluginExtension.getSourceSets().getByName(MAIN_SOURCESET);
        this.setClasspath(invokerConfiguration);
        this.getInputs().files(runtimeClasspathConfiguration, main.getOutput());
    }

    public void setInvokerExtension(CloudFunctionExtension invokerExtension) {
        this.invokerExtension = invokerExtension;
    }

    @Override
    public void exec() {
        String classpathFileCollection = this.getInputs().getFiles().getAsPath();
        this.setArgs(
                List.of(
                        "--target", invokerExtension.getTarget().get(),
                        "--port", invokerExtension.getPort().get(),
                        "--classpath", classpathFileCollection));

        if (!invokerExtension.getTestMode().get()) {
            super.exec();
        }

    }
}
