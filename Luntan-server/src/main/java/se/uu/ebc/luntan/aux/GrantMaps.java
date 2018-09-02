package  se.uu.ebc.luntan.aux;

import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.*;

import se.uu.ebc.luntan.enums.Department;

public class GrantMaps {

	public static Map<Department,Float> sum(Map<Department,Float> a, Map<Department,Float> b)  {
	
		Map<Department,Float> c = Stream.concat(a.entrySet().stream(), b.entrySet().stream())
			.collect(Collectors.toMap(
				entry -> entry.getKey(), // The key
				entry -> entry.getValue(), // The value
				// The "merger" as a method reference
				(x, y) -> x + y
			)
		);	
		return c;
	}

	public static Map<Department,Float> diff(Map<Department,Float> a, Map<Department,Float> b) {
	
		Map<Department,Float> c = Stream.concat(a.entrySet().stream(), b.entrySet().stream())
			.collect(Collectors.toMap(
				entry -> entry.getKey(), // The key
				entry -> entry.getValue(), // The value
				// The "merger" as a method reference
				(x, y) -> x - y
			)
		);	
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