package com.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.dto.ResponseDto;
import com.test.entity.KafkaSendData;
import com.test.service.KafkaService;

@RestController
@RequestMapping("/kafka")
public class KafkaController {
	
	private final KafkaService kafkaService;
	
	
	@Autowired
	public KafkaController(KafkaService kafkaService) {
		this.kafkaService = kafkaService;
	}
	
	
	@PostMapping("/send")
	public ResponseEntity<ResponseDto> send(@RequestParam String text){
		
		kafkaService.sendMessage("test-topic", text);
		ResponseDto dto = new ResponseDto();
		dto.setData(text);
		return ResponseEntity.ok(dto);
	}
	
//	@GetMapping("/messages")
//    public ResponseEntity<List<KafkaSendData>> getAllMessages() {
//        try {
//            // 서비스에서 데이터 조회
//            List<KafkaSendData> messages = KafkaSendData.getAllMessages();
//            
//            // 데이터가 있을 경우
//            if (!messages.isEmpty()) {
//                return ResponseEntity.ok(messages); // 200 OK + 데이터
//            } else {
//                return ResponseEntity.noContent().build(); // 204 No Content (데이터가 없을 경우)
//            }
//        } catch (Exception e) {
//            // 예외 발생 시
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
//        }
//		
//		
//    }
	

}
