package mx.e5.madmom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Jorge Jiménez on 13/02/17.
 */

public class MataCucarachas extends Pantalla
{
    private final MadMom madMom;

    // Imágenes que se utilizarán
    private Texture texturaFondoMataCucarachas;
    private Texture texturaBtnPausaMataCuc;

    // Dibujar
    private SpriteBatch batch;

    // Escenas
    private Stage escenaMataCucarachas;

    // Constructor
    public MataCucarachas(MadMom madMom){
        this.madMom = madMom;
    }

    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();
    }

    private void cargarTexturas() {
        texturaFondoMataCucarachas = new Texture("fondoCreditos.jpg");
        texturaBtnPausaMataCuc = new Texture("btnPausa.png");
    }

    private void crearObjetos() {
        batch = new SpriteBatch();
        escenaMataCucarachas = new Stage(vista, batch);
        Image imgFondo = new Image(texturaFondoMataCucarachas);
        escenaMataCucarachas.addActor(imgFondo);

        //Botón back
        TextureRegionDrawable trdBtnPausa = new TextureRegionDrawable(new TextureRegion(texturaBtnPausaMataCuc));
        ImageButton btnPausa = new ImageButton(trdBtnPausa);
        btnPausa.setPosition(3*btnPausa.getWidth()/4, 2*btnPausa.getHeight()/4);
        escenaMataCucarachas.addActor(btnPausa);

        // Acción botón
        btnPausa.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "Me hicieron CLICK");
                madMom.setScreen(new PantallaMenu(madMom));
            }
        });


        Gdx.input.setInputProcessor(escenaMataCucarachas);
        Gdx.input.setCatchBackKey(false);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        escenaMataCucarachas.draw();
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
