# Spring SNS 애플리케이션

## 소개

이 프로젝트는 Spring Boot를 사용하여 구축된 소셜 네트워킹 서비스(SNS) 애플리케이션입니다. 사용자는 회원 가입, 로그인, 게시물 작성, 좋아요, 댓글 작성 등을 할 수 있습니다. 프론트엔드는 HTML 템플릿을 사용하여 구현되었으며, 백엔드는 Spring Boot로 구현했습니다.

## 기능

- **회원 가입 및 인증**: 사용자는 애플리케이션에 회원 가입 및 로그인
- **게시물 작성 및 관리**: 사용자는 새 게시물을 작성하고, 수정하고, 삭제
- **좋아요 및 댓글 작성**: 사용자는 게시물에 좋아요를 누르고 댓글을 작성
- **사용자 프로필**: 각 사용자는 자신의 게시물과 활동을 보여주는 프로필 페이지를 가집니다.

## 프로젝트 구조

### 컨트롤러

- `MemberController.java`: 회원 관련 요청을 처리

### DTOs (데이터 전송 객체)

- `CommentRequestDTO.java`: 댓글 작성을 위한 데이터 전송 객체
- `CommentResponseDTO.java`: 댓글 조회를 위한 데이터 전송 객체
- `MemberDTO.java`: 사용자 데이터를 위한 데이터 전송 객체
- `PostRequestDTO.java`: 게시물 작성을 위한 데이터 전송 객체.
- `PostResponseDTO.java`: 게시물 조회를 위한 데이터 전송 객체

### 엔티티

- `LikeEntity.java`: 게시물에 대한 좋아요를 나타내는 엔티티
- `MemberEntity.java`: 사용자를 나타내는 엔티티
- `PostEntity.java`: 게시물을 나타내는 엔티티
- `CommentEntity.java`: 댓글을 나타내는 엔티티

### 레포지토리

- `CommentRepository.java`: 댓글에 대한 CRUD 작업을 위한 레포지토리
- `LikeRepository.java`: 좋아요에 대한 CRUD 작업을 위한 레포지토리
- `MemberRepository.java`: 사용자에 대한 CRUD 작업을 위한 레포지토리.
- `PostRepository.java`: 게시물에 대한 CRUD 작업을 위한 레포지토리

### 보안

- `WebSecurityConfig.java`: 애플리케이션의 보안 설정을 구성

### 템플릿

- `UserPost.html`: 사용자 게시물을 표시하기 위한 템플릿
- `login.html`: 로그인 페이지를 위한 템플릿
- `mypage.html`: 사용자 프로필 페이지를 위한 템플릿
- `view.html`: 단일 게시물을 보기 위한 템플릿
- `write.html`: 새 게시물을 작성하기 위한 템플릿

### 정적 리소스

- 애플리케이션에서 사용되는 이미지 및 기타 정적 파일을 포함합니다.

## 아키텍처 설명

Spring SNS 애플리케이션의 아키텍처를 설명입니다. SNS 프로젝트는 계층화된 아키텍처를 사용하여 프론트엔드와 백엔드를 분리하고 각 레이어의 책임을 정의합니다.

#### 1. 프레젠테이션 레이어

프레젠테이션 레이어는 사용자 인터페이스와 직접 상호작용합니다. Spring MVC 프레임워크를 사용하여 HTTP 요청을 처리하고 적절한 뷰를 반환합니다.

- **Controller**: 사용자 요청을 처리하고, 서비스 레이어에 필요한 데이터를 전달하며, 뷰를 반환합니다. 예: `MemberController.java`

#### 2. 서비스 레이어

서비스 레이어는 비즈니스 로직을 포함하고 있습니다. 컨트롤러에서 받은 데이터를 처리하고, 필요한 경우 데이터 접근 레이어와 상호작용합니다.

- **Service**: 비즈니스 로직을 구현하고, 데이터 접근 레이어와 통신합니다. 이 프로젝트에서는 서비스 클래스가 명시적으로 제공되지 않지만, 복잡한 비즈니스 로직이 추가될 경우 서비스 클래스를 구현할 수 있습니다.

#### 3. 데이터 접근 레이어

데이터 접근 레이어는 데이터베이스와의 상호작용을 담당합니다. Spring Data JPA를 사용하여 데이터베이스 CRUD 작업을 수행합니다.

- **Repository**: 엔티티를 데이터베이스에 저장, 수정, 삭제, 조회하는 메서드를 제공합니다. 예: `CommentRepository.java`, `LikeRepository.java`, `MemberRepository.java`, `PostRepository.java`

#### 4. 도메인 레이어

도메인 레이어는 애플리케이션의 핵심 비즈니스 객체를 포함합니다. 엔티티 클래스는 데이터베이스 테이블과 매핑되며, 애플리케이션에서 사용되는 주요 데이터를 정의합니다.

- **Entity**: 데이터베이스 테이블과 매핑되는 클래스입니다. 예: `LikeEntity.java`, `MemberEntity.java`, `PostEntity.java`, `CommentEntity.java`

#### 5. DTO (데이터 전송 객체)

DTO는 컨트롤러와 서비스 또는 서비스와 데이터 접근 레이어 간의 데이터 전송을 단순화합니다. DTO를 사용하여 데이터 모델을 캡슐화하고 필요한 데이터만 전송합니다.

- **DTO**: 데이터 전송 객체는 외부와 데이터를 주고받을 때 사용됩니다. 예: `CommentRequestDTO.java`, `CommentResponseDTO.java`, `MemberDTO.java`, `PostRequestDTO.java`, `PostResponseDTO.java`

#### 6. 보안 레이어

보안 레이어는 애플리케이션의 인증 및 권한 부여를 처리합니다. Spring Security를 사용하여 사용자 인증 및 권한 부여를 구현합니다.

- **Security Configuration**: 보안 설정을 구성하여 애플리케이션의 보안 정책을 정의합니다. 예: `WebSecurityConfig.java`





