package stockprices;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;

class StockPrices {
    PriorityQueue<Order> buy = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.price, o1.price));
    PriorityQueue<Order> sell = new PriorityQueue<>(Comparator.comparingInt(o -> o.price));
    Integer stockPrice = null;

    private void computeSales() {
        while (!sell.isEmpty() && !buy.isEmpty() && getBidPrice() >= getAskPrice()) {
            Order sellOrder = sell.peek();
            Order buyOrder = buy.peek();
            stockPrice = sellOrder.price;
            if (sellOrder.count == buyOrder.count) {
                sell.remove();
                buy.remove();
            } else if (sellOrder.count > buyOrder.count) {
                sellOrder.count -= buyOrder.count;
                buy.remove();
            } else {
                buyOrder.count -= sellOrder.count;
                sell.remove();
            }
        }
    }

    void addBuyOrder(Order order) {
        buy.add(order);
        computeSales();
    }

    void addSellOrder(Order order) {
        sell.add(order);
        computeSales();
    }


    public Integer getAskPrice() {
        return sell.isEmpty() ? null : sell.peek().price;
    }

    public Integer getBidPrice() {
        return buy.isEmpty() ? null : buy.peek().price;
    }

    public Integer getStockPrice() {
        return stockPrice;
    }

    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        final int N = Integer.parseInt(in.readLine());
        for (int testCaseIdx = 0; testCaseIdx < N; ++testCaseIdx) {
            StockPrices sp = new StockPrices();

            int n = Integer.parseInt(in.readLine());
            for (int orderIdx = 0; orderIdx < n; ++orderIdx) {
                String[] orderStrings = in.readLine().split(" shares at ");
                String[] typeCountStrings = orderStrings[0].split(" ");
                Order order = new Order(Integer.parseInt(orderStrings[1]), Integer.parseInt(typeCountStrings[1]));
                if (typeCountStrings[0].equals("buy")) {
                    sp.addBuyOrder(order);
                } else {
                    sp.addSellOrder(order);
                }
                out.println(
                        (sp.getAskPrice() == null ? "-" : sp.getAskPrice()) + " " +
                                (sp.getBidPrice() == null ? "-" : sp.getBidPrice()) + " " +
                                (sp.getStockPrice() == null ? "-" : sp.getStockPrice())
                );
            }

        }

        out.close();
    }
}

class Order {
    final int price;
    int count;

    public Order(int price, int count) {
        this.price = price;
        this.count = count;
    }
}
