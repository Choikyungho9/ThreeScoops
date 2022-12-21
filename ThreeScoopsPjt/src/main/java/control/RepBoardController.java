package control;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.threescoops.dto.PageBean;
import com.threescoops.exception.AddException;
import com.threescoops.exception.FindException;
import com.threescoops.exception.ModifyException;
import com.threescoops.exception.RemoveException;
import com.threescoops.service.RepBoardService;
import com.threescoops.vo.Customer;
import com.threescoops.vo.RepBoard;

//@Controller
public class RepBoardController {
	@Autowired
	private RepBoardService service;
	
	@PostMapping(value = "write", produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<?> write(HttpSession session, 
			@RequestPart   List<MultipartFile> f, //multiple속성인 경우 List타입으로 매핑 
			@RequestPart   MultipartFile fImg,
			RepBoard rb) {
//		String loginedId = (String)session.getAttribute("loginedId");
		String loginedId = "id1"; //샘플테스트
		Customer c = new Customer();
		
//		c.setId(loginedId);
		c.setId("id1");
		rb.setBoardC(c);
		try {
			int boardNo = service.writeBoard(rb);
			
			//파일업로드(f, fImg)작업
			//TODO
			com.threescoops.util.Attach.upload(boardNo, f);
			com.threescoops.util.Attach.upload(boardNo, fImg);
			
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(AddException e) {
			return new ResponseEntity<>(e.getMessage(),
					                    HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@GetMapping(value = "boardlist", produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<?> list(int currentPage){
		try {
			PageBean<RepBoard> pb = service.getPageBean(currentPage);//service.findAll();
			return new ResponseEntity<>(pb, HttpStatus.OK);
		} catch (FindException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	@GetMapping("boardlist")
//	@ResponseBody
//	public ResponseEntity<?> list(){
//		try {
//			List<RepBoard> pb = service.findAll();//service.findAll();
//			return new ResponseEntity<>(pb, HttpStatus.OK);
//		} catch (FindException e) {
//			e.printStackTrace();
//			return new ResponseEntity<>(e.getMessage(), 
//					HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	@GetMapping(value = "boarddetail", produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<?> detail(int boardNo){
		try {
			RepBoard rb = service.findByBoardNo(boardNo);
			//글번호별 첨부파일이름들 응답 
			List<String>fileNames = new ArrayList<>();
			String saveDirectory = "c:\\files";
			File dir = new File(saveDirectory); 
			String[] allFileNames = dir.list();
			for(String fn: allFileNames) {
				if(fn.startsWith(boardNo + "_")) {
					fileNames.add(fn);
				}
			}
			Map<String, Object> map = new HashMap<>();
			map.put("rb", rb);
			map.put("fileNames", fileNames);			
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (FindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	
	@PostMapping(value = "boardmodify", produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<?> modify(
			@RequestPart   List<MultipartFile> f, //multiple속성인 경우 List타입으로 매핑 
			@RequestPart   MultipartFile fImg,
			int boardNo, 
            String boardTitle, 
            String boardContent, 
            HttpSession session){
		RepBoard rb = new RepBoard();
		rb.setBoardNo(boardNo);
		rb.setBoardTitle(boardTitle);
		rb.setBoardContent(boardContent);
		
		Customer c = new Customer();
//		c.setId((String)session.getAttribute("loginedId"));
		c.setId("id1");
		rb.setBoardC(c);		
		try {
			service.modify(rb);
			
			boolean flag = false; //첨부안된 경우
			for(MultipartFile mf: f) {
				String orignName = mf.getOriginalFilename();
				long fileLength = mf.getSize();
				if(fileLength > 0 && !"".equals(orignName)) {
					flag = true;
					break;
				}
			}
			
			String orignImgName = fImg.getOriginalFilename();
			long imgFileLength = fImg.getSize();
			if(imgFileLength > 0 && !"".equals(orignImgName)) {
				flag = true;
			}
			
			if(flag) { //첨부된 경우
				//기존 첨부파일들을 모두 삭제 (c:\\files경로에서 boardNo값으로 시작하는 파일들을 찾아 삭제 )
				String saveDirectory = "c:\\files";
				File dir = new File(saveDirectory);
				File[] files = dir.listFiles();
				for(File f1: files) {
					if(f1.getName().startsWith(boardNo+"_")) {
						f1.delete();
					}
				}
				
				//새로운 첨부파일 저장
				//파일업로드(f, fImg)작업
				//TODO
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (ModifyException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(),  HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "boardremove", produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<?> remove(int boardNo){
		try {
			service.remove(boardNo);
			//기존 첨부파일들을 모두 삭제 (c:\\files경로에서 boardNo값으로 시작하는 파일들을 찾아 삭제 )
			String saveDirectory = "c:\\files";
			File dir = new File(saveDirectory);
			File[] files = dir.listFiles();
			for(File f1: files) {
				if(f1.getName().startsWith(boardNo+"_")) {
					f1.delete();
				}
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (RemoveException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(),  HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping(value = "reply", produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<?> reply(HttpSession session, RepBoard rb){
		try {
			System.out.println("reply컨트롤로로 들어옴");
			Customer c = new Customer();
//			c.setId((String)session.getAttribute("loginedId"));
			c.setId("id1");
			rb.setBoardC(c);
			System.out.println("in reply controller parentNo=" + rb.getParentNo());
			service.writeReply(rb);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (AddException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(),  HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
