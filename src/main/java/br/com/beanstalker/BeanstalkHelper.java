package br.com.beanstalker;

import com.amazonaws.services.elasticbeanstalk.AWSElasticBeanstalk;
import com.amazonaws.services.elasticbeanstalk.model.*;

import java.util.List;

public class BeanstalkHelper {

	private ByType type;
	private AWSElasticBeanstalk awsBeanStalk;
	private static final int MONITORING_INTERVAL = 1000 * 20;
	private static final int MONITORING_COUNT = 100;

	public BeanstalkHelper(AWSElasticBeanstalk awsBeanStalk) {
		this.awsBeanStalk = awsBeanStalk;
		byId();
	}

	public BeanstalkHelper byId() {
		this.type = ByType.ID;
		return this;
	}

	public BeanstalkHelper byName() {
		this.type = ByType.NAME;
		return this;
	}

	public BeanstalkHelper rebuildEnvironment(String environmentId) throws InterruptedException, BeanStalkException {
		if (isEnvironmentUp(environmentId))
			return this;

		RebuildEnvironmentRequest rebuildEnvironmentRequest = type.getRebuildEnvironmentRequest(environmentId);
		this.awsBeanStalk.rebuildEnvironment(rebuildEnvironmentRequest);
		waitEnviromentToBeReady(environmentId);
		return this;
	}

	public BeanstalkHelper terminateEnvironment(String environmentId) throws InterruptedException, BeanStalkException {
		if (isEnvironmentShutDown(environmentId))
			return this;

		TerminateEnvironmentRequest terminateConfig = type.getTerminateEnvironmentRequest(environmentId);
		this.awsBeanStalk.terminateEnvironment(terminateConfig);
		waitEnviromentToBeShutDown(environmentId);
		return this;
	}

	private boolean isEnvironmentUp(String environmentId) throws BeanStalkException {
		return getEnvironmentStatus(environmentId).isEnvironmentUp();
	}

	private boolean isEnvironmentShutDown(String environment) throws BeanStalkException {
		return !isEnvironmentUp(environment);
	}

	private EnvironmentStatusHealth getEnvironmentStatus(String environment) throws BeanStalkException {
		List<EnvironmentDescription> environments = getEnvironments(environment);
		if (environments.size() == 0) {
			throw new BeanStalkException("No environments with that id were found. Id: " + environment);
		}

		EnvironmentDescription environmentDesc = environments.get(0);
		System.out.println(" [STATUS/HEALTH] " + environmentDesc.getStatus() + "/" + environmentDesc.getHealth());

		return new EnvironmentStatusHealth(environmentDesc.getStatus(), environmentDesc.getHealth());
	}

	private void waitEnviromentToBeShutDown(String environmentId) throws InterruptedException, BeanStalkException {
		waitForEnvironmentToTransitionToStateAndHealth(environmentId, EnvironmentStatus.Terminated, EnvironmentHealth.Grey);
	}

	private void waitEnviromentToBeReady(String environmentId) throws InterruptedException, BeanStalkException {
		waitForEnvironmentToTransitionToStateAndHealth(environmentId, EnvironmentStatus.Ready, EnvironmentHealth.Green);
	}

	private void waitForEnvironmentToTransitionToStateAndHealth(String environment, EnvironmentStatus state, EnvironmentHealth health)
			throws InterruptedException, BeanStalkException {
		int count = 0;
		while (true) {
			Thread.sleep(MONITORING_INTERVAL);

			if (count++ > MONITORING_COUNT) {
				throw new RuntimeException("Environment " + environment + " never transitioned to " + state + "/" + health);
			}

			EnvironmentStatusHealth environmentStatus = getEnvironmentStatus(environment);
			if (environmentStatus.status.equalsIgnoreCase(state.toString()) == false) {
				continue;
			}

			if (health != null && environmentStatus.health.equalsIgnoreCase(health.toString()) == false) {
				continue;
			}
			return;
		}
	}

	private List<EnvironmentDescription> getEnvironments(String environment) {
		DescribeEnvironmentsRequest describer = type.getDescribeEnvironmentsRequest(environment);
		return this.awsBeanStalk.describeEnvironments(describer).getEnvironments();
	}
}
