package de.ars.schulung.tests.garage;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.ars.schulung.tests.garage.exception.AmountOfFuelNotPossibleException;

/**
 * This is a sample JUnit4-style test.
 */
public class GasTankTest {

	private static final double DEFAULT_CAPACITY = 60;
	private GasTank tank;

	@Rule
	public ExpectedException ex = ExpectedException.none();

	@Before
	public void setup() {
		tank = new GasTank(GasTankTest.DEFAULT_CAPACITY);
	}

	@Test
	public void testInitialState() {
		assertThat(tank.getCapacity(), is(equalTo(GasTankTest.DEFAULT_CAPACITY)));
		assertThat(tank.getFuel(), is(equalTo(0d)));
	}

	@Test
	public void testFillUpPossible() {
		final double amount = GasTankTest.DEFAULT_CAPACITY;
		tank.fillUp(amount);
		assertThat(tank.getFuel(), is(equalTo(amount)));
	}

	@Test(expected = AmountOfFuelNotPossibleException.class)
	public void testFillUpPossibleWithExpected() {
		// fill tank
		tank.fillUp(tank.getCapacity());
		final double amount = 0.5d;

		// try to tank
		tank.fillUp(amount);

		// no further tests possible (use try~catch, if necessary)
		// exception may occur during first fillUp
	}

	@Test
	public void testFillUpPossibleWithRule() {
		// fill tank
		tank.fillUp(tank.getCapacity());
		final double amount = 0.5d;

		// use rule
		ex.expect(AmountOfFuelNotPossibleException.class);
		// use custom matcher for further assertions

		// try to tank
		tank.fillUp(amount);
	}

}
