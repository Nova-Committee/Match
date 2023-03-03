# Match
Match is a lib that provides a "Match" class for pattern matching.

## Add as a dependency

### Maven

```xml
<repository>
  <id>nova-repository-releases<id/>
  <name>Nova Repository<name/>
  <url>https://maven.nova-committee.cn/releases<url/>
<repository/>
```

```xml
<dependency>
  <groupId>committee.nova<groupId/>
  <artifactId>Match<artifactId/>
  <version>1.0.0<version/>
<dependency/>
```

### Gradle(Groovy)

```groovy
maven {
    url "https://maven.nova-committee.cn/releases"
}
```

```groovy
implementation "committee.nova:Match:1.0.0"
```

### Gradle(Kotlin)

```
maven {
    url = uri("https://maven.nova-committee.cn/releases")
}
```

```
implementation("committee.nova:Match:1.0.0")
```

## Usage

```java
//Common
public void foo(Object obj) {
	if (obj instanceof String) {
		if (obj.equals("match")) return;
	}
	if (obj instanceof Integer) {
		System.out.println(((Integer) obj) + 1);
		return;
	}
	if (obj instanceof Long) {
		System.out.println(((Long) obj) + 1L);
	}
}

//With Match
public void foo(Object obj) {
	Match.from(obj)
		.inCase(String.class, s -> s.equals("match"))
		.brk(Integer.class, i -> System.out.println(i + 1))
		.pass(Long.class, l -> System.out.println(l + 1L))
		.exec();
}
```
