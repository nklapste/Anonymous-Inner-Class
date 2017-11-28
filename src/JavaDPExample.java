import java.util.HashMap;

/**
 * Lab 6: Anonymous Inner Classes and Reflection <br />
 * The {@code Animal} interface
 */
interface Animal {
    /**
     * An animal speaks
     */
    public void speak ();
}

/**
 * Lab 6: Anonymous Inner Classes and Reflection <br />
 * The {@code Lion} class
 */
class Lion implements Animal {
    /**
     * The lion speaks
     */
    public void speak() {
        System.out.println("ROAR");
    }
}

/**
 * Lab 6: Anonymous Inner Classes and Reflection <br />
 * The {@code Mouse} class
 */
class Mouse implements Animal {
    /**
     * The mouse speaks
     */
    public void speak() {
        System.out.println("SQUEAK");
    }
}

/**
 * Lab 6: Anonymous Inner Classes and Reflection <br />
 * The {@code Bison} class
 */
class Bison implements Animal {
    /**
     * The bison speaks
     */
    public void speak() {
        System.out.println("BELLOW");
    }
}

/**
 * Lab 6: Anonymous Inner Classes and Reflection <br />
 * The {@code AnimalType} class
 */
abstract class AnimalType {
    /**
     * Create and return an animal
     * @param criteria      {@code String} how is the animal like
     * @return              {@code Animal} the animal
     */

    private static HashMap<String,Animal> animalHashMap = new HashMap<>();

    public <T extends Animal> void addAnimal(String criteria, Class<T> animal) {
        try {
            animalHashMap.put(criteria, animal.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void removeCriteria(String criteria){
        animalHashMap.remove(criteria);
    }
    public abstract void baseAnimals();

    public AnimalType(){
        animalHashMap = new HashMap<>();
        baseAnimals();
    }

    public Animal getAnimal(String criteria) {
        // TODO: Lab 6 Part 2-1 -- Refactor this method

        // generate anonymous animal
        if (!animalHashMap.containsKey(criteria)) {
            return new Animal() {
                @Override
                public void speak() {
                    System.out.println("unknown noise!");
                }
            };
        }
        try {
            return (Animal) animalHashMap.get(criteria).getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return new Animal() {
            @Override
            public void speak() {
                System.out.println("unknown noise!");
            }
        };
    }

    public static void main(String[] args){

        AnimalType a = new AnimalType(){
            @Override
            public void baseAnimals() {
                animalHashMap.put("small", new Mouse());
                animalHashMap.put("big", new Bison());
                animalHashMap.put("lazy", new Lion());
            }
        };
        a.getAnimal("small").speak();
        a.addAnimal("predator", Lion.class);
        a.getAnimal("predator").speak();

        AnimalType b = new AnimalType(){
            @Override
            public void baseAnimals() {
                animalHashMap.put("small", new Mouse());
                animalHashMap.put("big", new Bison());
                animalHashMap.put("lazy", new Lion());
            }
        };
        b.getAnimal("predator").speak();


    }

}

