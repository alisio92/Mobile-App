package be.howest.nmct.ScoresStudenten;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alisio on 12/02/2015.
 */
public class Student implements Comparable<Student>{
    private String emailStuden;
    private Map<String, ModulePunt> scoresStudent = new HashMap<String, ModulePunt>();

    public Student(String emailStuden) {
        this.emailStuden = emailStuden;
    }

    public String getEmailStuden() {
        return emailStuden;
    }

    public void setEmailStuden(String emailStuden) {
        this.emailStuden = emailStuden;
    }

    public void voegScoreToe(String modulenaam, double score){
        ModulePunt mp = new ModulePunt(modulenaam, score);
        if(!scoresStudent.containsKey(modulenaam)) scoresStudent.put(modulenaam, mp);
    }

    public void voegScoreToe(String modulenaam, double score, int aantalSp){
        ModulePunt mp = new ModulePunt(modulenaam, aantalSp, score);
        if(!scoresStudent.containsKey(modulenaam)) scoresStudent.put(modulenaam, mp);
    }

    public double getTotaleScoreStudent(){
        double score = 0;
        int maxScore = 0;
        for(ModulePunt scores : scoresStudent.values()){
            maxScore+= scores.getAantalStudiepunten();
        }
        for(ModulePunt scores : scoresStudent.values()){
            score += scores.getScore() * scores.getAantalStudiepunten() / maxScore;
        }
        return score;
    }

    public static List<Double> getScoresModule(List<Student> studenten, String modulenaam){
        List<Double> totaalScores = new ArrayList<Double>();
        for(Student student : studenten){
            double score = 0;
            for(ModulePunt scores : student.scoresStudent.values()){
                if(scores.getModulenaam().equals(modulenaam)) score += scores.getScore();
            }
            totaalScores.add(score);
        }
        return totaalScores;
    }

    public static double getGemiddeldeScoreModule(List<Student> studenten, String modulenaam){
        double score = 0;
        for(Student student : studenten) {
            for (ModulePunt scores : student.scoresStudent.values()) {
                if (scores.getModulenaam().equals(modulenaam)) score += scores.getScore();
            }
        }
        int t = studenten.size() * 20;
        return score / (studenten.size() * 20) * 20;
    }

    public static void sorteerStudenten(List<Student> studenten){
        Collections.sort(studenten);
    }

    @Override
    public int compareTo(Student another) {
        if(getTotaleScoreStudent() > another.getTotaleScoreStudent()) return 1;
        if(getTotaleScoreStudent() < another.getTotaleScoreStudent()) return -1;
        return 0;
    }

    @Override
    public String toString() {
        return getEmailStuden() + " - Totale score: " + getTotaleScoreStudent();
    }
}
