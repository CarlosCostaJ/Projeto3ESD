package modelos;

/**
 *
 * @author carlo
 */

import br.com.davidbuzatto.jsge.animation.AnimationUtils;
import br.com.davidbuzatto.jsge.animation.frame.FrameByFrameAnimation;
import br.com.davidbuzatto.jsge.animation.frame.SpriteMapAnimationFrame;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.image.Image;
import static br.com.davidbuzatto.jsge.image.ImageUtils.loadImage;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;
import java.util.List;

public class Adversario extends Molde{
    
    public String nome;
    
    //Sprites Overworld e em batalha
    public FrameByFrameAnimation<SpriteMapAnimationFrame> spriteOverworld;
    public String caminhoSprite;
    public int spritePrincipal;
    
    /*public Adversario( int id, String nome, Vector2 pos, Vector2 dim, String caminhoSpriteOverworld,int linhasSPriteMap, int colunasSpriteMap, 
            int larguraSpriteMap, int alturaSpriteMap, int spritePrincipal, int x, int y ) {
        this.id = id;
        this.nome = nome;
        this.pos = pos;
        this.dim = dim;
        this.color = color;
        this.hitBox = new Rectangle ( this.pos.x - this.dim.x / 2, this.pos.y - this.dim.y / 2, this.dim.x, this.dim.y );
        //this.caminhoSpriteOverworld = caminhoSpriteOverworld;
        this.spritePrincipal = spritePrincipal;
        
         spriteOverworld = new FrameByFrameAnimation<>( 
            0.2, 
            AnimationUtils.getSpriteMapAnimationFrameList(  
                loadImage( caminhoSpriteOverworld ), 
                colunasSpriteMap, x, y, larguraSpriteMap/linhasSPriteMap, alturaSpriteMap/colunasSpriteMap
            )
        );
    }*/
    
    public Adversario (int id, String nome, Vector2 pos, Vector2 dim, String caminhoSprite, int linhasSPritemap, int colunasSpritemap, 
            int larguraSpritemap, int alturaSpritemap, int spritePrincipal, int x, int y ) {
        this.id = id;
        this.nome = nome;
        this.pos = pos;
        this.dim = dim;
        this.caminhoSprite = caminhoSprite;
        this.hitBox = new Rectangle ( (this.pos.x - this.dim.x / 2), (this.pos.y - this.dim.y / 2), this.dim.x , this.dim.y );
        this.hitBox.height = 200; 
        
        spriteOverworld = new FrameByFrameAnimation<>( 
            0.2, 
            AnimationUtils.getSpriteMapAnimationFrameList(  
                loadImage( caminhoSprite ), 
                colunasSpritemap, x, y, 64, 64
            )
        );
    
    }
    @Override
    public void draw( EngineFrame engine ) {
        // Desenha o corpo do NPC
        //engine.fillRectangle( pos.x - dim.x / 2, pos.y - dim.y / 2, dim.x, dim.y, color );
        //engine.drawRectangle( pos.x - dim.x / 2, pos.y - dim.y / 2, dim.x, dim.y, EngineFrame.BLACK );
        engine.drawRectangle( hitBox.x, hitBox.y, hitBox.width, hitBox.height, EngineFrame.BLUE );
        spriteOverworld.getFrame(this.spritePrincipal).draw( engine, pos.x - dim.x / 2, pos.y - dim.y / 2 );
        
        
        // Escreve o ID do NPC em cima dele para sabermos quem é o número 1, 2, etc.
        //engine.drawText( nome, pos.x - 5, pos.y - 25, EngineFrame.BLACK );
    }
     public void reposicionarHitBox() {
        this.hitBox = new Rectangle( (this.pos.x - this.dim.x / 2)+4, (this.pos.y - this.dim.y / 2)+4, this.dim.x-8, this.dim.y-4 );
    }
    
    public void reposicionarPokemon( double x, double y ){
        this.pos.x = x;
        this.pos.y = y;
    }
    
}
