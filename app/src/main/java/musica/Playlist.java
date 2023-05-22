package musica;

import java.util.ArrayList;
// import java.util.List;

/**
 * Representa la lista de reproduccion de un disco.
 * 
 * @author Martin Aguilar
 */
public class Playlist implements Entity {

    private String id;
    private ArrayList<Song> songs;
    private float size;

    public Playlist(String id) {
        this.id = id;
        songs = new ArrayList<Song>();
        this.size = 0;
    }

    /**
     * Obtiene el identificador unico de la lista de reproduccion.
     * 
     * @return El identificador unico de la lista de reproduccion.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador unico de la lista de reproduccion.
     * 
     * @param id El identificador unico de la lista de reproduccion.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene la lista de canciones que contiene la lista de reproduccion.
     * 
     * @return La lista de canciones que contiene la lista de reproduccion.
     */
    public ArrayList<Song> getSongs() {
        return songs;
    }

    /**
     * Establece la lista de canciones que contiene la lista de reproduccion.
     * 
     * @param songs La lista de canciones que contiene la lista de reproduccion.
     */
    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    /**
     * Obtiene el tamano de la lista de reproduccion.
     * 
     * @return El tamano de la lista de reproduccion.
     */
    public float getSize() {
        return size;
    }

    /**
     * Establece el tamano de la lista de reproduccion.
     * 
     * @param size El tamano de la lista de reproduccion.
     * 
     * @return El tamano de la lista de reproduccion.
     */
    public void setSize(float size) {
        this.size = size;
    }

    @Override
    public void scan(InteractiveScanner scanner) {
        throw new UnsupportedOperationException("Unimplemented method 'scan'");
    }

    /**
     * Despliega un menu para agregar una cancion a la lista de reproduccion.
     * 
     * @param scanner
     * @param availableSongs
     */
    public void scan(InteractiveScanner scanner, ArrayList<Song> availableSongs) {
        Song songResult = null;

        // Debe mostrar dos metodos: por filtrado o dar a seleccionar una de todas las
        // canciones

        int option = 0;

        do {
            System.out.println("Agregar cancion por...");
            System.out.println("""
                    1. Filtros
                    2. Seleccionar de todas las canciones
                    """);

            option = scanner.nextInt("Opcion");

            switch (option) {
                case 1 -> {
                    songResult = Song.pick(scanner, availableSongs);
                }

                case 2 -> {
                    for (int i = 0; i < availableSongs.size(); i++) {
                        System.out.println("%d. %s - %s".formatted(i + 1, availableSongs.get(i).getArtist(),
                                availableSongs.get(i).getTitle()));
                    }

                    int songOption = 0;

                    do {

                        songOption = scanner.nextInt("Opcion");

                        if (songOption < 1 || songOption > availableSongs.size()) {
                            // debe evitar que se entre en un ciclo infinito
                            System.out.println("Opcion invalida");
                        } else {
                            songResult = availableSongs.get(songOption - 1);
                        }
                    } while (songOption < 1 || songOption > availableSongs.size());
                }
            }
        } while (option < 1 || option > 2);

        if (songResult != null) {
            if (Float.parseFloat(songResult.getSize()) > 450) {
                System.out.println("Error: La cancion excede el tamano maximo de la lista de reproduccion.");
            } else {
                if (this.size + Float.parseFloat(songResult.getSize()) > 450) {
                    System.out.println("Error: La cancion excede el tamano maximo de la lista de reproduccion.");
                } else {
                    this.songs.add(songResult);
                    this.size += Float.parseFloat(songResult.getSize());
                }
            }
        }
    }

    /**
     * Convierte la lista de reproduccion a una cadena de texto.
     */
    @Override
    public String toString() {
        ArrayList<String> songs = new ArrayList<String>();

        for (int i = 0; i < this.songs.size(); i++) {
            songs.add((i + 1) + ". " + this.songs.get(i).getArtist() + " - " + this.songs.get(i).getTitle() + " ["
                    + this.songs.get(i).getLength()[0] + ":" + this.songs.get(i).getLength()[1] + "] " + " ("
                    + this.songs.get(i).getSize() + " MB) ");
        }

        // List<String> songs = this.songs.stream()
        // .map(song -> ". " + song.getArtist() + " - " + song.getTitle()
        // + "[" + song.getLength()[0] + ":" + song.getLength()[1] + "] "
        // + " (" + song.getSize() + " MB) ")
        // .toList();

        return """
                Titulo: %s
                ----------------------------------------
                Lista de Canciones:
                %s
                ----------------------------------------
                Tamaño: %d MB
                """.formatted(id, String.join("\n", songs), size);
    }

}
