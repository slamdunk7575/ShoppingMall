## ShoppingMall
상품후기(Review) CRUD 개발

## Dev Env
- Kotlin 1.6.2
- Spring Boot 2.7.1
- Spring Data JPA
- H2
- JUnit 5

## How To Run
프로젝트를 Clone or Download 합니다.
<br>터미널을 실행시켜 해당 폴더로 이동합니다.
~~~
$ gradle bootjar
~~~

build/libs 경로에 보면 모든 의존성 라이브러리가 포함된 jar 파일을 확인 할 수 있습니다.
<br>아래 명령어로 Application 을 실행할 수 있습니다.
~~~
nohup java -jar ~~~.jar &
tail -f nohup.out
~~~
