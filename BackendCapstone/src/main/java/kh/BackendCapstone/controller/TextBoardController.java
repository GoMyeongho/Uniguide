package kh.BackendCapstone.controller;

import kh.BackendCapstone.dto.request.TextBoardReqDto;
import kh.BackendCapstone.dto.response.TextBoardListResDto;
import kh.BackendCapstone.dto.response.TextBoardResDto;
import kh.BackendCapstone.service.TextBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Slf4j
public class TextBoardController {
	private final TextBoardService textBoardService;
	
	// 글 작성
	@PostMapping("/create")
	public ResponseEntity<Boolean> createBoard(@RequestBody TextBoardReqDto textBoardReqDto) {
		boolean isSuccess =  textBoardService.saveBoard(textBoardReqDto);
		log.warn("작성 글 생성({}) : {}", isSuccess, textBoardReqDto);
		return ResponseEntity.ok(isSuccess);
	}
	// 글 세부 조회
	@GetMapping("/find/id/{boardId}")
	public ResponseEntity<TextBoardResDto> findBoardById(@PathVariable("boardId") Long boardId) {
		TextBoardResDto rsp = textBoardService.findByBoardId(boardId);
		log.warn("글번호 {} 의 글 내용 조회 : {}", boardId, rsp);
		return ResponseEntity.ok(rsp);
	}
	
	// 카테고리별 전체 글 조회
	@GetMapping("/page/{category}")
	public ResponseEntity<Integer> findBoardPageAll(@PathVariable String category, @RequestParam int size) {
		int pageCount = textBoardService.getBoardPageCount(category, size);
		log.warn("카테고리 : {} 에 대한 페이지당 : {} 페이지 수 : {}", category, size, pageCount);
		return ResponseEntity.ok(pageCount);
	}
	@GetMapping("/find/{category}")
	public ResponseEntity<List<TextBoardListResDto>> findBoardAll(@PathVariable String category, @RequestParam int page, @RequestParam int size, @RequestParam String sort) {
		List<TextBoardListResDto> rsp = textBoardService.findBoardAllByCategory(category, page, size, sort);
		log.warn("카테고리 : {} 전체 글 {}개의 내용 조회 : {}",category, rsp.size(), rsp);
		return ResponseEntity.ok(rsp);
	}
	
	// 카테고리별 제목 검색
	@GetMapping("/page/title/{category}/{title}")
	public ResponseEntity<Integer> findBoardPageByTitle(@PathVariable String category, @PathVariable String title, @RequestParam int size) {
		int pageCount = textBoardService.getBoardPageCountByTitle(category, title, size);
		log.warn("카테고리 : {} 의 제목 검색 : {} 에대한 페이지당 : {} 페이지 수 : {}", category, title, size, pageCount);
		return ResponseEntity.ok(pageCount);
	}
	@GetMapping("/find/title/{category}/{title}")
	public ResponseEntity<List<TextBoardListResDto>> findBoardByTitle(@PathVariable String category, @PathVariable String title,
	                                                              @RequestParam int page, @RequestParam int size, @RequestParam String sort) {
		List<TextBoardListResDto> rsp = textBoardService.findBoardByTitle(category, title, page, size, sort);
		log.warn("카테고리 : {} 글 제목 : {} ,로 검색한 {}개의 내용 조회 : {}", category, title, rsp.size(), rsp);
		return ResponseEntity.ok(rsp);
	}
	
	// 카테고리별 닉네임 검색
	@GetMapping("/page/nickName/{category}/{nickName}")
	public ResponseEntity<Integer> findBoardByNickName(@PathVariable String category, @PathVariable String nickName, @RequestParam int size) {
		int pageCount = textBoardService.getBoardPageCountByNickName(category, nickName, size);
		log.warn("카테고리 : {} 의 작성자 검색 : {} 에대한 페이지당 : {} 페이지 수 : {}", category, nickName, size, pageCount);
		return ResponseEntity.ok(pageCount);
	}
	@GetMapping("/find/nickName/{category}/{nickName}")
	public ResponseEntity<List<TextBoardListResDto>> findBoardByNickName(@PathVariable String category, @PathVariable String nickName,
	                                                                  @RequestParam int page, @RequestParam int size, @RequestParam String sort) {
		List<TextBoardListResDto> rsp = textBoardService.findBoardByNickName(category, nickName, page, size, sort);
		log.warn("카테고리 : {} 글 작성자 : {} ,로 검색한 {}개의 내용 조회 : {}", category, nickName, rsp.size(), rsp);
		return ResponseEntity.ok(rsp);
	}
	
	// 카테고리별 제목 및 내용 검색
	@GetMapping("page/titleOrContent/{category}/{keyword}")
	public ResponseEntity<Integer> findBoardPageByTitleOrContent(@PathVariable String category, @PathVariable String keyword, @RequestParam int size) {
		int pageCount = textBoardService.getBoardPageCountByTitleAndContent(category, keyword, size);
		log.warn("카테고리 : {} 제목과 내용 검색 : {} 에 대한 페이지당 : {} 페이지 수 : {}", category, keyword, size, pageCount);
		return ResponseEntity.ok(pageCount);
	}
	@GetMapping("/find/titleOrContent/{category}/{keyword}")
	public ResponseEntity<List<TextBoardListResDto>> findBoardByTitleOrContent(@PathVariable String category, @PathVariable String keyword,
	                                                                       @RequestParam int size, @RequestParam int page, @RequestParam String sort) {
		List<TextBoardListResDto> rsp = textBoardService.findBoardByTitleAndContent(category ,keyword, size, page, sort);
		log.warn("카테고리 : {} 제목과 내용 검색 : {}, 페이지당 {} 인 페이지 {} 의 검색된 개수 : {} 결과 : {}", category, keyword, size, page, rsp.size(),rsp);
		return ResponseEntity.ok(rsp);
	}
	
	// 카테고리별 회원 검색
	@GetMapping("/page/member/{category}/{email}")
	public ResponseEntity<Integer> findBoardByMember(@PathVariable String category, @PathVariable String email, @RequestParam int size) {
		int pageCount = textBoardService.getBoardPageCountByMember(category, email, size);
		log.warn("카테고리 : {} 의 회원 검색 : {} 에대한 페이지당 : {} 페이지 수 : {}", category, email, size, pageCount);
		return ResponseEntity.ok(pageCount);
	}
	@GetMapping("/find/member/{category}/{email}")
	public ResponseEntity<List<TextBoardListResDto>> findBoardByMember(@PathVariable String category, @PathVariable String email,
	                                                                     @RequestParam int page, @RequestParam int size, @RequestParam String sort) {
		List<TextBoardListResDto> rsp = textBoardService.findBoardByMember(category, email, page, size, sort);
		log.warn("카테고리 : {} 글 회원 : {} ,로 검색한 {}개의 내용 조회 : {}", category, email, rsp.size(), rsp);
		return ResponseEntity.ok(rsp);
	}
	
	@PostMapping("/update/{boardId}")
	public ResponseEntity<Boolean> updateBoard(@RequestBody TextBoardReqDto textBoardReqDto, @PathVariable Long boardId) {
		boolean isSuccess = textBoardService.updateBoard(textBoardReqDto, boardId);
		log.warn("글번호 : {} 에대한 글 수정 {} 항목 : {}", boardId, isSuccess, textBoardReqDto);
		return ResponseEntity.ok(isSuccess);
	}
	@PostMapping("/delete/{boardId}/{email}")
	public ResponseEntity<Boolean> deleteBoard(@PathVariable("boardId") Long boardId, @PathVariable String email) {
		boolean isSuccess = textBoardService.deleteBoard(email,boardId);
		log.warn("email : {}의 글번호 : {} 삭제 요청 : {}", email, boardId, isSuccess);
		return ResponseEntity.ok(isSuccess);
	}
	
}