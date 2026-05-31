import ArvoreBPlus.ArvoreBPlus;

public class Main {
    public static void main(String[] args) {
        System.out.println("##############################");
        System.out.println("# IMPLEMENTAÇÃO DE ÁRVORE B+ #");
        System.out.println("# Por Daniel Salles Batista  #");
        System.out.println("##############################");

        // Defina a quantidade máxima de números aqui
        int limite = 256;

        System.out.println("\n========== N = 4 ==========");
        ArvoreBPlus arvoreN4 = new ArvoreBPlus(4);

        System.out.println("--- Inserindo " + limite + " valores... ---");
        for (int i = 1; i <= limite; i++) {
            arvoreN4.inserir(i, i * 4);
            System.out.printf(i + " ");
        }
        System.out.println();

        System.out.println("--- Exibição ---");
        arvoreN4.exibir();

        System.out.println("--- In ordem ---");
        arvoreN4.inOrdem();

        System.out.println("--- Excluindo números ímpares... ---");
        for (int i = 1; i <= limite; i += 2) {
            arvoreN4.excluir(i);
        }

        System.out.println("--- Exibição pós-exclusão ---");
        arvoreN4.exibir();

        System.out.println("--- In ordem pós-exclusão ---");
        arvoreN4.inOrdem();

        System.out.println("\n========== N = 5 ==========");
        ArvoreBPlus arvoreN5 = new ArvoreBPlus(5);

        System.out.println("--- Inserindo " + limite + " valores... ---");
        for (int i = 1; i <= limite; i++) {
            arvoreN5.inserir(i, i * 4);
            System.out.printf(i + " ");
        }
        System.out.println();

        System.out.println("--- Exibição ---");
        arvoreN5.exibir();

        System.out.println("--- In ordem ---");
        arvoreN5.inOrdem();

        System.out.println("--- Excluindo números ímpares... ---");
        for (int i = 1; i <= limite; i += 2) {
            arvoreN5.excluir(i);
        }

        System.out.println("--- Exibição pós-exclusão ---");
        arvoreN5.exibir();

        System.out.println("--- In ordem pós-exclusão ---");
        arvoreN5.inOrdem();
    }
}
