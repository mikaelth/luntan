package se.uu.ebc.luntan.vo;

import java.util.List;
import java.util.ArrayList;
import org.springframework.web.multipart.MultipartFile;

import lombok.*;

@Data //Using lombok for boiler plate...
public class CourseInstancesCSVUploadFB {

	private Integer year;
//	private String[] artifacts;
	private MultipartFile ciFile;
	private boolean ignoreExistingValues;
	
}

