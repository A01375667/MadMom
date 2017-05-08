package mx.itesm.madmom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static mx.itesm.madmom.Pantalla.ANCHO;

/**
 * Created by Sandy on 5/3/2017.
 */

public class Platos extends Objeto {
    TextureRegion[][] texturaPlatos;
    int max_platos=9;

    //Max platos 4
    private int countPlatos=1;
    //Estados del Plato SOLO Horizontal
    private EstadoMovimiento estadoMovimiento = EstadoMovimiento.QUIETO;

    private final float VELOCIDAD_X = 4;


    public Platos (Texture textura, float x, float y) {
        // Lee la textura como región
        TextureRegion texturaCompleta = new TextureRegion(textura);
        // La divide en 4 frames de 194x198
        texturaPlatos = texturaCompleta.split(150,179);

        sprite = new Sprite(texturaPlatos[0][countPlatos]);
        sprite.setPosition(x, y);    // Posición inicial


    }

    // Dibuja el personaje
    public void dibujar(SpriteBatch batch) {

        batch.draw(texturaPlatos[0][countPlatos],sprite.getX(), sprite.getY(),sprite.getOriginX(), sprite.getOriginY(),sprite.getWidth(), sprite.getHeight(),2,2,0);
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

    }




    // Mueve el personaje a la derecha/izquierda, prueba los bordes de la pantalla
    private void moverHorizontal() {
        // Ejecutar movimiento horizontal

        float nuevaX = sprite.getX();
        float delta = Gdx.graphics.getDeltaTime()*VELOCIDAD_X*100;


        // ¿Quiere ir a la Derecha?
        if ( estadoMovimiento==EstadoMovimiento.MOV_DERECHA) {
            if(nuevaX>=ANCHO-sprite.getWidth()){
                estadoMovimiento= EstadoMovimiento.QUIETO;
            }
            else
                sprite.setX(sprite.getX()+delta);

        }
        // ¿Quiere ir a la izquierda?
        if ( estadoMovimiento==EstadoMovimiento.MOV_IZQUIERDA) {
            if(nuevaX<=0)
                estadoMovimiento= EstadoMovimiento.QUIETO;
            else
                sprite.setX(sprite.getX()-delta);
        }

    }


    public void setEstadoMovimiento(EstadoMovimiento estadoMovimiento) {
        this.estadoMovimiento = estadoMovimiento;
    }

    public void setCountPlatos (boolean plato1){
        //true si toma un plato
        if (plato1){
            if (countPlatos<max_platos) countPlatos++;
        }
        //false si toma una basura
        else {
            if (countPlatos>0) countPlatos--;
        }

    }

    public int getCountPlatos (){
        return this.countPlatos;

    }




    enum EstadoMovimiento {
        MOV_DERECHA,
        MOV_IZQUIERDA,
        QUIETO
    }


    }