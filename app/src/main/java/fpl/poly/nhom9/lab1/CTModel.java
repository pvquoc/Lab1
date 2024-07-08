package fpl.poly.nhom9.lab1;

public class CTModel {
    private String name;
    private String country;
    private Integer population;  // Changed from int to Integer

    public CTModel() {
    }

    public CTModel(String name, String country, Integer population) {
        this.population = population;
        this.country = country;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPopulation() {  // Changed return type to Integer
        return population;
    }

    public void setPopulation(Integer population) {  // Changed parameter type to Integer
        this.population = population;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
