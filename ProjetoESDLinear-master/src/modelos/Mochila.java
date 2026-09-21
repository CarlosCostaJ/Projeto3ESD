package modelos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlo
 */
public class Mochila {
    
    public List<Pokemon> time; 
    public List<Pokemon> pcPokemon;
    
    public Mochila() {
        this.time = new ArrayList<>();
        this.pcPokemon = new ArrayList<>();
    }
    
    public void capturarPokemon( Pokemon pokemon ){
        if(time.size() < 6){
            time.add(pokemon);
        } else {
            pcPokemon.add(pokemon);
        }        
    }
    
    public void moverPokemonParaTime( Pokemon pokemon ){
        if( time.size() < 6 ){
            pokemon.hitBox = null;
            time.add(pokemon);
            pcPokemon.remove(pokemon);
        }
    }
    
    public void moverPokemonParaPc ( Pokemon pokemon ) {
        if( time.size() > 1 ){
            pokemon.hitBox = null;
            pcPokemon.add(pokemon);
            time.remove(pokemon);
        }
    }
}
