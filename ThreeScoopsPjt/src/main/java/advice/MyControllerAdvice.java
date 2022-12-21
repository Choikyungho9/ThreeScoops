package advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class MyControllerAdvice {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<?>except(Exception e) {
		
		System.out.println("Exception Handler : e.className" + e.getClass().getName() );
		e.printStackTrace();
		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	@ResponseBody
	public ResponseEntity<?> exceptMaxUploadSize(MaxUploadSizeExceededException e){
		HttpHeaders resHeaders = new HttpHeaders();
		resHeaders.add("Content-Type", "application/json;charset=UTF-8"); 
		resHeaders.add("Access-Control-Allow-Origin", "192.168.2.25:9999/demomvc1");  //정확한 응답허용할 URL
		resHeaders.add("Access-Control-Allow-Origin", "192.168.2.25:5500");  //정확한 응답허용할 URL
		resHeaders.add("Access-Control-Allow-Origin", "172.30.1.89:9999/demomvc1");  //정확한 응답허용할 URL
		resHeaders.add("Access-Control-Allow-Origin", "172.30.1.89:5500");  //정확한 응답허용할 URL
		resHeaders.add("Access-Control-Allow-Credentials", "true");
		
		//return new ResponseEntity<>(e.getMessage(), resHeaders, HttpStatus.BAD_REQUEST);
		Map<String, Object> map = new HashMap<>();
		map.put("status", 0);
		map.put("one", "hello");
		map.put("msg", e.getMessage());
		return new ResponseEntity<>(map, resHeaders, HttpStatus.BAD_REQUEST);
	}
	
}
