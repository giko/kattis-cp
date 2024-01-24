package doctorkattis;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoctorKattisTest {
    @Test
    void arriveAtClinicTest() throws InterruptedException {
        Clinic dk = new Clinic();
        dk.arriveAtClinic("LUNA", (byte) 31);
        dk.arriveAtClinic("BARRY", (byte) 31);
        assertEquals("LUNA", dk.query());
        dk.arriveAtClinic("NALA", (byte) 55);
        assertEquals("NALA", dk.query());
        dk.updateInfectionLevel("LUNA", (byte) 24);
        assertEquals("LUNA", dk.query());
        dk.updateInfectionLevel("LUNA", (byte) 10);
        dk.updateInfectionLevel("NALA", (byte) 10);
        dk.updateInfectionLevel("NALA", (byte) 10);
        assertEquals("NALA", dk.query());
        dk.treated("LUNA");
        assertEquals("NALA", dk.query());
        dk.treated("NALA");
        dk.treated("BARRY");
        assertEquals("The clinic is empty", dk.query());
    }

    @Test
    void patientCompare() {
        Patient luna = new Patient("LUNA", 33, 0);
        Patient barry = new Patient("BARRY", 33, 1);
        Patient nala = new Patient("BARRY", 55, 2);

        assertTrue(luna.compareTo(barry) > 0);
        assertTrue(nala.compareTo(barry) > 0);

    }


}