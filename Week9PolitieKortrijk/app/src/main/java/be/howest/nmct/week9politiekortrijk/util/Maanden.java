package be.howest.nmct.week9politiekortrijk.util;

/**
 * Created by alisio on 3/05/2015.
 */
public class Maanden {
    public static enum Maand{
        JANUARI("Januari", 1), FEBRUARI("Februari", 2), MAART("Maart", 3), APRIL("April", 4), MEI("Mei", 5), JUNI("Juni", 6), JULI("Juli", 7), AUGUSTUS("Augustus", 8), SEPTEMBER("September", 9), OKTOBER("Oktober", 10), NOVEMBER("November", 11), DECEMBER("December", 12);

        private String maand;
        private int index;

        public String getMaand() {
            return maand;
        }

        public int getIndex() {
            return index;
        }

        Maand(String maand, int index){
            this.maand = maand;
            this.index = index;
        }

        public static Maand getMaand(int index){
            for(Maand maand : Maand.values()){
                if(maand.index == index) return maand;
            }
            return null;
        }
    }
}
