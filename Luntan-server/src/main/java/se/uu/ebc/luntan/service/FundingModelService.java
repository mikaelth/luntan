package se.uu.ebc.luntan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Calendar;
import java.util.Comparator;

import java.time.Year;

import jakarta.annotation.PostConstruct;

import se.uu.ebc.luntan.entity.FundingModel;
import se.uu.ebc.luntan.repo.FundingModelRepo;


import se.uu.ebc.luntan.vo.FMVO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.dao.OptimisticLockingFailureException;


@Slf4j
@Service
public class FundingModelService {


	@Autowired
	FundingModelRepo fmRepo;

	
	private ModelMapper mapper = new ModelMapper();
	 	
 	
 	/* PhD Positions */

	public List<FMVO> getAllFundingModels() throws ResourceNotFoundException {
		List<FMVO> fmVOs = new ArrayList<FMVO>();
			for (FundingModel fm : fmRepo.findAll() ) {
 				fmVOs.add(mapper.map(fm,FMVO.class));
 			}
         	return fmVOs;        	        
    }

 
	public FMVO getPhDById (Long id) {
		log.debug("getById()");
		FundingModel p = fmRepo.findById(id).get();
		log.debug(p.toString());
		return mapper.map(p, FMVO.class);
	}   
 
    
    public FMVO saveFundingModel(FMVO pvo) throws Exception {
    	FundingModel p = pvo.getId() == null ? toFundingModel(pvo) : toFundingModel(fmRepo.findById(pvo.getId()).get(), pvo);
    	fmRepo.save(p);
    	
		FMVO pVO = mapper.map(p,FMVO.class);
		return pVO;
    
    }

    public synchronized void deleteFundingModel(Long pID) throws IllegalArgumentException, OptimisticLockingFailureException {
		fmRepo.deleteById(pID);
    }
   	 
	private FundingModel toFundingModel (FMVO pvo) throws Exception {
		return toFundingModel (new FundingModel(),pvo);
   	}

	private FundingModel toFundingModel (FundingModel p, FMVO pvo) throws Exception {
		mapper.map(pvo,p);
		return p;
	}
 
	
}