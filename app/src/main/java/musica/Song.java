package musica;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una cancion en la base de datos.
 * 
 * @author Martin Aguilar
 */
public class Song implements Entity {
    private String title;
    private String artist;
    private String author;
    private int[] length;
    private LocalDate releaseDate;
    private String genre;
    private String size;

    public Song() {
        title = "";
        length = new int[] { 0, 0 };
        artist = "";
        author = "";
        releaseDate = null;
        genre = "";
        size = "";
    }

    @Override
    public void scan(InteractiveScanner scanner) {
        this.title = scanner.nextLine("Titulo de la Cancion", 30);
        this.length = scanner.nextSongLength("Duracion (Minutos:Segundos)");
        this.artist = scanner.nextLineAlphabetic("Artista", 20);
        this.author = scanner.nextLineAlphabetic("Autor", 20);
        this.releaseDate = scanner.nextDate("Fecha de lanzamiento (dd/mm/aaaa)");
        this.genre = scanner.nextLineAlphabetic("Genero", 20);
        this.size = Float.toString(((length[0] * 2048) + (length[1] * 34)) / 1000);
    }

    /**
     * Realiza una busqueda en la lista de canciones por multiples criterios, que
     * seran seleccionados por el usuario.
     * 
     * @param songs Lista de canciones disponibles.
     * @return Lista de canciones que cumplen con los criterios de busqueda.
     */
    public static List<Song> filter(ArrayList<Song> songs, InteractiveScanner scanner) {
        List<Song> results = new ArrayList<Song>();

        int option = 0;

        do {
            System.out.println("Buscar por...");
            System.out.println("""
                    1. Titulo
                    2. Artista
                    3. Autor
                    4. Fecha de lanzamiento
                    5. Genero
                    6. Duracion
                    7. Tamaño
                    0. Cancelar""");

            option = scanner.nextInt("Opcion");

            switch (option) {
                case 1 -> {
                    String title = scanner.nextLine("Titulo", 30);
                    results = songs.stream().filter(song -> song.getTitle().toLowerCase().contains(title.toLowerCase()))
                            .toList();
                }

                case 2 -> {
                    String artist = scanner.nextLineAlphabetic("Artista", 20);
                    results = songs.stream()
                            .filter(song -> song.getArtist().toLowerCase().contains(artist.toLowerCase())).toList();
                }

                case 3 -> {
                    String author = scanner.nextLineAlphabetic("Autor", 20);
                    results = songs.stream()
                            .filter(song -> song.getAuthor().toLowerCase().contains(author.toLowerCase())).toList();
                }

                case 4 -> {
                    LocalDate releaseDate = scanner.nextDate("Fecha de lanzamiento (dd/mm/aaaa)");
                    results = songs.stream().filter(song -> song.getReleaseDate().equals(releaseDate)).toList();
                }

                case 5 -> {
                    String genre = scanner.nextLineAlphabetic("Genero", 20);
                    results = songs.stream().filter(song -> song.getGenre().toLowerCase().equals(genre.toLowerCase()))
                            .toList();
                }

                case 6 -> {
                    int[] length = scanner.nextSongLength("Duracion (Minutos:Segundos)");
                    results = songs.stream().filter(song -> song.getLength().equals(length)).toList();
                }

                case 7 -> {
                    String size = scanner.nextMb("Tamaño de la cancion (MB)");
                    results = songs.stream().filter(song -> song.getSize().equals(size)).toList();
                }

                case 0 -> {
                    System.out.println("Cancelando...");
                    results = new ArrayList<Song>();
                    results.add(new Song());
                }

                default -> {
                    System.out.println("Opcion invalida");
                }
            }
        } while (option < 0 || option > 7);

        return results;
    }

    /**
     * Muestra un menu para seleccionar una cancion de la lista de canciones por su
     * titulo, de manera interactiva.
     * 
     * @param availableSongs Lista de canciones disponibles.
     * @return La cancion seleccionada.
     */
    public static Song pick(InteractiveScanner scanner, ArrayList<Song> availableSongs) {
        Song result = null;
        List<Song> results;
        boolean shouldCancel = false;

        do {
            results = Song.filter(availableSongs, scanner);

            if (!results.isEmpty()) {
                if (!results.get(0).getTitle().isBlank()) {
                    System.out.println("Selecciona una cancion:");
                    for (int i = 0; i < results.size(); i++) {
                        System.out
                                .println(i + 1 + ". " + results.get(i).getArtist() + " - " + results.get(i).getTitle());
                    }

                    int option;

                    do {
                        option = scanner.nextInt("Opcion");

                        if (option < 1 || option > results.size()) {
                            System.out.println("Opcion invalida");
                        } else {
                            result = results.get(option - 1);
                        }
                    } while (option < 1 || option > results.size());
                } else {
                    shouldCancel = true;
                }
            } else {
                System.out.println("Error: No se encontraron resultados");
            }
        } while (results.isEmpty() && !shouldCancel);

        return result;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public String getSize() {
        return size;
    }

    public int[] getLength() {
        return length;
    }

    public void setLength(int[] length) {
        this.length = length;

    }

    @Override
    public String toString() {
        // Primero crea una variable que tenga el formato que le quieres dar
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return """

                        Nombre de la cancion: %s
                        Duracion: %s
                        Artista: %s
                        Autor: %s
                        Fecha de lanzamiento: %s
                        Genero: %s
                        Tamaño de la cancion: %s MB

                """.formatted(this.title,
                Integer.toString(this.length[0]) + ":" + Integer.toString(this.length[1]),
                this.artist,
                this.author, this.releaseDate.format(formatter), this.genre, this.size);
    }
}
