package mx.e5.madmom;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Sandy on 5/3/2017.
 */

public class AtrapaPlatos extends Pantalla {

    private final MadMom madMom;
    private final AssetManager manager;

    //Texturas
    private Texture texturaFondoCocina;
    private Texture texturaBtnPausa;
    private Objeto btnPausa;

    //Personaje
    private Texture texturaPlatos;
    private Platos platos;

    //Escenas
    private Stage escenaAtrapaPlatos;

    public AtrapaPlatos(MadMom madMom) {
        this.madMom = madMom;
        this.manager = madMom.getAssetManager();
    }

    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();

    }

    private void crearObjetos() {
        batch = new SpriteBatch();

        escenaAtrapaPlatos = new Stage(vista, batch);
        Image imgFondo = new Image(texturaFondoCocina);
        escenaAtrapaPlatos.addActor(imgFondo);

        platos= new Platos(texturaPlatos, ANCHO/2, 100);

        // Botón pausa
        btnPausa = new Objeto(texturaBtnPausa, ANCHO - 6*texturaBtnPausa.getWidth()/4, 18*texturaBtnPausa.getHeight()/4);
    }

    private void cargarTexturas() {
        texturaFondoCocina= new Texture("fondoCocina.jpg");
        texturaPlatos=new Texture("PLATOS_sprite.png");
        texturaBtnPausa= new Texture("btnPausa.png");
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        escenaAtrapaPlatos.draw();
        batch.begin();
        dibujarObjetos(platos);
        batch.end();

    }

    private void dibujarObjetos(Objeto objeto) {
        objeto.dibujar(batch);


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
