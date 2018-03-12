package br.com.beanstalker;

import com.amazonaws.services.elasticbeanstalk.model.EnvironmentHealth;
import com.amazonaws.services.elasticbeanstalk.model.EnvironmentStatus;
import org.junit.Assert;
import org.junit.Test;

public class EnvironmentStatusHealthTest {

	@Test
	public void testisEnvironmentUp() {
		EnvironmentStatusHealth environmentStatusHealth = new EnvironmentStatusHealth(EnvironmentStatus.Ready.toString(), null);
		Assert.assertTrue(environmentStatusHealth.isEnvironmentUp());

		environmentStatusHealth = new EnvironmentStatusHealth(EnvironmentStatus.Launching.toString(), null);
		Assert.assertTrue(environmentStatusHealth.isEnvironmentUp());

		environmentStatusHealth = new EnvironmentStatusHealth(EnvironmentStatus.Updating.toString(), null);
		Assert.assertTrue(environmentStatusHealth.isEnvironmentUp());

		environmentStatusHealth = new EnvironmentStatusHealth(EnvironmentStatus.Terminating.toString(), null);
		Assert.assertFalse(environmentStatusHealth.isEnvironmentUp());

		environmentStatusHealth = new EnvironmentStatusHealth(EnvironmentStatus.Terminated.toString(), null);
		Assert.assertFalse(environmentStatusHealth.isEnvironmentUp());
	}

	@Test
	public void testIsHealthy() {
		EnvironmentStatusHealth environmentStatusHealth = new EnvironmentStatusHealth(null, EnvironmentHealth.Green.toString());
		Assert.assertTrue(environmentStatusHealth.isHealthy());

		environmentStatusHealth = new EnvironmentStatusHealth(null, EnvironmentHealth.Grey.toString());
		Assert.assertFalse(environmentStatusHealth.isHealthy());

		environmentStatusHealth = new EnvironmentStatusHealth(null, EnvironmentHealth.Grey.toString());
		Assert.assertFalse(environmentStatusHealth.isHealthy());
	}

	@Test
	public void testIsHealthyYellow() {
		EnvironmentStatusHealth environmentStatusHealth = new EnvironmentStatusHealth(null, EnvironmentHealth.Yellow.toString());
		Assert.assertFalse(environmentStatusHealth.isHealthy());

		environmentStatusHealth = new EnvironmentStatusHealth(EnvironmentStatus.Ready.toString(), EnvironmentHealth.Yellow.toString());
		Assert.assertTrue(environmentStatusHealth.isHealthy());

		environmentStatusHealth = new EnvironmentStatusHealth(EnvironmentStatus.Updating.toString(), EnvironmentHealth.Yellow.toString());
		Assert.assertTrue(environmentStatusHealth.isHealthy());

		environmentStatusHealth = new EnvironmentStatusHealth(EnvironmentStatus.Launching.toString(), EnvironmentHealth.Yellow.toString());
		Assert.assertTrue(environmentStatusHealth.isHealthy());

		environmentStatusHealth = new EnvironmentStatusHealth(EnvironmentStatus.Terminated.toString(), EnvironmentHealth.Yellow.toString());
		Assert.assertFalse(environmentStatusHealth.isHealthy());

		environmentStatusHealth = new EnvironmentStatusHealth(EnvironmentStatus.Terminating.toString(), EnvironmentHealth.Yellow.toString());
		Assert.assertFalse(environmentStatusHealth.isHealthy());

	}
}
