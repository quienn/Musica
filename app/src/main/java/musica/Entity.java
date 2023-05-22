package musica;

import java.io.Serializable;

/**
 * Define una entidad que puede ser impresa o escaneada en la base de datos.
 */
public interface Entity extends Serializable {
    public void scan(InteractiveScanner scanner);
}
