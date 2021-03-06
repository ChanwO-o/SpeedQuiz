package com.speedquiz.classic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.app.Application;
import android.content.Context;

@SuppressWarnings("unchecked")
public class Dictionary extends Application {

	private ArrayList<String> FULLLIST = new ArrayList<String>(Arrays.asList(
			// 1.0
			"맛동산", "삐삐", "참나무", "정림사지 오층석탑", "해바라기씨", "파이리", "지우", "대리운전", "1577", "니모", "야쿠르트", "까마귀", "붕어빵", "불닭면", "와우", "태종무열왕릉비", "시치미",
			"텔레토비", "암기", "모나리자", "아이폰", "물잔", "황소", "갤럭시", "마요네즈", "롤", "코난", "창조물", "카카오", "정석", "무릎팍", "수탉", "기쁨", "메아리", "나트륨", "마그네슘",
			"알루미늄", "프랑스", "설상가상", "막상막하", "일석이조", "양다리", "지하철", "소멸", "참전", "김구", "이승만", "사상자", "좌우명", "레오나르도 다빈치", "쌍둥이", "개그콘서트", "부활절",
			"아이유", "하정우", "우박", "한류", "제크", "홈런볼", "미쯔", "초코파이", "오레오", "오예스", "삼양라면", "안드로이드", "전기자동차", "생물학", "물리", "화학", "혼합물", "지진", "물결",
			"이어폰", "칠성사이다", "도서관", "나침반", "메모", "크롬", "구글", "네이버", "딱지", "사회", "왕복", "복주머니", "세뱃돈", "정복", "뚜벅뚜벅", "마시멜로우", "알사탕", "국물", "끓는점",
			"연필심", "통나무","막대", "빨대", "백두산 호랑이", "동거", "소설", "수필", "제작자", "웹툰", "마우스 패드", "계산기", "미적분", "간장", "태백산맥", "토끼띠", "마술사", "베개싸움", "진수성찬",
			"진리", "갑을병정", "박자", "깔끔", "복권", "재산", "무리수", "라이언킹", "진품", "염색약", "콘센트", "충전기", "휘발류", "전염", "질병", "흑사병", "도미노피자", "옆집", "망원경", "연필깎이",
			"거짓말", "허세", "서랍", "조회", "운동장", "칠판 지우개", "은박지", "두근두근", "토닥토닥", "미끌", "추어탕", "곱창", "만우절", "제육볶음", "삼겹살", "쭉쭉", "이승철", "곰국", "사골", "함정",
			"갑골문자", "고인돌", "인사동", "불꽃놀이", "버블", "비눗거품", "배즙",
			
			// 1.1
			"무중력", "휴지통", "손잡이", "보약", "안마", "안알랴쥼", "막걸리", "화염", "불사조", "불가사의", "기숙학원", "고분고분", "갸우뚱", "기울기", "강냉이", "띄어쓰기", "철수세미", "신고",
			"신기록", "위임장", "이발소", "임전무퇴", "제비뽑기", "제대", "죄인", "톨게이트", "9시 뉴스", "포도당", "연합", "김연아", "화장대", "황소개구리", "영혼", "유체이탈", "승천", "흉기",
			"홍당무", "윗사람", "워낭소리", "부정부패", "부채질", "쓰레기봉투", "등잔밑", "포도주", "스타크래프트", "책벌레", "부침개", "꼬불꼬불", "폴더", "동그랑땡", "클라스", "나이키", "먹보",
			"지뢰찾기", "나무젓가락", "멜라닌", "프라이머리", "액션가면", "초코비", "생양파", "눈물바다", "흡연석", "노약좌석", "앙코르", "용수철", "아바타", "후두둑 후두둑", "입맞춤", "징계",
			"방울토마토", "옥타브", "복사기", "무중력", "헌혈", "빵조각", "장발장", "열쇠구멍", "엔터 키", "사고뭉치", "줄무늬", "체크무늬", "장염", "순댓국", "식초", "낙서장", "사차원", "꿀단지",
			"정육각형", "군복무", "치즈스틱", "강낭콩", "설문조사", "녹차밭", "전시회", "꼬부기", "달토끼", "셀카", "종이비행기", "흑기사", "초집중", "개미핥기", "짜파게티", "상남자", "아밀라제",
			"페이스북", "간접광고", "호랑이 가죽", "핸드백", "스냅백", "군모", "꽃가게", "삼국지", "세계사", "과산화수소", "손잡이", "단두대", "초상화", "사물함", "치마폭", "일요일", "보이스피싱", "핑계",
			"이효리", "바람과 함께 사라지다", "목도리도마뱀", "레슬링", "로꾸거", "꽃보다 남자", "휘청휘청", "저질댄스", "고래싸움에\n새우등 터진다", "호랑나비", "김홍국", "소녀시대", "역도","원숭이 엉덩이",
			"Sorry Sorry", "엉금엉금", "뛰는 놈 위\n나는 놈 있다", "소방차", "스케이트", "스테이크", "배영", "자유형", "박태환", "타이타닉", "캥거루", "리듬체조", "베토벤 바이러스", "지휘자", "바이올린",
			"캔디", "마이클 잭슨", "테크토닉","지렁이도 밟으면\n꿈틀한다", "반짝반짝", "샤방샤방", "금속활자", "인쇄", "안경점", "호두과자", "터미네이터", "페이스북", "좋아요", "팥빵", "슈크림 붕어빵", 
			"마이크", "세종대왕", "회초리","실내화", "낮잠", "졸음", "중간고사", "아메리카노", "힙합", "뽀로로", "피카츄", "호빵맨", "도라에몽", "왕따", "참새", "딱따구리", "노인과 바다", "양파링", 
			"꼬깔콘","트렁크", "깃발", "딱풀", "안경닦이", "산타", "사격", "권총", "에펠탑", "담배빵", "할복", "찜질방", "피씨방", "노래방", "책갈피", "물병",
			
			// 1.2
			"쥐덪", "귀찮이즘", "흰건반", "콩밥", "호박씨", "진달래꽃", "자가용", "독수리", "도깨비", "앵그리버드", "신용카드", "무이자 할부", "수수료", "포스트잇", "롯데백화점", "쿠키 앤 크림",
			"초코칩", "정류장", "초록 버스", "셔플", "참 잘했어요", "알림장", "시나브로", "매추리알", "살얼음", "풀하우스", "빈 수레", "기압", "패기", "압도", "실호라기", "화살표", "불화살",
			"오답", "성적표", "빼빼로", "압력밥솥", "술자리", "직장인", "회장", "삼다수", "정전", "메신저", "블루투스 키보드", "봉황", "샘물", "그린라이트", "자두씨", "쎈 주먹", "보자기", "배낭여행",
			"화면캡쳐", "쫄바지", "이클립스", "해운대", "중2병", "해장국", "카톡", "다이너마이트", "장갑차", "두개골", "러닝머신", "바가지", "바지락", "양팔간격", "문방구", "금도끼", "척추", "누텔라",
			"도라에몽", "구루병", "이산가족", "지옥훈련", "문병", "진동", "번데기", "으르렁", "개팔자가 상팔자", "새옹지마", "캠퍼스", "빙산일각", "히틀러", "이순신 장군", "도시락 폭탄",
			"고무신", "성냥팔이 소녀", "로미오와 줄리엣", "직쏘", "한국은행", "세숫대야", "첫눈에 반한", "무한도전", "파닥파닥", "주르룩", "폭탄주", "강남스타일", "부대찌개", "을미사변", "현모양처",
			"놋그릇", "구두약", "헬로키티", "당면", "보글보글", "전자렌지", "덩그러니", "엄마 손은 약손", "팔베개", "의리", "손걸레", "모닝 커피", "가시방석", "고슴도치", "비둘기", "돌연변이", "비극",
			"플라스틱", "등산", "이집트", "피라미드", "멘탈붕괴 \n(멘붕)", "베란다", "칵테일", "왈츠", "풍선껌", "스키점프", "방탈출", "모눈종이", "탯줄", "반창고", "거북이", "현미경", "미토콘드리아",
			"찡긋", "이쑤시개", "함정카드", "큐피드", "눈깔사탕", "부상", "운전면허", "카타르시스", "파도타기", "악동뮤지션", "붉은노을", "심리학", "변호사", "돌하루방", "제주감귤초콜릿", "기념품", "방충망",
			"뚝딱뚝딱", "가위바위보", "인공호흡", "선전포고", "뒷마당", "귀요미", "침팬지", "트로트", "바텐더", "전갈", "기린", "JYP", "자랑", "피노키오", "애호박", "어퍼컷", "스크류바", "기타",
			"호날두", "낙타", "기러기", "고드름", "질소", "앞구르기", "바나나맛우유", "소꿉놀이", "라면스프", "주전자", "찻잔", "쟁반짜장", "티백\n(tea bag)", "역주행",

			// 1.3
			"달걀귀신", "계란 후라이", "뿌셔뿌셔", "아이언맨", "김여사", "관광객", "야식", "구기종목", "아드레날린", "파랑새", "상상력", "콩 심은데 콩 나고,\n팥 심은데 팥난다", "뱁새가 황새를 \n따라간다",
			"낫 놓고 \n기역자도 모른다", "가는 말이 고와야\n오는 말이 곱다", "같은 떡도 남의\n것이 커 보인다", "돼지 목에\n진주 목걸이", "김치전", "산신령", "애국심", "호흡곤란", "출마", "깔창", "아나콘다",
			"불장난", "휴가", "타조", "식빵", "기부", "썰매", "장바구니", "장마", "풍뎅이", "정치인", "반기문", "국제 연합\n(UN)", "버터", "고추장", "재앙", "드르렁", "세발자전거", "달마시안",
			"오리털", "3분 카레", "봄바람", "음주운전", "전구", "약수터", "단풍", "수능", "자외선", "선글라스", "세탁기", "경찰", "해산물", "아이패드", "골짜기", "늑대인간", "교훈", "정신병원",
			"느림보", "포돌이", "해군", "참치", "회전초밥", "북두칠성", "백조", "압록강", "학교 종이 땡땡땡", "주민등록증", "손금", "워터파크", "홍대입구", "한우", "민달팽이", "청개구리", "첼로",
			"설렁탕", "안테나", "안전벨트", "공익광고", "공중부양", "분리수거", "인수분해", "타임어택", "저격수", "투수", "홈플레이트", "콘푸로스트", "타임스퀘어", "오아시스", "성형수술", "마찰력",
			"애니메이션", "대환영", "기립박수", "거미줄", "네비게이션", "코카콜라",

			// 2.00
			"소화제", "총각김치", "진돗개", "계백 장군", "개집", "셰퍼드", "요크셔테리어", "겨울왕국", "증조할아버지", "올림픽공원", "만리장성", "엠파이어 스테이트 빌딩", "개선문", "두루미", "액자",
			"인스타그램", "신데렐라", "콩쥐팥쥐", "백설공주", "유리구두", "복불복", "장근석", "은지원", "팔만대장경", "때수건", "육포", "소가죽", "화투", "화분", "스타벅스", "놀부보쌈", "이메일",
			"비키니", "닭도리탕", "비트박스", "버블티", "옥수수", "누렁니", "이혼", "새해", "사랑과전쟁", "인간힘", "패스트", "신발장", "위스키", "고객님", "염분", "불도저", "천재", "개껌",
			"50% 세일", "결혼식", "자물쇠", "해가 서쪽에서 뜨겠다", "티끌 모아 태산", "계륵", "갈비"));
	// http://ko.wikiquote.org/wiki/%ED%95%9C%EA%B5%AD_%EC%86%8D%EB%8B%B4
	// http://creativitygames.net/random-word-generator/randomwords/8
	/*
	 * 개선점:
	 * List 장르별로 나누기 (은어, 속담, 흉내내는 말, etc)
	 * 시작 전 카운트다운 추가 (3 2 1 GO texts)
	 * Animation 추가
	 * QuizPlay 화면 밑에 타이머 막대 추가, 시간에 따라 색상 변경 (초록으로 시작, 시간 달수록 빨강으로 변경)
	 * Add sounds using soundpool
	 * Add options bar in QuizMenu: control sound & vibration
	 * Custom dialogs
	 * 새단어 추가시 새단어 위치로 List scroll 하기
	 */
	
	private ArrayList<String> defaultList = new ArrayList<String>(); //edited default list
	private ArrayList<String> customList = new ArrayList<String>(); //user's custom list
	private boolean sound = false; //sound option
	
	public ArrayList<String> getDefaultList() {
		Collections.sort(defaultList);
		return defaultList;
	}
	
	public ArrayList<String> getCustomList() {
		Collections.sort(customList);
		return customList;
	}
	
	public ArrayList<String> getEntireList() {
		ArrayList<String> result = new ArrayList<String>();
		result.addAll(getDefaultList());
		result.addAll(getCustomList());
		return result;
	}
	
	public boolean getSound()
	{ return sound; }
	
	public void loadDefault() {
		try {
			FileInputStream fis = getApplicationContext().openFileInput("default_list.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			defaultList = (ArrayList<String>) ois.readObject();
			ois.close();
//			Toast.makeText(getApplicationContext(), "Loaded default!", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
//			Toast.makeText(getApplicationContext(), "Failed to load default", Toast.LENGTH_SHORT).show();
			defaultList = FULLLIST;
		}
	}

	public void loadCustom() {
		try {
			FileInputStream fis = getApplicationContext().openFileInput("custom_list.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			customList = (ArrayList<String>) ois.readObject();
			ois.close();
//			Toast.makeText(getApplicationContext(), "Loaded custom!", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
//			Toast.makeText(getApplicationContext(), "Failed to load custom", Toast.LENGTH_SHORT).show();
			customList = new ArrayList<String>();
		}
	}

	public void saveToDefaultList(List<String> list) {
		try {
			FileOutputStream fos = getApplicationContext().openFileOutput("default_list.txt", Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(list);
			oos.close();
//			Toast.makeText(getApplicationContext(), "Saved to default!", Toast.LENGTH_SHORT).show();
		} catch (FileNotFoundException e) {
//			Toast.makeText(getApplicationContext(), "ERROR savecustom: filenotfound", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
//			Toast.makeText(getApplicationContext(), "ERROR savecustom: IO", Toast.LENGTH_SHORT).show();
		}
	}

	public void saveToCustomList(List<String> list) {
		try {
			FileOutputStream fos = getApplicationContext().openFileOutput("custom_list.txt", Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(list);
			oos.close();
//			Toast.makeText(getApplicationContext(), "Saved to custom!", Toast.LENGTH_SHORT).show();
		} catch (FileNotFoundException e) {
//			Toast.makeText(getApplicationContext(), "ERROR savecustom: filenotfound", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
//			Toast.makeText(getApplicationContext(), "ERROR savecustom: IO", Toast.LENGTH_SHORT).show();
		}
	}
}