## Google Cloud Function Gradle Plugin

This is a [Gradle Plugin](https://plugins.gradle.org/) that makes it easy to test a Java based Google Cloud Function locally for Gradle based projects.


### Using the Plugin

Add the plugin to the `build.gradle` file the following way:

```gradle
plugins{
  id 'java'
  id "io.github.bijukunjummen.cloudfunction"
}
```

Note that the plugin depends on `java` plugin being present. 

Provide details of the endpoint that the function exposes:

```gradle
cloudFunctionInvoker {
  target = "functions.HelloHttp"
  port = 8080
}
```

This snippet indicates that the function being exposed is from `functions.HelloHttp` class, and the endpoint should be available locally at port 8080.

A local version of the function can now be started up this way:

```sh
./gradlew cloudFunctionInvoker
```

### Samples
Here are two sample projects which use the plugin:

1. [Http Cloud Function](https://github.com/bijukunjummen/http-cloudfunction-java-gradle)
2. [Pub/Sub Cloud Function](https://github.com/bijukunjummen/pubsub-cloudfunction-java-gradle)