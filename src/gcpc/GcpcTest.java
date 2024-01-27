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
        assertEquals(1, gcpc.updateScore(2, 1));

    }
    @Test
    public void test2() {
        Gcpc gcpc = new Gcpc(10_000);
        for (int i = 2; i <= 10_000; i++) {
            gcpc.updateScore(i, i);
        }
        assertEquals(10_000, gcpc.updateScore(2, 0));
        assertEquals(2, gcpc.updateScore(1, 0));
    }



}