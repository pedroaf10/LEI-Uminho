package View;

public class Menu {

    private String titulo;
    private String[] opcoes;

    public Menu(String titulo, String[] opcoes){
        int i;

        this.titulo = titulo;
        this.opcoes = new String[opcoes.length];

        for(i=0 ; i < opcoes.length ; i++) this.opcoes[i] = opcoes[i];
    }



    public void show(){
        int i;

        System.out.println("       " + this.titulo);

        for(i=0 ; i < this.opcoes.length ; i++){

            System.out.println("-> " + this.opcoes[i]);

        }

        System.out.println("\nOpçcao : ");

    }


}
