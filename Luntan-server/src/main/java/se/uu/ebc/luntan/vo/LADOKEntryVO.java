package se.uu.ebc.luntan.vo;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import org.springframework.web.multipart.MultipartFile;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import se.uu.ebc.luntan.enums.UpdateStatus;

import lombok.*;

@Data //Using lombok for boiler plate...
public class LADOKEntryVO {

	@CsvBindByPosition(position = 0)
	private String courseCode;

	@CsvBindByPosition(position = 1, locale = "sv_SE")
	private String designation;

	@CsvBindByPosition(position = 2, locale = "sv_SE")
	private Float size;

	@CsvBindByPosition(position = 5)
	private String instanceCode;

	@CsvBindByPosition(position = 10)
	private String startDate;

	@CsvBindByPosition(position = 13)
	private Integer registered;

	private Integer previousValue;

	private UpdateStatus status;
}

/* 

   0: Kod
   1: Benämning
   2: Omfattning
   3: Enhet
   4: Nivå inom studieordning
   5: Kod
   6: Studietakt
   7: Finansieringsform
   8: Undervisningsform
   9: Studieort
  10: Startdatum
  11: Kvinnor
  12: Män
  13: Total

 */
