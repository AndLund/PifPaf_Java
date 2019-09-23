/*
Projeto: PifPaf
Desenvolvido por Anderson
*/
package pifpaf;


public class Carta {

    //private final String FACE;
    private final int FACE; //troquei string por int
    private final String NAIPE;

    public Carta(int face, String naipe) {
        this.FACE = face;
        this.NAIPE = naipe;
    }

    public int getFACE() {
        return FACE;
    }

    public String getNAIPE() {
        return NAIPE;
    }
    
    @Override
    public String toString() {
        return FACE + " de " + NAIPE;
    }
}
