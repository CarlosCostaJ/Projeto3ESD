package modelos;

/**
 *
 * @author carlo
 */

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;

public class Pokemon extends Molde{
    public String nome;
    public String descricao;
    
    
    public Pokemon (int id, String nome, Vector2 pos, Vector2 dim, Color color ) {
        this.id = id;
        this.nome = nome;
        this.pos = pos;
        this.dim = dim;
        this.color = color;
        this.hitBox = new Rectangle ( this.pos.x - this.dim.x / 2, this.pos.y - this.dim.y / 2, this.dim.x, this.dim.y );
    }
    
    public Pokemon (int id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    
    @Override
    public void draw( EngineFrame engine ) {
        // Desenha o corpo do NPC
        engine.fillRectangle( pos.x - dim.x / 2, pos.y - dim.y / 2, dim.x, dim.y, color );
        engine.drawRectangle( pos.x - dim.x / 2, pos.y - dim.y / 2, dim.x, dim.y, EngineFrame.BLACK );
        engine.drawRectangle( hitBox.x, hitBox.y, hitBox.width, hitBox.height, EngineFrame.BLUE );
        
        // Escreve o ID do NPC em cima dele para sabermos quem é o número 1, 2, etc.
        engine.drawText( nome, pos.x - 33, pos.y - 38, EngineFrame.BLACK );
    }
    
    public void reposicionarPokemon( double x, double y ){
        this.pos.x = x;
        this.pos.y = y;
    }
    
    
}
