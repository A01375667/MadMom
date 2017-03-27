package mx.e5.madmom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by AnaPaulaMq on 16/02/2017.
 */
public abstract class Pantalla implements Screen
{
    public static final float ANCHO = 1280;
    public static final float ALTO = 800;
    protected OrthographicCamera camara;
    protected Viewport vista;
    protected SpriteBatch batch;

    public Pantalla() {
        camara = new OrthographicCamera(ANCHO, ALTO);
        camara.position.set(ANCHO/2, ALTO/2, 0);
        camara.update();
        vista = new StretchViewport(ANCHO, ALTO, camara);
        batch = new SpriteBatch();
    }

    protected void borrarPantalla() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        vista.update(width, height);
        actualizarVista();
    }

    protected void actualizarVista(){

    }

    @Override
    public void hide() {
        dispose();
    }
}