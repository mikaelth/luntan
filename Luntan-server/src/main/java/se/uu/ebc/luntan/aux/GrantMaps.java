package  se.uu.ebc.luntan.aux;

import java.util.List;
import java.util.Map;
import java.util.stream.*;

import se.uu.ebc.luntan.enums.Department;

import lombok.extern.slf4j.Slf4j;

import se.uu.ebc.luntan.enums.*;
import se.uu.ebc.luntan.aux.GrantMaps;

@Slf4j
public class GrantMaps {

	public static Map<Department,Float> sum(Map<Department,Float> a, Map<Department,Float> b)  {
		
		log.debug("Sum, first map " + a.toString());
		log.debug("Sum, second map " + b.toString());
		
		Map<Department,Float> c = Stream.concat(a.entrySet().stream(), b.entrySet().stream())
//			.peek( f -> log.debug("sum " + f.toString()) )
			.collect(Collectors.toMap(
				entry -> entry.getKey(), // The key
				entry -> entry.getValue(), // The value
				// The "merger" as a method reference
				(x, y) -> x + y
			)
		);	
		log.debug("Sum, resulting map " + c.toString());
		return c;
	}

	public static Map<Department,Float> diff(Map<Department,Float> a, Map<Department,Float> b) {
	
		log.debug("Diff, first map " + a.toString());
		log.debug("Diff, second map " + b.toString());

		Map<Department,Float> c = Stream.concat(a.entrySet().stream(), b.entrySet().stream())
//			.peek( f -> log.debug("diff " + f.toString()) )
			.collect(Collectors.toMap(
				entry -> entry.getKey(), // The key
				entry -> entry.getValue(), // The value
				// The "merger" as a method reference
				(x, y) -> x - y
			)
		);	
		log.debug("Diff, resulting map " + c.toString());
		return c;
	}

/* 
	public static Map<Department,Float> sum(List<Map<Department,Float>> maplist)  {
	
		Map<Department,Float> a = maplist.stream().map(m -> m.entrySet().stream()
			.collect(Collectors.toMap(
				entry -> entry.getKey(), // The key
				entry -> entry.getValue(), // The value
				// The "merger" as a method reference
				(x, y) -> x + y
			)).toMap(entry -> entry.getKey(),entry -> entry.getValue())
		);	
		return a;
	}
 */

}