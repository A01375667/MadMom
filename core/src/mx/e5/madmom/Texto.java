package mx.e5.madmom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Jorge Jiménez on 02/02/17.
 */

public class Texto
{
    private BitmapFont font;

    public Texto(String fuente){
        font = new BitmapFont(Gdx.files.internal(fuente));
    }

    public void mostrarMensaje(SpriteBatch batch, String mensaje, float x, float y){
        GlyphLayout glyp = new GlyphLayout();
        glyp.setText(font, mensaje);
        float anchoTexto = glyp.width;
        font.draw(batch, glyp, x - anchoTexto/2, y);
    }
}
