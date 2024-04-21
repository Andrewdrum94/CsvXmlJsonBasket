package ru.netology.basket;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public class Basket {

    private String[] products;
    private int[] prices;
    private int[] count;
    private int sumProducts;

    public String[] getProducts() {
        return products;
    }

    public int[] getPrices() {
        return prices;
    }

    public int[] getCount() {
        return count;
    }

    public int getSumProducts() {
        return sumProducts;
    }

    public void setSumProducts(int sumProducts) {
        this.sumProducts = sumProducts;
    }

    public void setProducts(String[] products) {
        this.products = products;
    }

    public void setPrices(int[] prices) {
        this.prices = prices;
    }

    public void setCount(int[] count) {
        this.count = count;
    }

    public Basket() {
    }

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.count = new int[products.length];
    }

    public Basket(String[] products, int[] prices, int[] count, int sumProducts) {
        this.products = products;
        this.prices = prices;
        this.count = count;
        this.sumProducts = sumProducts;
    }


    public void printProducts() {
        for (int i = 0; i < products.length; i++) {
            System.out.println(products[i] + " " + prices[i] + " руб/шт");
        }
    }

    public void addToCard(int product, int amount) {
        int currentPrice = prices[product];
        sumProducts += amount * currentPrice;
        count[product] += amount;
    }

    public void printCart() {
        for (int i = 0; i < count.length; i++) {
            if (count[i] > 0) {
                System.out.println(
                        products[i] + " " + count[i] + " шт " + prices[i] + " руб/шт " + (count[i] * prices[i]) + " руб в сумме");
            }
        }
        System.out.println("Итого: " + this.sumProducts + " руб");
    }

    public void saveTxt() {
        try (PrintWriter out = new PrintWriter("basket.txt")) {
            for (int i = 0; i < products.length; i++) {
                if (i != (products.length - 1)) {
                    out.print(products[i] + " ");
                } else {
                    out.println(products[i]);
                }
            }
            for (int j = 0; j < prices.length; j++) {
                if (j != (prices.length - 1)) {
                    out.print(prices[j] + " ");
                } else {
                    out.println(prices[j]);
                }
            }
            for (int c = 0; c < count.length; c++) {
                if (c != (count.length - 1)) {
                    out.print(count[c] + " ");
                } else {
                    out.println(count[c]);
                }
            }
            out.println(sumProducts);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Basket defaultBasket() {
        return new Basket(new String[]{"Хлеб", "Яблоки", "Молоко"}, new int[]{50, 15, 70});
    }

    public static Basket loadFromTxtFile(File txtFile) {
        try (BufferedReader in = new BufferedReader(new FileReader(txtFile))) {
            String[] product = in.readLine().split(" ");
            int[] prices = parseInputStringToIntegerArray(in.readLine().split(" "));
            int[] count = parseInputStringToIntegerArray(in.readLine().split(" "));
            int sumProducts = Integer.parseInt(in.readLine());
            return new Basket(product, prices, count, sumProducts);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return defaultBasket();
        }
    }

    private static int[] parseInputStringToIntegerArray(String[] pricesString) {
        int[] prices = new int[pricesString.length];
        for (int i = 0; i < pricesString.length; i++) {
            prices[i] = Integer.parseInt(pricesString[i]);
        }
        return prices;
    }

    public void saveJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("basket.json"), this);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Basket loadFromJson(File jsonFile) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonFile, Basket.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return defaultBasket();
        }
    }
}
