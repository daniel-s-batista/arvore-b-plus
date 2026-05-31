package PilhaInt;

public class PilhaInt {
    private ItemPilhaInt topo;

    public PilhaInt() {
        topo = null;
    }

    public void push(int val) {
        this.topo = new ItemPilhaInt(val, this.topo);
    }

    public int pop() {
        int val = this.topo.getVal();
        this.topo = this.topo.getProx();
        return val;
    }

    public boolean isEmpty() {
        return this.topo == null;
    }
}
