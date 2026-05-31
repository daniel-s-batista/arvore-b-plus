package PilhaObj;

public class PilhaObj<T> {
    private ItemPilhaObj<T> topo;

    public PilhaObj() {
        topo = null;
    }

    public void push(T obj) {
        this.topo = new ItemPilhaObj<T>(obj, this.topo);
    }

    public T pop() {
        T obj = this.topo.getObj();
        this.topo = this.topo.getProx();
        return obj;
    }

    public boolean isEmpty() {
        return this.topo == null;
    }
}
