package gcpc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GcpcTest {
    @Test
    public void test() {
        Gcpc gcpc = new Gcpc(10_000);
        assertEquals(2, gcpc.updateScore(2, 3));
        gcpc.updateScore(1, 4);
        gcpc.updateScore(4, 2);
        assertEquals(3, gcpc.updateScore(3, 5));
        assertEquals(1, gcpc.updateScore(1, 0));

    }

}