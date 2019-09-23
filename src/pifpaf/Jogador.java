/*
Projeto: PifPaf
Desenvolvido por Anderson
*/
package pifpaf;


public class Jogador {

    private final String NOME;
    private Carta[] cartas;

    public Jogador(String nome) {
        this.NOME = nome;
    }

    //método criado para poder pegar o nome do jogador
    public String getNome() {
        return this.NOME;
    }
    
    //recebe o indice e a carta para efetuar a troca
    public void atualizarCarta(int num, Carta escolhida) {
        cartas[num] = escolhida;
    } 
    
    public void setCartas(Carta[] cartas) { //atualiza as cartas
        this.cartas = cartas;
    } 
    
    public Carta getCarta(int n) {
        return cartas[n];
    }
    
    public void mostrarCartas() { //consulta as cartas (get)
        System.out.println("------------CARTAS DE " + NOME.toUpperCase() + "------------------");
        int i=0;
        for (Carta carta : cartas) {
            System.out.println(i+" - "+carta);
            i++;
        }
        System.out.println("--------------------------------------");
    }
}