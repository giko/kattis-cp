package stockprices;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockPricesTest {
    @Test
    public void testTwoSamePrices() {
        StockPrices sp = new StockPrices();
        sp.addSellOrder(new Order(99, 50));
        sp.addSellOrder(new Order(100, 50));
        sp.addBuyOrder(new Order(100, 101));
        assertEquals(100, sp.getBidPrice());
        assertNull(sp.getAskPrice());
        assertEquals(100, sp.getStockPrice());
    }
}