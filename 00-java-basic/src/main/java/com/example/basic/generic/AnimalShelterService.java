package com.example.basic.generic;

import com.example.basic.generic.model.Animal;
import com.example.basic.generic.model.Cat;
import com.example.basic.generic.model.Dog;

import java.util.List;

public interface AnimalShelterService {
    // 只读
    void acceptAnimals(List<? extends Animal> animals);

    // 只写
    void transferDogs(List<? super Dog> dogs);
    void transferCat(List<? super Cat> cats);
}
