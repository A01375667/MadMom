package mx.e5.madmom;

import com.badlogic.gdx.Gdx;
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

/**
 * Created by Jorge Jiménez on 21/03/17.
 */

public class PantallaProgreso extends Pantalla
{
    private final MadMom madMom;

    // Imágenes que se utilizarán
    private Texture texturaFondoProgreso;
    private Texture texturaBtnPausaProgreso;
    private Texture texturaVida;
    private Objeto vida1;
    private Objeto vida2;
    private Objeto vida3;

    // Dibujar
    private SpriteBatch batch;

    // Texto
    private Texto textoPuntos;

    // Escenas
    private Stage escenaProgreso;

    public PantallaProgreso(MadMom madMom) {
        this.madMom = madMom;
    }

    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();
    }

    private void cargarTexturas() {
        texturaFondoProgreso = new Texture("fondoAjustes.jpg");
        texturaBtnPausaProgreso = new Texture("btnPausa.png");
        texturaVida = new Texture("caraVida.png");
    }

    private void crearObjetos() {
        batch = new SpriteBatch();
        escenaProgreso = new Stage(vista, batch);
        Image imgFondo = new Image(texturaFondoProgreso);
        escenaProgreso.addActor(imgFondo);
        textoPuntos = new Texto("fuenteTextoInstruccion.fnt");

        //Botón pausa
        TextureRegionDrawable trdBtnPausa = new TextureRegionDrawable(new TextureRegion(texturaBtnPausaProgreso));
        ImageButton btnPausa = new ImageButton(trdBtnPausa);
        btnPausa.setPosition(ANCHO - 6*btnPausa.getWidth()/4, 18*btnPausa.getHeight()/4);
        escenaProgreso.addActor(btnPausa);

        // Acción botón
        btnPausa.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                madMom.setScreen(new PantallaMenu(madMom));
            }
        });

        vida1 = new Objeto(texturaVida, ANCHO/5 - texturaVida.getWidth()/2, 1*ALTO/5 - texturaVida.getHeight()/4);
        vida2 = new Objeto(texturaVida, ANCHO/2 - texturaVida.getWidth()/2, 1*ALTO/5 - texturaVida.getHeight()/4);
        vida3 = new Objeto(texturaVida, 4*ANCHO/5 - texturaVida.getWidth()/2, 1*ALTO/5 - texturaVida.getHeight()/4);

        Gdx.input.setInputProcessor(escenaProgreso);
        Gdx.input.setCatchBackKey(false);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();

        escenaProgreso.draw();

        batch.setProjectionMatrix(camara.combined);

        batch.begin();

        textoPuntos.mostrarMensaje(batch, "PUNTUACION:", ANCHO/2, 6*ALTO/7);
        textoPuntos.mostrarMensaje(batch, Integer.toString(madMom.puntosJugador), ANCHO/2, 5*ALTO/7);
        dibujarVidas();

        batch.end();
    }

    private void dibujarVidas() {
        if(madMom.vidasJugador == 3){
            vida1.dibujar(batch);
            vida2.dibujar(batch);
            vida3.dibujar(batch);
        } else if(madMom.vidasJugador == 2){
            vida1.dibujar(batch);
            vida2.dibujar(batch);
        } else if(madMom.vidasJugador == 1){
            vida1.dibujar(batch);
        } else if(madMom.vidasJugador <= 0){
            // PERDIÓ
        }
    }

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
