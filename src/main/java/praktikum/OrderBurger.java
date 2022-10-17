package praktikum;

public class OrderBurger {
    public String[] ingredients;

    public OrderBurger(String[] ingredients){
        this.ingredients = ingredients;
    }

    public OrderBurger(){

    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
}
