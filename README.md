# MoviePosterCrawler 🎬
- 영화 포스터 이미지 수집기.   
- 영화 보는 거 좋아해서, 영화관련 프로젝트를 해볼까 시작한게 우선 포스터를 수집해보자라는 생각에 만들게 되었음 
- 예전에 [ReviewHO](https://github.com/pasudo123/Review-HO) 라는 프로젝트를 수행하였음. 영화리뷰에 대한 긍부정 판단이긴 한데.. 결론은 영화감상이 취미.

### 개발환경
- java 1.8.0_181
- Eclipse Version 2018-09
- [HeidiSQL 9.5](https://www.heidisql.com/download.php?download=installer)
- [jsoup 1.11.3](https://jsoup.org/)
- [mysql connector java 8.0](https://dev.mysql.com/downloads/connector/j/)
- [spring-boot 1.5.18](https://docs.spring.io/spring-boot/docs/1.5.18.RELEASE/reference/htmlsingle/#boot-documentation)

### 실행
```java
private static final int MIN_ID = 4001;		// ID 값 시작점
private static final int MAX_ID = 10000;	// ID 값 종료점
private static final int UNIT = 100;		// 체크 단위

MovieLauncher launcher = new MovieLauncher();
launcher.start();
```
위의 MIN_ID 와 MAX_ID 의 값을 변경해가면서, URL 에 존재하는 포스팅의 이미지를 가져오면 됨.

```java
public interface MovieKeeper {

  public static final String SAVE_DIRECTORY_PATH = "E:\\doubler\\poster\\";
  public static final String IMAGE_EXT_PNG = "png";

  // Method1();
  // Method2();
  
}
```
스레드 풀 구현하였고, 이후 처리로직은 따로 콜백메소드에서 수행되도록 하였음.

### 진행하려는 것
- 영화 포스터로 Memory Game 만들기 (진행중)

### 읽고 정리할 것들
한번 읽어보기  
- https://d2.naver.com/helloworld/1469717
- https://www.baeldung.com/java-executor-service-tutorial
- https://jmchung.github.io/blog/2013/10/25/how-to-solve-jsoup-does-not-get-complete-html-document/
- https://inyl.github.io/programming/2017/12/02/timeout.html

### ISSUE
- Oracle Sequence 와 MySQL Auto_increment
- MySQL Server TIMEZONE 설정 관련
- MySQL Batch (addBatch -> executeBatch -> clearBatch)
