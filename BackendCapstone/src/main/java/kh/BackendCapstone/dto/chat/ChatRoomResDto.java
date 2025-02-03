package kh.BackendCapstone.dto.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kh.BackendCapstone.constant.ChatRoomType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@Slf4j
@ToString
@NoArgsConstructor
public class ChatRoomResDto {
    private String roomId;
    private String name;
    private LocalDateTime regDate;
    private ChatRoomType roomType = ChatRoomType.GROUP; // 기본값 설정
    private Integer personCnt; // 참여 가능 인원 필드 추가

    @JsonIgnore // 웹소켓 세션의 직렬화 방지
    private Set<WebSocketSession> sessions = Collections.newSetFromMap(new ConcurrentHashMap<>());

    //세션 수가 0인지 확인하는 메서드
    public boolean isSessionEmpty() {
        return this.sessions == null || this.sessions.isEmpty();
    }

    @Builder    // 빌더 패턴 적용
    public ChatRoomResDto(String roomId, String name, LocalDateTime regDate, Integer personCnt) {
        this.roomId = roomId;
        this.name = name;
        this.regDate = regDate;
        this.personCnt = personCnt;
        this.sessions = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }
}
