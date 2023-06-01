package com.github.bijukunjummen.cloudfunction.task;

import com.github.bijukunjummen.cloudfunction.CloudFunctionRunExtension;
import com.github.bijukunjummen.cloudfunction.Constants;
import com.github.bijukunjummen.cloudfunction.GcpCloudFunctionGradlePlugin;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.SourceSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Task responsible for starting the Google Cloud function invoker, a local instance of the running
 * function which can be integration tested against
 *
 * @author Biju Kunjummen
 */
public class CloudFunctionRunTask extends JavaExec {

  private static final String INVOKER_MAIN_CLASS = "com.google.cloud.functions.invoker.runner.Invoker";
  private static final String RUNTIME_CLASSPATH = "runtimeClasspath";
  private static final String MAIN_SOURCESET = "main";
  private List<File> classpathFileLocations = new ArrayList<>();

  private static final Logger LOGGER = LoggerFactory.getLogger(GcpCloudFunctionGradlePlugin.class);
  private CloudFunctionRunExtension invokerExtension;

  public CloudFunctionRunTask() {
    getMainClass().set(INVOKER_MAIN_CLASS);
    JavaPluginExtension javaPluginExtension = getProject().getExtensions()
        .getByType(JavaPluginExtension.class);
    ConfigurationContainer configurations = getProject().getConfigurations();
    Configuration invokerConfiguration = configurations.getAt(Constants.INVOKER_CONFIGURATION_NAME);
    Configuration runtimeClasspathConfiguration = configurations.getAt(RUNTIME_CLASSPATH);

    SourceSet main = javaPluginExtension.getSourceSets().getByName(MAIN_SOURCESET);
    this.setClasspath(invokerConfiguration);
    this.getInputs().files(runtimeClasspathConfiguration, main.getOutput());
    this.classpathFileLocations.addAll(runtimeClasspathConfiguration.getFiles());
    this.classpathFileLocations.addAll(main.getOutput().getFiles());
  }

  public void setInvokerExtension(CloudFunctionRunExtension invokerExtension) {
    this.invokerExtension = invokerExtension;
  }

  @Override
  public void exec() {
    String classpathFileCollection = this.classpathFileLocations
        .stream()
        .map(file -> file.toString())
        .collect(Collectors.joining(File.pathSeparator));

    this.setArgs(
        List.of(
            "--target", invokerExtension.getTarget().get(),
            "--port", invokerExtension.getPort().get(),
            "--classpath", classpathFileCollection));

    if (invokerExtension.getTestMode().get()) {
      LOGGER.info("Classpath = {}", classpathFileCollection);
    } else {
      LOGGER.debug("Classpath = {}", classpathFileCollection);
    }
    if (!invokerExtension.getTestMode().get()) {
      super.exec();
    }

  }
}
