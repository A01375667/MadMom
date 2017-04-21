package mx.e5.madmom;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Sandy on 3/26/2017.
 */

public class Objeto {
    protected Sprite sprite;    // Imagen

    public Objeto() {

    }

    public Objeto(Texture textura, float x, float y) {
        sprite = new Sprite(textura);
        sprite.setPosition(x, y);
    }

    public boolean contiene(Vector3 v) {
        float x = v.x;
        float y = v.y;

        return x>=sprite.getX() && x<=sprite.getX()+sprite.getWidth()
                && y>=sprite.getY() && y<=sprite.getY()+sprite.getHeight();
    }



    public void dibujar(SpriteBatch batch) {
        sprite.draw(batch);
    }

}
