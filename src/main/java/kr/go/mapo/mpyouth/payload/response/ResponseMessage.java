package kr.go.mapo.mpyouth.payload.response;

public class ResponseMessage {
    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGIN_FAIL = "로그인 실패";
    public static final String READ_USER = "회원 정보 조회 성공";
    public static final String NOT_FOUND_USER = "회원을 찾을 수 없습니다.";
    public static final String CREATED_USER = "회원 등록 성공";
    public static final String UPDATE_USER = "회원 정보 수정 성공";
    public static final String DELETE_USER = "회원 탈퇴 성공";
    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
    public static final String DB_ERROR = "데이터베이스 에러";
    public static final String LOGOUT_SUCCESS = "로그아웃 성공";
    public static final String SEND_ADMIN_LOGIN_ID_SUCESS = "로그인 아이디 메일 보내기 성공";
    public static final String SEND_AUTH_KEY_SUCCESS = "이메일 인증키 보내기 성공";
    public static final String SEND_INIT_PASSWORD_SUCESS = "임시 비밀번호 발급 성공";
    public static final String USER_INFO_UPDATE_SUCESS = "유저 정보 갱신 성공";
    public static final String USER_PASSWROD_UPDATE_SUCESS ="유저 비밀번호 갱신 성공";
    public static final String USER_DELETE_SUCCESS = "유저 삭제 성공";
    public static final String USERS_SELECT_SUCCESS = "유저 리스트 조회 성공";
    public static final String USER_SELECT_SUCCESS = "유저 조회 성공";
    public static final String ISSUE_NEW_ACCESS_TOKEN_AND_REFRESH_TOKEN = "액세스,리프레쉬 토큰 새로 발급";
}
