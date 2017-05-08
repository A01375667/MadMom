package mx.itesm.madmom;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by MrSpecter on 28/04/17.
 */

class Botella extends Objeto
{
    public float velocidad_x = 400;      // Velocidad vertical (arriba)

    // Recibe la imagen
    public Botella(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    // Mueve
    public void mover(float delta) {
        float distancia = velocidad_x*delta;
        sprite.setX(sprite.getX()+distancia);
    }

    public boolean chocaCon(Bacteria bacteria) {
        return sprite.getBoundingRectangle().overlaps(bacteria.sprite.getBoundingRectangle());
    }
}
