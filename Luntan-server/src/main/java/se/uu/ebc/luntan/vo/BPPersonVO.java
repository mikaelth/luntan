package se.uu.ebc.luntan.vo;

import java.util.Date;


import lombok.extern.slf4j.Slf4j;
import lombok.*;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BPPersonVO {

    private String lastModifiedBy;
    private Date lastModifiedDate;
    private String firstName;
    private String lastName;
    private String ldapEntry;
    private String userName;

	private String courseCode;
	private String courseInstanceCode;
    
}
