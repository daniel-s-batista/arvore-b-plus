package FilaObj;

public class FilaObj<T> {
    private ItemFilaObj<T> inicio;

    public FilaObj() {
        this.inicio = null;
    }

    public void enqueue(T obj) {
        if (this.inicio == null) {
            this.inicio = new ItemFilaObj<T>(obj);
        }
        else {
            ItemFilaObj<T> fim = this.inicio;
            while (fim.getProx() != null) {
                fim = fim.getProx();
            }
            fim.setProx(new ItemFilaObj<T>(obj));
        }
    }

    public T dequeue() {
        T obj = this.inicio.getObj();
        this.inicio = this.inicio.getProx();
        return obj;
    }

    public boolean isEmpty() {
        return this.inicio == null;
    }
}
