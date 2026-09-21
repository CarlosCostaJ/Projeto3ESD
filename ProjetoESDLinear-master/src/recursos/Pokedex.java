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
        pokemons.add( new Pokemon(1,"Pikachu", new Vector2(), new Vector2(50,50), Color.YELLOW ) );
        pokemons.add( new Pokemon(2,"Zacian", new Vector2(), new Vector2(50,50), EngineFrame.SKYBLUE ) );
        pokemons.add( new Pokemon(3,"Rayquaza", new Vector2(), new Vector2(50,50), Color.GREEN ) );
        pokemons.add( new Pokemon(4,"Charizard", new Vector2(), new Vector2(50,50), Color.ORANGE ) );
        pokemons.add( new Pokemon(5,"Luxio", new Vector2(), new Vector2(50,50), Color.BLACK ) );
        pokemons.add( new Pokemon(6,"Skitty", new Vector2(), new Vector2(50,50), Color.PINK ) );
        pokemons.add( new Pokemon(7,"Solgaleo", new Vector2(), new Vector2(50,50), Color.WHITE ) );
        
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
