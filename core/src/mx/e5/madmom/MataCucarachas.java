package mx.e5.madmom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jorge Jiménez on 13/02/17.
 * Created by Ana Paula M on 16/02/17.
 */

public class MataCucarachas extends Pantalla
{
    private final MadMom madMom;

    private AssetManager manager;

    int vueltaInicial = 0;

    // Imágenes que se utilizarán
    private Texture texturaFondoMataCucarachas;
    private Texture texturaBtnPausaMataCuc;
    private Texture texturaCucaracha;

    // Tiempo visible de instrucciones
    private float tiempoVisibleInstrucciones = 2.0f;

    // Tiempo del minijuego
    private float tiempoMiniJuego = 10;

    // Dibujar
    private SpriteBatch batch;

    // Escenas
    private Stage escenaMataCucarachas;

    //Arreglo con las cucarachas
    ArrayList<Cucaracha> arrCucas = new ArrayList<Cucaracha>();

    private Texto textoInstruccion;   // Para poner mensajes en la pantalla
    private Texto textoTiempo;

    // Constructor
    public MataCucarachas(MadMom madMom){
        this.madMom = madMom;
        this.manager = madMom.getAssetManager();
    }

   @Override
    public void show() {
        cargarTexturas();
        crearObjetos();
    }

    private void cargarTexturas() {
        texturaFondoMataCucarachas = manager.get("fondoBaño.jpg");
        texturaBtnPausaMataCuc = manager.get("btnPausa.png");
        texturaCucaracha = manager.get("cucaracha.png");
    }

    private void crearObjetos() {
        batch = new SpriteBatch();
        escenaMataCucarachas = new Stage(vista, batch);
        Image imgFondo = new Image(texturaFondoMataCucarachas);
        escenaMataCucarachas.addActor(imgFondo);
        textoInstruccion = new Texto("fuenteTextoInstruccion.fnt");
        textoTiempo = new Texto("fuenteTiempo.fnt");

        //Botón pausa
        TextureRegionDrawable trdBtnPausa = new TextureRegionDrawable(new TextureRegion(texturaBtnPausaMataCuc));
        ImageButton btnPausa = new ImageButton(trdBtnPausa);
        btnPausa.setPosition(ANCHO - 6*btnPausa.getWidth()/4, 18*btnPausa.getHeight()/4);
        escenaMataCucarachas.addActor(btnPausa);

        // Acción botón
        btnPausa.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                madMom.setScreen(new PantallaMenu(madMom));
            }
        });


        //Dibujar Cucarachas iniciales


       /* Cucaracha cuca1 = new Cucaracha(texturaCucaracha,ALTO/2, ANCHO/2);
        Cucaracha cuca2 = new Cucaracha(texturaCucaracha,ALTO/3, ANCHO/3);
        Cucaracha cuca3 = new Cucaracha(texturaCucaracha,ALTO/4, ANCHO/4);
        Cucaracha cuca4 = new Cucaracha(texturaCucaracha,ALTO/5, ANCHO/5);
        arrCucas.add(cuca1);
        arrCucas.add(cuca2);
        arrCucas.add(cuca3);
        arrCucas.add(cuca4);
        dibujarCucas(arrCucas);


        Gdx.app.log("clicked", arrCucas.toString());
        Gdx.input.setInputProcessor(escenaMataCucarachas);
        Gdx.input.setCatchBackKey(false);*/
    }

    @Override
    public void render(float delta) {

        /*Random rn = new Random();
        vueltaInicial+= rn.nextInt(50)+1;
        borrarPantalla();
        escenaMataCucarachas.draw();
        arrCucas = moverCucas(arrCucas);

        if(vueltaInicial%100 == 0){ //Cucarachas nuevas

            Random xran = new Random();
            Random yran = new Random();

            float maxX = xran.nextInt((int) Pantalla.ALTO)+1;
            float maxY = yran.nextInt((int) Pantalla.ALTO)+1;

            Cucaracha cuca = new Cucaracha(texturaCucaracha, maxX, maxY);
            escenaMataCucarachas.addActor(cuca.cucaImg);
            arrCucas.add(cuca);
        }

        batch.begin();

        tiempoMiniJuego -= delta;
        // Tiempo restante
        textoTiempo.mostrarMensaje(batch, "TIEMPO RESTANTE: ", 7*ANCHO/10, 5*ALTO/32);
        textoTiempo.mostrarMensaje(batch, String.format("%.0f", tiempoMiniJuego), 10*ANCHO/11, 5*ALTO/32);

        tiempoVisibleInstrucciones -= delta;
        if(tiempoVisibleInstrucciones > 0){
            // Instrucciones
            textoInstruccion.mostrarMensaje(batch, "EVITA LA INVASION", ANCHO/2, 3*ALTO/4);
        }

        if(tiempoMiniJuego <= 0){
            madMom.vidasJugador--;
            madMom.setScreen(new PantallaProgreso(madMom));
        }

        batch.end();*/
    }

    private void dibujarCucas(ArrayList<Cucaracha> arreglo) {

       /* for (Cucaracha CUCA : arreglo) {

            escenaMataCucarachas.addActor(CUCA.cucaImg);

        }*/
    }

    /*private ArrayList<Cucaracha> moverCucas(ArrayList<Cucaracha> arreglo){

        for (Cucaracha CUCA : arreglo) {

            CUCA.mover();
            if(CUCA.VerSiBorrarCuca() == true){arreglo.remove(CUCA);}

        }
        return arreglo;

    }*/


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {


    }
}
