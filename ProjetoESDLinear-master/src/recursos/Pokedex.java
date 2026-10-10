package recursos;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import modelos.Pokemon;

/**
 *
 * @author carlo
 */
public class Pokedex {
    
    public List<Pokemon> pokemons;
    
    public Pokedex(){
        
        this.pokemons = new ArrayList<>();
        pokemons.add( new Pokemon(1,"Pikachu", new Vector2(), new Vector2(64,64), "resources/images/overworld/pikachu_overworld.png",       4,4,256,256,0 ) );
        pokemons.add( new Pokemon(2,"Zacian", new Vector2(), new Vector2(64,64), "resources/images/overworld/zacian_overworld_Map.png",     4,4,256,256,0  ) );
        pokemons.add( new Pokemon(3,"Tyruntrum", new Vector2(), new Vector2(64,64), "resources/images/overworld/tyruntrum_overworld.png",   2,4,128,256,0,0,128 ) );
        pokemons.add( new Pokemon(4,"Bulbassaur", new Vector2(), new Vector2(64,64), "resources/images/overworld/bulbassaur_overworld.png", 2,4,128,256,0,0,64 ) );
        pokemons.add( new Pokemon(5,"Charizard", new Vector2(), new Vector2(64,64), "resources/images/overworld/charizard_overworld.png",   2,4,128,256,0,0,64 ) );
        pokemons.add( new Pokemon(6,"Scyther", new Vector2(), new Vector2(64,64), "resources/images/overworld/scyther_overworld.png",       2,4,128,256,0,0,64 ) );
    
        //pokemons.add( new Pokemon(3,"Rayquaza", new Vector2(), new Vector2(50,50), Color.GREEN ) );
        //pokemons.add( new Pokemon(4,"Charizard", new Vector2(), new Vector2(50,50), Color.ORANGE ) );
        //pokemons.add( new Pokemon(5,"Luxio", new Vector2(), new Vector2(50,50), Color.BLACK ) );
        //pokemons.add( new Pokemon(6,"Skitty", new Vector2(), new Vector2(50,50), Color.PINK ) );
        pokemons.add( new Pokemon(7,"Solgaleo", new Vector2(), new Vector2(128,128), Color.WHITE, "resources/images/overworld/solgaleo_overworld_Map.png" ) );
        
    }
    
    public List<Pokemon> get() {
        return pokemons;
    }
    
    public void addPokemon( Pokemon pokemon ){
        pokemons.add(pokemon);
    }
    
    public void removerPokemon( Pokemon pokemon ) {
        pokemons.remove(pokemon); 
    }
    
}
