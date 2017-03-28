package mx.e5.madmom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

/**
 * Created by AnaPau MQ on 16/02/2017.
 */

public class Cucaracha extends Objeto{

    private final float VELOCIDAD_X = 2;
    private final float VELOCIDAD_Y = 2;

    private float YActual; // El tamaño real actual (cambiando)
    private float YOriginal;   // Altura inicial (no cambia)

    private float XActual; // El tamaño real actual (cambiando)
    private float XOriginal;   // Altura inicial (no cambia)

    //Animacion
    private Animation<TextureRegion> spriteAnimado;         // Animación caminando
    private float timerAnimacion;                           // Tiempo para cambiar frames de la animación

    //Estados de Movimiento Cucaracha
    private EstadoMovimiento estadoMovimiento = EstadoMovimiento.QUIETO;

    //Tiempo para cambiar direccion
    public int lastChangedDirectionTime = 0; //milliseconds
    public final int CHANGE_DIRECTION_PERIOD = 1000; //milliseconds


    // Recibe una imagen con varios frames (ver cucarachaSprite.png)
    public Cucaracha(Texture textura, float x, float y) {
        // Lee la textura como región
        TextureRegion texturaCompleta = new TextureRegion(textura);
        // La divide en 4 frames de 235x125 (ver cucarachaSprite.png)
        TextureRegion[][] texturaPersonaje = texturaCompleta.split(235, 125);
        // Crea la animación con tiempo de 0.25 segundos entre frames.

        spriteAnimado = new Animation(0.15f, texturaPersonaje[0][0], texturaPersonaje[0][1], texturaPersonaje[0][2], texturaPersonaje[0][3]);
        // Animación infinita
        spriteAnimado.setPlayMode(Animation.PlayMode.LOOP);
        // Inicia el timer que contará tiempo para saber qué frame se dibuja
        timerAnimacion = 0;
        // Crea el sprite con el personaje quieto (idle)
        sprite = new Sprite(texturaPersonaje[0][0]);
        sprite.setPosition(x, y);    // Posición inicial
    }


    // Dibuja el personaje
    public void dibujar(SpriteBatch batch) {

        // Dibuja el personaje dependiendo del estadoMovimiento
        switch (estadoMovimiento) {
            case MOV_DERECHA:
                timerAnimacion += Gdx.graphics.getDeltaTime();
                // Frame que se dibujará
                TextureRegion region = spriteAnimado.getKeyFrame(timerAnimacion);
                if (estadoMovimiento==EstadoMovimiento.MOV_DERECHA) {
                    if (!region.isFlipX()) {
                        region.flip(true,false);
                    }
                } else {
                    if (region.isFlipX()) {
                        region.flip(true,false);
                    }
                }
                batch.draw(region,sprite.getX(),sprite.getY());
                break;

            case MOV_IZQUIERDA:

            case QUIETO:
            case INICIANDO:
                sprite.draw(batch); // Dibuja el sprite estático
                break;
        }
    }

    public void actualizar(float delta){
        switch (estadoMovimiento){
            case MOV_DERECHA:
            case MOV_IZQUIERDA:
            case MUERTO:
        }

    }





    enum EstadoMovimiento {
        MUERTO,
        MOV_DERECHA,
        MOV_IZQUIERDA,
        QUIETO,
        INICIANDO
    }

}
