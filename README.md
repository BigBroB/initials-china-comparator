#### 说明
主要是之前和别人讨论排序问题，当时碰到的问题是，需要按照中文第一个字的拼音首字母排序，百度发下自带的比较器 `Collator.getInstance(Locale.CHINA)` ，但是使用和追源码发现
```
    @Override
    public int compare(Object o1, Object o2) {
       return compare((String)o1, (String)o2);
    }
```
这里它只支持传入`String`类型，所以打算做一个加强版。采用注解确定实体哪个字段参与排序。

[github地址](https://github.com/BigBroB/initials-china-comparator)

#### 比较器 InitialsChinaComparator
```
    @Override
    public int compare(Object o1, Object o2) {
        try {
            Field[] o1Fields = getFields(o1);
            String source = "";
            String target = "";
            // 获取字段值
            for (Field o1Field : o1Fields) {
                CompareString compareString = o1Field.getAnnotation(CompareString.class);
                if (compareString != null) {
                    source = (String) getValueByFieldNameByGetMethod(o1, o1Field.getName());
                    break;
                }
            }
            // 获取字段值
            Field[] o2Fields = getFields(o2);
            for (Field o2Field : o2Fields) {
                CompareString compareString = o2Field.getAnnotation(CompareString.class);
                if (compareString != null) {
                    target = (String) getValueByFieldNameByGetMethod(o2, o2Field.getName());
                    break;
                }
            }
            // 比较
            Collator collator = Collator.getInstance(Locale.CHINA);
            return this.order*collator.compare(source, target);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

```

#### 使用
##### 实体
```
public class TestCompare {

    // 标记参与排序
    @CompareString
    private String name;
    
    ...
    
    // getter
    // setter
    
}

```
##### 排序

```
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

```

#### 最后
欢迎大佬们指导