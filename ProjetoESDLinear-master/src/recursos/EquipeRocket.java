package recursos;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import modelos.Adversario;
/**
 *
 * @author carlo
 */
public class EquipeRocket {
    
    public List<Adversario> adversarios;
    
    public EquipeRocket(){
        
        this.adversarios = new ArrayList<>();
        //adversarios.add( new Adversario(5,"Cierra", new Vector2(), new Vector2(50,50), Color.RED ) );
        adversarios.add( new Adversario(1,"Cynthia", new Vector2(), new Vector2(64,64),  "resources/images/overworld/npc1_ow.png",              4,3,192,256,0,0,64 ) );
        adversarios.add( new Adversario(2,"Lance", new Vector2(), new Vector2(64,64),  "resources/images/overworld/npc2_ow.png",                2,3,192,128,0,128,0 ) );
        adversarios.add( new Adversario(3,"Garry", new Vector2(), new Vector2(64,64),  "resources/images/overworld/npc3_ow.png",                2,3,192,128,0,128,0 ) );
        adversarios.add( new Adversario(4,"Giovanni", new Vector2(), new Vector2(64,64), "resources/images/overworld/npc4_ow.png",              4,3,192,256,0,0,64 ) );
        adversarios.add( new Adversario(5,"CapangaMasc", new Vector2(), new Vector2(64,64), "resources/images/overworld/npc5_ow.png",           4,3,192,256,0,0,64 ) );
        adversarios.add( new Adversario(6,"CapangaFem", new Vector2(), new Vector2(64,64),  "resources/images/overworld/npc6_ow.png",           4,3,192,256,0,0,64 ) );
        adversarios.add( new Adversario(7,"Red", new Vector2(), new Vector2(64,64), "resources/images/overworld/npc7_ow.png",                   4,3,192,256,0,0,64 ) );
     
        
    }
    
    public List<Adversario> get() {
        return adversarios;
    }
    
    public void addPokemon( Adversario adversario ){
        adversarios.add(adversario);
    }
}
