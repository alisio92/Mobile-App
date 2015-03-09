package be.howest.nmct.ScoresStudenten;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alisio on 12/02/2015.
 */
public class RunAdmin {
    public static void main(String[] args){
        List<Student> studenten = new ArrayList<Student>();
        String modulenaam1 = "web";
        String modulenaam2 = "design";
        String modulenaam3 = "prog";

        Student student1 = new Student("jan");
        student1.voegScoreToe(modulenaam1, 16);
        student1.voegScoreToe(modulenaam2, 5);
        student1.voegScoreToe(modulenaam3, 12);
        double score1 = student1.getTotaleScoreStudent();

        Student student2 = new Student("piet");
        student2.voegScoreToe(modulenaam1, 5);
        student2.voegScoreToe(modulenaam2, 4);
        student2.voegScoreToe(modulenaam3, 2);
        double score2 = student2.getTotaleScoreStudent();

        Student student3 = new Student("joris");
        student3.voegScoreToe(modulenaam1, 15);
        student3.voegScoreToe(modulenaam2, 14);
        student3.voegScoreToe(modulenaam3, 12);
        double score3 = student3.getTotaleScoreStudent();

        studenten.add(student1);
        studenten.add(student2);
        studenten.add(student3);

        List<Double> gem1 = Student.getScoresModule(studenten, modulenaam1);
        List<Double> gem2 = Student.getScoresModule(studenten, modulenaam2);
        List<Double> gem3 = Student.getScoresModule(studenten, modulenaam3);
        System.out.println("Gemiddelde van module " + modulenaam1 + ": " + Student.getGemiddeldeScoreModule(studenten, modulenaam1));
        System.out.println("Gemiddelde van module " + modulenaam2 + ": " + Student.getGemiddeldeScoreModule(studenten, modulenaam2));
        System.out.println("Gemiddelde van module " + modulenaam3 + ": " + Student.getGemiddeldeScoreModule(studenten, modulenaam3));

        System.out.println("De scores voor de module " + modulenaam1 + " zijn:");
        for(Double d : gem1){
            System.out.println(d);
        }
        System.out.println("De scores voor de module " + modulenaam2 + " zijn:");
        for(Double d : gem2){
            System.out.println(d);
        }
        System.out.println("De scores voor de module " + modulenaam3 + " zijn:");
        for(Double d : gem3){
            System.out.println(d);
        }
        System.out.println("Sorteer de student volgens hun totale score:");
        Student.sorteerStudenten(studenten);
        for(Student student : studenten){
            System.out.println(student.toString());
        }
    }
}
