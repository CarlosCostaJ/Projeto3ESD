package modelos;

/**
 *
 * @author carlo
 */
/*
 * Copyright (C) 2026 Prof. Dr. David Buzatto
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

import br.com.davidbuzatto.jsge.animation.frame.FrameByFrameAnimation;
import br.com.davidbuzatto.jsge.animation.frame.SpriteMapAnimationFrame;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;
import java.util.List;
import java.util.Optional;

//Vol ta aqui
//BackUP
/**
 * Jogador model for the camera usage example.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Jogador {
    
    /**
     * Jogador position.
     */
    public Vector2 pos;
    public Vector2 dim;
    private Vector2 vel;
    private double speed;
    private Color color;
    
    public Rectangle hitBox;
    public Rectangle areaInteracao;
    
    public Mochila mochila;
    
    private FrameByFrameAnimation<SpriteMapAnimationFrame> spriteMapCima;
    private FrameByFrameAnimation<SpriteMapAnimationFrame> spriteMapBaixo;
    private FrameByFrameAnimation<SpriteMapAnimationFrame> spriteMapEsquerda;
    private FrameByFrameAnimation<SpriteMapAnimationFrame> spriteMapDireita;
    
    

    /**
     * Creates the player for the camera example.
     *
     * @param pos Position.
     * @param dim Dimension.
     * @param speed Speed.
     * @param color Color.
     */
    public Jogador( Vector2 pos, Vector2 dim, double speed, Color color ) {
        this.pos = pos;
        this.dim = dim;
        this.vel = new Vector2();
        this.speed = speed;
        this.color = color;
        this.hitBox = new Rectangle( this.pos.x - this.dim.x / 2, this.pos.y - this.dim.y / 2, this.dim.x, this.dim.y );
        this.areaInteracao = new Rectangle ( (this.pos.x - this.dim.x )  , (this.pos.y - this.dim.y ) , this.dim.x*2, this.dim.y*2 );
        this.mochila = new Mochila();
    }
    
    /**
     * Updates the player.
     *
     * @param delta Time delta.
     * @param boundary Boundary limits.
     * @param engine EngineFrame.
     */
    public void update( double delta, Rectangle boundary, EngineFrame engine, List<Molde> obstaculos ) {
        
        if ( engine.isKeyDown(EngineFrame.KEY_LEFT ) || engine.isKeyDown(EngineFrame.KEY_A ) ) {
            vel.x = -speed;
        } else if ( engine.isKeyDown(EngineFrame.KEY_RIGHT ) || engine.isKeyDown(EngineFrame.KEY_D ) ) {
            vel.x = speed;
        } else {
            vel.x = 0;
        }
        
        if ( engine.isKeyDown(EngineFrame.KEY_UP ) || engine.isKeyDown(EngineFrame.KEY_W ) ) {
            vel.y = -speed;
        } else if ( engine.isKeyDown(EngineFrame.KEY_DOWN ) || engine.isKeyDown(EngineFrame.KEY_S ) ) {
            vel.y = speed;
        } else {
            vel.y = 0;
        }
        
        // --- MOVIMENTAÇÃO E COLISÃO NO EIXO X ---
        pos.x += vel.x * delta;
        hitBox = new Rectangle( this.pos.x - this.dim.x / 2, this.pos.y - this.dim.y / 2, this.dim.x, this.dim.y );
        
        if ( obstaculos != null ) {
            for ( Molde objeto : obstaculos ) {
                if ( objeto != null && br.com.davidbuzatto.jsge.collision.CollisionUtils.checkCollisionRectangles( this.hitBox, objeto.hitBox ) ) {
                    pos.x -= vel.x * delta; // Desfaz o movimento em X
                    hitBox = new Rectangle( this.pos.x - this.dim.x / 2, this.pos.y - this.dim.y / 2, this.dim.x, this.dim.y );
                    break; // INTERROMPE O LOOP IMEDIATAMENTE APÓS BATER EM UM OBSTÁCULO
                }
            }
        }
        
        // Atualiza a área de interação após resolver o eixo X
        areaInteracao = new Rectangle( (pos.x - dim.x), (pos.y - dim.y), dim.x * 2, dim.y * 2 );

        // --- MOVIMENTAÇÃO E COLISÃO NO EIXO Y ---
        pos.y += vel.y * delta;
        hitBox = new Rectangle( this.pos.x - this.dim.x / 2, this.pos.y - this.dim.y / 2, this.dim.x, this.dim.y );
        
        if ( obstaculos != null ) {
            for ( Molde objeto : obstaculos ) {
                if ( objeto != null && br.com.davidbuzatto.jsge.collision.CollisionUtils.checkCollisionRectangles( this.hitBox, objeto.hitBox ) ) {
                    pos.y -= vel.y * delta; // Desfaz o movimento em Y
                    hitBox = new Rectangle( this.pos.x - this.dim.x / 2, this.pos.y - this.dim.y / 2, this.dim.x, this.dim.y );
                    break; // INTERROMPE O LOOP IMEDIATAMENTE APÓS BATER EM UM OBSTÁCULO
                }
            }
        }
        
        // --- RESTRIÇÃO DAS BORDAS DO MUNDO ---
        if ( pos.x - dim.x / 2 <= boundary.x ) {
            pos.x = boundary.x + dim.x / 2;
        } else if ( pos.x + dim.x / 2 >= boundary.x + boundary.width ) {
            pos.x = boundary.x + boundary.width - dim.x / 2;
        }
        
        if ( pos.y - dim.y / 2 <= boundary.y ) {
            pos.y = boundary.y + dim.y / 2;
        } else if ( pos.y + dim.y / 2 >= boundary.y + boundary.height ) {
            pos.y = boundary.y + boundary.height - dim.y / 2;
        }
        
        // Atualiza a hitbox e a área de interação finais oficiais do jogador
        hitBox = new Rectangle( this.pos.x - this.dim.x / 2, this.pos.y - this.dim.y / 2, this.dim.x, this.dim.y );
        areaInteracao = new Rectangle( (pos.x - dim.x), (pos.y - dim.y), dim.x * 2, dim.y * 2 );
        
    }
    
    /**
     * Draws the player.
     *
     * @param engine EngineFrame.
     */
    public void draw( EngineFrame engine ) {
        engine.fillRectangle( pos.x - dim.x / 2, pos.y - dim.y / 2, dim.x, dim.y, color );
        engine.drawRectangle(pos.x - dim.x / 2, pos.y - dim.y / 2, dim.x, dim.y, EngineFrame.BLACK );
        engine.drawRectangle( (pos.x - dim.x )  , (pos.y - dim.y ) , dim.x*2, dim.y*2, EngineFrame.BROWN );
        engine.drawRectangle( hitBox.x, hitBox.y, hitBox.width, hitBox.height, EngineFrame.RED );
        
        engine.drawText( String.valueOf(mochila.time.size()), pos.x - 5, pos.y - 25, EngineFrame.BLACK );
    }
    
}

