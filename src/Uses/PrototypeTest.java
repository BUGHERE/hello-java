package Uses;

import java.io.BufferedReader;
import java.io.InputStreamReader;

// 重写克隆方法
class Realizetype implements Cloneable {
    public Object clone() throws CloneNotSupportedException {
        return (Realizetype)super.clone();
    }
}

public class PrototypeTest {
    public static void main(String[] args)throws CloneNotSupportedException {
        // 克隆
        Realizetype obj1=new Realizetype();
        Realizetype obj2=(Realizetype)obj1.clone();
        System.out.println("obj1==obj2?"+(obj1==obj2));
        System.out.println("obj1 equals obj2?"+(obj1.equals(obj2)));
        String s1 = new String("abc");
        String s2 = new String("abc");
        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    }
}
