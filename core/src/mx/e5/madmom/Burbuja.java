package mx.e5.madmom;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by MrSpecter on 18/04/17.
 */

public class Burbuja extends Objeto
{
    private final float VELOCIDAD_Y = 400;      // Velocidad vertical (arriba)

    // Recibe la imagen
    public Burbuja(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    // Mueve
    public void mover(float delta) {
        float distancia = VELOCIDAD_Y*delta;
        sprite.setY(sprite.getY()+distancia);
    }

    public boolean chocaCon(Bacteria bacteria) {
        return sprite.getBoundingRectangle().overlaps(bacteria.sprite.getBoundingRectangle());
    }
}
