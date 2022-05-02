package lamda;

import java.util.ArrayList;

public class StudentInfo {
void checkStudents(ArrayList<Student> al, StudentsCheck sc){
    for(Student s : al){
        if(sc.check(s)){
            System.out.println(s);
        }
    }
}

      /*  void printStudentsOverGrade(ArrayList<Student> a1,double grade ){
        for(Student s : a1){
            if(s.avgGrade > grade){
                System.out.println(s);
            }
        }
        }
        void printStudentsUnderAge(ArrayList<Student> a1, int age ){
            for(Student s : a1){
                if(s.age < age){
                    System.out.println(s);
                }
            }

        }
        void printStudentsMixConditions(ArrayList<Student> a1, int age, double grade, char sex ){
            for(Student s : a1){
                if(s.age > age && s.avgGrade < grade && s.sex == sex){
                    System.out.println(s);
                }
            }


        }

       */
}
interface StudentsCheck {
boolean check(Student s);
}
