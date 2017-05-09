package mx.itesm.madmom;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Sandy on 5/9/2017.
 */

public class Anillo extends Objeto {
    //scale


    // Recibe la imagen
    public Anillo (Texture textura, float x, float y, float scale) {
        super(textura, x, y);
        sprite.scale(scale);
    }
    // Dibuja el personaje
    public void dibujar(SpriteBatch batch) {
        sprite.draw(batch);
    }

    @Override
    public boolean contiene(Vector3 v) {

        return super.contiene(v);

    }

}
