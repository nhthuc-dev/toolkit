# Toolkit

[![Maven Central](https://img.shields.io/maven-central/v/io.github.leratortech/toolkit-core.svg)](https://search.maven.org/artifact/io.github.leratortech/toolkit-core/2.0.0/jar)

Toolkit is a comprehensive Java utility library providing helpers for collections, concurrency, file handling, JSON, numbers, OS utilities, security, text utilities, time utilities, tree structures, and validation. It is published on Maven Central.

---

## Add dependency

Maven users:
```xml
<dependency>
    <groupId>io.github.leratortech</groupId>
    <artifactId>toolkit-core</artifactId>
    <version>2.0.0</version>
</dependency>
```

Gradle users:
```gradle
dependencies {
    implementation 'io.github.leratortech:toolkit-core:2.0.0'
}
```
Folder Structure:
```
toolkit/
├── .github/
│   └── workflows/
│       └── maven.yml
├── .gitignore
├── HELP.md
├── README.md
├── pom.xml
└── toolkit-core/
    ├── pom.xml
    └── src/
        ├── main/
        │   └── java/
        │       └── com/
        │           └── leratortech/
        │               └── toolkit/
        │                   ├── ClassUtils.java
        │                   ├── TextUtils.java
        │                   ├── ThreadUtils.java
        │                   ├── collection/
        │                   │   ├── ArrayUtils.java
        │                   │   ├── CollectionUtils.java
        │                   │   ├── ListUtils.java
        │                   │   ├── MapUtils.java
        │                   │   └── SetUtils.java
        │                   ├── concurrency/
        │                   │   ├── AsyncUtils.java
        │                   │   ├── ExecutorBuilder.java
        │                   │   └── RetryUtils.java
        │                   ├── file/
        │                   │   └── FileUtils.java
        │                   ├── function/
        │                   │   └── FunctionalUtils.java
        │                   ├── json/
        │                   │   └── JsonUtils.java
        │                   ├── number/
        │                   │   ├── MathUtils.java
        │                   │   └── MoneyUtils.java
        │                   ├── os/
        │                   │   ├── OperatingSystem.java
        │                   │   └── OsUtils.java
        │                   ├── security/
        │                   │   └── SecurityUtils.java
        │                   ├── time/
        │                   │   └── TimeUtils.java
        │                   ├── tree/
        │                   │   ├── TreeBuilder.java
        │                   │   ├── TreeMapUtils.java
        │                   │   ├── TreeNode.java
        │                   │   ├── TreeSearch.java
        │                   │   ├── TreeSort.java
        │                   │   ├── TreeStats.java
        │                   │   ├── TreeTraversal.java
        │                   │   └── TreeUtils.java
        │                   └── validation/
        │                       ├── EmailValidator.java
        │                       ├── JsonValidator.java
        │                       ├── LuhnValidator.java
        │                       ├── NumberValidator.java
        │                       ├── PasswordValidator.java
        │                       ├── PhoneValidator.java
        │                       ├── UrlValidator.java
        │                       └── UsernameValidator.java
        └── test/
            └── java/
                └── com/
                    └── leratortech/
                        └── toolkit/
                            ├── collection/
                            │   └── CollectionUtilsTest.java
                            ├── function/
                            │   └── FunctionalUtilsTest.java
                            ├── json/
                            │   └── JsonUtilsTest.java
                            ├── os/
                            │   └── OsUtilsTest.java
                            └── tree/
                                ├── CategoryTest.java
                                └── VietnamAdminTreeExample.java
```
---

## Main packages

| Package          | Nội dung nổi bật                                                                 |
|------------------|---------------------------------------------------------------------------------|
| collection       | ArrayUtils, ListUtils, MapUtils, CollectionUtils, SetUtils                       |
| concurrency      | AsyncUtils, ExecutorBuilder, RetryUtils                                          |
| file             | FileUtils                                                                        |
| function         | FunctionalUtils (Supplier, Function, Predicate tiện ích)                        |
| json             | JsonUtils (hỗ trợ Gson & Jackson nhanh gọn)                                      |
| number           | MathUtils, MoneyUtils                                                            |
| os               | OsUtils, OperatingSystem detection                                               |
| security         | SecurityUtils (hash, encrypt, secure random…)                                    |
| text             | TextUtils, ClassUtils, ThreadUtils                                               |
| time             | TimeUtils (LocalDateTime, Duration, ZoneId…)                                     |
| tree             | TreeNode, TreeBuilder, TreeTraversal, TreeSearch, TreeSort, TreeStats…          |
| validation       | EmailValidator, PhoneValidator, UrlValidator, LuhnValidator, PasswordValidator… |

---

## Example usage

Java example:
```java
import io.github.leratortech.toolkit.collection.ListUtils;
import io.github.leratortech.toolkit.json.JsonUtils;

import java.util.List;

public class Example {
    public static void main(String[] args) {
        List<String> names = ListUtils.of("Alice", "Bob", "Charlie");
        String json = JsonUtils.toJson(names);
        System.out.println(json);
    }
}
```