package de.ars.schulung.tests.garage;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import de.ars.schulung.tests.garage.exception.ShiftNotPossibleException;

@RunWith(JUnitPlatform.class)
public class GearTransmissionTest {

	private static final int MAXGEAR = 6;

	private GearTransmission transmission;

	@BeforeEach
	public void setup() {
		transmission = new GearTransmission(GearTransmissionTest.MAXGEAR);
	}

	@Test
	@DisplayName("Wrong arguments for max gear")
	void testConstructorWithWrongArgs() {
		assertAll( //
				() -> {
					assertThrows(IllegalArgumentException.class, () -> new GearTransmission(-5));
				}, //
				() -> {
					assertThrows(IllegalArgumentException.class, () -> new GearTransmission(0));
				}, //
				() -> {
					new GearTransmission(1);
				} //
		);
	}

	@Test
	@DisplayName("Initial state")
	void testInitialState() {
		assertAll( //
				() -> {
					assertThat(transmission.getCurrentGear(), is(nullValue()));
				}, //
				() -> {
					assertThat(transmission.getMaxGear(), is(equalTo(GearTransmissionTest.MAXGEAR)));
				} //
		);
	}

	@Test
	@DisplayName("Shifting up all available gears")
	void testCurrentGear() throws ShiftNotPossibleException {
		for (int i = 0; i < GearTransmissionTest.MAXGEAR; i++) {
			transmission.shiftUp();
			assertThat(transmission.getCurrentGear(), is(equalTo(i + 1)));
		}
	}

	@Test
	@DisplayName("Shifting up one gear too much")
	void testCurrentGearException() throws ShiftNotPossibleException {
		for (int i = 0; i < GearTransmissionTest.MAXGEAR; i++) {
			transmission.shiftUp();
		}
		assertThrows(ShiftNotPossibleException.class, transmission::shiftUp);
		// again, to test that the exception is thrown repeatedly
		assertThrows(ShiftNotPossibleException.class, transmission::shiftUp);
	}
	
	@ParameterizedTest(name = "Shifting up with maximum gears of {0}")
	@ValueSource(ints = {1,3,4,5,6,7,100})
	void testShiftingParameterized(int maxGears) throws ShiftNotPossibleException {
		GearTransmission gt = new GearTransmission(maxGears);
		for (int i = 0; i < maxGears; i++) {
			gt.shiftUp();
			assertThat(gt.getCurrentGear(), is(equalTo(i + 1)));
		}
		assertThrows(ShiftNotPossibleException.class, gt::shiftUp);
	}

}
