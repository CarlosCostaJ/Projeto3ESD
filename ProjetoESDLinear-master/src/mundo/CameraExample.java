package mundo;

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


import br.com.davidbuzatto.jsge.core.Camera2D;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.core.utils.ColorUtils;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;

import br.com.davidbuzatto.jsge.core.Camera2D;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.BLACK;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.BLUE;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.FONT_BOLD;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.GRAY;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.KEY_DELETE;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.KEY_EQUAL;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.KEY_KP_ADD;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.KEY_KP_SUBTRACT;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.KEY_MINUS;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.KEY_PAGE_DOWN;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.KEY_R;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.LIGHTGRAY;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.ORANGE;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.RAYWHITE;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.WHITE;
import br.com.davidbuzatto.jsge.core.utils.ColorUtils;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;

import modelos.Adversario;
import modelos.Jogador;
import modelos.Pokemon;
import recursos.Pokedex;
import recursos.EquipeRocket;
import modelos.Molde;
import br.com.davidbuzatto.jsge.collision.CollisionUtils;
import br.com.davidbuzatto.jsge.image.Image;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import br.com.davidbuzatto.jsge.imgui.GuiComponent;
import br.com.davidbuzatto.jsge.imgui.GuiGlue;
import br.com.davidbuzatto.jsge.imgui.GuiPanel;
import br.com.davidbuzatto.jsge.imgui.GuiWindow;
import java.util.ArrayList;
import java.util.List;

import br.com.davidbuzatto.jsge.image.ImageUtils;

/**
 * Camera usage example.
 *
 * @author Prof. Dr. David Buzatto
 */
public class CameraExample extends EngineFrame {
    
    private int showInfo;
    
    private Rectangle limiteDoMundo;
    private Rectangle limiteDoJogador;
    private Jogador jogador;
    private Adversario adversario;
    private List<Molde> obstaculosMapa;
    private Pokemon pokemon;
    private Pokedex pokedex;
    private EquipeRocket adversarios;
    private Camera2D camera;
    private boolean emBatalha = false;
    private boolean menuPcAberto = false;
    private GuiWindow janelaBatalha;
    private GuiButton botaoAtacar;
    private GuiButton botaoFugir;
    private GuiGlue glueBatalha;
    
    /**
     * Creates the example.
     */
    public CameraExample() {
        super( 800, 600, "Camera", 60, true );
    }
    
    @Override
    public void create() {
        
        showInfo = 1;
        
        useAsDependencyForIMGUI();
        limiteDoMundo = new Rectangle( 0, 0, 1200*7, 720*7 );
        limiteDoJogador = new Rectangle( 30, 30, limiteDoMundo.width - 60, limiteDoMundo.height -60  );
        
        obstaculosMapa = new ArrayList<>();
        
        
        jogador = new Jogador( 
                new Vector2( limiteDoMundo.width / 2, limiteDoMundo.height / 2 ),
                new Vector2( 46, 64 ),
                1000,
                BLUE
        );
        
        pokedex = new Pokedex();
        adversarios = new EquipeRocket();
        
        
        
        // === SORTEIO ALEATÓRIO SEM SOBREPOSIÇÃO ===
        java.util.Random random = new java.util.Random();
        
        for ( Pokemon p : pokedex.get() ) {
            boolean posicaoValida = false;
            int tentativas = 0;
            
            while ( !posicaoValida && tentativas < 200 ) {
                // Sorteia X e Y respeitando as bordas do mundo (para o pokémon não nascer cortado na ponta)
                double xSorteado = limiteDoMundo.x + (p.dim.x / 2) + random.nextDouble() * (limiteDoMundo.width - p.dim.x);
                double ySorteado = limiteDoMundo.y + (p.dim.y / 2) + random.nextDouble() * (limiteDoMundo.height - p.dim.y);
                
                // Define temporariamente a posição e atualiza a HitBox do pokémon
                p.pos = new Vector2( xSorteado, ySorteado );
                
                p.reposicionarHitBox();
                // Verifica se colide com QUALQUER obstáculo já existente na lista (adversário ou outros pokémons)
                boolean colidiu = false;
                for ( Molde obstaculo : obstaculosMapa ) {
                    if ( CollisionUtils.checkCollisionRectangles( p.hitBox, obstaculo.hitBox ) ) {
                        colidiu = true;
                        break;
                    }
                }
                
                // Se não bateu em nada, a posição é válida!
                if ( !colidiu ) {
                    posicaoValida = true;
                }
                
                tentativas++;
            }
            
            // Adiciona o pokémon posicionado na lista de obstáculos do mapa
            obstaculosMapa.add( p );
        }
        
        
        for ( Adversario a : adversarios.get() ) {
            boolean posicaoValida = false;
            int tentativas = 0;
            
            while ( !posicaoValida && tentativas < 200 ) {
                // Sorteia X e Y respeitando as bordas do mundo (para o pokémon não nascer cortado na ponta)
                double xSorteado = limiteDoMundo.x + (a.dim.x / 2) + random.nextDouble() * (limiteDoMundo.width - a.dim.x);
                double ySorteado = limiteDoMundo.y + (a.dim.y / 2) + random.nextDouble() * (limiteDoMundo.height - a.dim.y);
                
                // Define temporariamente a posição e atualiza a HitBox do pokémon
                a.pos = new Vector2( xSorteado, ySorteado );
                a.reposicionarHitBox();
                
                // Verifica se colide com QUALQUER obstáculo já existente na lista (adversário ou outros pokémons)
                boolean colidiu = false;
                for ( Molde obstaculo : obstaculosMapa ) {
                    if ( CollisionUtils.checkCollisionRectangles( a.hitBox, obstaculo.hitBox ) ) {
                        colidiu = true;
                        break;
                    }
                }
                
                // Se não bateu em nada, a posição é válida!
                if ( !colidiu ) {
                    posicaoValida = true;
                }
                
                tentativas++;
            }
            
            // Adiciona o pokémon posicionado na lista de obstáculos do mapa
            obstaculosMapa.add( a );
        }
        
        
        
        
        
        
        
        camera = new Camera2D();
        
        //Painel de Batalha
        janelaBatalha = new GuiWindow( 0, 0, 400, 300, "Batalha contra NPC" );
        botaoAtacar = new GuiButton( 0, 0, 120, 40, "Atacar" );
        botaoFugir = new GuiButton( 0, 0, 120, 40, "Fugir" );

        glueBatalha = new GuiGlue( janelaBatalha );
        glueBatalha.addChild( botaoAtacar, 50, 100 );
        glueBatalha.addChild( botaoFugir, 220, 100 );

        // Deixe invisível inicialmente
        glueBatalha.setVisible( false );
        
    }

    @Override
    public void update( double delta ) {
        
        
        if(menuPcAberto){
            for(Pokemon p: jogador.mochila.pcPokemon){
                if (this.isMouseButtonPressed( this.MOUSE_BUTTON_LEFT )){
                    if (CollisionUtils.checkCollisionPointRectangle(this.getMousePositionPoint(), p.hitBox)) {
                        if(jogador.mochila.time.size()<6){
                            jogador.mochila.time.addLast(p);
                            jogador.mochila.pcPokemon.remove(p);
                        }
                        System.out.println("Clicou no " + p.nome);
                    }
                }
            }
        }
        if ( !emBatalha ) {
        jogador.update( delta, limiteDoJogador, this, obstaculosMapa);
        //jogador.update( delta, adversario.hitBox, this );
        
        if ( isKeyPressed( KEY_SHIFT ) ) {
            menuPcAberto = !menuPcAberto;
            /*for(Pokemon p: jogador.mochila.pcPokemon){
                if (this.isMouseButtonPressed( this.MOUSE_BUTTON_LEFT )){
                    if (CollisionUtils.checkCollisionPointRectangle(this.getMousePositionPoint(), p.hitBox)) {
                        System.out.println("Clicou no " + p.nome);
                    }
                }
            }*/
            
        }  
        
        if(isKeyPressed(KEY_I)){
            showInfo = showInfo * -1;
        }
        
        if( isKeyPressed( KEY_E ) ){
            jogador.mochila.pcPokemon.add(jogador.mochila.time.getLast());
            jogador.mochila.time.removeLast();
        }
        
        if ( isKeyDown( KEY_DELETE ) ) {
            camera.rotation--;
        } else if ( isKeyDown( KEY_PAGE_DOWN ) ) {
            camera.rotation++;
        }
        
        if ( isKeyDown( KEY_KP_ADD ) || isKeyDown( KEY_EQUAL ) ) {
            camera.zoom += 0.01;
        } else if ( isKeyDown( KEY_KP_SUBTRACT ) || isKeyDown( KEY_MINUS ) ) {
            camera.zoom -= 0.01;
            if ( camera.zoom < 0.1 ) {
                camera.zoom = 0.1;
            }
        }
        
        if ( isKeyPressed( KEY_R ) ) {
            camera.rotation = 0;
            camera.zoom = 1;
            jogador.pos.x = limiteDoMundo.width / 2;
            jogador.pos.y = limiteDoMundo.height / 2;
        }
        
        
        
        
        /*if( CollisionUtils.checkCollisionRectangles(jogador.areaInteracao, adversario.hitBox) == true && isKeyPressed(KEY_SPACE))
        {
            //jogador.update(delta, adversario.hitBox, this, false, adversario);
            System.out.println("Tocou");
        }*/
        
        updateCamera();
            
            // Verifica se apertou a tecla de interação perto do adversário
            for( Adversario a : adversarios.get() ){    
                if ( isKeyPressed( EngineFrame.KEY_SPACE ) && CollisionUtils.checkCollisionRectangles( jogador.areaInteracao, a.hitBox ) ) {
                    System.out.println("Tocou");
                    System.out.println(a.dim.y);
                    emBatalha = true;
                    glueBatalha.setVisible( true ); // Mostra a janela de batalha              
                }
            }
            for ( Pokemon p : pokedex.get()){
                if (CollisionUtils.checkCollisionRectangles(jogador.areaInteracao, p.hitBox)){
                    jogador.mochila.capturarPokemon(p);
                    System.out.println("Você pegou o pokémon" + p.nome+"!");
                    obstaculosMapa.remove(p);
                    pokedex.removerPokemon(p);
                    break;
                }  
            }
            
        } else {
            // 2. Lógica específica do modo batalha (atualiza os componentes da IMGUI)
            glueBatalha.update( delta );
            
            if ( botaoAtacar.isMousePressed() ) {
                System.out.println("Jogador atacou!");
                // Lógica de dano, etc.
            }
            
            if ( botaoFugir.isMousePressed() || janelaBatalha.isCloseButtonPressed() ) {
                emBatalha = false;
                glueBatalha.setVisible( false ); // Fecha a janela e volta ao jogo
            }
        }
        
    }
    
    @Override
    public void draw() {
        //if(!emBatalha){
        clearBackground( WHITE );
        setFontSize( 16 );
        setFontStyle( FONT_BOLD );
        
        beginMode2D( camera );
        limiteDoMundo.fill( this, ORANGE );
        //limiteDoJogador.fill( this, RED );
        drawGrid();
        
        jogador.draw( this );
        
        
        for( Molde m: obstaculosMapa ){
            m.draw(this);
        }
        /*for( Adversario a: adversarios.getEquipeRocket() ){
            a.draw(this);
        }
        
        for( Pokemon p : pokedex.getPokedex() ) {
            p.draw(this);
        }*/
        
        endMode2D();
        
        drawTime();
        if(showInfo > 0 ){
            drawInfo();
        }
            
        
        
 
        
        if (menuPcAberto) {
            // 1. Fundo da interface do PC
            fillRectangle(100, 40, getScreenWidth() - 160, getScreenHeight() - 80, new Color(40, 40, 80, 245));
            drawRectangle(100, 40, getScreenWidth() - 160, getScreenHeight() - 80, Color.YELLOW);
    
            // 2. Título
            setFontSize(22);
            drawText("PC DE POKÉMON - CAIXA DE ARMAZENAMENTO", 110, 75, Color.WHITE);
            setFontSize(16);
            drawText("Pressione SHIFT para fechar", 110, getScreenHeight() - 65, Color.LIGHT_GRAY);
    
            // 3. Listar os Pokémon guardados no PC
            List<Pokemon> listaPc = jogador.mochila.pcPokemon; // Acessa os guardados
    
            int xInicialSlot = 155;
            int yInicialSlot = 130;
            int iniXSlot = 130;
            int iniYSlot = 130;
            int alturaSlot = 64;
            int larguraSlot = 64;
            int quantidade = listaPc.size();
            int colunas = 0;
            int espacamentoXSlot = 64; // Espaço vertical para cada linha de Pokémon
            int espacamentoYSlot = 12; // Espaço vertical para cada linha de Pokémon
            
            
            for( int i = 0; i < 6; i++ ) {
                for ( int j = 0; j < 9; j++ ) {
                    
                    fillRectangle( iniXSlot+(larguraSlot*j), iniYSlot+(alturaSlot*i), larguraSlot, alturaSlot, new Color(60, 60, 110));
                    drawRectangle( iniXSlot+(larguraSlot*j), iniYSlot+(alturaSlot*i), larguraSlot, alturaSlot, Color.LIGHT_GRAY);
                }
            }
            
            int cont = 0;
            for( int i = 0; i < 6; i++ ){
                for( int j = 0; j < 9; j++ ){
                    if( cont < quantidade ) {
                        int tempX = larguraSlot*j;
                        int iniXRelativo = iniXSlot + tempX;
                        int tempY = alturaSlot*i;
                        int iniYRelativo = iniYSlot + tempY;
                        Pokemon p = listaPc.get(cont);                    
                        p.reposicionarPokemon( iniXRelativo + (larguraSlot/2), iniYRelativo+(alturaSlot/2));
                        p.reposicionarHitBox();
                        p.draw(this);
                        cont++;
                    }   
                }
            }
                    
            //fillRectangle(xInicial-25, yInicial-10, (espacamentoX*9)+3, 65, new Color(60, 60, 110));
                    //drawRectangle(posXItem, yInicial+espacamentoY, 500, 50, Color.LIGHT_GRAY);
            
            
            /*System.out.println(colunas);
            if (listaPc.isEmpty()) {
                drawText("O PC está vazio!", iniXSlot, iniYSlot, Color.RED);
            } else {
                for (int i = 0; i < listaPc.size(); i++) {
                    int posXItem = 0;
                    if( colunas == 3 ){
                        colunas = 0;
                        //yInicial += 64;
                        System.out.println("Entrei aqui!");
                    }               
                        posXItem = iniXSlot + (colunas * larguraSlot);
                    
                    Pokemon p = listaPc.get(i);
            
                    // Caixa de fundo para cada slot de Pokémon no PC
                    
                    // --- DESENHAR O SPRITE DO POKÉMON NO MENU ---
                    if (p.spriteOverworld != null) {
                             p.reposicionarPokemon( posXItem + 10,  iniYSlot+alturaSlot );
                             p.draw(this); 
                             colunas ++;
                    }
            
                    // Escreve as informações do Pokémon ao lado do sprite
                    /*String textoPokemon = p.nome + " (HP: " + p.hpAtual + "/" + p.hpMaximo + ")";
                    drawText(textoPokemon, xInicial + 70, posYItem + 30, Color.WHITE);
                    
                }
            }*/
        }
        
        
        
        
        
        
        
        //}
        //else {
            glueBatalha.draw(); // Desenha a janela, fundo e botões filhos automaticamente
        //}
        
    }
    
    private void drawGrid() {
        
        int step = 30;
        
        int xStart = (int) limiteDoJogador.x;
        int xEnd = (int) ( limiteDoJogador.x + limiteDoJogador.width );
        int yStart = (int) limiteDoJogador.y;
        int yEnd = (int) ( limiteDoJogador.y + limiteDoJogador.height );
        
        int lines = ( yEnd - yStart ) / step;
        int columns = ( xEnd - xStart ) / step;
        
        Color c;
        
        for ( int i = 0; i < lines; i++ ) {
            for ( int j = 0; j < columns; j++ ) {
                if ( i % 2 == 0 ) {
                    if ( j % 2 == 0 ) {
                        c = GRAY;
                    } else {
                        c = LIGHTGRAY;
                    }
                } else {
                    if ( j % 2 == 0 ) {
                        c = LIGHTGRAY;
                    } else {
                        c = GRAY;
                    }
                }
                fillRectangle( 
                        xStart + j * step, 
                        yStart + i * step, 
                        step, step, 
                        ColorUtils.colorTint( 
                                c, 
                                ColorUtils.colorFromHSV( 
                                        45 + i / (double) lines * 210, 1, 1
                                )
                        )
                );
            }
        }
        
    }
    
    private void drawTime(){
        int x = 5;
        int y = 510;
        int largura = 85;
        //int tamanhoTime = jogador.mochila.time.size();        
        //BACKUP
        for (int i = 0; i < 6; i++) {
            int larguraRelativa = largura * i;
            int temp = y - larguraRelativa;

            fillRoundRectangle(x, temp, largura, largura-0.7, 10, ColorUtils.fade(DARKGRAY, 0.9));
            if(i+1 == jogador.mochila.time.size()){
                    drawRoundRectangle(x, temp, largura, largura-0.7, 10, GOLD);
            } else {
                drawRoundRectangle(x, temp, largura, largura-0.7, 10, BLACK);
            }
            if (i < jogador.mochila.time.size()) {                
                Pokemon p = jogador.mochila.time.get(i);                
                p.reposicionarPokemon(x + (largura / 2), temp + (largura / 2));
                p.draw(this);
                p.reposicionarHitBox();
            }
        }
    }
    
    private void drawInfo() {
        //TESTE
        //fillRectangle( 5, 545, 50, 400, ColorUtils.fade( LIGHTGRAY, 0.2 ) );
        //fillRectangle( 5, 510, 400, 85, WHITE );
        drawText( "tween animation control:", 460, 20, BLACK );
        
        fillRoundRectangle( 5, 5, 340, 200, 10, ColorUtils.colorAlpha( RAYWHITE, 0.5 ) );
        drawRoundRectangle( 5, 5, 340, 200, 10, BLACK );
        
        drawFPS( 20, 20 );
        drawText( "<R> to reset", 210, 20, BLACK );
        
        int y = 40;
        int step = 18;
        
        Vector2 playerScreen = camera.getWorldToScreen( jogador.pos.x, jogador.pos.y );
        drawText( "Player: ", 20, y, BLACK );
        drawText( String.format( " World: (%.2f, %.2f)", jogador.pos.x, jogador.pos.y ), 30, y += step, BLACK );
        drawText( String.format( "Screen: (%.2f, %.2f)", playerScreen.x, playerScreen.y ), 30, y += step, BLACK );
        
        y += step;
        
        drawText( "Camera: ", 20, y += step, BLACK );
        drawText( String.format( "  Target: (%.2f, %.2f)", camera.target.x, camera.target.y ), 30, y += step, BLACK );
        drawText( String.format( "  Offset: (%.2f, %.2f)", camera.offset.x, camera.offset.y ), 30, y += step, BLACK );
        drawText( String.format( "Rotation: %.2f (DEL/PG-Down)", camera.rotation ), 30, y += step, BLACK );
        drawText( String.format( "    Zoom: %.2f (+/-)", camera.zoom ), 30, y += step, BLACK );
        
    }
    
    
    private void updateCamera() {
        
        if ( jogador.pos.x <= getScreenWidth() / 2 ) {
            camera.target.x = getScreenWidth() / 2;
        } else if ( jogador.pos.x >= limiteDoMundo.width - getScreenWidth() / 2 ) {
            camera.target.x = limiteDoMundo.width - getScreenWidth() / 2 ;
        } else {
            camera.target.x = jogador.pos.x;
        }
        
        if ( jogador.pos.y <= getScreenHeight() / 2 ) {
            camera.target.y = getScreenHeight()/ 2;
        } else if ( jogador.pos.y >= limiteDoMundo.height - getScreenHeight()/ 2 ) {
            camera.target.y = limiteDoMundo.height - getScreenHeight()/ 2 ;
        } else {
            camera.target.y = jogador.pos.y;
        }
        
        camera.offset.x = getScreenWidth() / 2;
        camera.offset.y = getScreenHeight() / 2;
        
    }
    
    /**
     * Runs the example.
     * @param args Arguments.
     */
    public static void main( String[] args ) {
        new CameraExample();
    }
    
}

