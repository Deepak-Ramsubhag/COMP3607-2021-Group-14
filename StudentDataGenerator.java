import java.util.ArrayList;

public class StudentDataGenerator implements Aggregate {

    ArrayList<StudentData> studentData;

    public StudentDataGenerator() {
        studentData = new ArrayList<StudentData>();
    }

    public void generateList() {
        StudentData student = new StudentData(123456, "Deepak Ramsubhag");
        studentData.add(student);

        StudentData student2 = new StudentData(654321, "John Doe");
        studentData.add(student2);

        StudentData student3 = new StudentData(987654, "Jane Doe");
        studentData.add(student3);

    }

    public Iterator createIterator() {

        Iterator iterator = new ListIterator();

        return iterator;
    }

    private class ListIterator implements Iterator {

        private int currentPosition = 0;

        public boolean hasNext() {

            if (currentPosition < studentData.size())
                return true;
            else
                return false;
        }

        public Object next() {
            if (this.hasNext()) {

                Object o = studentData.get(currentPosition++);
                return o;

            } else
                return null;

        }

    }

}
