package br.com.beanstalker.example;

import br.com.beanstalker.BeanstalkException;
import br.com.beanstalker.creator.EnvironmentCreator;
import com.amazonaws.services.elasticbeanstalk.AWSElasticBeanstalk;

public class EnvironmentCreatorExample extends BaseExample {

	public void createSimple() throws BeanstalkException {
		AWSElasticBeanstalk aws = getAWSElasticBeastalk();
		EnvironmentCreator.creator(aws).newEnv()
				.withApplicationName("APP NAME")
				.withName("ENV NAME")
				.withUrl("MY-ENV")
				.withConfiguration("64bit Amazon Linux 2017.09 v2.8.4 running Docker 17.09.1-ce")
				.withVersion("V1")
				.load();
	}

	public void createMultipleEnvs() throws BeanstalkException {
		AWSElasticBeanstalk aws = getAWSElasticBeastalk();
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
					.withVersion("V1")
				.load();
	}

	public void createFromSavedConfig() throws BeanstalkException {
		AWSElasticBeanstalk aws = getAWSElasticBeastalk();
		EnvironmentCreator.creator(aws).newEnv()
				.withApplicationName("APP NAME")
				.withName("ENV NAME")
				.withUrl("MY-ENV")
				.withSavedConfiguration("SAVED_CONFIG")
				.load();
	}

	public void killAll() throws BeanstalkException {
		AWSElasticBeanstalk aws = getAWSElasticBeastalk();
		EnvironmentCreator environmentCreator = EnvironmentCreator.creator(aws).newEnv()
				.withApplicationName("APP NAME")
				.withName("ENV NAME")
				.withUrl("MY-ENV")
				.withSavedConfiguration("SAVED_CONFIG")
				.load();

		environmentCreator.killAll();
	}

	public void killSomeEnv() throws InterruptedException, BeanstalkException {
		AWSElasticBeanstalk aws = getAWSElasticBeastalk();
		EnvironmentCreator environmentCreator = EnvironmentCreator.creator(aws).newEnv()
				.withApplicationName("APP NAME")
				.withName("ENV NAME")
				.withUrl("MY-ENV")
				.withSavedConfiguration("SAVED_CONFIG")
				.load();

		environmentCreator.kill("APP NAME", "ENV NAME");
	}

	public void getEnv() throws InterruptedException, BeanstalkException {
		AWSElasticBeanstalk aws = getAWSElasticBeastalk();
		EnvironmentCreator environmentCreator = EnvironmentCreator.creator(aws).newEnv()
				.withApplicationName("APP NAME")
				.withName("ENV NAME")
				.withUrl("MY-ENV")
				.withSavedConfiguration("SAVED_CONFIG")
				.load();

		environmentCreator.getEnvironment("APP NAME", "ENV NAME");
	}
}
