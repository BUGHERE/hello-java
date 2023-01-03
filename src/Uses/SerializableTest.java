package Uses;

import java.io.*;

class SerializablePerson extends Person implements Serializable {

}

public class SerializableTest{
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("serializable_out"));
        SerializablePerson sp = new SerializablePerson();
        out.writeObject(sp);
        System.out.println(sp);
        out.close();

        ObjectInputStream in = new ObjectInputStream(new FileInputStream("serializable_out"));
        sp = (SerializablePerson) in.readObject();
        System.out.println(sp);
        in.close();
    }
}
