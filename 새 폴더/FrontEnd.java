package front;

import java.util.Scanner;

import back.BackEnd;

public class FrontEnd {
	
	public FrontEnd(int recordIdx) {
		this.controller(recordIdx);
	}
	
	private void controller(int recordIndex) {
		Scanner scanner = new Scanner(System.in);
		String title = this.makeTitle();
		String[][] menu = {
				{"EXIT", "프로그램을 종료합니다.~"}, 
				{"메인", "연산하기", "끝내기"}, 
				{"연산", "새로운 연산", "이어서 연산", "이전화면"}};
		String userData = new String();
		String message = new String();
		int selectMenu;
		boolean loopCheck = true;
		
		while(true) {
			this.display(title);
			if(message != null) this.display(message);
			this.display(this.makeSubMenu(menu[recordIndex]));
			if(!loopCheck) break;
			
			userData = this.userInput(scanner);
			
			if(this.isInteger(userData)) {
				selectMenu = this.convertToInteger(userData);
				if(this.isIntegerRange(selectMenu, 0, menu[recordIndex].length-2)) {
					message = null;
					recordIndex += (selectMenu == 0)? -1: 1;
					
					if(recordIndex == 0) {
						loopCheck = false;
					}else if(recordIndex >= 2){
						recordIndex = 2;
						this.operator(title, menu[recordIndex], scanner);
					}
				}else {
					message = "[ 0~" + (menu[recordIndex].length-2) + " 범위내 숫자를 입력해주세요]\n\n";
				}
			}else {
				message = "[ 숫자로 입력해 주세요 ]\n\n";
			}
		}
		scanner.close();
	}
	
	private String makeTitle() {
		StringBuffer title = new StringBuffer();
		title.append("****************************************************\n\n");
		title.append("	    JS Framework Calculator  v1.0\n");
		title.append("		           Designed By HoonZzang\n\n");
		title.append("****************************************************\n\n");
		return title.toString();
	}

	private String makeMessage(String text) {
		StringBuffer message = new StringBuffer();
		message.append("[ ");
		message.append(text);
		message.append(" ]");
		return message.toString();
	}

	private String makeSubMenu(String[] menu) {
		StringBuffer subMenu = new StringBuffer();
		subMenu.append("  [ " + menu[0] + " ]");
		subMenu.append(" _____________________________________");
		for(int underBar=0; underBar <= 4-menu[0].length(); underBar++) {
			subMenu.append("_");
		}
		subMenu.append("\n\n");
		if(menu.length <= 2) {
			subMenu.append("    " + menu[menu.length-1] + "  \n");
			subMenu.append("  ________________________________________________\n");
		}else {
			for(int colIdx=1; colIdx<menu.length; colIdx++) {
				if(colIdx == menu.length-1) { 
					subMenu.append("0. " + menu[colIdx] + "  \n");
				}else {	subMenu.append("    "+ colIdx + ". " + menu[colIdx] + "     ");}
			}
			subMenu.append("  __________________________________ select : ");
		}				
		return subMenu.toString();
	}
	
	/* 사용자 입력 전용 메서드 
	 *  고려사항 : 	
	 *  	1. 숫자로 변환이 불가능한 데이터가 입력되어질 경우 해결방안
	 *      2. Scanner Class의 Life Cycle과 Performance  
	 * */
	private String userInput(Scanner scanner) {	
		return scanner.next();
	}
	
	/* 정수 변환여부 체크 */
	private boolean isInteger(String value) {
		boolean isResult = true;
		try {
			Integer.parseInt(value);
		}catch(Exception e){
			isResult = false;// e.printStackTrace();
		}
		return isResult;
	}
	
	/* 문자 >> 정수 변환 */
	private int convertToInteger(String value) {
		return Integer.parseInt(value);
	}
	
	/* 정수의 범의 체크 */
	private boolean isIntegerRange(int value, int starting, int last) {
		return (value >= starting && value <= last)? true: false;
	}
		
	/* 출력 전용 메서드1 */
	private void display(String text) {
		System.out.print(text);
	}
	
	/* 출력 전용 메서드2 */
	private void display(String[] text) {
		for(int idx=0; idx < text.length; idx++) {
			System.out.print((idx+1) + ". " + text[idx] + "  ");
		}
	}
	
	
	/* 연산 Controller */
	private void operator(String title, String[] menu, Scanner scanner) {
		BackEnd backend = new BackEnd();
		int[] data = {-1, -1, -1, -1};
		String[] subTitle = {"숫자를 입력해주세요 ", "연산자를 선택해주세요", "숫자를 입력해주세요", "연산결과"};
		int subTitleIdx = 0;
		String temp = new String(), message = new String();
		
		while(true) {
			this.display(title);
			this.display(this.makeSubTitle(subTitle[subTitleIdx]));
			
			while(true) {
				this.display((message != null)? message+"\n": "");
								
				for(int dataIdx=0; dataIdx<data.length;dataIdx++) {
					if(data[dataIdx] >= 0) {
						this.display(" " + ((dataIdx==1)? 
								" " + this.convertToOperator(data[dataIdx]):
									(dataIdx==data.length-1)? " = " + data[dataIdx] : " " + data[dataIdx]));
					}else {
						break;
					}
				}
				
				if(subTitleIdx == 1) this.display("\n[선택 : 1. 더하기  2. 빼기   3. 곱하기   4. 나누기] : ");
				
				if(subTitleIdx == 4) this.display("\n" + this.makeSubMenu(menu));
				
				temp = this.userInput(scanner);
				if(this.isInteger(temp)) {
					if(subTitleIdx != 1) {
						data[subTitleIdx] = this.convertToInteger(temp);
						subTitleIdx = (subTitleIdx == subTitle.length-1)? 0: subTitleIdx+1;
						message = null;
						break;
					}else {
						if(this.isIntegerRange(this.convertToInteger(temp), 1, 4)) {
							data[subTitleIdx] = this.convertToInteger(temp);
							subTitleIdx = (subTitleIdx == subTitle.length)? 0: subTitleIdx+1;
							message = null;
							break;
						}else {
							message = "[ 1 ~ 4 범위의 숫자로 입력해주세요 ]";
						}
					}
				}else {
					message = "[ 정수로 입력해주세요 ]";
				}
			}
			if(subTitleIdx == subTitle.length-1) {
				switch(data[1]) {
				case 1:
					data[3] = (int)backend.plus(data[0], data[2]);
					break;
				case 2:
					data[3] = (int)backend.minus(data[0], data[2]);
					break;
				case 3:
					data[3] = (int)backend.product(data[0], data[2]);
					break;
				default :
					data[3] = (int)backend.divide(data[0], data[2]);
				}
			}
		}
		
		
	}
	
	/* 선택한 연산 번호를 문자로 변환 후 리턴 */
	private String convertToOperator(int operatorNumber) {
		String operator = new String();
		switch(operatorNumber) {
		case 1:
			operator = "＋";
			break;
		case 2:
			operator = "－";
			break;
		case 3:
			operator = "×";
			break;
		default :
			operator = "÷";			
		}
		return operator;
	}
	
	/* 연산에 사용할 subTitle */
	private String makeSubTitle(String subTitle) {
		StringBuffer sb = new StringBuffer();
		sb.append("[ ");
		sb.append(subTitle);
		sb.append(" ] ");
		sb.append(" _____________________________\n");
		return sb.toString();
	}
	
}
