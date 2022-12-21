package com.threescoops.vo;

//import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter  @Getter
@NoArgsConstructor
@AllArgsConstructor
public class RepBoard {
	private int level;
	private int boardNo;
	private int parentNo;
	private String boardTitle;
	private Customer boardC;
	
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private java.util.Date boardDt;
	private int boardViewCnt;
	private String boardContent;
	
	
	@Override
	public String toString() {
		return "RepBoard [level=" + level + ", boardNo=" + boardNo + ", parentNo=" + parentNo + ", boardTitle="
				+ boardTitle + ", boardC=" + boardC.getId() + ", boardDt=" + boardDt + ", boardViewCnt=" + boardViewCnt
				+ ", boardContent=" + boardContent + "]";
	}
	
	
	
	
	
}
