package com.test.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.test.entity.KafkaRecvData;
import com.test.entity.KafkaSendData;
import com.test.repository.KafkaRecvRepository;
import com.test.repository.KafkaSendRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaService {
	private final KafkaTemplate<String, String> kafkaTemplate;
	
	private final KafkaSendRepository kafkaSendRepository;
	private final KafkaRecvRepository kafkaRecvRepository;
	
	@Value("${server.port}")
	private String port;
	
	private LocalDate today = LocalDate.now();
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	private String formattedDate = today.format(formatter);
	
	@Autowired
    public KafkaService(KafkaTemplate<String, String> kafkaTemplate, KafkaSendRepository kafkaSendRepository, KafkaRecvRepository kafkaRecvRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaSendRepository = kafkaSendRepository;
        this.kafkaRecvRepository = kafkaRecvRepository;
    }

	/**
	 * 
	 * @param topic
	 * @param message
	 */
    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
        
        
        KafkaSendData kafkaSendData = new KafkaSendData();
        kafkaSendData.setSendData(message);
        kafkaSendData.setBaseDt(formattedDate);
        kafkaSendData.setTopic(topic);
        // 필요한 필드 설정 후 데이터베이스에 저장
        kafkaSendRepository.save(kafkaSendData);
    }
    
    /**
     * 
     * @param record
     */
    @KafkaListener(topics = "test-topic", groupId = "my-group")
    public void listen(ConsumerRecord<String, String> record) {
    	
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = today.format(formatter);
        
        insertKafkaMsg(record);
    }
    
    @KafkaListener(topics = "test1", groupId = "my-group")
    public void listenTest1(ConsumerRecord<String, String> record) {
    	insertKafkaMsg(record);
    }
    
    
    @KafkaListener(topics = "test2", groupId = "my-group")
    public void listenTest2(ConsumerRecord<String, String> record) {
    	insertKafkaMsg(record);
    }
    
    
    @KafkaListener(topics = "test3", groupId = "my-group")
    public void listenTest3(ConsumerRecord<String, String> record) {
    	insertKafkaMsg(record);
    }
    
    
    @KafkaListener(topics = "test4", groupId = "my-group")
    public void listenTest4(ConsumerRecord<String, String> record) {
    	insertKafkaMsg(record);
    }
    
    
    @KafkaListener(topics = "test5", groupId = "my-group")
    public void listenTest5(ConsumerRecord<String, String> record) {
    	insertKafkaMsg(record);
    }
    
    
    private void insertKafkaMsg(ConsumerRecord<String, String> record) {
    	
    	String message = record.value(); 
        String topic = record.topic();
        String key   = record.key();
        log.info("topic : "+topic +" Received : " + message);
        
    	KafkaRecvData kafkaRecvData = new KafkaRecvData();
        kafkaRecvData.setSendData(message);
        kafkaRecvData.setBaseDt(formattedDate);
        kafkaRecvData.setTopic(topic);
        kafkaRecvData.setTarget(port);
        kafkaRecvData.setKey(key);
        kafkaRecvRepository.save(kafkaRecvData);
    }
    
    
    
}
