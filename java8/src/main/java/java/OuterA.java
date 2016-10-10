package java;

/**
 * Created by tianyuzhi on 16/10/9.
 */
public class OuterA {
    public Contents cont() {
        return new Contents() {
            private int i = 11;


            public int value() {
                return i;
            }
        }; // 在这里需要一个分号
    }



    public static void main(String[] args) {
        OuterA p = new OuterA();
        Contents c = p.cont();
       System.out.println(c.value());
    }
}

