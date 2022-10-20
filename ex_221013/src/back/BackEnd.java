package back;

public class BackEnd {

	public BackEnd() {

		
	}

	/*
	 * Method의 양식 [Access Modifier | Restrict] [Return - type] [Method-name]
	 * [parameter,....]
	 * 
	 */

	public long controller(char operation, long digit1, long digit2) {
		long basket;
		if (operation == '+') {
			basket = digit1 + digit2;
		} else if (operation == '-') {
			basket = digit1 - digit2;
		} else if (operation == '*') {
			basket = digit1 * digit2;
		} else {
			basket = digit1 / digit2;
		}

		return basket;
	}

	
	
	public long plus(long digit1, long digit2) {
		return digit1 + digit2;
	}

	public long minus(long digit1, long digit2) {
		return digit1 - digit2;
	}

	public long product(long digit1, long digit2) {
		return digit1 * digit2;
	}

	public long divide(long digit1, long digit2) {
		return digit1 / digit2;
	}

}
