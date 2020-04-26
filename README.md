# FoT-Gateway-IoT-Service

## Introduction

The FoT-Gateway-IoT-Service exposes sensor data of IoT system through a RESTful Web Service. It accesses data stored in local database, managed by [fot-gateway-local-storage](https://github.com/WiserUFBA/fot-gateway-local-storage), allowing users get data and information about sensors in JSON format.

## Installation

To install this bundle, you need install previously these dependencies in karaf:
```
bundle:install mvn:org.codehaus.jackson/jackson-jaxrs/1.9.2
bundle:install mvn:org.codehaus.jackson/jackson-core-asl/1.9.2
bundle:install mvn:org.codehaus.jackson/jackson-mapper-asl/1.9.2
```

This module depends of modules [fot-gateway-mapping-devices](https://github.com/WiserUFBA/fot-gateway-mapping-devices) and [fot-gateway-local-storage](https://github.com/WiserUFBA/fot-gateway-local-storage). They need to be installed and started before FoT-Gateway-IoT-Service.

To install this bundle using our custom maven support execute the following commands in Karaf Shell:

```sh
config:edit org.ops4j.pax.url.mvn 
config:property-append org.ops4j.pax.url.mvn.repositories ", https://github.com/WiserUFBA/wiser-mvn-repo/raw/master/releases@id=wiser"
config:update
bundle:install mvn:br.ufba.dcc.wiser.soft_iot/fot-gateway-mapping-devices/1.0.0
bundle:install mvn:br.ufba.dcc.wiser.soft_iot/fot-gateway-local-storage/1.0.0
bundle:install mvn:br.ufba.dcc.wiser.soft_iot/fot-gateway-iot-service/1.0.0
```

## How to use

This module creates a RESTful Web Service that is possible to collect data sensor and information about the devices connected.

To get connected devices:
```
http://<servicemix-urls>:<servicemix-port>/cxf/iot-service/devices
```
To get information about specific device:
```
http://<servicemix-urls>:<servicemix-port>/cxf/iot-service/device/{device_id}
```
To get data sensor of:
```
http://<servicemix-urls>:<servicemix-port>/cxf/iot-service/device/{device_id}/{sensor_id}
```
To get data sensor of in a time-interval:
```
http://<servicemix-urls>:<servicemix-port>/cxf/iot-service/device/{device_id}/{sensor_id}/{start_datetime}/{end_datetime}
```
For more information about syntax of RESTful Web Service, access:
```
http://<servicemix-urls>:<servicemix-port>/cxf/iot-service?_wadl
```

## Deploy to Maven Repo

To deploy this repo into our custom maven repo, change pom according to the new version and after that execute the following command. Please ensure that both wiser-mvn-repo and this repo are on the same folder.

```sh
mvn -DaltDeploymentRepository=release-repo::default::file:../wiser-mvn-repo/releases/ deploy
```



## Support and development

<p align="center">
	Developed by Leandro Andrade at </br>
  <img src="https://wiki.dcc.ufba.br/pub/SmartUFBA/ProjectLogo/wiserufbalogo.jpg"/>
</p>
# soft-iot-auto-irrigation
