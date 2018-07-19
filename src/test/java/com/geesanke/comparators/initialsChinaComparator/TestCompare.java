package com.geesanke.comparators.initialsChinaComparator;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestCompare {

    @CompareString
    private String name;
    
    public TestCompare(String name) {
        this.name = name;
    }
    public TestCompare() {
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        List<TestCompare> tests = new ArrayList<TestCompare>();
        TestCompare test1 = new TestCompare("杨");
        TestCompare test2 = new TestCompare("白");
        TestCompare test3 = new TestCompare("康");
        
        tests.add(test1);
        tests.add(test2);
        tests.add(test3);
        
        Collections.sort(tests, new InitialsChinaComparator(InitialsChinaComparator.DESC));
        
        for(TestCompare test:tests) {
            
            System.out.println(test.getName());
            
        }
        
        
    }
    

    
    
    
    
    
}
