package kh.BackendCapstone.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kh.BackendCapstone.constant.Active;
import kh.BackendCapstone.entity.Member;
import kh.BackendCapstone.entity.Permission;
import kh.BackendCapstone.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FirebaseUploadService {

	private final RestTemplate restTemplate;
	private final MemberService memberService;
	private final PermissionRepository permissionRepository;
	
	/*
    fileUpload 사용 방법 : Flask 부분을 다운받고 구글 드라이브의 firebase 파일 안에  ipsi-firebase-adminsdk.json을 app.py가 있는 디렉토리에 놓는다.
    FirebaseUploadService 에서 handleFileUpload를 사용할 컨트롤러에서 부른다.
    file은 프론트에서 파일을 받아와서 넣어주면 되고(리엑트에서 example/FileUploaderExample 부분을 보면 됩니다.), folderPath는 파이어 베이스 안에 저장될 경로 기본적으로 firebase/ 폴더안에서 시작
    spring 과 flask를 킨다.
    실행한다.
    */
	public String handleFileUpload(MultipartFile file, String folderPath) {
		// Flask API로 요청 보내기
		String flaskUrl = "https://6704-39-117-57-245.ngrok-free.app/spring/upload/firebase";

		// 파일을 포함한 멀티파트 데이터 전송을 위한 HttpHeaders 설정
		HttpHeaders headers = new HttpHeaders();

		// Multipart 파일과 폴더 경로를 전송하는 방식으로 변경
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", file.getResource());
		body.add("folderPath", "firebase/" + folderPath);
		body.add("fileName", "");

		// HttpEntity로 요청 본문 만들기
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

		try {
			// Flask API로 POST 요청 보내기
			ResponseEntity<String> response = restTemplate.exchange(flaskUrl, HttpMethod.POST, entity, String.class);
			log.warn("플라스크 통신으로 인한 결과 : {}",response);
			// Flask 서버의 응답 처리
			return response.getBody();
		} catch (Exception e) {
			log.error("Flask 서버와의 통신 중 오류 발생: ", e);
			return "파일 업로드 중 오류가 발생했습니다.";
		}
	}
	public String handleFileUploadWithName(MultipartFile file, String folderPath, String fileName) {
		// Flask API로 요청 보내기
		String flaskUrl = "https://6704-39-117-57-245.ngrok-free.app/spring/upload/firebase";

		// 파일을 포함한 멀티파트 데이터 전송을 위한 HttpHeaders 설정
		HttpHeaders headers = new HttpHeaders();

		// Multipart 파일과 폴더 경로를 전송하는 방식으로 변경
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", file.getResource());
		body.add("folderPath", "firebase/" + folderPath);
		body.add("fileName", fileName);

		// HttpEntity로 요청 본문 만들기
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

		try {
			// Flask API로 POST 요청 보내기
			ResponseEntity<String> response = restTemplate.exchange(flaskUrl, HttpMethod.POST, entity, String.class);
			log.warn("플라스크 통신으로 인한 결과 : {}", response);
			// Flask 서버의 응답 처리
			return response.getBody();
		} catch (Exception e) {
			log.error("Flask 서버와의 통신 중 오류 발생: ", e);
			return "파일 업로드 중 오류가 발생했습니다.";
		}
	}
	@Transactional
	public String getNewPermission(MultipartFile file, String folderPath, String token) {
		try {
			// 토큰을 통해 Member 객체를 가져옴
			Member member = memberService.convertTokenToEntity(token);
			
			// 폴더 경로를 동적으로 설정
			folderPath = getFolderPath(folderPath, member);
			String fileName = getFileName(member);
			
			// Flask API에 파일 업로드 요청을 보내고 응답 받음
			String rspFlask = handleFileUploadWithName(file, folderPath, fileName);
			
			// 응답을 JSON 형식으로 파싱하여 URL 추출
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonResponse = objectMapper.readTree(rspFlask);
			String message = jsonResponse.get("message").asText();
			String error = jsonResponse.get("error").asText();
			String url = jsonResponse.get("url").asText();
			
			// 성공적인 파일 업로드 확인
			if ("File uploaded successfully".equals(message)) {
				// 파일 업로드가 성공하면 Permission 객체 생성
				Permission permission = new Permission();
				permission.setMember(member);
				permission.setActive(Active.INACTIVE);
				permission.setPermissionUrl(url); // URL 설정
				permissionRepository.save(permission);
				return "파일을 업로드 하는데 성공했습니다.";
			} else {
				return "파일을 업로드 하는데 실패했습니다.: " + message + "or" + error;
			}
		} catch (Exception e) {
			log.error("파일 업로드 중 오류 발생: ", e);
			return "파일 업로드 중 오류가 발생했습니다.";
		}
	}
	
	public String getFolderPath(String folderPath, Member member) {
		folderPath += "/" + member.getMemberId();
		return folderPath;
	}
	
	public String getFileName(Member member) {
		int permissionSize = permissionRepository.countAllByMember(member);
		return member.getMemberId() + "_" + permissionSize;
	}
	
}
