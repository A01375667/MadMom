package mx.e5.madmom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import static mx.e5.madmom.Pantalla.ALTO;
import static mx.e5.madmom.Pantalla.ANCHO;

/**
 * Created by AnaPau MQ on 16/02/2017.
 */

public class Cucaracha extends Objeto{

    private EstadoMovimiento estado;

    private final float VELOCIDAD_X = 2;
    private final float VELOCIDAD_Y = 2;


    //Animacion
    private Animation<TextureRegion> spriteAnimado;         // Animación caminando
    private float timerAnimacion;                           // Tiempo para cambiar frames de la animación

    //Estados de Movimiento Cucaracha Horizontal
    private EstadoMovimiento estadoMovimiento = EstadoMovimiento.QUIETO;

    //Estados de Movimiento Cucaracha Horizontal
    private EstadoMovimientoVertical estadoMovimientoVertical=EstadoMovimientoVertical.NORMAL;

    //
    private float originalX;
    private float originalY;

    private float angulo=45;
    private float nuevoAngulo;
    private float arcoTangente;



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



       if (MathUtils.randomBoolean())
        setEstadoMovimiento(EstadoMovimiento.MOV_IZQUIERDA);
       else setEstadoMovimiento(EstadoMovimiento.MOV_DERECHA);

      if (MathUtils.randomBoolean()) setEstadoMovimientoVertical(EstadoMovimientoVertical.SUBIENDO);
       else setEstadoMovimientoVertical(EstadoMovimientoVertical.BAJANDO);






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
                        region.flip(true,true);
                    }
                } else {
                    if (region.isFlipX()) {
                        region.flip(true,true);
                    }
                }
                batch.draw(region, sprite.getX(), sprite.getY(), sprite.getOriginX(), sprite.getOriginY(),sprite.getWidth(), sprite.getHeight(), 1, 1, angulo);
                //batch.draw(region,sprite.getX(),sprite.getY());
                break;

            case MOV_IZQUIERDA:
                timerAnimacion += Gdx.graphics.getDeltaTime();
                // Frame que se dibujará
                region = spriteAnimado.getKeyFrame(timerAnimacion);

                if (estadoMovimiento==EstadoMovimiento.MOV_DERECHA) {

                    if (!region.isFlipX()) {
                        region.flip(true,false);

                    }
                } else {
                    if (region.isFlipX()) {
                        region.flip(true,false);
                    }
                }

                batch.draw(region, sprite.getX(), sprite.getY(), sprite.getOriginX(), sprite.getOriginY(),sprite.getWidth(), sprite.getHeight(), 1, 1, angulo);
                //batch.draw(region,sprite.getX(),sprite.getY());
                break;

            case QUIETO:
            case INICIANDO:

                sprite.draw(batch); // Dibuja el sprite estático
                break;
        }



    }

    public void actualizar(float delta){
        switch (estadoMovimiento){

            case MOV_DERECHA:
                moverHorizontal();
                break;
            case MOV_IZQUIERDA:
                moverHorizontal();
                break;

        }

            switch (estadoMovimientoVertical){
                case SUBIENDO:
                    moverVertical();
                     break;


                case BAJANDO:
                    moverVertical();
                    break;

                    }

            }



    // Mueve el personaje a la derecha/izquierda, prueba los bordes de la pantalla
    private void moverHorizontal() {
        // Ejecutar movimiento horizontal

        float nuevaX = sprite.getX();
        float delta = Gdx.graphics.getDeltaTime()*VELOCIDAD_X*100;


        // ¿Quiere ir a la Derecha?
        if ( estadoMovimiento==EstadoMovimiento.MOV_DERECHA) {
            sprite.setX(sprite.getX()+delta);
            if(nuevaX>=ANCHO-sprite.getWidth()){
                estadoMovimiento=EstadoMovimiento.MOV_IZQUIERDA;
            }
        }
        // ¿Quiere ir a la izquierda?
        if ( estadoMovimiento==EstadoMovimiento.MOV_IZQUIERDA) {

            sprite.setX(sprite.getX()-delta);
            if(nuevaX<=0){
                estadoMovimiento= EstadoMovimiento.MOV_DERECHA;

            }



        }



    }

    public void moverVertical(){
        float delta = Gdx.graphics.getDeltaTime()*VELOCIDAD_Y*100;
        originalX=sprite.getX();
        originalY=sprite.getY();

        switch (estadoMovimientoVertical){
            case SUBIENDO:
                sprite.setY(sprite.getY()+delta);//Aceleracion para subir a lo largo de la pantalla
                if(sprite.getY()>=ALTO-sprite.getHeight()){//Evitar que se salga de la pantalla
                    estadoMovimientoVertical=EstadoMovimientoVertical.BAJANDO;//cambia la direccion hacia abajo para que no se salga de la pantalla

                }
                break;

            case BAJANDO:
                sprite.setY(sprite.getY()-delta);
                if(sprite.getY()<=0){//Evitar que se salga de la pantall
                    estadoMovimientoVertical=EstadoMovimientoVertical.SUBIENDO;//cambia la direccion hacia abajo para que no se salga de la pantalla
                }
                break;

        }

    }

    // Modificador de estadoMovimiento
    public void setEstadoMovimiento(EstadoMovimiento estadoMovimiento) {
        this.estadoMovimiento = estadoMovimiento;
    }

    // Modificador de estadoMovimiento
    public void setEstadoMovimientoVertical (EstadoMovimientoVertical estadoMovimientoVertical) {
        this.estadoMovimientoVertical=estadoMovimientoVertical;
    }


    @Override
    public boolean contiene(Vector3 v) {

        return super.contiene(v);

    }





    enum EstadoMovimiento {

        MOV_DERECHA,
        MOV_IZQUIERDA,
        QUIETO,
        INICIANDO
    }

    enum EstadoMovimientoVertical {
        SUBIENDO,
        BAJANDO,
        NORMAL
    }

}
