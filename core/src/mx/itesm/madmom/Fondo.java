package mx.itesm.madmom;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Jorge on 10/04/17.
 */

public class Fondo
{
    public Texture textura;
    private float x;
    private final float velocidad = 100;    // pixeles/seg.

    public Fondo(Texture textura) {
        this.textura = textura;
        x=0;
    }

    public float getX() {
        return x;
    }

    public void dibujar(SpriteBatch batch, float delta) {
        // Dibujar en posición actual
        batch.draw(textura, x, 0);    // Primer fondo
        float xDerecha = x + textura.getWidth();
        if ( xDerecha>=0 && xDerecha<Pantalla.ANCHO) {
            batch.draw(textura, xDerecha, 0);   // Segundo fondo
        }
        // Nueva posición
        x -= velocidad*delta;
        if (x <= -textura.getWidth()) { // Se sale de la pantalla?
            x = x + textura.getWidth();
        }
    }
}
