package kh.BackendCapstone.dto.response;


import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class CommentResDto {
	private String email;
	private Long boardId;
	private Long commentId;
	private String content;
	private LocalDateTime regDate;
}