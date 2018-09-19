package common;

public class Function {
   //로그인 
   public static final int LOGIN=100;// 이미 로그인된 사람들
   //방관련 메세지
   public static final int MYLOG=110;// 로그인하는 사람들
   public static final int MAKEROOM=200;// 방만들기
   public static final int MYROOMIN=210;//방들어가기
   public static final int ROOMADD=215; //방에 들어가 있는 사람
   public static final int ROOMOUT=220;//방나가기(남아있는 사람)
   public static final int MYROOMOUT=230;//방나가는 사람 처리
   public static final int WAITUPDATE=240;//대기실 수정
   public static final int ROOMUP=250; //게임 사용자 위치
   //GAME 메세지
   public static final int GAMEREADY=300;//게임 준비
   public static final int GAMESTART=310;//게임시작
   //public static final int GAMEMUNJE=320;//문제출제
   //public static final int GAMEDAP=330;//답 제출
   public static final int GAMEYESNO=340;//정답보여주기 (이미지와 이름)
   public static final int GAMESCORE=350; //개인별 점수 계산
   public static final int GAMERANK=360; //점수비교를 하여 순위정하기
   public static final int PLAYUP=370; //대기창 플레이 글씨 변경
   public static final int GAMEEND=390; // 게임 종료
   // 채팅 관련
   public static final int WAITCHAT=400;//채팅하기
   public static final int GAMECHAT=410;//채팅하기
   public static final int LOGOUT=500;//게임종료
   public static final int MYLOGOUT=510;
} 