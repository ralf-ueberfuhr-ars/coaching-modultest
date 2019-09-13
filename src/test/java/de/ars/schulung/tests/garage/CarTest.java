package de.ars.schulung.tests.garage;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.ars.schulung.tests.garage.exception.ShiftNotPossibleException;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class CarTest {

	@Mock(lenient = true) // allow unnecessary stubbing
	private Engine engine;
	@Mock
	private GearTransmission transmission;
	@Mock
	private GasTank tank;
	@Mock
	private Clutch clutch;

	@InjectMocks
	private Car car;

	@AfterEach
	void ensureNoMoreInteractions() {
		verifyNoMoreInteractions(engine, tank, transmission, clutch);
	}

	@Nested
	@DisplayName("fill-up tests")
	class FillUpTests {
		@DisplayName("started engine")
		@Test
		void testNoFillUpWithStartedEngine() {
			// arrange
			when(engine.isEngineStarted()).thenReturn(true);
			// act+assert
			assertThrows(IllegalStateException.class, () -> {
				car.fillUp(5d);
			});
			// assert
			verify(engine).isEngineStarted();
			verify(tank, never()).fillUp(anyDouble());
		}

		@DisplayName("amount not possible")
		@Test
		void testNoFillUpWithAmountNotPossible() {
			// arrange
			when(engine.isEngineStarted()).thenReturn(false);
			when(tank.isAmountPossible(anyDouble())).thenReturn(false);
			// act
			car.fillUp(5d);
			// assert
			verify(engine).isEngineStarted();
			verify(tank, never()).fillUp(anyDouble());
		}

		@DisplayName("(default case)")
		@Test
		void testFillUp() {
			// arrange
			final double amount = 5d;
			when(engine.isEngineStarted()).thenReturn(false);
			when(tank.isAmountPossible(anyDouble())).thenReturn(true);
			// act
			car.fillUp(amount);
			// assert
			assertAll( //
					() -> {
						verify(engine).isEngineStarted();
					}, //
					() -> {
						verify(tank).isAmountPossible(amount);
					}, //
					() -> {
						verify(tank).fillUp(amount);
					} //
			);
		}
	}

	@Nested
	@DisplayName("shift-up tests")
	class ShiftUpTests {

		@DisplayName("(default case)")
		@Test
		void testShiftUp() throws ShiftNotPossibleException {
			// arrange
			when(clutch.isPressed()).thenReturn(true);
			// act
			car.shiftUp();
			// assert
			verify(transmission).shiftUp();
		}

		@DisplayName("clutch not pressed")
		@Test
		void testShiftUpWithClutchNotPressed() throws ShiftNotPossibleException {
			// arrange
			when(clutch.isPressed()).thenReturn(false);
			// act+assert
			assertThrows(IllegalStateException.class, car::shiftUp);
			// assert
			verify(transmission, never()).shiftUp();
		}

		@DisplayName("shift ends with exception")
		@Test
		void testShiftUpWithException() throws ShiftNotPossibleException {
			// arrange
			when(clutch.isPressed()).thenReturn(true);
			doThrow(ShiftNotPossibleException.class).when(transmission).shiftUp();
			// act+assert
			assertThrows(ShiftNotPossibleException.class, car::shiftUp);
			// assert
		}

	}

	@Nested
	@DisplayName("drive tests")
	class DriveTests {

		@DisplayName("(default case)")
		@Test
		void testDrive() {
			// arrange
			when(tank.isEmpty()).thenReturn(false);
			when(engine.isEngineStarted()).thenReturn(false);
			// act
			car.drive();
			// assert
			verify(engine).start();
			verify(engine).isEngineStarted();
		}

		@DisplayName("gastank is empty")
		@Test
		void testDriveWithEmptyGasTank() {
			// arrange
			when(tank.isEmpty()).thenReturn(true);
			when(engine.isEngineStarted()).thenReturn(false);
			// act
			car.drive();
			// assert
			verify(engine, never()).start();
			verify(engine, atMostOnce()).isEngineStarted();
		}

		@DisplayName("both gastank is empty and engine is started")
		@Test
		void testDriveWithEmptyGasTankAndStartedEngine() {
			// arrange
			when(tank.isEmpty()).thenReturn(true);
			when(engine.isEngineStarted()).thenReturn(true);
			// act
			car.drive();
			// assert
			verify(engine, never()).start();
			verify(engine, atMostOnce()).isEngineStarted();
		}

		@DisplayName("engine is started")
		@Test
		void testDriveWithStartedEngine() {
			// arrange
			when(tank.isEmpty()).thenReturn(false);
			when(engine.isEngineStarted()).thenReturn(true);
			// act
			car.drive();
			// assert
			verify(engine, never()).start();
			verify(engine).isEngineStarted();
		}

	}

	@Nested
	@DisplayName("park tests")
	class ParkTests {

		@DisplayName("(default case)")
		@Test
		void testDrive() {
			// arrange
			when(engine.isEngineStarted()).thenReturn(true);
			// act
			car.park();
			// assert
			verify(engine).stop();
			verify(engine).isEngineStarted();
		}

		@DisplayName("gastank is empty")
		@Test
		void testDriveWithEmptyGasTank() {
			// arrange
			when(tank.isEmpty()).thenReturn(true);
			when(engine.isEngineStarted()).thenReturn(false);
			// act
			car.drive();
			// assert
			verify(engine, never()).start();
			verify(engine, atMostOnce()).isEngineStarted();
		}

		@DisplayName("both gastank is empty and engine is started")
		@Test
		void testDriveWithEmptyGasTankAndStartedEngine() {
			// arrange
			when(tank.isEmpty()).thenReturn(true);
			when(engine.isEngineStarted()).thenReturn(true);
			// act
			car.drive();
			// assert
			verify(engine, never()).start();
			verify(engine, atMostOnce()).isEngineStarted();
		}

		@DisplayName("engine is started")
		@Test
		void testDriveWithStartedEngine() {
			// arrange
			when(tank.isEmpty()).thenReturn(false);
			when(engine.isEngineStarted()).thenReturn(true);
			// act
			car.drive();
			// assert
			verify(engine, never()).start();
			verify(engine).isEngineStarted();
		}

	}

}
