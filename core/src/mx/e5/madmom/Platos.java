package mx.e5.madmom;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Sandy on 5/3/2017.
 */

public class Platos extends Objeto {
    TextureRegion[][] texturaPlatos;
    float x, y;
    int countPlatos=8;

    private final float VELOCIDAD_X = 2;


    public Platos (Texture textura, float x, float y) {
        // Lee la textura como región
        TextureRegion texturaCompleta = new TextureRegion(textura);
        // La divide en 4 frames de 194x198
        texturaPlatos = texturaCompleta.split(164,179);

        sprite = new Sprite(texturaPlatos[0][0]);
        sprite.setPosition(x, y);    // Posición inicial

    }

    // Dibuja el personaje
    public void dibujar(SpriteBatch batch) {

        //sprite.draw(batch); // Dibuja el sprite estático

        batch.draw(texturaPlatos[0][8],sprite.getX(), sprite.getY(),sprite.getOriginX(), sprite.getOriginY(),sprite.getWidth(), sprite.getHeight(),2,2,0);
    }




    }