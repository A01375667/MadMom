package mx.e5.madmom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.Random;

/**
 * Created by AnaPau MQ on 16/02/2017.
 */

public class Cucaracha{

    TextureRegionDrawable cucadib;
    ImageButton cucaImg;
    float currentX;
    float currentY;
    private float DX = 4; // Incremento en x, define el dezplanzamiento en cada frame
    private float DY = 4;

    public Cucaracha(Texture textura, float x, float y) {

        this.cucadib = new TextureRegionDrawable(new TextureRegion(textura));
        this.cucaImg = new ImageButton(cucadib);

        this.cucaImg.setPosition(x, y);
        this.currentX = x;
        this.currentY = y;

    }

    public void ChangePosition(float x, float y){

        this.cucaImg.setPosition(x, y);
    }

    public TextureRegionDrawable getCucadib() {
        return cucadib;
    }

    public ImageButton getCucaImg() {
        return cucaImg;
    }

    public void mover(){


        float xp = cucaImg.getX();
        float yp = cucaImg.getY();

        //Prueba limites DERECHA-IZQUIERDA

        if(xp>=Pantalla.ANCHO || xp <=0) {

            DX = -DX; //iNVIERTE EL SENTIDO


        }

        if(DX>=0){

            Random rn = new Random();
            DX += (Gdx.graphics.getDeltaTime()/5)*(rn.nextInt(10)+1);
        }

        if(DY>=0){

            Random rn2 = new Random();
            DY+= (Gdx.graphics.getDeltaTime()/5)*(rn2.nextInt(10)+1);;
        }



        //Prueba limites ARRIBA-ABAJO
        if(yp>= Pantalla.ALTO || yp <=0){

            DY=-DY;

        }


        cucaImg.setPosition(xp+DX,yp+DY);

    }

    public boolean VerSiBorrarCuca(){

        final boolean[] bandera = {false};

        cucaImg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "Me hicieron CLICK");
                bandera[0] = true;
                cucaImg.remove();
            }
        });

        return bandera[0];

    }

}
