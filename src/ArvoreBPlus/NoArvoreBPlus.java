package ArvoreBPlus;

public class NoArvoreBPlus {
    private int n = 5;
    private int[] vInfo;
    private int[] vPos;
    private NoArvoreBPlus[] vLig;
    private int TL;
    private NoArvoreBPlus prox;
    private NoArvoreBPlus ant;

    public NoArvoreBPlus(int n) {
        this.n = n;
        this.vInfo = new int[n];
        this.vPos = new int[n];
        this.vLig = new NoArvoreBPlus[n + 1];
        this.TL = 0;
    }

    public NoArvoreBPlus(int n, int info, int posArq) {
        this(n);
        this.vInfo[0] = info;
        this.vPos[0] = posArq;
        this.TL = 1;
    }

    public int procurarPosicaoFolha(int info) {
        int pos = 0;
        while (pos < this.TL && info > this.vInfo[pos]) {
            pos++;
        }
        return pos;
    }

    public int procurarPosicaoDescida(int info) {
        int pos = 0;
        while (pos < this.TL && info >= this.vInfo[pos]) {
            pos++;
        }
        return pos;
    }

    public void remanejar(int pos) {
        vLig[this.TL + 1] = vLig[this.TL];
        for (int i = this.TL; i > pos; i--) {
            vInfo[i] = vInfo[i - 1];
            vPos[i] = vPos[i - 1];
            vLig[i] = vLig[i - 1];
        }
    }

    public void remanejarExclusao(int pos) {
        for (int i = pos; i < this.TL - 1; i++) {
            vInfo[i] = vInfo[i + 1];
            vPos[i] = vPos[i + 1];
            vLig[i] = vLig[i + 1];
        }
        vLig[this.TL - 1] = vLig[this.TL];
    }

    public boolean isFolha() {
        return vLig[0] == null;
    }

    public int getTL() {
        return TL;
    }

    public void setTL(int TL) {
        this.TL = TL;
    }

    public int getInfo(int pos) {
        return this.vInfo[pos];
    }

    public void setInfo(int info, int pos) {
        this.vInfo[pos] = info;
    }

    public int getPos(int pos) {
        return this.vPos[pos];
    }

    public void setPos(int posArq, int pos) {
        this.vPos[pos] = posArq;
    }

    public NoArvoreBPlus getLig(int pos) {
        return this.vLig[pos];
    }

    public void setLig(NoArvoreBPlus lig, int pos) {
        this.vLig[pos] = lig;
    }

    public NoArvoreBPlus getProx() {
        return prox;
    }

    public void setProx(NoArvoreBPlus prox) {
        this.prox = prox;
    }

    public NoArvoreBPlus getAnt() {
        return this.ant;
    }

    public void setAnt(NoArvoreBPlus ant) {
        this.ant = ant;
    }

    public int getN() {
        return n;
    }
}
