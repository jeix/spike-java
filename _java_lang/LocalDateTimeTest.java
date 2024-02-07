import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

public class LocalDateTimeTest {

	public static void main(String[] args) {

		LocalDateTime ldt = LocalDateTime.now();
		
		String sdt = ldt.toString();
		System.out.println(sdt);             //-> 2020-04-10T11:11:32.703627

		ldt = LocalDateTime.parse(sdt);
		System.out.println(ldt.toString());  //-> 2020-04-10T11:11:32.703627

		sdt = ldt.format(ISO_LOCAL_DATE_TIME);
		System.out.println(sdt);             //-> 2020-04-10T11:11:32.703627

		sdt = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"));
		System.out.println(sdt);             //-> 2020-04-10T11:11:32.703627

		sdt = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		System.out.println(sdt);             //-> 2020-04-10 11:11:32
	}
}