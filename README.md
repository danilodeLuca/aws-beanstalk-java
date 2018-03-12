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
