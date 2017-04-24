package mx.e5.madmom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
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
 * Created by Sandy on 3/28/2017.
 */

public class PantallaNivel extends Pantalla {

    private final MadMom madMom;

    //Texturas
    private Texture texturaFondoNivel;
    private Texture texturaBtnNivel1;
    private Texture texturaBtnNivel2;
    private Texture texturaBtnBack;


    //Texto
    private Texto textoNivel1;   // Para poner mensajes en la pantalla
    private Texto textoNivel2;
    private Texto textoNivel;

    // Manager
    private AssetManager manager;

    // Dibujar
    private SpriteBatch batch;

    //Escenas
    private Stage escenaNivel;

    public PantallaNivel(MadMom madMom) {
        this.madMom = madMom;
        this.manager = madMom.getAssetManager();
    }


    @Override
    public void show() {
        cargarTexturas();
        crearObjetos();

    }


    private void cargarTexturas() {
        /*texturaFondoNivel= manager.get("fondoMenu.jpg");
        texturaBtnNivel1= manager.get("btnNIVELparque.png");
        texturaBtnNivel2= manager.get("btnNIVELdisco.png");
        texturaBtnBack= manager.get("btnBack.png");*/
        texturaFondoNivel= new Texture("fondoAjustes.jpg");
        texturaBtnNivel1= new Texture("btnNIVELparque.png");
        texturaBtnNivel2= new Texture("btnNIVELdisco.png");
        texturaBtnBack= new Texture("btnBack.png");

    }

    private void crearObjetos() {
        batch = new SpriteBatch();
        escenaNivel = new Stage(vista, batch);
        Image imgFondo = new Image(texturaFondoNivel);
        escenaNivel.addActor(imgFondo);

        textoNivel1 = new Texto("fuenteTiempo.fnt");
        textoNivel2 = new Texto("fuenteTiempo.fnt");
        textoNivel = new Texto("fuenteTextoInstruccion.fnt");


        //Botón Nivel 1
        TextureRegionDrawable trdBtnNivel1 = new TextureRegionDrawable(new TextureRegion(texturaBtnNivel1));
        ImageButton btnNivel1 = new ImageButton(trdBtnNivel1);
        btnNivel1.setPosition(ANCHO/9, 2*btnNivel1.getHeight()/3);
        escenaNivel.addActor(btnNivel1);
        // Acción botón play
        btnNivel1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                madMom.setScreen(new MataCucarachas(madMom));
            }
        });

        //Botón Nivel 2
        TextureRegionDrawable trdBtnNivel2 = new TextureRegionDrawable(new TextureRegion(texturaBtnNivel2));
        ImageButton btnNivel2 = new ImageButton(trdBtnNivel2);
        btnNivel2.setPosition(ANCHO - (btnNivel2.getWidth()+ANCHO/9), 2*btnNivel2.getHeight()/3);
        escenaNivel.addActor(btnNivel2);
        // Acción botón play
        btnNivel2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                //madMom.setScreen(new PantallaMenu(madMom));
            }
        });

        //Botón de Back
        TextureRegionDrawable trdBtnConfig = new TextureRegionDrawable(new TextureRegion(texturaBtnBack));
        ImageButton btnBack = new ImageButton(trdBtnConfig);
        btnBack.setPosition(2*btnBack.getWidth()/4, 18*btnBack.getHeight()/4);
        escenaNivel.addActor(btnBack);
        // Acción botón cback
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                madMom.setScreen(new PantallaMenu(madMom));

            }
        });


        Gdx.input.setInputProcessor(escenaNivel);
        Gdx.input.setCatchBackKey(false);
    }



    @Override
    public void render(float delta) {
        borrarPantalla();
        escenaNivel.draw();
        batch.begin();
        textoNivel1.mostrarMensaje(batch, "Parque",4*ANCHO/16, 7*ALTO/32 );
        textoNivel2.mostrarMensaje(batch, "Disco",12*ANCHO/16, 7*ALTO/32);
        textoNivel.mostrarMensaje(batch,"DIFICULTAD",ANCHO/2,8*ALTO/9);
        batch.end();

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
