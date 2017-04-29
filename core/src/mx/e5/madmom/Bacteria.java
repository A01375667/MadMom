package mx.e5.madmom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by MrSpecter on 18/04/17.
 */

public class Bacteria extends Objeto
{
    private Animation<TextureRegion> spriteAnimado;
    private float timerAnimacion;

    public Bacteria(Texture textura, float x, float y) {
        TextureRegion texturaCompleta = new TextureRegion(textura);
        TextureRegion[][] texturaPersonaje = texturaCompleta.split(99,100);

        spriteAnimado = new Animation(0.2f, texturaPersonaje[0][0], texturaPersonaje[0][1],
                texturaPersonaje[0][2], texturaPersonaje[0][3]/*, texturaPersonaje[0][4]*/);
        spriteAnimado.setPlayMode(Animation.PlayMode.LOOP);

        timerAnimacion = 0;

        sprite = new Sprite(texturaPersonaje[0][0]);
        sprite.setPosition(x, y);
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        timerAnimacion += Gdx.graphics.getDeltaTime();
        TextureRegion region = spriteAnimado.getKeyFrame(timerAnimacion);
        batch.draw(region, sprite.getX(), sprite.getY(), sprite.getOriginX(),
                sprite.getOriginY(),sprite.getWidth(), sprite.getHeight(), 1, 1, 0);

    }

    // Actualiza la posición del objeto (tamaño, subiendo y bajando)
    public void actualizar(float delta) {

    }

    @Override
    public boolean contiene(Vector3 v) {
        return super.contiene(v);
    }

    public void desaparecer() {

    }
}
