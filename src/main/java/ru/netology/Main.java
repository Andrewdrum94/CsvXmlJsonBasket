package ru.netology;

import ru.netology.basket.Basket;
import ru.netology.logging.ClientLog;
import ru.netology.shop.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Basket basket;
        basket = Shop.load();
        System.out.println("Список возможных товаров для покупки");
        basket.printProducts();
        ClientLog person1 = new ClientLog();
        while (true) {
            System.out.println("Введите номер товара и количество или введите 'end'");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                break;
            }
            String[] parts = input.split(" ");
            if (parts.length == 2) {
                int productNumber, productCount;
                if (isPositiveDigit(parts[0]) && isPositiveDigit(parts[1])) {
                    productNumber = Integer.parseInt(parts[0]) - 1;
                    productCount = Integer.parseInt(parts[1]);
                    if (productNumber >= basket.getProducts().length || productNumber < 0)
                        System.out.println("Товара с таким номером не существует");
                    else if (productCount <= 0) {
                        System.out.println("Количество указанных товаров не может быть отрицательным или равным нулю");
                    } else {
                        basket.addToCard(productNumber, productCount);
                        person1.log(productNumber, productCount);
                    }
                } else System.out.println("Ошибка ввода. Повторите попытку");
            } else {
                System.out.println("Вам необходимо ввести 2 числа. Повторите попытку");
            }
        }
        System.out.println("Ваша корзина:");
        basket.printCart();
        Shop.save(basket);
        Shop.log(person1);
    }

    private static boolean isPositiveDigit(String str) {
        if (str == null || str.trim().length() == 0) return false;
        if (str.charAt(0) == '-') return false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') return false;
        }
        return true;
    }
}
