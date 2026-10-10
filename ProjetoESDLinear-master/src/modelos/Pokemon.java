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
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;
import br.com.davidbuzatto.jsge.image.Image;
import static br.com.davidbuzatto.jsge.image.ImageUtils.loadImage;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class Pokemon extends Molde{
    
    public String nome;
    public int hpMaximo;
    public int hpAtual;
    public int ataque;
    public int velocidade;
    public String descricao;
    
    //Sprites Overworld e em batalha
    public FrameByFrameAnimation<SpriteMapAnimationFrame> spriteOverworld;
    public String caminhoSprite;
    public int spritePrincipal;
    //public int linhasSpriteMap;
    //public int colunasSpriteMap;
    public List<Image> framesBatalhaFrente; // GIF animado de frente
    public List<Image> framesBatalhaCostas;
    
    //Golpes vejo dps
    public List<String> golpes;
    
    public Pokemon (int id, String nome, Vector2 pos, Vector2 dim, Color color, String caminhoSprite ) {
        this.id = id;
        this.nome = nome;
        this.pos = pos;
        this.dim = dim;
        this.color = color;
        this.caminhoSprite = caminhoSprite;
        this.hitBox = new Rectangle ( (this.pos.x - this.dim.x / 2), (this.pos.y - this.dim.y / 2), this.dim.x, this.dim.y );
        
        
        spriteOverworld = new FrameByFrameAnimation<>( 
            0.2, 
            AnimationUtils.getSpriteMapAnimationFrameList(  
                loadImage( caminhoSprite ), 
                4, 512/4, 512/4
            )
        );
    
    }
    
    public Pokemon (int id, String nome, Vector2 pos, Vector2 dim, String caminhoSprite, int linhasSPritemap, int colunasSpritemap, 
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
                colunasSpritemap, x, y, larguraSpritemap/linhasSPritemap, alturaSpritemap/colunasSpritemap
            )
        );
    
    }
    
    public Pokemon (int id, String nome, Vector2 pos, Vector2 dim, String caminhoSprite, int linhasSPritemap, int colunasSpritemap, 
            int larguraSpritemap, int alturaSpritemap, int spritePrincipal) {
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
                colunasSpritemap, larguraSpritemap/linhasSPritemap, alturaSpritemap/colunasSpritemap
            )
        );
    
    }
    
    @Override
    public void draw( EngineFrame engine ) {
        // Desenha o corpo do NPC
        //engine.fillRectangle( pos.x - dim.x / 2, pos.y - dim.y / 2, dim.x, dim.y, color );
        //engine.drawRectangle( pos.x - dim.x / 2, pos.y - dim.y / 2, dim.x, dim.y, EngineFrame.BLACK );
        engine.drawRectangle( hitBox.x, hitBox.y, hitBox.width, hitBox.height, EngineFrame.BLUE );
        
        // Escreve o ID do NPC em cima dele para sabermos quem é o número 1, 2, etc.
        engine.drawText( nome, pos.x - 33, pos.y - 38, EngineFrame.BLACK );
                
        spriteOverworld.getFrame(this.spritePrincipal).draw( engine, pos.x - dim.x / 2, pos.y - dim.y / 2 );
    }
    
    public void reposicionarHitBox() {
        this.hitBox = new Rectangle( (this.pos.x - this.dim.x / 2)+4, (this.pos.y - this.dim.y / 2)+4, this.dim.x-8, this.dim.y-4 );
    }
    
    public void reposicionarPokemon( double x, double y ){
        this.pos.x = x;
        this.pos.y = y;
    }
    
    
}
