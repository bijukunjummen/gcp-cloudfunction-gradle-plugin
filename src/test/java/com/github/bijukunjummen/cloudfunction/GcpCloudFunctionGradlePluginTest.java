package com.github.bijukunjummen.cloudfunction;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Biju Kunjummen
 */
class GcpCloudFunctionGradlePluginTest {
    @Test
    void pluginRegistersATask() {
        Project project = ProjectBuilder.builder().build();
        project.getPlugins().apply("java");
        project.getPlugins().apply("io.github.bijukunjummen.cloudfunction");

        assertNotNull(project.getTasks().findByName("cloudFunctionRun"));
    }
}
