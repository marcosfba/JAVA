/**
 * 
 */
package br.edu.unitri.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author marcos.fernando
 *
 */
public final class ConverterUtil {
	
	public static Date localDateToDate(LocalDate localDate){
		Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		return Date.from(instant);
	}	
	
	public static LocalDate utilDateToLocalDate(Date date){		
		Instant instant = Instant.ofEpochMilli(date.getTime());
		LocalDateTime res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		return res.toLocalDate();
	}


}
