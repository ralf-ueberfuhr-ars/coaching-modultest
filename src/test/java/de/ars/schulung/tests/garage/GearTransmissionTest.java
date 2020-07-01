package de.ars.schulung.tests.garage;

import de.ars.schulung.tests.garage.exception.ShiftNotPossibleException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class GearTransmissionTest {

    GearTransmission g;

    @BeforeEach
    void setup() {
        g = new GearTransmission(6);
    }

    @Test
    @DisplayName("Testet ersten ShiftUp")
    void testFirstShiftUp() throws ShiftNotPossibleException {
        // Arrange
        //GearTransmission g = new GearTransmission(6);
        // Act
        g.shiftUp();
        // Assert
        assertAll( //
                () -> assertEquals(1,g.getCurrentGear()), //
                () -> assertEquals(6, g.getMaxGear())
        );
    }

    @Test
    void testShiftUpTooMuch() throws ShiftNotPossibleException {
        // Arrange
        //GearTransmission g = new GearTransmission(6);
        // Act
        g.shiftUp();
        g.shiftUp();
        g.shiftUp();
        g.shiftUp();
        g.shiftUp();
        g.shiftUp();
        // Assert
        assertThrows(ShiftNotPossibleException.class, () -> g.shiftUp());
    }




    /*
    public void shiftUp() throws ShiftNotPossibleException {
		if (currentGear == null) {
			currentGear = 1;
		} else if (currentGear != maxGear) {
			currentGear++;
		} else {
			throw new ShiftNotPossibleException();
		}
	}
     */


}
