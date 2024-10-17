package com.conversor;

import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final List<String> SUPPORTED_CURRENCIES = Arrays.asList(
            "USD", "ARS", "BRL", "CLP", "COP", "EUR", "GBP", "CAD", "MXN"
    );

    public static void main(String[] args) {
        try {
            JsonObject rates = CurrencyAPI.getExchangeRates("USD");
            Scanner scanner = new Scanner(System.in);
            ConversionHistory history = new ConversionHistory();

            while (true) {
                printMainMenu();
                System.out.print("Ingrese una opción (1, 2, 3, 4 o 5): ");

                int option;
                try {
                    option = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Opción no válida. Por favor ingrese un número del 1 al 5.");
                    scanner.next(); // Limpiar el scanner de la entrada incorrecta
                    continue;
                }

                if (option == 5) {
                    System.out.println("Gracias por usar el conversor de monedas. ¡Hasta luego!");
                    history.saveToFile();  // Guardar el historial antes de salir
                    break;
                }

                if (option == 4) {
                    history.showHistory();
                    continue;
                }

                String fromCurrency, toCurrency;
                if (option == 3) {
                    System.out.println("Seleccione la moneda de origen:");
                    fromCurrency = selectCurrency(scanner);
                    if (fromCurrency == null) continue;

                    System.out.println("Seleccione la moneda de destino:");
                    toCurrency = selectCurrency(scanner);
                    if (toCurrency == null) continue;
                } else {
                    System.out.println("Seleccione la moneda de la otra divisa:");
                    String selectedCurrency = selectCurrency(scanner);
                    if (selectedCurrency == null) continue;

                    if (option == 1) {
                        fromCurrency = "USD";
                        toCurrency = selectedCurrency;
                    } else {
                        fromCurrency = selectedCurrency;
                        toCurrency = "USD";
                    }
                }

                System.out.print("Ingrese la cantidad a convertir: ");
                double amount;
                try {
                    amount = scanner.nextDouble();
                } catch (InputMismatchException e) {
                    System.out.println("Entrada no válida. Por favor ingrese un número válido.");
                    scanner.next(); // Limpiar la entrada incorrecta
                    continue;
                }

                System.out.print("Ingrese el número de decimales que desea en el resultado (por ejemplo, 2): ");
                int decimals;
                try {
                    decimals = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Número de decimales no válido. Usando 2 por defecto.");
                    scanner.next(); // Limpiar la entrada incorrecta
                    decimals = 2;
                }

                // Obtener las tasas de cambio y calcular el resultado
                double fromRate = CurrencyConverter.getExchangeRate(rates, fromCurrency);
                double toRate = CurrencyConverter.getExchangeRate(rates, toCurrency);
                double result = CurrencyConverter.convertCurrency(amount, fromRate, toRate);

                // Mostrar el resultado de la conversión usando el número de decimales especificado
                String formattedResult = CurrencyConverter.formatResult(result, decimals);
                System.out.printf(">>> %.2f %s equivalen a %s %s\n", amount, fromCurrency, formattedResult, toCurrency);
                history.addConversion(fromCurrency, amount, toCurrency, result);  // Guardar en el historial
            }
        } catch (Exception e) {
            System.out.println("Se ha producido un error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printMainMenu() {
        System.out.println("=============================================");
        System.out.println("      Conversor de Monedas (USD Base)       ");
        System.out.println("=============================================");
        System.out.println("Seleccione la operación que desea realizar:");
        System.out.println("1. Convertir USD a otra moneda");
        System.out.println("2. Convertir otra moneda a USD");
        System.out.println("3. Convertir entre dos monedas diferentes");
        System.out.println("4. Mostrar historial de conversiones");
        System.out.println("5. Salir");
    }

    private static String selectCurrency(Scanner scanner) {
        for (int i = 0; i < SUPPORTED_CURRENCIES.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, SUPPORTED_CURRENCIES.get(i));
        }

        System.out.print("Ingrese el número de la moneda que desea seleccionar: ");
        int currencyIndex;

        try {
            currencyIndex = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada no válida. Por favor ingrese un número de la lista.");
            scanner.next(); // Limpiar la entrada incorrecta
            return null;
        }

        if (currencyIndex < 1 || currencyIndex > SUPPORTED_CURRENCIES.size()) {
            System.out.println("Número fuera de rango. Inténtelo de nuevo.");
            return null;
        }

        return SUPPORTED_CURRENCIES.get(currencyIndex - 1);  // Devolver el código de la moneda
    }
}
