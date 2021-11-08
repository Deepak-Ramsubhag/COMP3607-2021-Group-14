
public class App {
    public static void main(String[] args) throws Exception {

        StudentDataGenerator sdg = new StudentDataGenerator();
        sdg.generateList();

        Iterator iter = sdg.createIterator();

        while (iter.hasNext()) {
            StudentData student = (StudentData) iter.next();
            System.out.println(student.getIdentifier() + "   " + student.getFullName());
        }
    }
}
