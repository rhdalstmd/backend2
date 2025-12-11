# 스프링 To-Do 게시판

스프링 부트를 사용하여 개발한 To-Do 게시판 웹 애플리케이션입니다.

## 주요 기능

### 📝 게시글 CRUD
- **생성(Create)**: 제목, 내용, 작성자를 입력하여 새로운 To-Do 작성
- **조회(Read)**: 전체 목록 조회 및 상세 페이지 조회 (조회수 자동 증가)
- **수정(Update)**: 기존 게시글의 제목과 내용 수정
- **삭제(Delete)**: 게시글 삭제 (연관된 댓글도 함께 삭제)

### 🔍 검색 기능
- 제목 또는 내용으로 검색
- 실시간 검색 결과 표시

### 📄 페이징 기능
- 페이지당 10개 게시글 표시
- 이전/다음 페이지 네비게이션
- 현재 페이지 및 전체 페이지 수 표시

### 🎯 필터링 기능
- 전체 게시글 보기
- 미완료 게시글만 보기
- 완료된 게시글만 보기

### ✅ 완료 상태 관리
- 게시글별 완료/미완료 상태 토글
- 완료된 게시글은 시각적으로 구분 (취소선, 배경색 변경)

### 💬 댓글 기능
- 게시글에 댓글 작성
- 댓글 삭제
- 댓글 개수 표시

### 📊 통계 정보
- 조회수 추적
- 댓글 개수 표시
- 작성일/수정일 자동 기록

## 기술 스택

### 백엔드
- **Spring Boot 2.7.18**: 웹 애플리케이션 프레임워크
- **Spring Data JPA**: ORM 및 데이터 접근 계층
- **Hibernate**: JPA 구현체
- **H2 Database**: 내장 데이터베이스 (개발 환경)
- **Lombok**: 보일러플레이트 코드 자동 생성
- **Bean Validation**: 입력 검증

### 프론트엔드
- **Thymeleaf**: 서버 사이드 템플릿 엔진
- **HTML5/CSS3**: 마크업 및 스타일링
- **JavaScript**: 클라이언트 사이드 스크립트

### 빌드 도구
- **Maven**: 프로젝트 관리 및 빌드

## 프로젝트 구조

```
spring-todo-board/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── todoboard/
│   │   │           ├── TodoBoardApplication.java      # 메인 애플리케이션 클래스
│   │   │           ├── controller/                     # 컨트롤러 계층
│   │   │           │   ├── TodoController.java        # To-Do 컨트롤러
│   │   │           │   ├── CommentController.java     # 댓글 컨트롤러
│   │   │           │   └── HomeController.java        # 홈 컨트롤러
│   │   │           ├── service/                        # 서비스 계층
│   │   │           │   ├── TodoService.java           # To-Do 비즈니스 로직
│   │   │           │   └── CommentService.java        # 댓글 비즈니스 로직
│   │   │           ├── repository/                     # 데이터 접근 계층
│   │   │           │   ├── TodoRepository.java        # To-Do Repository
│   │   │           │   └── CommentRepository.java     # 댓글 Repository
│   │   │           ├── entity/                         # 엔티티 클래스
│   │   │           │   ├── BaseEntity.java            # 공통 엔티티 (생성일/수정일)
│   │   │           │   ├── Todo.java                  # To-Do 엔티티
│   │   │           │   └── Comment.java               # 댓글 엔티티
│   │   │           └── dto/                            # DTO 클래스
│   │   │               ├── TodoRequest.java           # To-Do 요청 DTO
│   │   │               ├── TodoResponse.java          # To-Do 응답 DTO
│   │   │               ├── CommentRequest.java        # 댓글 요청 DTO
│   │   │               └── CommentResponse.java       # 댓글 응답 DTO
│   │   └── resources/
│   │       ├── application.properties                  # 애플리케이션 설정
│   │       └── templates/                              # Thymeleaf 템플릿
│   │           └── todos/
│   │               ├── list.html                      # 목록 페이지
│   │               ├── detail.html                    # 상세 페이지
│   │               └── form.html                      # 작성/수정 폼
│   └── test/
│       └── java/
└── pom.xml                                             # Maven 설정 파일
```

## 실행 방법

### 1. 사전 요구사항
- Java 11 이상
- Maven 3.6 이상

### 2. 빌드
```bash
mvn clean package
```

### 3. 실행
```bash
java -jar target/spring-todo-board-1.0.0.jar
```

### 4. 접속
브라우저에서 `http://localhost:8080` 접속

### 5. H2 콘솔 접속 (선택사항)
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:todoboard`
- Username: `sa`
- Password: (빈 값)

## 주요 코드 특징

### 1. 박스 주석
모든 주요 클래스와 메서드에는 박스 주석이 포함되어 있습니다.

```java
/**
 * ========================================
 * To-Do 서비스 클래스
 * ========================================
 * - To-Do 관련 비즈니스 로직 처리
 * - 트랜잭션 관리
 */
```

### 2. 한줄 주석
주요 코드 라인에는 설명 주석이 포함되어 있습니다.

```java
// 조회수 증가
todo.increaseViewCount();

// 변경 감지 (Dirty Checking)를 통한 업데이트
todo.update(request.getTitle(), request.getContent());
```

### 3. 계층형 아키텍처
- **Controller**: HTTP 요청 처리 및 뷰 반환
- **Service**: 비즈니스 로직 처리 및 트랜잭션 관리
- **Repository**: 데이터베이스 접근
- **Entity**: 도메인 모델
- **DTO**: 데이터 전송 객체

### 4. JPA Auditing
생성일과 수정일을 자동으로 관리합니다.

```java
@CreatedDate
@Column(updatable = false)
private LocalDateTime createdAt;

@LastModifiedDate
private LocalDateTime updatedAt;
```

### 5. 입력 검증
Bean Validation을 사용한 입력 검증

```java
@NotBlank(message = "제목을 입력해주세요.")
@Size(max = 200, message = "제목은 200자 이내로 입력해주세요.")
private String title;
```

## 데이터베이스 스키마

### todos 테이블
| 컬럼명 | 타입 | 설명 |
|--------|------|------|
| id | BIGINT | 기본키 (자동 증가) |
| title | VARCHAR(200) | 제목 |
| content | TEXT | 내용 |
| author | VARCHAR(50) | 작성자 |
| completed | BOOLEAN | 완료 여부 |
| view_count | BIGINT | 조회수 |
| created_at | TIMESTAMP | 생성일 |
| updated_at | TIMESTAMP | 수정일 |

### comments 테이블
| 컬럼명 | 타입 | 설명 |
|--------|------|------|
| id | BIGINT | 기본키 (자동 증가) |
| content | VARCHAR(500) | 댓글 내용 |
| author | VARCHAR(50) | 작성자 |
| todo_id | BIGINT | To-Do 외래키 |
| created_at | TIMESTAMP | 생성일 |
| updated_at | TIMESTAMP | 수정일 |

## 화면 구성

### 1. 목록 페이지 (`/todos`)
- 전체 게시글 목록 표시
- 검색 기능
- 필터링 기능 (전체/미완료/완료)
- 페이징 네비게이션
- 새 글 작성 버튼

### 2. 상세 페이지 (`/todos/{id}`)
- 게시글 상세 정보 표시
- 완료 상태 토글 버튼
- 수정/삭제 버튼
- 댓글 목록 및 작성 폼

### 3. 작성/수정 폼 (`/todos/new`, `/todos/{id}/edit`)
- 제목, 내용, 작성자 입력 필드
- 입력 검증 및 에러 메시지 표시
- 취소 버튼

## 라이선스
이 프로젝트는 학습 목적으로 제작되었습니다.

## 작성자
스프링 To-Do 게시판 개발팀

## 버전
1.0.0
