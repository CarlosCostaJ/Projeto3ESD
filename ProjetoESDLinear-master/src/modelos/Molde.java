package modelos;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;

/**
 *
 * @author carlo
 */
public abstract class Molde {
    public int id;
    public Vector2 pos;
    public Vector2 dim;
    public Color color;    
    public Rectangle hitBox;

    public abstract void draw( EngineFrame engine );
}
