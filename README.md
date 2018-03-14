# AWS Elastic Beanstalk - Java API Helper

Simple way to use AWS Elastic Beanstalk Java API

## Usage

You can checkout the class BeanstalkHelperExample.java....  Here is some code

### Rebuild Terminated Environment ById
```java
AWSElasticBeanstalk awsElasticBeastalk = getAWSElasticBeastalk();
new BeanstalkHelper(awsElasticBeastalk).byId().rebuildEnvironment("ENV_ID");
```
### Rebuild Terminated Environment ByName
```java
AWSElasticBeanstalk awsElasticBeastalk = getAWSElasticBeastalk();
new BeanstalkHelper(awsElasticBeastalk).byName().rebuildEnvironment("ENV_NAME");
```
### Terminate Environment ById
```java
AWSElasticBeanstalk awsElasticBeastalk = getAWSElasticBeastalk();
new BeanstalkHelper(awsElasticBeastalk).byId().terminateEnvironment("ENV_ID");
```
### Terminate Environment ByName
```java
AWSElasticBeanstalk awsElasticBeastalk = getAWSElasticBeastalk();
new BeanstalkHelper(awsElasticBeastalk).byName().terminateEnvironment("ENV_NAME");
```


## Environment Creator
There is one option for creation your environments using EnvironmentCreator.java

```java
AWSElasticBeanstalk aws = getAWSElasticBeastalk();
```
### Simple Environment 
```java
EnvironmentCreator.creator(aws)
	.newEnv()
		.withApplicationName("APP NAME")
		.withName("ENV NAME")
		.withUrl("MY-ENV")
		.withConfiguration("64bit Amazon Linux 2017.09 v2.8.4 running Docker 17.09.1-ce")
		.withVersion("V1")
	.process();
```
### Multiple Environments 
```java
EnvironmentCreator.creator(aws)
	.newEnv()
		.withApplicationName("APP NAME")
		.withName("ENV NAME")
		.withUrl("MY-ENV")
		.withConfiguration("64bit Amazon Linux 2017.09 v2.8.4 running Docker 17.09.1-ce")
		.withVersion("V1")
	.newEnv()
		.withApplicationName("APP NAME")
		.withName("ENV NAME2")
		.withUrl("MY-ENV2")
		.withConfiguration("64bit Amazon Linux 2017.09 v2.8.4 running Docker 17.09.1-ce")
		.withVersion("V2")
	.process();
```
### Environment from Saved configuration on your Application
```java
EnvironmentCreator.creator(aws)
	.newEnv()
		.withApplicationName("APP NAME")
		.withName("ENV NAME")
		.withUrl("MY-ENV")
		.withSavedConfiguration("SAVED_CONFIG")
	.process();
```
### Environment from Saved configuration on your Application and Wait to be ready
```java
EnvironmentCreator.creator(aws)
	.newEnv()
		.withApplicationName("APP NAME")
		.withName("ENV NAME")
		.withUrl("MY-ENV")
		.withSavedConfiguration("SAVED_CONFIG")
	.processAndWait();
```
### Killing your Environments
```java
EnvironmentCreator.creator(aws).killAll();
EnvironmentCreator.creator(aws).kill("APP NAME", "ENV NAME");
```
