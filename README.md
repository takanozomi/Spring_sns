
# Spring SNS 애플리케이션

## 소개

이 프로젝트는 Spring Boot를 사용하여 구축된 소셜 네트워킹 서비스(SNS) 애플리케이션입니다. 사용자는 회원 가입, 로그인, 게시물 작성, 좋아요, 댓글 작성 등을 할 수 있습니다. 프론트엔드는 HTML 템플릿을 사용하여 구현되었으며, 백엔드는 Spring Boot로 구동됩니다.

## 기능

- **회원 가입 및 인증**: 사용자는 애플리케이션에 회원 가입 및 로그인할 수 있습니다.
- **게시물 작성 및 관리**: 사용자는 새 게시물을 작성하고, 수정하고, 삭제할 수 있습니다.
- **좋아요 및 댓글 작성**: 사용자는 게시물에 좋아요를 누르고 댓글을 작성할 수 있습니다.
- **사용자 프로필**: 각 사용자는 자신의 게시물과 활동을 보여주는 프로필 페이지를 가집니다.

## 프로젝트 구조

### 컨트롤러

- `MemberController.java`: 회원 관련 요청을 처리합니다.

### DTOs (데이터 전송 객체)

- `CommentRequestDTO.java`: 댓글 작성을 위한 데이터 전송 객체입니다.
- `CommentResponseDTO.java`: 댓글 조회를 위한 데이터 전송 객체입니다.
- `MemberDTO.java`: 사용자 데이터를 위한 데이터 전송 객체입니다.
- `PostRequestDTO.java`: 게시물 작성을 위한 데이터 전송 객체입니다.
- `PostResponseDTO.java`: 게시물 조회를 위한 데이터 전송 객체입니다.

### 엔티티

- `LikeEntity.java`: 게시물에 대한 좋아요를 나타내는 엔티티입니다.
- `MemberEntity.java`: 사용자를 나타내는 엔티티입니다.
- `PostEntity.java`: 게시물을 나타내는 엔티티입니다.
- `CommentEntity.java`: 댓글을 나타내는 엔티티입니다.

### 레포지토리

- `CommentRepository.java`: 댓글에 대한 CRUD 작업을 위한 레포지토리입니다.
- `LikeRepository.java`: 좋아요에 대한 CRUD 작업을 위한 레포지토리입니다.
- `MemberRepository.java`: 사용자에 대한 CRUD 작업을 위한 레포지토리입니다.
- `PostRepository.java`: 게시물에 대한 CRUD 작업을 위한 레포지토리입니다.

### 보안

- `WebSecurityConfig.java`: 애플리케이션의 보안 설정을 구성합니다.

### 템플릿

- `UserPost.html`: 사용자 게시물을 표시하기 위한 템플릿입니다.
- `login.html`: 로그인 페이지를 위한 템플릿입니다.
- `mypage.html`: 사용자 프로필 페이지를 위한 템플릿입니다.
- `view.html`: 단일 게시물을 보기 위한 템플릿입니다.
- `write.html`: 새 게시물을 작성하기 위한 템플릿입니다.

### 정적 리소스

- 애플리케이션에서 사용되는 이미지 및 기타 정적 파일을 포함합니다.

## 시작하기

### 사전 요구 사항

- Java 8 이상
- Maven

### 설치

1. 저장소를 클론합니다:
    ```sh
    git clone https://github.com/yourusername/Spring_sns-main.git
    ```
2. 프로젝트 디렉토리로 이동합니다:
    ```sh
    cd Spring_sns-main
    ```
3. Maven을 사용하여 프로젝트를 빌드합니다:
    ```sh
    mvn clean install
    ```
4. 애플리케이션을 실행합니다:
    ```sh
    mvn spring-boot:run
    ```

### 사용법

- 브라우저를 열고 `http://localhost:8080`으로 이동합니다.
- 새 계정을 등록하거나 기존 계정으로 로그인합니다.
- 게시물을 작성하고, 좋아요를 누르고, 댓글을 작성합니다.

## 기여

기여는 환영합니다! 저장소를 포크하고 풀 리퀘스트를 제출해주세요.

## 라이선스

이 프로젝트는 MIT 라이선스로 라이선스가 부여되어 있습니다.
