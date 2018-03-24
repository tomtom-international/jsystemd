# jsystemd

jsystemd aims to provide a better platform to integrate Java applications with systemd, to run as proper operating system services.

The integration provides bindings for any Java process to notify systemd once it completes initialization, as well
as provide detailed status information to the service supervisor.

The distribution of this software is composed of two parts:

* the [jsystemd-core](jsystemd-core/index.html) module that includes all the basic bindings to integrate with systemd
* the [jsystemd-spring-boot-starter](jsystemd-spring-boot-starter/index.html) module that provides automtic configuration of jsystemd when running under
inside Spring Boot applications.

The jsystemd-core module packages some components that expose the status of the JVM to systemd (the Spring Boot starter package automatically configures those).

Binaries are available for download from [GitHub](https://github.com/jpmsilva/jsystemd/releases),
and are also published to [Maven Central](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.github.jpmsilva.jsystemd%22).

Check the [How to](howto.html) page for information on how to get started.