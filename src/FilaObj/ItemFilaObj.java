package FilaObj;

public class ItemFilaObj<T> {
    private T obj;
    private ItemFilaObj<T> prox;

    public ItemFilaObj(T obj) {
        this.obj = obj;
    }

    public T getObj() {
        return obj;
    }

    public ItemFilaObj<T> getProx() {
        return prox;
    }

    public void setProx(ItemFilaObj<T> prox) {
        this.prox = prox;
    }
}
