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
        adversarios.add( new Adversario(1,"Jesse", new Vector2(), new Vector2(50,50), Color.RED ) );
        adversarios.add( new Adversario(2,"James", new Vector2(), new Vector2(50,50), Color.RED ) );
        adversarios.add( new Adversario(3,"Arlos", new Vector2(), new Vector2(50,50), Color.RED ) );
        adversarios.add( new Adversario(4,"Clif", new Vector2(), new Vector2(50,50), Color.RED ) );
        adversarios.add( new Adversario(5,"Cierra", new Vector2(), new Vector2(50,50), Color.RED ) );
        adversarios.add( new Adversario(6,"Giovanni", new Vector2(), new Vector2(50,50), Color.BLACK ) );
        
    }
    
    public List<Adversario> get() {
        return adversarios;
    }
    
    public void addPokemon( Adversario adversario ){
        adversarios.add(adversario);
    }
}
