package mx.itesm.madmom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Sandy on 3/26/2017.
 */

public class PantallaInicial extends Pantalla {

    private float tiempoVisible = 2.5f;
    // Es la referencia a la aplicación (la única que puede cambiar pantallas)
    private MadMom madMom;

    // Logo del tec
    private Texture texturaLogo;
    private Sprite spriteLogo;

    public PantallaInicial(MadMom madMom) {
        this.madMom = madMom;
    }


    @Override
    public void show() {
        texturaLogo = new Texture(Gdx.files.internal("logo.png"));
        spriteLogo = new Sprite(texturaLogo);
        spriteLogo.setPosition(ANCHO / 2 - spriteLogo.getWidth() / 2, ALTO / 2 - spriteLogo.getHeight() / 2);
        escalarLogo();

    }

    private void escalarLogo() {
        float factorCamara = ANCHO / ALTO;
        float factorPantalla = 1.0f * Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        float escala = factorCamara / factorPantalla;
        spriteLogo.setScale(escala, 1);
    }

    @Override
    public void render(float delta) {
        // Dibujar
        borrarPantalla();

        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        // Dibuja el logo centrado
        spriteLogo.draw(batch);
        batch.end();

        // Actualizar para cambiar pantalla
        tiempoVisible -= delta;
        if (tiempoVisible <= 0) { // Se acabaron los dos segundos
            // Cambia a la pantalla del MENU
            //juego.setScreen(new PantallaMenu(juego));
            // AHORA cambia a la pantalla "Cargando..." y después al menú
            madMom.setScreen(new PantallaCargando(madMom, Pantallas.MENU, Pantallas.TipoPantalla.MENU));
        }

    }

    @Override
    public void actualizarVista() {
        escalarLogo();
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