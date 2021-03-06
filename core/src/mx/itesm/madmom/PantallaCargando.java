package mx.itesm.madmom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Jorge Jiménez on 27/03/17.
 */

public class PantallaCargando extends Pantalla
{
    // Animación cargando
    private static final float TIEMPO_ENTRE_FRAMES = 0.05f;
    private Sprite spriteCargando;
    private float timerAnimacion = TIEMPO_ENTRE_FRAMES;

    // AssetManager
    private AssetManager manager;

    private MadMom madMom;
    private mx.itesm.madmom.Pantallas siguientePantalla;
    private mx.itesm.madmom.Pantallas.TipoPantalla tipo;
    private int avance; // % de carga
    private Texto texto;

    private Texture texturaCargando;

    public PantallaCargando(MadMom madMom, mx.itesm.madmom.Pantallas siguientePantalla, mx.itesm.madmom.Pantallas.TipoPantalla tipo) {
        this.madMom = madMom;
        this.siguientePantalla = siguientePantalla;
        this.tipo=tipo;
    }

    @Override
    public void show() {
        texturaCargando = new Texture(Gdx.files.internal("cargando.png"));
        spriteCargando = new Sprite(texturaCargando);
        spriteCargando.setPosition(ANCHO/2-spriteCargando.getWidth()/2,ALTO/2-spriteCargando.getHeight()/2);
        cargarRecursosSigPantalla();
        texto = new Texto("fuenteTextoInstruccion.fnt");


    }

    // Carga los recursos de la siguiente pantalla
    private void cargarRecursosSigPantalla() {
        manager = madMom.getAssetManager();
        avance = 0;
        switch (siguientePantalla) {
            case MENU:
                cargarRecursosMenu();
                break;
            case CONFIGURACION:
                cargarRecursosConfiguracion();
                break;
            case CREDITOS:
                cargarRecursosCreditos();
                break;
            case AJUSTES:
                cargarRecursosAjustes();
                break;
            case ACERCADE:
                cargarRecursosAcercaDe();
                break;
            case MATACUCARACHAS:
                cargarRecursosMataCucarachas();
                break;
            case NIVEL:
                cargarRecursosNivel();
                break;
            case INVADERS:
                cargarRecursosInvaders();
                break;
            case ATRAPAPLATOS:
                cargarRecursosAtrapaPlatos();
                break;
            case BUSCARANILLO:
                cargarRecursosBuscarAnillo();
                break;
            case PROGRESO:
                cargarRecursosProgreso();
                break;
            case HISTORIA:
                cargarRecursosHistoria();
        }
    }

    private void cargarRecursosBuscarAnillo() {
        manager.load("fondoEcontrarAnillo.jpg", Texture.class);
        manager.load("anilloEnc.png", Texture.class);
        manager.load("fondoPausa.png", Texture.class);
        manager.load("btnMusica.png", Texture.class);
        manager.load("btnMENUU.png", Texture.class);
        manager.load("cuadroVacio.png", Texture.class);
        manager.load("cuadroPaloma.png", Texture.class);
    }

    private void cargarRecursosHistoria() {
        manager.load("His1.jpg", Texture.class);
        manager.load("His2.jpg", Texture.class);
        manager.load("His3.jpg", Texture.class);
        manager.load("His4.jpg", Texture.class);
        manager.load("His5.jpg", Texture.class);
        manager.load("His6.jpg", Texture.class);
        manager.load("His7.jpg", Texture.class);
        manager.load("His8.jpg", Texture.class);
    }

    private void cargarRecursosAtrapaPlatos() {
        manager.load("fondoCocina.jpg", Texture.class);
        manager.load("PLATOS_sprite.png", Texture.class);
        manager.load("btnPausa.png", Texture.class);
        manager.load("manzana.png", Texture.class);
        manager.load("pescado.png", Texture.class);
        manager.load("Plato.png", Texture.class);
        manager.load("fondoPausa.png", Texture.class);
        manager.load("btnMusica.png", Texture.class);
        manager.load("btnMENUU.png", Texture.class);
        manager.load("cuadroVacio.png", Texture.class);
        manager.load("cuadroPaloma.png", Texture.class);
    }

    private void cargarRecursosAcercaDe() {
        manager.load("fondoAcercaDe.jpg",Texture.class);
        manager.load("btnBack.png", Texture.class);
    }

    private void cargarRecursosCreditos() {
        manager.load("fondoCreditos.png",Texture.class);
        manager.load("btnBack.png", Texture.class);
    }

    private void cargarRecursosConfiguracion() {
        manager.load("fondoAjustes.jpg", Texture.class);
        manager.load("btnBack.png", Texture.class);
        manager.load("btnCreditos.png", Texture.class);
        manager.load("btnAjustesLetra.png", Texture.class);
        manager.load("btnAcercaDe.png", Texture.class);
    }

    private void cargarRecursosAjustes() {
        manager.load("fondoAjustes.jpg", Texture.class);
        manager.load("btnMusica.png",Texture.class);
        manager.load("cuadroVacio.png", Texture.class);
        manager.load("cuadroPaloma.png", Texture.class);
        manager.load("btnBack.png", Texture.class);
        manager.load("mamaDerecha.png", Texture.class);
        manager.load("musicaMenu.mp3",  Music.class);
    }

    private void cargarRecursosMenu() {
        manager.load("fondoMenu.jpg", Texture.class);
        manager.load("btnPlay1.png", Texture.class);
        manager.load("btnAjustes.png", Texture.class);
        manager.load("musicaMenu.mp3", Music.class);
        manager.load("boton.mp3", Sound.class);
    }

    private void cargarRecursosMataCucarachas() {
        manager.load("fondoBaño.jpg", Texture.class);
        manager.load("btnPausa.png", Texture.class);
        manager.load("cucarachaSprite.png",Texture.class);
        manager.load("Disparar.mp3", Sound.class);
        manager.load("fondoPausa.png", Texture.class);
        manager.load("btnMusica.png", Texture.class);
        manager.load("btnMENUU.png", Texture.class);
        manager.load("cuadroVacio.png", Texture.class);
        manager.load("cuadroPaloma.png", Texture.class);



    }

    private void cargarRecursosNivel() {
        manager.load("fondoAjustes.jpg", Texture.class);
        manager.load("btnNIVELparque.png", Texture.class);
        manager.load("btnNIVELdisco.png", Texture.class);
        manager.load("btnBack.png", Texture.class);
        manager.load("SpaceSong.mp3", Music.class);

    }

    private void cargarRecursosInvaders() {
        manager.load("fondoInvaders.jpg", Texture.class);
        manager.load("botella.png", Texture.class);
        manager.load("spriteBacterias.png", Texture.class);
        manager.load("burbuja.png", Texture.class);
        manager.load("btnPausa.png", Texture.class);
        manager.load("bacteriaEncerrada.png", Texture.class);
        manager.load("fondoPausa.png", Texture.class);
        manager.load("btnMusica.png", Texture.class);
        manager.load("btnMENUU.png", Texture.class);
        manager.load("cuadroVacio.png", Texture.class);
        manager.load("cuadroPaloma.png", Texture.class);

    }

    private void cargarRecursosProgreso(){
        manager.load("fondoAjustes.jpg", Texture.class);
        manager.load("btnPausa.png", Texture.class);
        manager.load("caraVida.png", Texture.class);
        manager.load("fondoPausa.png", Texture.class);
        manager.load("btnMusica.png", Texture.class);
        manager.load("btnMENUU.png", Texture.class);
        manager.load("cuadroVacio.png", Texture.class);
        manager.load("cuadroPaloma.png", Texture.class);

    }

    @Override
    public void render(float delta) {
       //borrarPantalla(1,1,1,1);

        batch.setProjectionMatrix(camara.combined);
        batch.begin();

        if (tipo.equals(mx.itesm.madmom.Pantallas.TipoPantalla.MENU)){
            borrarPantalla(1,1,1,1);
            spriteCargando.draw(batch);
            texto.mostrarMensaje(batch,avance+" %",ANCHO/2,ALTO/2);}

        batch.end();
        // Actualizar
        timerAnimacion -= delta;
        if (timerAnimacion<=0) {
            timerAnimacion = TIEMPO_ENTRE_FRAMES;
            spriteCargando.rotate(20);
        }

        // Actualizar carga
        actualizarCargaRecursos();
    }

    private void actualizarCargaRecursos() {
        if (manager.update()) { // Terminó?
            switch (siguientePantalla) {
                case MENU:
                    madMom.setScreen(new mx.itesm.madmom.PantallaMenu(madMom));   // 100% de carga
                    break;
                case CONFIGURACION:
                    madMom.setScreen(new PantallaConfiguracion(madMom));
                    break;
                case CREDITOS:
                    madMom.setScreen(new mx.itesm.madmom.PantallaCreditos(madMom));
                    break;
                case AJUSTES:
                    madMom.setScreen(new PantallaAjustes(madMom));
                    break;
                case ACERCADE:
                    madMom.setScreen(new PantallaAcercaDe(madMom));
                    break;
                case MATACUCARACHAS:
                    madMom.setScreen(new MataCucarachas(madMom));   // 100% de carga
                    break;
                case NIVEL:
                    madMom.setScreen(new PantallaNivel(madMom));
                    break;
                case INVADERS:
                    madMom.setScreen(new BacteriaInvaders(madMom));
                    break;
                case BUSCARANILLO:
                    madMom.setScreen(new BuscaAnillo(madMom));
                    break;
                case PROGRESO:
                    madMom.setScreen(new PantallaProgreso(madMom));
                    break;
                case ATRAPAPLATOS:
                    madMom.setScreen(new mx.itesm.madmom.AtrapaPlatos(madMom));
                    break;
                case HISTORIA:
                    madMom.setScreen(new mx.itesm.madmom.PantallaHistoria(madMom));
            }
        }
        avance = (int)(manager.getProgress()*100);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        texturaCargando.dispose();
    }


}
