package se.uu.ebc.luntan.controller;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import se.uu.ebc.luntan.service.FundingModelService;
import se.uu.ebc.luntan.vo.FMVO;


@Slf4j
@RestController
@RequestMapping(value = "/")
@CrossOrigin(origins = "http://localhost:1841", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class FundingModelRestController {

	@Autowired
	FundingModelService fmService;
	
	private record DeleteStatus (Boolean success, Long id) {}
	private record CreateFundingModelStatus (Boolean success, FMVO fundingModel) {}
	private record CreateFundingModelListStatus (Boolean success, List<FMVO> fundingModels) {};
			

	/* Funding models */

    @GetMapping(value="rest/fundingmodels")
    public ResponseEntity<CreateFundingModelListStatus> allFMs() {
		log.debug("Looking for fundingmodels");
		return ResponseEntity.ok(new CreateFundingModelListStatus (true, fmService.getAllFundingModels() )); 
    }
/* 

	@Secured({("ROLE_SUBJECTCOORDINATOR")})
    @RequestMapping(value="/fundingmodels/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFM(@RequestBody String json, @PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			FundingModel fm = new JSONDeserializer<FundingModel>().use(null, FundingModel.class).use(LocalDateTime.class, new DateTransformer("yyyy-MM-dd") ).deserialize(json);
			fm = fmRepo.save(fm);

 			String restResponse = new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("fundingmodels").transform(new DateTransformer("yyyy-MM-dd"), "lastModifiedDate").deepSerialize(fm);
			restResponse = new StringBuilder(restResponse).insert(1, "success: true,").toString();

            return new ResponseEntity<String>(restResponse, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	@Secured({("ROLE_SUBJECTCOORDINATOR")})
    @RequestMapping(value="rest/fundingmodels", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFM(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			FundingModel fm = new JSONDeserializer<FundingModel>().use(null, FundingModel.class).use(LocalDateTime.class, new DateTransformer("yyyy-MM-dd") ).deserialize(json);
			fm = fmRepo.save(fm);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+fm.getId().toString()).build().toUriString());

 			String restResponse = new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("fundingmodels").transform(new DateTransformer("yyyy-MM-dd"), "lastModifiedDate").deepSerialize(fm);
			restResponse = new StringBuilder(restResponse).insert(1, "success: true,").toString();

            return new ResponseEntity<String>(restResponse, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	@Secured({("ROLE_SUBJECTCOORDINATOR")})
	@RequestMapping(value = "rest/fundingmodels/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFM(@PathVariable("id") Long id) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
//			fmRepo.delete(fmRepo.findById(id));
			fmRepo.delete(fmRepo.findById(id).get());
            return new ResponseEntity<String>("{success: true, id : " +id.toString() + "}", headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 */

}