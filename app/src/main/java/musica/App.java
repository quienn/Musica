package musica;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa la aplicacion de musica.
 * 
 * Esta aplicacion puede agregar canciones, grabar discos y realizar busquedas
 * extensas en las canciones disponibles en la base de datos. Tambien, al
 * finalizar su ejecucion guarda sus datos, tanto de canciones como playlists,
 * en un archivo que al iniciar el programa sera restaurado.
 */
public class App {

  final InteractiveScanner scanner;

  ArrayList<Song> songs;

  ArrayList<Playlist> playlists;

  public App() {
    scanner = new InteractiveScanner();

    try {
      FileInputStream fis = new FileInputStream("respaldo.arr");
      ObjectInputStream ois = new ObjectInputStream(fis);

      songs = (ArrayList<Song>) ois.readObject();
      playlists = (ArrayList<Playlist>) ois.readObject();
      ois.close();
    } catch (IOException | ClassNotFoundException e) {
      songs = new ArrayList<>();
      playlists = new ArrayList<>();
    }
  }

  /**
   * Contiene un menu de opciones para el usuario. Este debe elegir unicamente una
   * opcion y el programa procedera a desplegar menus en base a este principal.
   */
  public static void main(String[] args) {
    App app = new App();

    int option = 0;

    do {
      System.out.println("Menu principal");
      System.out.println("""
          -----------------------
          1. Agregar Cancion
          2. Borrar Cancion
          3. Modificar Cancion
          -----------------------
          4. Grabar Disco
          -----------------------
          5. Buscar Canciones...
          -----------------------
          0. Salir""");

      option = app.scanner.nextInt("Opcion");
      System.out.println("-".repeat(80));

      switch (option) {
        case 1 -> app.addSong();

        case 2 -> app.removeSong();

        case 3 -> app.updateSong();

        case 4 -> app.burnCd();

        case 5 -> app.showSongs();

        case 0 -> {
          System.out.println("Saliendo...");
        }

        default -> System.out.println("Error: Opcion Invalida");
      }
    } while (option != 0);

    try {
      FileOutputStream fos = new FileOutputStream("respaldo.arr");
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(app.songs);
      oos.writeObject(app.playlists);

      oos.close();
    } catch (IOException e) {
      System.out.println("Error: No se pudo guardar el respaldo.");
    }
  }

  /**
   * Despliega un menu con opciones para modificar una cancion, ya sea por
   * atributo especifico o sobreescribiendo totalmente sus datos.
   */
  public void updateSong() {
    int option = 0;

    if (!songs.isEmpty()) {
      System.out.println("Canciones disponibles:");
      System.out.println("-".repeat(80));

      for (int i = 0; i < songs.size(); i++) {
        System.out.println(i + 1 + ". " + songs.get(i).getArtist() + " - " + songs.get(i).getTitle());
      }

      System.out.println("-".repeat(80));

      option = scanner.nextInt("Cancion a modificar");
      System.out.println("-".repeat(80));

      if (option > 0 && option <= songs.size()) {
        Song song = songs.get(option - 1);

        System.out.println("Menu de Modificacion");
        System.out.println("""
            1. Modificar Titulo
            2. Modificar Artista
            3. Modificar Autor
            4. Modificar Fecha de Lanzamiento
            5. Modificar Genero
            6. Modificar Duracion
            7. Sobreescribir por completo
            0. Cancelar
            """);

        option = scanner.nextInt("Opcion");
        System.out.println("-".repeat(80));

        switch (option) {
          case 1 -> {
            String value = scanner.nextLine("Titulo", 30);
            String confirm = scanner.nextYesNo("Confirmacion: Desea modificar el titulo? (s/n)");
            System.out.println("-".repeat(80));

            if (confirm.equals("s")) {
              song.setTitle(value);
              System.out.println("Informacion: Titulo modificado.");
            } else {
              System.out.println("Cancelando...");
            }
          }

          case 2 -> {
            String value = scanner.nextLineAlphabetic("Artista", 20);
            String confirm = scanner.nextYesNo("Confirmacion: Desea modificar el artista? (s/n)");
            System.out.println("-".repeat(80));

            if (confirm.equals("s")) {
              song.setArtist(value);
              System.out.println("Informacion: Artista modificado.");
            } else {
              System.out.println("Cancelando...");
            }
          }

          case 3 -> {
            String value = scanner.nextLineAlphabetic("Autor", 20);
            String confirm = scanner.nextYesNo("Confirmacion: Desea modificar el autor? (s/n)");
            System.out.println("-".repeat(80));

            if (confirm.equals("s")) {
              song.setAuthor(value);
              System.out.println("Informacion: Autor modificado.");
            } else {
              System.out.println("Cancelando...");
            }
          }

          case 4 -> {
            LocalDate value = scanner.nextDate("Fecha de lanzamiento (dd/mm/aaaa)");
            String confirm = scanner
                .nextYesNo("Confirmacion: Desea modificar la fecha de lanzamiento? (s/n)");
            System.out.println("-".repeat(80));

            if (confirm.equals("s")) {
              song.setReleaseDate(value);
              System.out.println("Informacion: Fecha de lanzamiento modificada.");
            } else {
              System.out.println("Cancelando...");
            }
          }

          case 5 -> {
            String value = scanner.nextLineAlphabetic("Genero", 20);
            String confirm = scanner.nextYesNo("Confirmacion: Desea modificar el genero? (s/n)");
            System.out.println("-".repeat(80));

            if (confirm.equals("s")) {
              song.setGenre(value);
              System.out.println("Informacion: Genero modificado.");
            } else {
              System.out.println("Cancelando...");
            }
          }

          case 6 -> {
            int[] value = scanner.nextSongLength("Duracion (Minutos:Segundos)");
            String confirm = scanner.nextYesNo("Confirmacion: Desea modificar la duracion? (s/n)");
            System.out.println("-".repeat(80));

            if (confirm.equals("s")) {
              song.setLength(value);
              System.out.println("Informacion: Duracion modificada.");
            } else {
              System.out.println("Cancelando...");
            }
          }

          case 7 -> {
            String confirm = scanner.nextYesNo("Confirmacion: Desea sobreescribir la cancion? (s/n)");
            System.out.println("-".repeat(80));

            if (confirm.equals("s")) {
              song.scan(scanner);
              System.out.println("Informacion: Cancion sobreescrita.");
            } else {
              System.out.println("Cancelando...");

            }
          }

          case 0 -> {
            System.out.println("Cancelando...");
          }
        }
      } else {
        System.out.println("Error: Opcion Invalida");
      }
    } else {
      System.out.println("Error: No hay canciones en el sistema.");
    }
  }

  /**
   * Despliega un menu con opciones para borrar una cancion.
   */
  public void removeSong() {
    int option = 0;

    if (!songs.isEmpty()) {
      System.out.println("Canciones disponibles:");
      System.out.println("-".repeat(80));

      for (int i = 0; i < songs.size(); i++) {
        System.out.println(i + 1 + ". " + songs.get(i).getArtist() + " - " + songs.get(i).getTitle());
      }

      System.out.println("-".repeat(80));

      option = scanner.nextInt("Cancion a borrar");
      System.out.println("-".repeat(80));

      if (option > 0 && option <= songs.size()) {
        songs.remove(option - 1);
        System.out.println("Informacion: Cancion borrada.");
      } else {
        System.out.println("Error: Opcion Invalida");
      }
    } else {
      System.out.println("Error: No hay canciones en el sistema.");
    }
  }

  /**
   * Despliega un menu con opciones para agregar una cancion. Al final, debe
   * preguntar si se desea agregar otra cancion, o si se desea cancelar la
   * operacion.
   */
  public void addSong() {
    String option = "";

    Song song = new Song();
    song.scan(scanner);

    if (Float.parseFloat(song.getSize()) > 450) {
      System.out.println("Error: La cancion no puede pesar mas de 450MB.");
      return;
    } else {
      System.out.println(song);
      option = scanner.nextYesNo("Confirmacion: Desea agregar la cancion? (s/n)");
      System.out.println("-".repeat(80));

      if (option.equals("s")) {
        songs.add(song);
        System.out.println("Cancion '%s' agregada.".formatted(song.getTitle()));
      } else {
        System.out.println("Cancelando...");
      }
    }
  }

  /**
   * Despliega un menu para grabar un disco. Al final, debe preguntar si se desea
   * grabar otro disco, o si se desea cancelar la operacion.
   */
  public void burnCd() {
    int option = 0;
    Playlist playlist;
    boolean shouldSave = false;

    if (!songs.isEmpty()) {
      // Si existe, mostrar un menu con las playlists ya disponibles para grabar
      // almacenadas
      // en el sistema.
      if (!playlists.isEmpty()) {
        System.out
            .println("Informacion: Existen playlists en el sistema. Seleccione una o cree una desde cero.");

        for (int i = 0; i < playlists.size(); i++) {
          System.out.println(i + 1 + ". " + playlists.get(i).getId());
        }
        System.out.println("0. Crear desde cero");

        option = scanner.nextInt("Opcion");
        System.out.println("-".repeat(80));

        if (option > 0 && option <= playlists.size()) {
          playlist = playlists.get(option - 1);
        } else {
          playlist = new Playlist(scanner.nextLine("ID del disco", 30));
          shouldSave = true;
        }
      } else {
        playlist = new Playlist(scanner.nextLine("ID del disco", 30));
        shouldSave = true;
      }

      do {
        System.out.println("Menu del Disco '" + playlist.getId() + "'");
        System.out.println("Almacenamiento Disponible: %sMB / 450MB".formatted(playlist.getSize()));
        System.out.println("""
            1. Agregar
            2. Borrar
            3. Mostrar
            0. Finalizar
            """);

        option = scanner.nextInt("Opcion");
        System.out.println("-".repeat(80));

        switch (option) {
          case 1 -> {
            String optionQuestion = "";
            int lastPlaylistSize = playlist.getSongs().size();

            do {
              playlist.scan(scanner, songs);

              if (playlist.getSongs().size() == lastPlaylistSize) {
                optionQuestion = "n";
              } else {
                optionQuestion = scanner.nextYesNo("Desea agregar otra cancion? (s/n)");
              }
            } while (optionQuestion.equals("s"));
          }

          case 2 -> {
            int removeOption = 0;

            if (!playlist.getSongs().isEmpty()) {
              System.out.println("Canciones en el disco:");
              System.out.println("-".repeat(80));

              for (int i = 0; i < playlist.getSongs().size(); i++) {
                System.out.println(i + 1 + ". " + playlist.getSongs().get(i).getArtist() + " - "
                    + playlist.getSongs().get(i).getTitle());
              }

              System.out.println("-".repeat(80));

              removeOption = scanner.nextInt("Cancion a borrar");
              System.out.println("-".repeat(80));

              if (removeOption > 0 && removeOption <= playlist.getSongs().size()) {
                playlist.setSize(playlist.getSize()
                    - Float.parseFloat(playlist.getSongs().get(removeOption - 1).getSize()));
                playlist.getSongs().remove(removeOption - 1);

                System.out.println("Informacion: Cancion borrada.");
              } else {
                System.out.println("Error: Opcion Invalida");
              }
            } else {
              System.out.println("Error: No hay canciones en el disco.");
            }
          }

          case 3 -> {
            if (!playlist.getSongs().isEmpty()) {
              System.out.println("Canciones en el disco:");
              System.out.println("-".repeat(80));

              for (Song song : playlist.getSongs()) {
                System.out.println(song);
              }

              System.out.println("-".repeat(80));
            } else {
              System.out.println("Error: No hay canciones en el disco.");
            }
          }

          case 0 -> {
            System.out.println("Finalizando...");
          }

          default -> System.out.println("Error: Opcion Invalida");
        }
      } while (option != 0);

      if (playlist.getSongs().isEmpty()) {
        shouldSave = false;
      }

      if (shouldSave) {
        // Deberia evitar que se guarden discos con el mismo ID

        if (playlists.stream().anyMatch(p -> p.getId().equals(playlist.getId()))) {
          int copies = 0;

          for (Playlist p : playlists) {
            if (p.getId().startsWith(playlist.getId())) {
              copies++;
            }
          }

          playlist.setId(playlist.getId() + " #" + copies);
          playlists.add(playlist);
        } else {
          playlists.add(playlist);
        }

        System.out.println("Informacion: Nueva playlist '" + playlist.getId() + "' guardada.");
      }

      if (playlist.getSongs().isEmpty()) {
        System.out.println("Error: No se puede grabar un disco sin canciones.");
      } else {
        try {
          FileOutputStream fos = new FileOutputStream("disco.dic");
          fos.write(playlist.toString().getBytes());
          fos.close();

          System.out.println("Informacion: Disco grabado.");
        } catch (IOException e) {
          System.out.println("Error: No se pudo guardar el disco.");
        }
      }
    } else {
      System.out.println("Error: No hay canciones en el sistema.");
    }
  }

  /**
   * Despliega un menu para realizar busquedas de canciones.
   */
  public void showSongs() {
    int option = 0;

    if (!songs.isEmpty()) {
      // Debe mostrar dos metodos: por filtro normal, o mostrar todas las canciones
      // disponibles
      // en el sistema.

      System.out.println("Menu de Busqueda");
      System.out.println("""
          1. Buscar por Filtro
          2. Mostrar todas las canciones
          """);

      option = scanner.nextInt("Opcion");

      switch (option) {
        case 1 -> {
          List<Song> result = Song.filter(songs, scanner);

          System.out.println("Resultados de la busqueda:");
          System.out.println("-".repeat(80));

          if (!result.isEmpty()) {
            if (result.get(0).getTitle().isBlank()) {
              System.out.println("Error: No se encontraron resultados.");
            } else {
              for (Song song : result) {
                System.out.println(song);
              }
            }
          } else {
            System.out.println("Error: No se encontraron resultados.");
          }

          System.out.println("-".repeat(80));
        }

        case 2 -> {
          System.out.println("Canciones disponibles:");
          System.out.println("-".repeat(80));

          for (Song song : songs) {
            System.out.println(song);
          }

          System.out.println("-".repeat(80));
        }

        default -> System.out.println("Error: Opcion Invalida");
      }
    } else {
      System.out.println("Error: No hay canciones en el sistema.");
    }
  }
}
