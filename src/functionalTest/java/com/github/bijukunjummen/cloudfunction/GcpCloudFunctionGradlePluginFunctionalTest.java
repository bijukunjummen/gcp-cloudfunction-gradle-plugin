package com.github.bijukunjummen.cloudfunction;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * @author Biju Kunjummen
 */
class GcpCloudFunctionGradlePluginFunctionalTest {
    @Test
    void canRunTask() throws IOException {
        // Setup the test build
        File projectDir = new File("build/functionalTest");
        Files.createDirectories(projectDir.toPath());
        writeString(new File(projectDir, "settings.gradle"), "");
        writeString(new File(projectDir, "build.gradle"),
                """
                        plugins {
                          id('java')
                          id('io.github.bijukunjummen.cloudfunction')
                        }
                        repositories {
                          mavenCentral()
                        } 
                        cloudFunctionRun {
                          target = "com.github.bijukunjummen.cloudfunction.HelloHttp"
                          testMode = true
                        }
                        """);

        // Run the build
        GradleRunner runner = GradleRunner.create();
        runner.forwardOutput();
        runner.withPluginClasspath();
        runner.withArguments("cloudFunctionRun");
        runner.withProjectDir(projectDir);
        BuildResult result = runner.build();

        // Verify the result
        String output = result.getOutput();
        assertTrue(output.contains("cloudFunctionRun"));
    }

    private void writeString(File file, String string) throws IOException {
        try (Writer writer = new FileWriter(file)) {
            writer.write(string);
        }
    }
}
