package be.howest.nmct.BMI;

/**
 * Created by alisio on 12/02/2015.
 */
public class BMIInfo {

    public static enum Category{
        GROOT_ONDERGEWICHT(0,15), ERNSTIG_ONDERGEWICHT(15,16), ONDERGEWICHT(16,8.5f), NORMAAL(18.5f,25), OVERGEWICHT(25,30), MATIG_OVERGEWICHT(30,35), ERNSTIG_OVERGEWICHT(35,40), ZEER_GROOT_OVERGEWICHT(40,50);

        private float lowerBoundary;
        private float upperBoundary;

        public float getLowerBoundary() {
            return lowerBoundary;
        }

        public float getUpperBoundary() {
            return upperBoundary;
        }

        public Boolean isInBoundary(float getal){
            if(getal>lowerBoundary && getal<=upperBoundary) return true;
            //if(getal>=lowerBoundary && getal<upperBoundary) return true;
            return false;
        }

        Category(float lowerBoundary, float upperBoundary) {
            this.lowerBoundary = lowerBoundary;
            this.upperBoundary = upperBoundary;
        }

        public static Category getCategory(float index){
            for(Category category : Category.values()){
                if(category.isInBoundary(index)) return category;
            }
            for(Category category : Category.values()){
                if(index <= 0 && category.name().equals("GROOT_ONDERGEWICHT")) return category;
                if(index > 50 && category.name().equals("ZEER_GROOT_OVERGEWICHT")) return category;
            }
            return null;
        }
    }

    private float height = 1.70f;
    private int mass = 70;
    private float bmiIndex;

    public float getHeight() {
        return height;
    }

    public int getMass() {
        return mass;
    }

    public float getBmiIndex() {
        bmiIndex = mass / (height * height);
        return bmiIndex;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public void setBmiIndex(float bmiIndex) {
        this.bmiIndex = bmiIndex;
    }

    public BMIInfo(float height, int mass) {
        this.height = height;
        this.mass = mass;
    }
}
