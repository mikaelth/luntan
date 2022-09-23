package se.uu.ebc.luntan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Calendar;

import se.uu.ebc.luntan.entity.Programme;
import se.uu.ebc.luntan.vo.ProgrammeVO;
import se.uu.ebc.luntan.repo.ProgrammeRepo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProgrammeService {


	@Autowired
	ProgrammeRepo progRepo;


	/* Programmes */

	public List<ProgrammeVO> getAllProgrammes() throws Exception {
		List<ProgrammeVO> cVO = new ArrayList<ProgrammeVO>();
		try {	
			for (Programme c : progRepo.findAll()) {
 				cVO.add(new ProgrammeVO(c));
 			}
         	return cVO;        	        
        } catch (Exception e) {
			log.error("getAllProgrammes got a pesky exception: "+ e + e.getCause());

			return null;
			
        }
    }
    
	
    public ProgrammeVO saveProgramme(ProgrammeVO cVO) throws Exception {
    	Programme c = cVO.getId() == null ? toProgramme(cVO) : toProgramme(progRepo.findById(cVO.getId()).get(), cVO);
    	progRepo.save(c);
		return new ProgrammeVO(c);
    
    }


    public synchronized void deleteProgramme(Long cID) throws Exception {
		Programme c = progRepo.findById(cID).get();
		progRepo.delete(c);
    }


	private Programme toProgramme (ProgrammeVO cvo) throws Exception {
 		return toProgramme (new Programme(), cvo);
   	}

	private Programme toProgramme (Programme c, ProgrammeVO cvo) throws Exception {


		try {
			c.setId(cvo.getId()) ;
			c.setCode(cvo.getCode()) ;
			c.setSeName(cvo.getSeName()) ;
			c.setDirection(cvo.getDirection()) ;
			c.setNote(cvo.getNote()) ;
    		c.setInactive(cvo.isInactive());
			c.setProgramDirector(cvo.getProgramDirector());

		} catch (Exception e) {
			log.error("toProgramme got a pesky exception: "+ e + e.getCause());
		} finally {
			return c;
		}
	}
 

}