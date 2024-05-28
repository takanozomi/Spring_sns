package kr.co.inhatc.inhatc;

import jakarta.servlet.http.HttpSession;
import kr.co.inhatc.inhatc.dto.CommentRequestDTO;
import kr.co.inhatc.inhatc.dto.CommentResponseDTO;
import kr.co.inhatc.inhatc.dto.MemberDTO;
import kr.co.inhatc.inhatc.dto.PostRequestDTO;
import kr.co.inhatc.inhatc.dto.PostResponseDTO;
import kr.co.inhatc.inhatc.entity.PostEntity;
import kr.co.inhatc.inhatc.entity.commentEntity;
import kr.co.inhatc.inhatc.exception.CustomException;
import kr.co.inhatc.inhatc.service.commentService;
import kr.co.inhatc.inhatc.service.MemberService;
import kr.co.inhatc.inhatc.service.PostService;

import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.core.io.UrlResource;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PostService postService;
    private final commentService commentservice;
    private final String path = "C:\\Users\\82102\\OneDrive\\바탕 화면\\spring test\\inhatc\\src\\main\\java\\kr\\";

    @GetMapping("/")
    public String home() {
        return "login";
    }

    @GetMapping("/member/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        memberService.save(memberDTO);
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/member/write")
    public String write() {
        return "write";
    }

    @GetMapping("/UserPost")
    public String UserPost() {
        return "UserPost";
    }

    @GetMapping("/member/UserPost")
    public ResponseEntity UserPost(Model model, HttpSession session) {
        String loginEmail = (String) session.getAttribute("loginEmail");
        if (loginEmail == null) {
            return ResponseEntity.badRequest().body("로그인이 필요한 서비스입니다.");
        }
        List<PostResponseDTO> userPosts = postService.findByUserID(loginEmail);

        System.out.println(userPosts);
        return ResponseEntity.ok(userPosts);
    }

    @PostMapping("/member/delete")
    public String deleteContent(Long id, HttpSession session, Model model) {
        String loginEmail = (String) session.getAttribute("loginEmail");
        if (loginEmail == null) {
            model.addAttribute("error", "로그인이 필요한 서비스입니다.");
        }
        postService.delete(id);
        return "view";
    }

    @PostMapping("/member/login")
    public String login(@RequestParam String memberEmail, @RequestParam String memberPassword, Model model,
            HttpSession session) {
        try {
            // 로그인 시도
            MemberDTO loggedInMember = memberService.login(memberEmail, memberPassword);

            // 세션에 로그인 정보 저장
            session.setAttribute("loginEmail", loggedInMember.getMemberEmail());
            model.addAttribute("loggedInMember", loggedInMember);
            System.out.println(model);

            // 로그인 성공 시 메인 페이지로 리다이렉트
            return "redirect:/member/view";
        } catch (RuntimeException e) {
            // 로그인 실패 시 에러 메시지 추가
            model.addAttribute("error", "이메일 또는 비밀번호가 올바르지 않습니다.");
            return "login"; // 로그인 페이지로 이동
        }
    }

    @PostMapping("/member/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }

    @PostMapping("/api/posts")
    public ResponseEntity<String> createPost(@RequestParam("content") String content,
            @RequestParam(value = "file", required = false) MultipartFile file,
            HttpSession session) {
        String loginEmail = (String) session.getAttribute("loginEmail");
        if (loginEmail == null) {
            return ResponseEntity.badRequest().body("로그인이 필요한 서비스입니다.");
        }

        PostRequestDTO params = new PostRequestDTO(content, loginEmail, 'N', null, file, 0);

        if (file != null && !file.isEmpty()) {
            try {
                String imgPath = postService.imgupload(file, loginEmail);
                params.setImgsource(imgPath);
            } catch (IOException e) {
                return ResponseEntity.status(500).body("이미지 업로드 실패: " + e.getMessage());
            }
        }

        postService.save(params);
        return ResponseEntity.ok("게시물이 성공적으로 저장되었습니다.");
    }

    @PostMapping("/api/comments")
    public ResponseEntity<CommentResponseDTO> addComment(@RequestBody CommentRequestDTO requestDTO,
            HttpSession session) {
        // 세션에서 사용자의 이메일 가져오기
        String userEmail = (String) session.getAttribute("loginEmail");

        // 요청 DTO에 사용자의 이메일 설정
        requestDTO.setUser(userEmail);

        // 댓글 추가 서비스 호출
        CommentResponseDTO comment = commentservice.addComment(requestDTO);

        // 응답
        return ResponseEntity.ok(comment);
    }

    // 게시글의 모든 댓글 조회
    @GetMapping("/api/comments/{postId}")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentResponseDTO> comments = commentservice.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/view")
    public ResponseEntity<List<PostResponseDTO>> postList() {
        // 삭제되지 않은 게시물들을 찾아 리스트로 반환
        List<PostResponseDTO> posts = postService.findAllByDeleteYn('N'); // 'N'은 삭제되지 않은 게시물
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable Long id) {
        PostResponseDTO post = postService.findById(id);
        return ResponseEntity.ok(post);
    }

    // Java Spring Controller 예시
    @GetMapping("/member/view")
    public String viewMember(Model model, HttpSession session) {
        String loginEmail = (String) session.getAttribute("loginEmail");
        if (loginEmail == null) {
            model.addAttribute("error", "로그인이 필요한 서비스입니다.");
            return "login";
        }
        try {
            MemberDTO memberDTO = memberService.getMemberByEmail(loginEmail);
            if (memberDTO != null) {
                model.addAttribute("memberEmail", memberDTO.getMemberEmail());
            } else {
                model.addAttribute("error", "Member not found.");
                return "login"; // Handling the case where no member details are found
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error retrieving member details.");
            return "login"; // Handling any other exceptions that might occur
        }
        return "view"; // 로그인 정보를 포함한 뷰 페이지
    }

    @GetMapping("/member/mypage")
    public String mypage(Model model, HttpSession session) {
        // Retrieve the login email from the session
        String loginEmail = (String) session.getAttribute("loginEmail");

        // Check if the user is logged in
        if (loginEmail == null) {
            return "redirect:/login"; // Redirect to login page if not logged in
        }

        // Fetch member details using the email
        try {
            MemberDTO memberDTO = memberService.getMemberByEmail(loginEmail);
            if (memberDTO != null) {
                model.addAttribute("loggedInMember", memberDTO); // Add the whole DTO for flexibility
            } else {
                model.addAttribute("error", "Member not found.");
                return "mypage"; // Handling the case where no member details are found
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error retrieving member details.");
            return "mypage"; // Handling any other exceptions that might occur
        }

        return "mypage";
    }

    @GetMapping("/Userimgsource")
    public ResponseEntity<Resource> imagesource(@RequestParam String email) {
        try {
            Path path = Paths.get("C:\\Users\\82102\\OneDrive\\바탕 화면\\spring test\\inhatc\\src\\main\\java\\kr\\"
                    + email + "\\profile.png");
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"" + resource.getFilename() + "\"")
                        .contentType(MediaType.IMAGE_PNG)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /*
     * 포스트 이미지를 가져오는 컨트롤러
     */
    @GetMapping("/image/{postId}")
    public ResponseEntity<Resource> postImage(@PathVariable Long postId) {
        try {
            // 게시글 ID를 통해 imgsource 경로를 조회
            PostResponseDTO post = postService.findById(postId);
            String filename = post.getImgsource();

            Path file = Paths.get("path/to/images/directory").resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/mypage/imgsource")
    public ResponseEntity<Resource> getProfileImage(HttpSession session) {
        String loginEmail = (String) session.getAttribute("loginEmail");
        if (loginEmail == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Respond with 401 Unauthorized if no login
        }

        try {
            Path path = Paths.get("C:\\Users\\82102\\OneDrive\\바탕 화면\\spring test\\inhatc\\src\\main\\java\\kr\\"
                    + loginEmail + "\\profile.png");
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"" + resource.getFilename() + "\"")
                        .contentType(MediaType.IMAGE_PNG)
                        .body(resource);

            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/member/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, HttpSession session) {
        try {
            String loginEmail = (String) session.getAttribute("loginEmail"); // 세션에서 로그인 이메일 가져오기
            if (loginEmail == null) {
                return "업로드 실패: 로그인 정보가 없습니다.";
            }
            memberService.storeFile(file, loginEmail);
            return "mypage";
        } catch (Exception e) {
            return "파일 업로드 실패: " + e.getMessage();
        }
    }

    @PostMapping("/member/contentimg")
    public String uploadImgFile(@RequestParam("file") MultipartFile file, HttpSession session) {
        try {
            String loginEmail = (String) session.getAttribute("loginEmail");
            if (loginEmail == null) {
                return "login";
            }
            String result = postService.imgupload(file, loginEmail);
            return result.equals("성공적으로 업로드 완료") ? "mypage" : result;

        } catch (Exception e) {
            return "fail: " + e.getMessage();
        }
    }

    @PostMapping("/togglelove")
    public ResponseEntity<String> toggleLove(@RequestParam("postId") Long postId, @RequestParam("email") String email) {
        try {
            boolean liked = postService.toggleLove(postId, email);
            return ResponseEntity.ok(liked ? "liked" : "unliked");
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @GetMapping("/gettingSession")
    @ResponseBody
    public Map<String, String> getSession(HttpSession session) {
        String loginEmail = (String) session.getAttribute("loginEmail");
        Map<String, String> response = new HashMap<>();
        response.put("loginEmail", loginEmail != null ? loginEmail : "No session found");
        return response;
    }

}
