package mx.itesm.madmom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
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
public class PantallaConfiguracion extends Pantalla
{
    private final MadMom madMom;

    // Imágenes que se utilizarán
    private Texture texturaFondoConfig;
    private Texture texturaBtnBackConfig;
    private Texture texturaBtnCreditos;
    private Texture texturaBtnAjustes;
    private Texture texturaBtnAcercaDe;

    // Dibujar
    private SpriteBatch batch;

    // Escenas
    private Stage escenaConfig;

    //Asset Manager
    private AssetManager manager;

    //Efecto
    private Sound efectoBoton;

    // Constructor
    public PantallaConfiguracion(MadMom madMom){
        this.madMom = madMom;
        this.manager=madMom.getAssetManager();
    }

    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();
    }

    private void cargarTexturas() {
        texturaFondoConfig = manager.get("fondoAjustes.jpg");
        texturaBtnBackConfig = manager.get("btnBack.png");
        texturaBtnCreditos = manager.get("btnCreditos.png");
        texturaBtnAjustes = manager.get("btnAjustesLetra.png");
        texturaBtnAcercaDe=manager.get("btnAcercaDe.png");
        efectoBoton=manager.get("boton.mp3");
    }

    private void crearObjetos() {
        batch = new SpriteBatch();
        escenaConfig = new Stage(vista, batch);
        Image imgFondo = new Image(texturaFondoConfig);
        escenaConfig.addActor(imgFondo);

        //Botón back
        TextureRegionDrawable trdBtnBack = new TextureRegionDrawable(new TextureRegion(texturaBtnBackConfig));
        ImageButton btnBack = new ImageButton(trdBtnBack);
        btnBack.setPosition(2*btnBack.getWidth()/4, 18*btnBack.getHeight()/4);
        escenaConfig.addActor(btnBack);
        // Acción botón back
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (madMom.estadoMusica.equals(EstadoMusica.PLAY))
                    efectoBoton.play();
                madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom, mx.itesm.madmom.Pantallas.MENU, mx.itesm.madmom.Pantallas.TipoPantalla.MENU));
            }
        });

        //Botón créditos
        TextureRegionDrawable trdBtnCreditos = new TextureRegionDrawable(new TextureRegion(texturaBtnCreditos));
        ImageButton btnCreditos = new ImageButton(trdBtnCreditos);
        btnCreditos.setPosition(ANCHO/2 - btnCreditos.getWidth()/2, ALTO - btnCreditos.getHeight()-40);
        escenaConfig.addActor(btnCreditos);
        // Acción botón créditos
        btnCreditos.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (madMom.estadoMusica.equals(EstadoMusica.PLAY))
                    efectoBoton.play();
                madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom, mx.itesm.madmom.Pantallas.CREDITOS, mx.itesm.madmom.Pantallas.TipoPantalla.MENU));
            }
        });

        //Botón Ajustes
        TextureRegionDrawable trdBtnSonido = new TextureRegionDrawable(new TextureRegion(texturaBtnAjustes));
        ImageButton btnAjustes = new ImageButton(trdBtnSonido);
        btnAjustes.setPosition(ANCHO/2 - btnAjustes.getWidth()/2, 80 );
        escenaConfig.addActor(btnAjustes);
        // Acción botón créditos
        btnAjustes.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (madMom.estadoMusica.equals(EstadoMusica.PLAY))
                    efectoBoton.play();
                madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom, mx.itesm.madmom.Pantallas.AJUSTES, mx.itesm.madmom.Pantallas.TipoPantalla.MENU));
            }
        });

        //Botón Acerca de...
        TextureRegionDrawable trdBtnAcercaDe = new TextureRegionDrawable(new TextureRegion(texturaBtnAcercaDe));
        ImageButton btnAcercaDe = new ImageButton(trdBtnAcercaDe);
        btnAcercaDe.setPosition(ANCHO/2 - btnAcercaDe.getWidth()/2, ALTO/2-100);
        escenaConfig.addActor(btnAcercaDe);
        // Acción botón créditos
        btnAcercaDe.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (madMom.estadoMusica.equals(EstadoMusica.PLAY))
                    efectoBoton.play();
                madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom, mx.itesm.madmom.Pantallas.ACERCADE, mx.itesm.madmom.Pantallas.TipoPantalla.MENU));
            }
        });



        Gdx.input.setInputProcessor(escenaConfig);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        escenaConfig.draw();
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom, mx.itesm.madmom.Pantallas.MENU, mx.itesm.madmom.Pantallas.TipoPantalla.MENU));
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
        manager.unload("fondoAjustes.jpg");
        manager.unload("btnBack.png");
        manager.unload("btnCreditos.png");
        manager.unload("btnAjustesLetra.png");
        manager.unload("btnAcercaDe.png");

    }
}
