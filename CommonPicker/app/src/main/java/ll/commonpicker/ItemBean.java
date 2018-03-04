package ll.commonpicker;

/**
 * Created by ZhangPeng on 3-3-0003.
 */

public class ItemBean<T> {

    private String name;
    private boolean highLight;
    private T t;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHighLight() {
        return highLight;
    }

    public void setHighLight(boolean highLight) {
        this.highLight = highLight;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
