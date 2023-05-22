package musica;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Una version de {@code Scanner} que solicita datos interactivamente, con
 * alertas y mensajes de error, y espera hasta que la informacion sea valida.
 */
public class InteractiveScanner {
    private final Scanner scanner;

    public InteractiveScanner() {
        scanner = new Scanner(System.in);
    }

    /**
     * Solicita una linea de texto al usuario. No puede estar vacia.
     * 
     * @param message       Mensaje a desplegar al usuario.
     * @param maxCharacters Numero maximo de caracteres que puede tener la linea.
     * @return La linea ingresada por el usuario.
     */
    public String nextLine(String message, int maxCharacters) {
        String value = "";

        do {
            System.out.print(message + "> ");
            value = scanner.nextLine();

            if (value.isBlank()) {
                System.out.println("Error: El valor no puede estar vacio.");
            } else if (value.length() > maxCharacters) {
                System.out.println("Error: El valor no puede ser mayor a " + maxCharacters + " caracteres.");
            }
        } while (value.isBlank() || value.length() > maxCharacters);

        return value.trim();
    }

    /**
     * Solicita una linea de texto al usuario, unicamente valida con caracteres
     * alfabeticos y espacios. No puede estar vacia
     * 
     * @param message       Mensaje a desplegar al usuario.
     * @param maxCharacters Numero maximo de caracteres que puede tener la linea.
     * @return
     */
    public String nextLineAlphabetic(String message, int maxCharacters) {
        String value = "";
        boolean valid = false;

        do {
            System.out.print(message + "> ");
            value = scanner.nextLine();

            if (value.isBlank()) {
                System.out.println("Error: El valor no puede estar vacio.");
            } else if (value.length() > maxCharacters) {
                System.out.println("Error: El valor no puede ser mayor a " + maxCharacters + " caracteres.");
            } else if (!value.matches("[a-zA-Z ]+")) {
                System.out.println("Error: El valor solo puede contener letras y espacios.");
            } else {
                valid = true;
            }
        } while (!valid);

        return value.trim();
    }

    /**
     * Solicita un numero positivo al usuario.
     * 
     * @param message Mensaje a desplegar al usuario.
     * @return El entero ingresado por el usuario.
     */
    public int nextInt(String message) {
        int num = 0;
        boolean isNumber = false;

        do {
            System.out.print(message + "> ");
            try {

                num = Integer.parseInt(scanner.nextLine());

                if (num >= 0) {
                    isNumber = true;
                } else {
                    System.out.println("error: Debe proporcionar un número positivo.");
                }

            } catch (NumberFormatException e) {
                System.out.println("error: Debe proporcionar un número.");
            }
        } while (!isNumber);

        return num;
    }

    public LocalDate nextDate(String message) {
        LocalDate value = null;
        boolean valid = false;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        do {
            System.out.print(message + "> ");
            try {
                value = LocalDate.parse(scanner.nextLine(), formatter);

                if (value.equals(LocalDate.now()) || value.isBefore(LocalDate.now())) {
                    // Validar si es año bisiesto
                    if (value.getMonthValue() == 2 && value.getDayOfMonth() == 29) {
                        if (value.getYear() % 4 != 0) {
                            System.out.println("Error: El valor no es valido.");
                        } else {
                            valid = true;
                        }
                    } else {
                        valid = true;
                    }
                } else {
                    System.out.println("Error: El valor no debe ser una fecha futura.");
                }
            } catch (Exception e) {
                System.out.println("Error: El valor no es valido.");
            }
        } while (!valid);

        return value;
    }

    public int[] nextSongLength(String message) {
        int[] value = new int[2];
        boolean valid = false;

        do {
            System.out.print(message + "> ");
            try {
                String[] parts = scanner.nextLine().split(":");

                if (parts.length == 2) {
                    value[0] = Integer.parseInt(parts[0]);
                    value[1] = Integer.parseInt(parts[1]);

                    // nota: tampoco puede ser 0:00 (ser una cancion vacia)
                    if (value[0] >= 0 && value[1] >= 0 && value[1] < 60 && (value[0] > 0 || value[1] > 0)) {
                        valid = true;
                    } else {
                        System.out.println("Error: El valor no es valido.");
                    }
                } else {
                    System.out.println("Error: El valor no es valido.");
                }
            } catch (Exception e) {
                System.out.println("Error: El valor no es valido.");
            }
        } while (!valid);

        return value;
    }

    public String nextMb(String message) {
        String value = "";
        boolean valid = false;

        do {
            System.out.print(message + "> ");
            value = scanner.nextLine();

            if (value.isBlank()) {
                System.out.println("Error: Ingrese un valor valido");
            } else if (value.length() > 4) {
                System.out.println("Error: El valor excede la capacidad maxima");

            } else if (!value.matches("[0-9]+")) {
                System.out.println("Error: Solo se aceptan caracteres numericos");

            } else {
                valid = true;
            }

        } while (!valid);

        return value.trim();
    }

    public String nextYesNo(String message) {
        String value = "";
        boolean valid = false;

        do {
            System.out.print(message + "> ");
            value = scanner.nextLine();

            if (value.isBlank()) {
                System.out.println("Error: Ingrese un valor valido");
            } else if (!value.matches("[snSN]+")) {
                System.out.println("Error: Solo se aceptan caracteres 's' o 'n'");
            } else {
                valid = true;
            }

        } while (!valid);

        return value.toLowerCase();
    }
}
