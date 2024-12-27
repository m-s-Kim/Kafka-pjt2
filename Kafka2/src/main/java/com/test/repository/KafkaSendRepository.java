package com.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.test.entity.KafkaSendData;

public interface KafkaSendRepository extends JpaRepository<KafkaSendData, Long> {
	
	
}