package PilhaInt;

public class ItemPilhaInt {
    private ItemPilhaInt prox;
    private int val;

    public ItemPilhaInt(int val) {
        this(val, null);
    }

    public ItemPilhaInt(int val, ItemPilhaInt prox) {
        this.val = val;
        this.prox = prox;
    }

    public int getVal() {
        return val;
    }

    public ItemPilhaInt getProx() {
        return prox;
    }
}
