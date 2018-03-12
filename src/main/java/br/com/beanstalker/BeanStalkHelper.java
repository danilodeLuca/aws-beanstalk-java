package br.com.beanstalker;

import com.amazonaws.services.elasticbeanstalk.AWSElasticBeanstalk;
import com.amazonaws.services.elasticbeanstalk.model.*;

import java.util.List;

public class BeanStalkHelper {

	private ByType type;
	private AWSElasticBeanstalk awsBeanStalk;
	private static final int MONITORING_INTERVAL = 1000 * 20;
	private static final int MONITORING_COUNT = 100;

	public BeanStalkHelper(ByType type) {
		this.type = type;
	}

	public BeanStalkHelper(AWSElasticBeanstalk awsBeanStalk) {
		this.awsBeanStalk = awsBeanStalk;
	}

	public static BeanStalkHelper byId() {
		return new BeanStalkHelper(ByType.ID);
	}

	public static BeanStalkHelper byName() {
		return new BeanStalkHelper(ByType.NAME);
	}

	public BeanStalkHelper rebuildEnvironment(String environmentId) throws InterruptedException, BeanStalkException {
		boolean environmentUp = isEnvironmentUp(environmentId);
		if (environmentUp)
			return this;

		RebuildEnvironmentRequest rebuildEnvironmentRequest = new RebuildEnvironmentRequest();
		rebuildEnvironmentRequest.withEnvironmentId(environmentId);
		this.awsBeanStalk.rebuildEnvironment(rebuildEnvironmentRequest);
		waitEnviromentToBeReady(environmentId);
		return this;
	}

	private boolean isEnvironmentUp(String environmentId) throws BeanStalkException {
		String actualStatus = getEnvironmentStatus(environmentId);
		return !actualStatus.equalsIgnoreCase(EnvironmentStatus.Terminated.toString()) && !actualStatus
				.equalsIgnoreCase(EnvironmentStatus.Terminating.toString());

	}

	private String getEnvironmentStatus(String environmentId) throws BeanStalkException {
		List<EnvironmentDescription> environments = getEnvironments(environmentId);
		if (environments.size() == 0) {
			System.out.println("No environments with that id were found.");
			throw new BeanStalkException("No environments with that id were found. Id: " + environmentId);
		}

		EnvironmentDescription environment = environments.get(0);
		System.out.println(" [STATUS/HEALTH] " + environment.getStatus() + "/" + environment.getHealth());

		return environment.getStatus();
	}

	public BeanStalkHelper terminateEnvironment(String environmentId) throws InterruptedException {
		TerminateEnvironmentRequest terminateConfig = new TerminateEnvironmentRequest();
		terminateConfig.withEnvironmentId(environmentId);
		this.awsBeanStalk.terminateEnvironment(terminateConfig);
		waitEnviromentToBeShutDown(environmentId);
		return this;
	}

	private void waitEnviromentToBeShutDown(String environmentId) throws InterruptedException {
		waitForEnvironmentToTransitionToStateAndHealth(environmentId, EnvironmentStatus.Terminated, EnvironmentHealth.Grey);
	}

	public void waitEnviromentToBeReady(String environmentId) throws InterruptedException {
		waitForEnvironmentToTransitionToStateAndHealth(environmentId, EnvironmentStatus.Ready, EnvironmentHealth.Green);
	}

	public void waitForEnvironmentToTransitionToStateAndHealth(String environmentId, EnvironmentStatus state, EnvironmentHealth health)
			throws InterruptedException {
		int count = 0;
		while (true) {
			Thread.sleep(MONITORING_INTERVAL);

			if (count++ > MONITORING_COUNT) {
				throw new RuntimeException("Environment " + environmentId + " never transitioned to " + state + "/" + health);
			}

			List<EnvironmentDescription> environments = getEnvironments(environmentId);

			if (environments.size() == 0) {
				System.out.println("No environments with that id were found.");
				return;
			}

			EnvironmentDescription environment = environments.get(0);
			System.out.println(" - " + environment.getStatus() + "/" + environment.getHealth());
			if (environment.getStatus().equalsIgnoreCase(state.toString()) == false) {
				continue;
			}

			if (health != null && environment.getHealth().equalsIgnoreCase(health.toString()) == false) {
				continue;
			}
			return;
		}
	}

	private List<EnvironmentDescription> getEnvironments(String environmentId) {
		DescribeEnvironmentsRequest describer = new DescribeEnvironmentsRequest().withEnvironmentIds(environmentId);

		return this.awsBeanStalk.describeEnvironments(describer).getEnvironments();
	}
}
