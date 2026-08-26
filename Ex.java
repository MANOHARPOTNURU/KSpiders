package Kspiders;



import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class Ex {

    public static void main(String[] args) {
        
      
        System.out.println("Sets :");

        Set<String> hashSet = new HashSet<>();
        hashSet.add("Banana");
        hashSet.add("Apple");
      hashSet.add("Cherry");
        hashSet.add("Apple");
        hashSet.add(null);    
        System.out.println("HashSet (Unordered): " + hashSet);
        
        

       Set<String> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add("Banana");
        linkedHashSet.add("Apple");
        linkedHashSet.add("Cherry");
        linkedHashSet.add(null);
        System.out.println("LinkedHashSet (Insertion Order): " + linkedHashSet);
        
      Set<String> treeSet = new TreeSet<>();
        treeSet.add("Banana");
        treeSet.add("Apple");
      treeSet.add("Cherry");
        System.out.println("TreeSet (Sorted Order): " + treeSet);

        
        System.out.println("Operations :");

        Set<Integer> numbers = new HashSet<>(Arrays.asList(10, 20, 30, 40, 50));

        System.out.println("Size: " + numbers.size());
        System.out.println("Contains 30? " + numbers.contains(30));
        numbers.remove(20);
        System.out.println("After removing 20: " + numbers);
        numbers.addAll(Arrays.asList(60, 70));
        System.out.println("After addAll [60, 70]: " + numbers);

        numbers.removeIf(n -> n > 50);
        System.out.println("After removeIf (>50): " + numbers);

        Set<String> immutableSet = Set.of("Red", "Green", "Blue");
        System.out.println("Immutable Set: " + immutableSet);
        

        Set<Integer> setA = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        Set<Integer> setB = new HashSet<>(Arrays.asList(4, 5, 6, 7, 8));

        Set<Integer> union = new HashSet<>(setA);
        union.addAll(setB);
        System.out.println("Union (A ∪ B): " + union);

        Set<Integer> intersection = new HashSet<>(setA);
        intersection.retainAll(setB);
        System.out.println("Intersection (A ∩ B): " + intersection);

        Set<Integer> difference = new HashSet<>(setA);
        difference.removeAll(setB);
        System.out.println("Difference (A - B): " + difference);

        
        Set<String> languages = Set.of("Java", "Python", "C++", "Go");

        System.out.println("Enhanced For-Loop: ");
        for (String lang : languages) {
            System.out.println(lang + " ");
        }
        System.out.println();

        System.out.println("Iterator: ");
        Iterator<String> iterator = languages.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next() + " ");
        }
        System.out.println();
        

        System.out.println("Stream/ForEach: ");
        languages.forEach(lang -> System.out.println(lang + " "));
        System.out.println();

       

        NavigableSet<Integer> navSet = new TreeSet<>(Arrays.asList(10, 20, 30, 40, 50));
        System.out.println("Original Navigable Set: " + navSet);
        System.out.println("First (Lowest): " + navSet.first());
        System.out.println("Last (Highest): " + navSet.last());
        System.out.println("Lower than 30 (< 30): " + navSet.lower(30));
        System.out.println("Floor of 30 (<= 30): " + navSet.floor(30));
        System.out.println("Ceiling of 30 (>= 30): " + navSet.ceiling(30));
        System.out.println("Higher than 30 (> 30): " + navSet.higher(30));
        System.out.println("Subset (20 to 50 exclusive): " + navSet.subSet(20, 50));

        Set<Integer> descendingSet = new TreeSet<>(Comparator.reverseOrder());
        descendingSet.addAll(Arrays.asList(5, 1, 9, 3));
        System.out.println("TreeSet with Reverse Comparator: " + descendingSet);

      

        Set<Person> people = new HashSet<>();
        people.add(new Person(1, "Alice"));
        people.add(new Person(2, "Bob"));
        people.add(new Person(1, "Alice")); 
        System.out.println("People count (Duplicates filtered): " + people.size());
        people.forEach(System.out::println);      
        Set<String> syncSet = Collections.synchronizedSet(new HashSet<>());
        syncSet.add("Thread-safe item");

        Set<String> concurrentSet = ConcurrentHashMap.newKeySet();
        concurrentSet.add("Concurrent item");

        System.out.println("Synchronized Set: " + syncSet);
        System.out.println("Concurrent Set: " + concurrentSet);
    }

    static class Person {
        private final int id;
        private final String name;

        public Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return id == person.id && Objects.equals(name, person.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }

        @Override
        public String toString() {
            return "Person{id=" + id + ", name='" + name + "'}";
        }
    }
}