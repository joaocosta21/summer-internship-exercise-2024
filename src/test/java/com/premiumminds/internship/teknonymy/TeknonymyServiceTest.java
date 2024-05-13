package com.premiumminds.internship.teknonymy;

import com.premiumminds.internship.teknonymy.TeknonymyService;
import com.premiumminds.internship.teknonymy.Person;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class TeknonymyServiceTest {

  /**
   * The corresponding implementations to test.
   *
   * If you want, you can make others :)
   *
   */
  public TeknonymyServiceTest() {
  };

  // Test of single child
  @Test
  public void PersonNoChildrenTest() {
    Person person = new Person("John",'M',null, LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "";
    assertEquals(result, expected);
  }

  // Test of normal father
  @Test
  public void PersonOneChildTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[]{ new Person("Holy",'F', null, LocalDateTime.of(1046, 1, 1, 0, 0)) },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "father of Holy";
    assertEquals(result, expected);
  }

  // Test of father comparing ages
  @Test
  public void PersonTwoChildrenTestYear() {
    Person person = new Person(
        "John",
        'M',
        new Person[]{
          new Person("Holy",'F', null, LocalDateTime.of(1043, 1, 1, 0, 0)),
          new Person("Moly",'F', null, LocalDateTime.of(1046, 1, 1, 0, 0))
        },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "father of Holy";
    assertEquals(result, expected);
  }

  // Test for father comparing ages in minute
  @Test
  public void PersonTwoChildrenTestSecond() {
    Person person = new Person(
        "John",
        'M',
        new Person[]{
          new Person("Holy",'F', null, LocalDateTime.of(1046, 1, 1, 0, 0)),
          new Person("Moly",'F', null, LocalDateTime.of(1046, 1, 1, 0, 1))
        },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "father of Holy";
    assertEquals(result, expected);
  }

  // Test of grnadfather
  @Test
  public void PersonOneGrandchildTest() {
      Person person = new Person(
          "John",
          'M',
          new Person[]{ 
              new Person(
                  "Holy",
                  'F', 
                  new Person[]{
                      new Person("Joly",'F', null, LocalDateTime.of(1065, 1, 1, 0, 0)),
                  }, 
                  LocalDateTime.of(1046, 1, 1, 0, 0))
          },
          LocalDateTime.of(1046, 1, 1, 0, 0));
      String result = new TeknonymyService().getTeknonymy(person);
      String expected = "grandfather of Joly"; // Adjusted expected value to check for grandfather
      assertEquals(expected, result);
  }

  // Test of a grandfather with two grandchildren from different children
  @Test
  public void PersonOneTwoGrandchildTest() {
      Person person = new Person(
          "John",
          'M',
          new Person[]{ 
              new Person(
                  "Holy",
                  'F', 
                  new Person[]{
                      new Person("Joly",'F', null, LocalDateTime.of(1065, 1, 1, 0, 0)),
                  }, 
                  LocalDateTime.of(1055, 1, 1, 0, 0)),
              new Person( 
                  "Moly",
                  'F', 
                  new Person[]{
                      new Person("Koly",'F', null, LocalDateTime.of(1064, 1, 1, 0, 0)),
                  }, 
                  LocalDateTime.of(1056, 1, 1, 0, 0))
          },
          LocalDateTime.of(1046, 1, 1, 0, 0));
      String result = new TeknonymyService().getTeknonymy(person);
      String expected = "grandfather of Koly"; // Adjusted expected value to check for grandfather
      assertEquals(expected, result);
  }

  // Example test
  @Test
  public void PersonDefaultTest() {
      Person person = new Person(
          "A",
          'F',
          new Person[]{ 
              new Person(
                  "B",
                  'M', 
                  new Person[]{
                      new Person("E",'M', null, LocalDateTime.of(2019, 1, 1, 0, 0)),
                  }, 
                  LocalDateTime.of(2000, 1, 1, 0, 0)),
              new Person("C",'M', null, LocalDateTime.of(2002, 1, 1, 0, 0)),
              new Person( 
                  "D",
                  'F', 
                  new Person[]{
                      new Person("F",'M', null, LocalDateTime.of(2018, 1, 1, 0, 0)),
                      new Person("G",'M', null, LocalDateTime.of(2021, 1, 1, 0, 0)),
                      new Person("H",'M', null, LocalDateTime.of(2022, 1, 1, 0, 0)),
                  }, 
                  LocalDateTime.of(2003, 1, 1, 0, 0))
          },
          LocalDateTime.of(1980, 1, 1, 0, 0));
      String result = new TeknonymyService().getTeknonymy(person);
      String expected = "grandmother of F"; // Adjusted expected value to check for grandfather
      assertEquals(expected, result);
      String result2 = new TeknonymyService().getTeknonymy(person.children()[0]);
      String expected2 = "father of E"; // Adjusted expected value to check for grandfather
      assertEquals(expected2, result2);
      String result3 = new TeknonymyService().getTeknonymy(person.children()[1]);
      String expected3 = ""; // Adjusted expected value to check for grandfather
      assertEquals(expected3, result3);
      String result4 = new TeknonymyService().getTeknonymy(person.children()[2]);
      String expected4 = "mother of F"; // Adjusted expected value to check for grandfather
      assertEquals(expected4, result4);
      String result5 = new TeknonymyService().getTeknonymy(person.children()[0].children()[0]);
      String expected5 = ""; // Adjusted expected value to check for grandfather
      assertEquals(expected5, result5);
      String result6 = new TeknonymyService().getTeknonymy(person.children()[2].children()[0]);
      String expected6 = ""; // Adjusted expected value to check for grandfather
      assertEquals(expected6, result6);
      String result7 = new TeknonymyService().getTeknonymy(person.children()[2].children()[1]);
      String expected7 = ""; // Adjusted expected value to check for grandfather
      assertEquals(expected7, result7);
      String result8 = new TeknonymyService().getTeknonymy(person.children()[2].children()[2]);
      String expected8 = ""; // Adjusted expected value to check for grandfather
      assertEquals(expected8, result8);
  }

  // Test of a great-grandfather
  @Test
  public void PersonOneGrandGrandchildTest() {
      Person person = new Person(
          "John",
          'M',
          new Person[]{ 
              new Person(
                  "Holy",
                  'F', 
                  new Person[]{
                      new Person("Joly",
                      'F',
                      new Person[]{
                          new Person("Koly",'F', null, LocalDateTime.of(1075, 1, 1, 0, 0)),
                      },
                      LocalDateTime.of(1065, 1, 1, 0, 0)),
                  }, 
                  LocalDateTime.of(1046, 1, 1, 0, 0))
          },
          LocalDateTime.of(1046, 1, 1, 0, 0));
      String result = new TeknonymyService().getTeknonymy(person);
      String expected = "great-grandfather of Koly"; // Adjusted expected value to check for grandfather
      assertEquals(expected, result);
  }

  // Basic test with a child on E
  @Test
  public void PersonLongGenerationTest() {
    Person person = new Person(
      "A",
      'M',
      new Person[]{ 
          new Person(
              "B",
              'M', 
              new Person[]{
                  new Person("E",
                  'M', 
                  new Person[]{
                      new Person("I",'M', null, LocalDateTime.of(2049, 1, 1, 0, 0)),
                  }, 
                  LocalDateTime.of(2019, 1, 1, 0, 0)),
              }, 
              LocalDateTime.of(2000, 1, 1, 0, 0)),
          new Person("C",'M', null, LocalDateTime.of(2002, 1, 1, 0, 0)),
          new Person( 
              "D",
              'F', 
              new Person[]{
                  new Person("F",'M', null, LocalDateTime.of(2018, 1, 1, 0, 0)),
                  new Person("G",'M', null, LocalDateTime.of(2021, 1, 1, 0, 0)),
                  new Person("H",'M', null, LocalDateTime.of(2022, 1, 1, 0, 0)),
              }, 
              LocalDateTime.of(2003, 1, 1, 0, 0))
      },
      LocalDateTime.of(1980, 1, 1, 0, 0));
      String result = new TeknonymyService().getTeknonymy(person);
      String expected = "great-grandfather of I"; 
      assertEquals(expected, result);
  }

  // Test of great-great-great-grandfather
  @Test
  public void PersonGreatGreatGreatGrandmotherTest() {
    Person person = new Person(
      "A",
      'M',
      new Person[]{ 
          new Person(
              "B",
              'M', 
              new Person[]{
                  new Person(
                  "C",
                  'M', 
                  new Person[]{
                      new Person(
                      "D",
                      'M', 
                      new Person[]{ 
                        new Person(
                          "E",
                          'M', 
                          new Person[]{
                            new Person(
                              "F", 
                              'F', 
                              null, 
                              LocalDateTime.of(2069, 1, 1, 0, 0))
                          }, 
                          LocalDateTime.of(2069, 1, 1, 0, 0)),
                        }, 
                      LocalDateTime.of(2049, 1, 1, 0, 0)),
                  }, 
                  LocalDateTime.of(2019, 1, 1, 0, 0)),
              }, 
              LocalDateTime.of(2000, 1, 1, 0, 0)),  
      },
      LocalDateTime.of(1980, 1, 1, 0, 0));
      String result = new TeknonymyService().getTeknonymy(person);
      String expected = "great-great-great-grandfather of F";
      assertEquals(expected, result);
  }

}
