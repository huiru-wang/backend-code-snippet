package com.example.basic.generic;

import com.example.basic.generic.model.Animal;
import com.example.basic.generic.model.Cat;
import com.example.basic.generic.model.Dog;

import java.util.ArrayList;
import java.util.List;

public class GenericDemo {

    public static void main(String[] args) {
        List<Cat> cats = new ArrayList<>();
        List<Dog> dogs = new ArrayList<>();

        AnimalShelter animalShelter = new AnimalShelter();
        animalShelter.acceptAnimals(cats);
        animalShelter.acceptAnimals(dogs);




    }

    static class AnimalShelter {
        private List<Dog> dogs = new ArrayList<>();
        private List<Cat> cats = new ArrayList<>();

        // 只读
        public void acceptAnimals(List<? extends Animal> animals) {
            for (Animal animal : animals) {
                if (animal instanceof Dog) {
                    dogs.add((Dog) animal);
                }
                if (animal instanceof Cat) {
                    cats.add((Cat) animal);
                }
            }
        }

        public void transferDogs(List<? super Dog> dogs) {

        }

        public void transferCats(List<? super Cat> cats) {

        }
    }
}
