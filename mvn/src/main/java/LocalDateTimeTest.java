import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

public class LocalDateTimeTest {

	public static void main(String[] args) {

		LocalDateTime ldt = LocalDateTime.now();
		String sdt = ldt.toString(); //-> 2020-04-10T11:11:32.703627
		System.out.println(sdt);

		ldt = LocalDateTime.parse(sdt);
		System.out.println(ldt.toString());

		sdt = ldt.format(ISO_LOCAL_DATE_TIME); //-> 2020-04-10T11:11:32.703627
		System.out.println(sdt);

		sdt = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"));
		System.out.println(sdt);

		sdt = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		System.out.println(sdt);
	}
}