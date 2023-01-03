package Uses;

public class CloneTest implements Cloneable{
    public Person person;
    public CloneTest() {
        person = new Person();
    }
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    protected Object cloneDeep() throws CloneNotSupportedException {
        CloneTest clone = (CloneTest)super.clone();
        clone.person = new Person();
        return clone;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        CloneTest test = new CloneTest();
        System.out.println(test.person);
        CloneTest cloneResult = (CloneTest)test.clone();
        System.out.println(cloneResult.person);
        CloneTest cloneDeepResult = (CloneTest)test.cloneDeep();
        System.out.println(cloneDeepResult.person);
    }
}
