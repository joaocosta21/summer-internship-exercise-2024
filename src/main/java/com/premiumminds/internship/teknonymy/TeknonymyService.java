package com.premiumminds.internship.teknonymy;

import java.util.Objects;

public class TeknonymyService implements ITeknonymyService {

  @Override
  public String getTeknonymy(Person person) {
    if (person.children() == null || person.children().length == 0) {
        return "";
    }

    // Find the oldest descendant and the maximum generation distance among all generations of the person
    Pair<Person, Integer> oldestDescendantAndMaxDistance = findOldestDescendantAndMaxDistance(person);

    Person oldestDescendant = oldestDescendantAndMaxDistance.getFirst();
    int maxGenerationDistance = oldestDescendantAndMaxDistance.getSecond();

    // Build the teknonymy based on the generation distance for the oldest descendant
    return buildTeknonymy(maxGenerationDistance, oldestDescendant, person);
  }

  private Pair<Person, Integer> findOldestDescendantAndMaxDistance(Person person) {
    if (person.children() == null) {
      return new Pair<>(person, 0);
    }
    Person oldestInGeneration = null;
    int maxGenerationDistance = -1;
    
    for (Person child : person.children()) {
      // Recursively find the oldest descendant for each child
      Pair<Person, Integer> oldestInChildAndDistance = findOldestDescendantAndMaxDistance(child);
      Person oldestInChild = oldestInChildAndDistance.getFirst();
      int generationDistance = oldestInChildAndDistance.getSecond() + 1; // Increment distance by 1 for the current generation
      // Check if the current child's oldest descendant is older than the current oldestInGeneration
      if (generationDistance > maxGenerationDistance || (generationDistance == maxGenerationDistance && oldestInChild.dateOfBirth().isBefore(oldestInGeneration.dateOfBirth()))) {
          maxGenerationDistance = generationDistance;
          oldestInGeneration = oldestInChild;
      }
    }

    return new Pair<>(oldestInGeneration, maxGenerationDistance);
  }

  // Method to build the teknonymy based on the generation distance and the gender of the original person
  private String buildTeknonymy(int generationDistance, Person oldestDescendant, Person targetPerson) {
    // System.out.println(generationDistance);
    if (generationDistance == 1) {
      if (targetPerson.sex() == 'M') {
        return "father of " + oldestDescendant.name();
      } else if (targetPerson.sex() == 'F') {
          return "mother of " + oldestDescendant.name();
        }
      } else if (generationDistance == 2) {
          if (targetPerson.sex() == 'M') {
            return "grandfather of " + oldestDescendant.name();
          } else if (targetPerson.sex() == 'F') {
            return "grandmother of " + oldestDescendant.name();
          }
      } else {
        return repeat("great-", generationDistance - 2) + "grand" + (targetPerson.sex() == 'M' ? "father" : "mother") + " of " + oldestDescendant.name();
      }
    return ""; // Default return value if no match is found
  }

  // Method to repeat a string n times
  private String repeat(String str, int n) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < n; i++) {
      sb.append(str);
    }
    return sb.toString();
  }
}
