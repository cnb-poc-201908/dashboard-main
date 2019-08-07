package com.bmw.main;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bmw.main.service.CDKService;
import com.bmw.main.service.MainService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainApplicationTests {

//	@Autowired
//    private ProgressService progressService;
//	
//	@Test
//	public void contextLoads() {
//		progressService.testPing();
//	}

	@Autowired
	private CDKService cDKClient;
	
	@Test
	public void contextLoads() {
		cDKClient.ping("MBOBI", "987501");
	}
}
