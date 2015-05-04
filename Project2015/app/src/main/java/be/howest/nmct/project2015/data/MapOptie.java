package be.howest.nmct.project2015.data;

/**
 * Created by alisio on 4/05/2015.
 */
public class MapOptie {
    public static enum Optie{
        NORMAL(0, "Normaal"), TERRAIN(1, "Terrein"), SATELLITE(2, "Satelliet");

        private int id;
        private String name;

        public String getName() {
            return name;
        }

        Optie(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public static String getMapOptie(int index){
            for(Optie optie : Optie.values()){
                if(optie.id == index) return optie.name;
            }
            return null;
        }
    }

}
