package com.conversor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

public class ConversionHistory {
    private final List<String> history;

    public ConversionHistory() {
        this.history = new ArrayList<>();
    }

    // Método para agregar una nueva entrada al historial
    public void addConversion(String fromCurrency, double fromAmount, String toCurrency, double toAmount) {
        LocalDateTime timestamp = LocalDateTime.now();
        String formattedTimestamp = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String entry = String.format("[%s] %.2f %s -> %.2f %s", formattedTimestamp, fromAmount, fromCurrency, toAmount, toCurrency);
        history.add(entry);
    }

    // Método para mostrar el historial de conversiones
    public void showHistory() {
        if (history.isEmpty()) {
            System.out.println("No hay conversiones realizadas aún.");
        } else {
            System.out.println("Historial de conversiones:");
            for (String entry : history) {
                System.out.println(entry);
            }
        }
    }

    // Método para guardar el historial en un archivo
    public void saveToFile() {
        try (FileWriter writer = new FileWriter("historial.txt")) {
            for (String entry : history) {
                writer.write(entry + "\n");
            }
            System.out.println("Historial guardado en historial.txt");
        } catch (IOException e) {
            System.out.println("Error al guardar el historial: " + e.getMessage());
        }
    }
}
