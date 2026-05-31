package PilhaObj;

public class ItemPilhaObj<T> {
    private ItemPilhaObj<T> prox;
    private T obj;

    public ItemPilhaObj(T obj) {
        this(obj, null);
    }

    public ItemPilhaObj(T obj, ItemPilhaObj<T> prox) {
        this.obj = obj;
        this.prox = prox;
    }

    public T getObj() {
        return obj;
    }

    public ItemPilhaObj<T> getProx() {
        return prox;
    }
}
