package de.ars.schulung.tests.garage;

import de.ars.schulung.tests.garage.exception.ShiftNotPossibleException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarTest {

    private @Mock Clutch clutch;
    private @Mock Engine engine;
    private @Mock GasTank gastank;
    private @Mock GearTransmission geartransmission;

    @InjectMocks
    private Car car; // Testling

    @Test
    void testShiftUpWhenClutchNotPressed() throws ShiftNotPossibleException {
        // Arrange
        when(clutch.isPressed()).thenReturn(false);
        // Act + Assert
        assertAll( //
                () -> assertThrows(IllegalStateException.class, car::shiftUp, "meldung"),
                () -> verify(geartransmission, never()).shiftUp()
        );
    }

    /*
        verify(gastank).fillUp(5.5);
        verify(gastank).fillUp(anyDouble());

    */

    /*
    	public void shiftUp() throws IllegalStateException, ShiftNotPossibleException {
		if (!clutch.isPressed()) {
			throw new IllegalStateException("Die Kupplung ist nich gedrückt!");
		}

		try {
			geartransmission.shiftUp();
		} catch (ShiftNotPossibleException e) {
			ErrorHandler.handleError(e);
		}
	}
     */

}
