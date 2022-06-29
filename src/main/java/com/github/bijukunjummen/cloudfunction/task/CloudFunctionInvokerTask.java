package com.github.bijukunjummen.cloudfunction.task;

import com.github.bijukunjummen.cloudfunction.CloudFunctionInvokerExtension;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public class CloudFunctionInvokerTask extends DefaultTask {
    private CloudFunctionInvokerExtension invokerExtension;

    @TaskAction
    public void invoke() {
        System.out.println("Cloud Function Invoker called..");
    }

    public void setInvokerExtension(CloudFunctionInvokerExtension invokerExtension) {
        this.invokerExtension = invokerExtension;
    }
}
