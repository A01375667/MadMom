package mx.itesm.madmom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by MrSpecter on 08/05/17.
 */

public class PantallaHistoria extends Pantalla
{
    private final MadMom madMom;
    private final AssetManager manager;
    private int cont = 1;

    // Fondos
    private mx.itesm.madmom.Fondo fondo;
    private Texture texturaFondo1;
    private Texture texturaFondo2;
    private Texture texturaFondo3;
    private Texture texturaFondo4;
    private Texture texturaFondo5;
    private Texture texturaFondo6;
    private Texture texturaFondo7;
    private Texture texturaFondo8;

    // Procesador
    private final ProcesadorEntrada procesadorEntrada = new ProcesadorEntrada();

    public PantallaHistoria(MadMom madMom){
        this.madMom = madMom;
        manager = madMom.getAssetManager();
    }

    @Override
    public void show() {
        texturaFondo1 = manager.get("His1.jpg");
        texturaFondo2 = manager.get("His2.jpg");
        texturaFondo3 = manager.get("His3.jpg");
        texturaFondo4 = manager.get("His4.jpg");
        texturaFondo5 = manager.get("His5.jpg");
        texturaFondo6 = manager.get("His6.jpg");
        texturaFondo7 = manager.get("His7.jpg");
        texturaFondo8 = manager.get("His8.jpg");
        fondo = new mx.itesm.madmom.Fondo(texturaFondo1);

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(procesadorEntrada);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        fondo.dibujar(batch, 0);
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
        manager.unload("His1.jpg");
        manager.unload("His2.jpg");
        manager.unload("His3.jpg");
        manager.unload("His4.jpg");
        manager.unload("His5.jpg");
        manager.unload("His6.jpg");
        manager.unload("His7.jpg");
        manager.unload("His8.jpg");
    }

    // Procesar entrada
    private class ProcesadorEntrada implements InputProcessor
    {
        private Vector3 v = new Vector3();
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            v.set(screenX, screenY, 0);
            camara.unproject(v);

            // CAMBIAR DE FONDO
            switch (cont){
                case 1:
                    cont++;
                    fondo.textura = texturaFondo2;
                    break;
                case 2:
                    cont++;
                    fondo.textura = texturaFondo3;
                    break;
                case 3:
                    cont++;
                    fondo.textura = texturaFondo4;
                    break;
                case 4:
                    cont++;
                    fondo.textura = texturaFondo5;
                    break;
                case 5:
                    cont++;
                    fondo.textura = texturaFondo6;
                    break;
                case 6:
                    cont++;
                    fondo.textura = texturaFondo7;
                    break;
                case 7:
                    cont++;
                    fondo.textura = texturaFondo8;
                    break;
                case 8:
                    int num= MathUtils.random(0,3);
                    if (num==1)
                        madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom,
                                mx.itesm.madmom.Pantallas.MATACUCARACHAS,
                                mx.itesm.madmom.Pantallas.TipoPantalla.JUEGO));
                    else if (num==2)
                        madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom,
                                mx.itesm.madmom.Pantallas.INVADERS,
                                mx.itesm.madmom.Pantallas.TipoPantalla.JUEGO));
                    else madMom.setScreen(new mx.itesm.madmom.PantallaCargando(madMom,
                                mx.itesm.madmom.Pantallas.ATRAPAPLATOS,
                                mx.itesm.madmom.Pantallas.TipoPantalla.JUEGO));
                    break;
            }
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }

        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }
    }
}
