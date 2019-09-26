/*
Projeto: PifPaf
Desenvolvido por Anderson e Rafael
*/
package pifpaf;

import java.io.IOException;
import java.util.Scanner;

public class Jogo {

    private final Scanner entrada = new Scanner(System.in);
    private final Baralho BARALHO;
    private Jogador[] jogadores;
    private Carta lixeira;
    
    //Construtor
    public Jogo() { 
        BARALHO = new Baralho();
        //BARALHO.mostrarBaralho(); //Está inicializando o objeto chamando o método
        BARALHO.embaralhar(); //Está inicializando o objeto chamando o método
        //BARALHO.mostrarBaralho();//Está inicializando o objeto chamando o método
        BARALHO.nomeDoJogo();
    }

    public void inicio() {
        int qtdJogadores = 2;
        jogadores = new Jogador[qtdJogadores];

        for (int i = 0; i < qtdJogadores; i++) {
            System.out.println("Jogador " + (i+1) + ", informe seu nome:");
            jogadores[i] = new Jogador(entrada.next());
        }
    }
    
    //separa carta para os 2 jogadores
    public void distribuirCartas(int qtdCartas) { 
        for (Jogador objJogador : jogadores) {
            objJogador.setCartas(BARALHO.distribuirCartas(qtdCartas));
        }
        System.out.println("\n\n\t\tCARTAS DISTRIBUÍDAS =D\n\n");
    }
    
    //caso seja uma trinca
    public boolean mesmoNumero(Carta x,Carta y,Carta z){  
        //if((x.getFACE()).equals(y.getFACE()) && (x.getFACE()).equals(z.getFACE())){
        if(x.getFACE() == y.getFACE() && x.getFACE() == z.getFACE()){
            System.out.println("Você formou uma trinca.");
            //aqui tem q tirar as 3 cartas e continuar o jogo
            return true;
        }else{
            System.out.println("Infelizmente você não conseguiu formar a trinca.");
            return false;
        }
    }
    
    //caso seja uma sequencia
    public boolean mesmoNaipe(Carta x,Carta y,Carta z){
        //if((y.getFACE()).equals(x.getFACE() + 1) && (z.getFACE()).equals(x.getFACE() + 2)){
        if(y.getFACE() == (x.getFACE() + 1) && z.getFACE() == (x.getFACE() + 2)){
            System.out.println("Você formou uma sequencia.");
            //aqui tem q tirar as 3 cartas e continuar o jogo
            return true;
        }else{
            System.out.println("Infelizmente você não conseguiu formar a sequencia.");
            return false;
        }
    }
    
    /*quando uma trinca ou uma sequencia eh encontrada, chama essa função para
    fazer o teste*/
    public int avaliaTrinca(int c) throws IOException {
        String sn;
        Carta x,y,z;
        boolean teste;
        int win = 0;
        do{
            //se precisar, colocar aqui o mostrarCartas
            System.out.println("Formou alguma trinca ou sequencia?");
            sn = entrada.next();

            if(sn.equals("s") || sn.equals("sim")){
                System.out.println("informe o numero a esquerda das 3 cartas, na ordem: ");
                x = jogadores[c].getCarta(entrada.nextInt());
                y = jogadores[c].getCarta(entrada.nextInt());
                z = jogadores[c].getCarta(entrada.nextInt());

                /*pré-avaliação: mesmonaipe é uma trinca, naipe diferente é uma sequencia*/
                if((x.getNAIPE()).equals(y.getNAIPE()) && (x.getNAIPE()).equals(z.getNAIPE())){
                //if(x.getNAIPE() == y.getNAIPE() && x.getNAIPE() == z.getNAIPE()){
                    teste = mesmoNaipe(x,y,z);
                    if(teste){
                        win++; //se o resultado de teste for true, incrementa vitoria
                    }
                }else if(!(x.getNAIPE()).equals(y.getNAIPE()) && !(x.getNAIPE()).equals(z.getNAIPE())){
                //else if(x.getNAIPE() != y.getNAIPE() && x.getNAIPE() != z.getNAIPE()){
                    teste = mesmoNumero(x,y,z);
                    if(teste){
                        win++;
                    }
                }
            }else{
                System.out.println("OK.");
            }    
        }while(sn.equals("s") || sn.equals("sim"));
        //System.out.println(win); //teste para verificar win
        return win;
    }
    
    //o jogador decide se vai puxar carta do maço ou da lixeira
    public void puxarCarta(int c) throws IOException {
        Carta cartaEscolhida = null;
        String letra;
        int subst = 0;
        
        System.out.println("--------------------------\nlixeira: "+lixeira+"\n--------------------------");
        System.out.println("Digite 'l' para puxar a carta do lixo ou 'm' para puxar do maço:");
        letra = entrada.next();
        
        if(letra.equals("l")){
            System.out.println("Escolha a carta que deseja substituir:");
            subst = entrada.nextInt();
            cartaEscolhida = lixeira; //carta escolhida é retirada da lixeira
            //lixeira = jogadores[c].getCarta(subst);//coloca a carta na lixeira
        }else if(letra.equals("m")){
            System.out.println("---------------------------------------");
            System.out.println("A carta puxada foi: \n9 - "+BARALHO.maco());
            System.out.println("---------------------------------------");
            System.out.println("Escolha a carta que deseja substituir (0-9):");
            subst = entrada.nextInt();
            if(subst!=9) cartaEscolhida = BARALHO.tirandoMaco(); /*carta escolhida é retirada do maço
            e depois o contador é incrementado*/
        }
        if(subst!=9){
            lixeira = jogadores[c].getCarta(subst); //a carta descartada é colocada na lixeira
            jogadores[c].atualizarCarta(subst,cartaEscolhida); /*manda o indice da carta que
            deseja trocar e a carta que foi escolhida do maço ou da lixeira*/
        }else{
            lixeira = BARALHO.tirandoMaco();
        }
    }
    
    //método que mostra as cartas atualizadas de cada jogador
    public void mostrarCartas() throws IOException {
        int cont=0;
        int[] vit = new int[2];
        
        do{
            if(cont==0){
                jogadores[cont].mostrarCartas();
            }else{
                jogadores[cont].mostrarCartas();
            }
            puxarCarta(cont);
            vit[cont] = avaliaTrinca(cont);
            if(vit[cont]!=3){
                if(cont==1) cont=0;
                else cont=1;
            }
            //System.out.println(vit[cont]);
        }while(vit[cont]!=3);
        System.out.println("Fim de jogo!! Jogador "+jogadores[cont].getNome()+" venceu a partida!!!");
    }

    //método principal
    public static void main(String[] args) throws IOException{
        Jogo game = new Jogo();
        game.inicio();
        game.distribuirCartas(9);
        game.mostrarCartas();
    }
}