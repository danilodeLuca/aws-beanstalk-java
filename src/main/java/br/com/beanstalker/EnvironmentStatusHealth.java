package br.com.beanstalker;

import com.amazonaws.services.elasticbeanstalk.model.EnvironmentHealth;
import com.amazonaws.services.elasticbeanstalk.model.EnvironmentStatus;

class EnvironmentStatusHealth {
	String status;
	String health;

	EnvironmentStatusHealth(String status, String health) {
		this.status = status;
		this.health = health;
	}

	public boolean isEnvironmentUp() {
		return status != null && !EnvironmentStatus.Terminated.toString().equalsIgnoreCase(status) && !EnvironmentStatus.Terminating.toString()
				.equalsIgnoreCase(status);
	}

	public boolean isHealthy() {
		return EnvironmentHealth.Green.toString().equalsIgnoreCase(health) || checkYellowIsHealth();
	}

	private boolean checkYellowIsHealth() {
		return EnvironmentHealth.Yellow.toString().equalsIgnoreCase(health) && isEnvironmentUp();
	}

}
