package com.aluracursos.ConversorMonedas;

import java.net.URI;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MonedaServidor servicio = new MonedaServidor();

        boolean continuar = true;

        while (continuar) {
            System.out.println("********************************************************");
            System.out.println("Sea bienvenido/a al Conversor de Moneda");
            System.out.println("\n1) Dólar =>> Peso Chileno");
            System.out.println("2) Peso Chileno =>> Dólar");
            System.out.println("3) Dólar =>> Real Brasileño");
            System.out.println("4) Real Brasileño =>> Dólar");
            System.out.println("5) Dólar =>> Peso Colombiano");
            System.out.println("6) Peso Colombiano =>> Dólar");
            System.out.println("7) Salir");
            System.out.println("Elija una opción válida:");
            System.out.println("********************************************************");

            int opcion = scanner.nextInt();
            String base = "";
            String destino = "";

            switch (opcion) {
                case 1 -> { base = "USD"; destino = "CLP"; }
                case 2 -> { base = "CLP"; destino = "USD"; }
                case 3 -> { base = "USD"; destino = "BRL"; }
                case 4 -> { base = "BRL"; destino = "USD"; }
                case 5 -> { base = "USD"; destino = "COP"; }
                case 6 -> { base = "COP"; destino = "USD"; }
                case 7 -> {
                    System.out.println("Gracias por usar el conversor. ¡Hasta luego!");
                    continuar = false;
                    continue;
                }
                default -> {
                    System.out.println("Opción inválida. Intente nuevamente.\n");
                    continue;
                }
            }

            System.out.print("Ingrese el monto a convertir: ");
            double cantidad = scanner.nextDouble();

            URI direccion = URI.create("https://v6.exchangerate-api.com/v6/e2cfedc12ddc477e4416ecd5/latest/" + base);

            try {
                Moneda moneda = servicio.obtenerTasas(direccion);
                Double tasa = moneda.conversion_rates().get(destino);

                if (tasa != null) {
                    double resultado = cantidad * tasa;
                    System.out.printf("%.2f %s equivalen a %.2f %s%n%n", cantidad, base, resultado, destino);
                } else {
                    System.out.println("No se encontró la tasa de cambio para " + destino + "\n");
                }

            } catch (RuntimeException e) {
                System.out.println("Error al obtener tasas para " + base + ". Detalle: " + e.getMessage());
            }
        }

        scanner.close();
    }

}
