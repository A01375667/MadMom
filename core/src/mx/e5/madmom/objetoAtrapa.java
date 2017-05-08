package mx.e5.madmom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import static mx.e5.madmom.Pantalla.ALTO;
import static mx.e5.madmom.Pantalla.ANCHO;

/**
 * Created by Sandy on 5/5/2017.
 */

public class objetoAtrapa extends Objeto {
    //Velocidad en Y
    private float VELOCIDAD_Y = 2;
    private float VELOCIDAD_X=MathUtils.random(1,2);
    float delta;
    private float tiempoAire;

    private EstadoMovimiento estado= EstadoMovimiento.SUBIENDO;
    private HORIZONTAL direccion=HORIZONTAL.DERECHA;

    private Tipo tipo;


    public objetoAtrapa(Texture textura, float x, float y, Tipo tipo){
        super(textura, x, y);
        if(MathUtils.randomBoolean())
            direccion=HORIZONTAL.DERECHA;
        else direccion=HORIZONTAL.IZQUIERDA;
        this.tipo=tipo;


    }


    // Dibuja el objeto
    public void dibujar(SpriteBatch batch) {
        switch (estado){
            case SUBIENDO:
                sprite.draw(batch);
                break;
            case BAJANDO:
                if (sprite.getY()<= 6*ALTO/11) {
                    sprite.scale(0.01f);

                }

                sprite.draw(batch);

                break;
        }
    }

    public void moverObjeto (){
        delta = Gdx.graphics.getDeltaTime()*VELOCIDAD_Y*100;

        if (estado==EstadoMovimiento.SUBIENDO) {
            if (VELOCIDAD_Y>=4) VELOCIDAD_Y-=2;

            sprite.setY(sprite.getY()+delta);//Aceleracion para subir a lo largo de la pantalla

            if(sprite.getY()>=ALTO-sprite.getHeight()){//Evitar que se salga de la pantalla
                tiempoAire= MathUtils.random(1.5f,2.5f);
                VELOCIDAD_Y=0;
                estado=EstadoMovimiento.QUIETO;//cambia la direccion hacia abajo para que no se salga de la pantalla
                 }
            }

            else if(estado==EstadoMovimiento.BAJANDO){
            if (VELOCIDAD_Y<4) VELOCIDAD_Y+=0.5;

                sprite.setY(sprite.getY()-delta);

        }

        }

        public void mover (){
            switch (direccion){
                case IZQUIERDA:
                    delta = Gdx.graphics.getDeltaTime()*VELOCIDAD_X*100;
                    sprite.setX(sprite.getX()-delta);
                    if(sprite.getX()>ANCHO-sprite.getWidth()) direccion=HORIZONTAL.NORMAL;
                    break;
                case DERECHA:
                    delta = Gdx.graphics.getDeltaTime()*VELOCIDAD_X*100;
                    sprite.setX(sprite.getX()+delta);
                    if(sprite.getX()>ANCHO-sprite.getWidth()) direccion=HORIZONTAL.NORMAL;

            }

        }

        public void actualizar (){
            switch (estado){
                case SUBIENDO:
                    moverObjeto();
                    mover();
                    break;
                case BAJANDO:
                    moverObjeto();
                    mover();
                    break;
                case QUIETO:
                    tiempoAire-=delta;
                    if (tiempoAire<=0) estado=EstadoMovimiento.BAJANDO;
            }
        }


    public boolean colisiona(Platos platos) {

        return sprite.getBoundingRectangle().overlaps(platos.sprite.getBoundingRectangle());

    }

    enum EstadoMovimiento {
        BAJANDO,
        SUBIENDO,
        QUIETO
    }

    enum HORIZONTAL {
        DERECHA,
        IZQUIERDA,
        NORMAL
    }


    enum Tipo {
        BASURA,
        PLATO

    }

}



