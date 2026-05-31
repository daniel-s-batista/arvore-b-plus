package ArvoreBPlus;

import PilhaInt.PilhaInt;
import PilhaObj.PilhaObj;

public class ArvoreBPlus {
    private int n;
    public NoArvoreBPlus raiz;

    public ArvoreBPlus(int n) {
        this.raiz = null;
        this.n = n;
    }

    public NoArvoreBPlus buscarFolha(int info) {
        NoArvoreBPlus no = raiz;
        int pos;
        while (!no.isFolha()) {
            pos = no.procurarPosicaoDescida(info);
            no = no.getLig(pos);
        }
        return no;
    }

    public NoArvoreBPlus buscarPai(NoArvoreBPlus no, int info) {
        NoArvoreBPlus aux, pai;
        int pos;
        aux = pai = raiz;
        while (aux != null && no != aux) {
            pos = aux.procurarPosicaoDescida(info);
            pai = aux;
            aux = aux.getLig(pos);
        }
        return pai;
    }

    private void split(NoArvoreBPlus no, NoArvoreBPlus pai) {
        int m;
        PilhaObj<NoArvoreBPlus> pilha = new PilhaObj<NoArvoreBPlus>();

        pilha.push(pai);
        pilha.push(no);

        while (!pilha.isEmpty()) {
            no = pilha.pop();
            pai = pilha.pop();

            NoArvoreBPlus noEsq = new NoArvoreBPlus(n),
                          noDir = new NoArvoreBPlus(n);

            if (no.isFolha()) {
                m = (int) Math.ceil((n - 1) / 2.0);
            }
            else {
                m = (int) Math.ceil(n / 2.0) - 1;
            }

            for (int i = 0; i < m; i++) {
                noEsq.setInfo(no.getInfo(i), i);
                noEsq.setPos(no.getPos(i), i);
                noEsq.setLig(no.getLig(i), i);
                noEsq.setTL(noEsq.getTL() + 1);
            }
            if (!no.isFolha()) {
                noEsq.setLig(no.getLig(m), m);
            }

            if (no.isFolha()) {         // Folha
                for (int i = m; i < no.getTL(); i++) {
                    noDir.setInfo(no.getInfo(i), i - m);
                    noDir.setPos(no.getPos(i), i - m);
                    noDir.setLig(no.getLig(i), i - m);
                    noDir.setTL(noDir.getTL() + 1);
                }
            }
            else {                      // Pai
                for (int i = m + 1, j = 0; i < no.getTL(); i++, j++) {
                    noDir.setInfo(no.getInfo(i), j);
                    noDir.setPos(no.getPos(i), j);
                    noDir.setLig(no.getLig(i), j);
                    noDir.setTL(noDir.getTL() + 1);
                }
            }
            noDir.setLig(no.getLig(no.getTL()), noDir.getTL());

            if (no.isFolha()) {
                if (no.getAnt() != null) {
                    no.getAnt().setProx(noEsq);
                }
                noEsq.setAnt(no.getAnt());
                noEsq.setProx(noDir);
                noDir.setAnt(noEsq);
                noDir.setProx(no.getProx());
                if (no.getProx() != null) {
                    no.getProx().setAnt(noDir);
                }
            }

            if (no == pai) {
                no.setInfo(no.getInfo(m), 0);
                no.setPos(no.getPos(m), 0);
                no.setTL(1);
                no.setLig(noEsq, 0);
                no.setLig(noDir, 1);
            }
            else {
                int pos = pai.procurarPosicaoFolha(no.getInfo(m));
                pai.remanejar(pos);
                pai.setInfo(no.getInfo(m), pos);
                pai.setPos(no.getPos(m), pos);
                pai.setTL(pai.getTL() + 1);
                pai.setLig(noEsq, pos);
                pai.setLig(noDir, pos + 1);

                if (pai.getTL() >= n) { // Se deu overflow
                    no = pai;
                    pai = buscarPai(no, no.getInfo(0));
                    pilha.push(pai);
                    pilha.push(no);
                }
            }
        }
    }

    public void inserir(int info, int posArq) {
        if (raiz == null) {
            raiz = new NoArvoreBPlus(this.n, info, posArq);
        }
        else {
            NoArvoreBPlus no = buscarFolha(info);
            int pos = no.procurarPosicaoFolha(info);
            no.remanejar(pos);
            no.setInfo(info, pos);
            no.setPos(posArq, pos);
            no.setTL(no.getTL() + 1);
            if (no.getTL() >= n) {
                this.split(no, buscarPai(no, info));
            }
        }
    }

    private NoArvoreBPlus localizarSubEsq(NoArvoreBPlus no, int pos) {
        no = no.getLig(pos);
        while (!no.isFolha()) {
            no = no.getLig(no.getTL());
        }
        return no;
    }

    private NoArvoreBPlus localizarSubDir(NoArvoreBPlus no, int pos) {
        no = no.getLig(pos);
        while (!no.isFolha()) {
            no = no.getLig(0);
        }
        return no;
    }

    private int localizarPosicaoFilho(NoArvoreBPlus pai, NoArvoreBPlus filho) {
        int pos = 0;

        // Um pai com TL chaves possui TL + 1 filhos.
        while (pos <= pai.getTL() && pai.getLig(pos) != filho) {
            pos++;
        }

        return pos;
    }

    private void redistribuir_concatenar(NoArvoreBPlus no, int m) {
        NoArvoreBPlus pai = buscarPai(no, no.getInfo(0)),
                      irmaEsq = null, irmaDir = null;
        PilhaObj<NoArvoreBPlus> pilha = new PilhaObj<NoArvoreBPlus>();
        boolean concatenou;

        pilha.push(pai);
        pilha.push(no);
        while (!pilha.isEmpty()) {
            no = pilha.pop();
            pai = pilha.pop();
            concatenou = false;

            if (pai != no) {
                int posPai = localizarPosicaoFilho(pai, no);
                if (posPai > 0)
                    irmaEsq = pai.getLig(posPai - 1);
                else
                    irmaEsq = null;
                if (posPai < pai.getTL())
                    irmaDir = pai.getLig(posPai + 1);
                else
                    irmaDir = null;

                if (no.isFolha()) { // Nó folha
                    if (irmaEsq != null && irmaEsq.getTL() > m) { // Redistribui da esquerda
                        int TLEsq = irmaEsq.getTL();
                        no.remanejar(0);
                        no.setInfo(irmaEsq.getInfo(TLEsq - 1), 0);
                        no.setPos(irmaEsq.getPos(TLEsq - 1), 0);
                        no.setLig(irmaEsq.getLig(TLEsq), 0);
                        no.setTL(no.getTL() + 1);

                        pai.setInfo(no.getInfo(0), posPai - 1);
                        pai.setPos(no.getPos(0), posPai - 1);

                        irmaEsq.setTL(TLEsq - 1);
                    }
                    else if (irmaDir != null && irmaDir.getTL() > m) { // Redistribui da direita
                        no.setInfo(irmaDir.getInfo(0), no.getTL());
                        no.setPos(irmaDir.getPos(0), no.getTL());
                        no.setLig(irmaDir.getLig(0), no.getTL() + 1);
                        no.setTL(no.getTL() + 1);

                        irmaDir.remanejarExclusao(0);
                        irmaDir.setTL(irmaDir.getTL() - 1);

                        pai.setInfo(irmaDir.getInfo(0), posPai);
                        pai.setPos(irmaDir.getPos(0), posPai);
                    }
                    else { // Se não der para redistribuir, concatenamos nós
                        concatenou = true;
                        if (irmaEsq != null) {  // irmaEsq <-- no
                            pai.remanejarExclusao(posPai - 1);
                            pai.setTL(pai.getTL() - 1);
                            pai.setLig(irmaEsq, posPai - 1);

                            for (int i = 0; i < no.getTL(); i++) {
                                irmaEsq.setInfo(no.getInfo(i), irmaEsq.getTL());
                                irmaEsq.setPos(no.getPos(i), irmaEsq.getTL());
                                irmaEsq.setLig(no.getLig(i), irmaEsq.getTL());
                                irmaEsq.setTL(irmaEsq.getTL() + 1);
                            }
                            irmaEsq.setLig(no.getLig(no.getTL()), irmaEsq.getTL());

                            irmaEsq.setProx(no.getProx());
                            if (no.getProx() != null) {
                                no.getProx().setAnt(irmaEsq);
                            }
                        }
                        else if (irmaDir != null) { // no <-- irmaDir
                            pai.remanejarExclusao(posPai);
                            pai.setTL(pai.getTL() - 1);
                            pai.setLig(no, posPai);

                            for (int i = 0; i < irmaDir.getTL(); i++) {
                                no.setInfo(irmaDir.getInfo(i), no.getTL());
                                no.setPos(irmaDir.getPos(i), no.getTL());
                                no.setLig(irmaDir.getLig(i), no.getTL());
                                no.setTL(no.getTL() + 1);
                            }
                            no.setLig(irmaDir.getLig(irmaDir.getTL()), no.getTL());

                            no.setProx(irmaDir.getProx());
                            if (irmaDir.getProx() != null) {
                                no.getProx().setAnt(no);
                            }
                        }
                    }
                }
                else { // Nó não folha
                    if (irmaEsq != null && irmaEsq.getTL() > m) { // Redistribui da esquerda
                        int TLEsq = irmaEsq.getTL();
                        no.remanejar(0);
                        no.setInfo(pai.getInfo(posPai - 1), 0);
                        no.setPos(pai.getPos(posPai - 1), 0);
                        no.setLig(irmaEsq.getLig(TLEsq), 0);
                        no.setTL(no.getTL() + 1);

                        pai.setInfo(irmaEsq.getInfo(TLEsq - 1), posPai - 1);
                        pai.setPos(irmaEsq.getPos(TLEsq - 1), posPai - 1);

                        irmaEsq.setTL(TLEsq - 1);
                    }
                    else if (irmaDir != null && irmaDir.getTL() > m) { // Redistribui da direita
                        no.setInfo(pai.getInfo(posPai), no.getTL());
                        no.setPos(pai.getPos(posPai), no.getTL());
                        no.setLig(irmaDir.getLig(0), no.getTL() + 1);
                        no.setTL(no.getTL() + 1);

                        pai.setInfo(irmaDir.getInfo(0), posPai);
                        pai.setPos(irmaDir.getPos(0), posPai);

                        irmaDir.remanejarExclusao(0);
                        irmaDir.setTL(irmaDir.getTL() - 1);
                    }
                    else { // Se não der para redistribuir, concatenamos nós
                        concatenou = true;
                        if (irmaEsq != null) {  // irmaEsq <-- no
                            irmaEsq.setInfo(pai.getInfo(posPai - 1), irmaEsq.getTL());
                            irmaEsq.setPos(pai.getPos(posPai - 1), irmaEsq.getTL());
                            irmaEsq.setTL(irmaEsq.getTL() + 1);
                            pai.remanejarExclusao(posPai - 1);
                            pai.setTL(pai.getTL() - 1);
                            pai.setLig(irmaEsq, posPai - 1);

                            for (int i = 0; i < no.getTL(); i++) {
                                irmaEsq.setInfo(no.getInfo(i), irmaEsq.getTL());
                                irmaEsq.setPos(no.getPos(i), irmaEsq.getTL());
                                irmaEsq.setLig(no.getLig(i), irmaEsq.getTL());
                                irmaEsq.setTL(irmaEsq.getTL() + 1);
                            }
                            irmaEsq.setLig(no.getLig(no.getTL()), irmaEsq.getTL());
                        }
                        else if (irmaDir != null) { // no <-- irmaDir
                            no.setInfo(pai.getInfo(posPai), no.getTL());
                            no.setPos(pai.getPos(posPai), no.getTL());
                            no.setTL(no.getTL() + 1);
                            pai.remanejarExclusao(posPai);
                            pai.setTL(pai.getTL() - 1);
                            pai.setLig(no, posPai);

                            for (int i = 0; i < irmaDir.getTL(); i++) {
                                no.setInfo(irmaDir.getInfo(i), no.getTL());
                                no.setPos(irmaDir.getPos(i), no.getTL());
                                no.setLig(irmaDir.getLig(i), no.getTL());
                                no.setTL(no.getTL() + 1);
                            }
                            no.setLig(irmaDir.getLig(irmaDir.getTL()), no.getTL());
                        }
                    }
                }
            }

            if (concatenou) {
                // Se houve concatenação, precisamos realizar algumas verificações adicionais
                if (pai != raiz && pai.getTL() < m) {
                    // Se não estivermos na raiz, precisamos repetir o processo
                    no = pai;
                    pai = buscarPai(no, no.getInfo(0));
                    pilha.push(pai);
                    pilha.push(no);
                }
                else if (pai == raiz && pai.getTL() == 0) {
                    // Neste caso, precisamos mover a raiz devido à falta de elementos
                    if (irmaEsq != null) {
                        // Se movemos os itens de "no" para "irmaEsq"
                        raiz = irmaEsq;
                    }
                    else {
                        // Se movemos os itens de "irmaDir" para "no"
                        raiz = no;
                    }
                }
            }
        }
    }

    public void excluir(int info) {
        // Precisamos ter 1 ou mais elementos na árvore antes de começar
        if (raiz != null) {
            NoArvoreBPlus no = buscarFolha(info);
            int m = (int) Math.ceil(n / 2.0) - 1,
                pos = no.procurarPosicaoFolha(info);

            // Elemento encontrado?
            if (pos < no.getTL() && no.getInfo(pos) == info) {
                no.remanejarExclusao(pos);
                no.setTL(no.getTL() - 1);

                NoArvoreBPlus filho = no;
                NoArvoreBPlus pai = buscarPai(filho, filho.getInfo(0));

                while (pai != filho) {
                    int posFilho = localizarPosicaoFilho(pai, filho);

                    if (posFilho > 0) {
                        // Este separador representa o menor valor da subárvore.
                        NoArvoreBPlus folha = filho;

                        while (!folha.isFolha()) {
                            folha = folha.getLig(0);
                        }

                        pai.setInfo(folha.getInfo(0), posFilho - 1);
                        pai.setPos(folha.getPos(0), posFilho - 1);
                        break;
                    }

                    // O filho é o primeiro: a alteração relevante pode estar mais acima.
                    filho = pai;
                    pai = buscarPai(filho, filho.getInfo(0));
                }

                if (no.getTL() < m) {
                    if (no != raiz) {
                        redistribuir_concatenar(no, m);
                    }
                    else if (no.getTL() == 0) {
                        raiz = null;
                    }
                }
            }
        }
    }

    public void inOrdem() {
        this.inOrdem(this.raiz);
    }

    private void inOrdem(NoArvoreBPlus raiz) {
        NoArvoreBPlus aux = raiz;
        int posAux;
        PilhaObj<NoArvoreBPlus> pilha = new PilhaObj<NoArvoreBPlus>();
        PilhaInt pilhaInt = new PilhaInt();

        // Inicialização para entrar no loop
        pilha.push(aux);
        pilhaInt.push(-1);
        while (!pilha.isEmpty()) {
            aux = pilha.pop();
            posAux = pilhaInt.pop() + 1;

            while (aux != null) {
                // Andamos tudo para a esquerda
                pilha.push(aux);
                pilhaInt.push(0);
                aux = aux.getLig(0);
            }

            if (!pilha.isEmpty()) {
                // Pegamos o nó no topo da pilha e o exibimos
                aux = pilha.pop();
                posAux = pilhaInt.pop();

                if (aux != null && posAux < aux.getTL()) {
                    System.out.print(aux.getInfo(posAux) + " ");

                    // Se existirem ainda valores no nó, colocamos ele de volta na pilha
                    if (posAux + 1 < aux.getTL()) {
                        pilha.push(aux);
                        pilhaInt.push(posAux + 1);
                    }

                    pilha.push(aux.getLig(posAux + 1));
                    pilhaInt.push(0);
                }
            }
        }
        System.out.println();
    }

    public void exibir() {
        NoArvoreBPlus no = raiz;
        if (no != null) {
            while (no.getLig(0) != null) {
                no = no.getLig(0);
            }
            while (no != null) {
                for (int i = 0; i < no.getTL(); i++) {
                    System.out.print(no.getInfo(i) + " ");
                }
                no = no.getProx();
            }
        }
        System.out.println();
    }
}
