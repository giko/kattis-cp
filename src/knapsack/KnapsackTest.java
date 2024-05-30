package knapsack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KnapsackTest {
    @Test
    public void checkBaseCases() {
        var result = KnapsackSolver.knapsack(
                new int[]{5, 5, 5},
                new int[]{1, 10, 100},
                5
        );
        assertEquals(1, result.size());
        assertEquals(2, result.get(0));


        result = KnapsackSolver.knapsack(
                new int[]{4, 3, 2, 1},
                new int[]{5, 4, 3, 2},
                6
        );
        assertEquals(3, result.size());
        assertEquals(3, result.get(0));
        assertEquals(2, result.get(1));
        assertEquals(1, result.get(2));
    }

}