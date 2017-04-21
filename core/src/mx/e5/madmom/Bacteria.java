package mx.e5.madmom;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by MrSpecter on 18/04/17.
 */

public class Bacteria extends Objeto
{
    public Bacteria(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        super.dibujar(batch);
    }

    // Actualiza la posición del objeto (tamaño, subiendo y bajando)
    public void actualizar(float delta) {
        sprite.rotate(10);
    }

    @Override
    public boolean contiene(Vector3 v) {
        return super.contiene(v);
    }

    public void desaparecer() {

    }
}
