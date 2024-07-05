# EConfig

### EConfig is a very simple and easy to use config file format

When I was working on a personal project, I wanted to save some variables in a config file, but then I stumbled onto a small problem.
I don't really like the syntax of JSON and setting up YAML was also a bit of work.
So I wrote this small library.

This library works by loading a .ec or .econfig file into memory, so we can access it.

Under the hood the library uses a [TinyMap](https://github.com/intelie/tinymap) to store the variables <br/>
In short a TinyMap is a java map which is optimized to use a lot less memory compared to the normal HashMap <br/>
This makes the library also very lightweight

By using this map the lib currently only supports reading (writing will be added in the future)

### Install
Currently the project is hosted on my own repo, but you can also build it yourself
```xml

<repository>
  <id>harmvdhorst-repo</id>
  <url>https://repo.harmvdhorst.nl/releases</url>
</repository>

<dependency>
    <groupId>nl.harmvdhorst</groupId>
    <artifactId>EConfig</artifactId>
    <version>1.0.0</version>
</dependency>
```

### The syntax
The syntax is inspired by YAML and TOML

```

// this is a comment

// 'test' is the key, 'testvalue123' is the value
test: testvalue123

// EConfig uses something called categories
// to create on type the catgory name between ()
(category1)
    key1: value1
    key2: value2

```
You can read the file by using

```java

EConfig config = new EConfig(new File("test.ec"));

String test = config.getString("test-2");

// or add the category name to get a key in the category

String key2 = config.getString("category1.key2");
```

Currently the lib only supports
``String, Integer, Double, Float, Boolean``
But supported for arrays + maps will be added in the future


## TODO

- Add support for Arrays + Maps
- Modify + save config


## Notice
This library uses [tinymap](https://github.com/intelie/tinymap) which is licenced under the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
