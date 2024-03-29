package board;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {
    // 1. 게시물 목록 만들기
    // 2. 삭제 구현
    // 3. 수정 구현
    // 4. 상세보기 구현
    // 5. 전체삭제 구현
    // 6. 등록 구현
	
	//추가로 만들어야하는거... 조회수
    private static Connection conn = null;
    private static PreparedStatement boardListPstmt = null; //게시글 리스트
    private static PreparedStatement boardListPstmt2 = null; //검색했을때 그 키워드를 가진 게시글 불러오기
    private static PreparedStatement boardDetailPstmt = null;


    static {

        try {
            // 1. JDBC 드라이버 로딩
            Class.forName("oracle.jdbc.OracleDriver");
            // 2. DB 연결
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521/xe",
                    "bituser", //계정이름
                    "1004" //계정비밀번호
            );
            // 3. SQL 실행 객체 준비
            // 4. SQL 실행
            System.out.println("DB연결 성공");
            conn.setAutoCommit(false);

            boardListPstmt = conn.prepareStatement("select * from tb_boards");
            boardListPstmt2 = conn.prepareStatement("select * from tb_boards where btitle like ?");
            boardDetailPstmt = conn.prepareStatement("select * from tb_boards where bno = ?");

            // 5. 결과 처리
            // 6. 연결 해제
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public List<BoardVO> list(BoardVO boardVO) {
        List<BoardVO> list = new ArrayList<>();
        try {
        	ResultSet rs = null;
        	String searchKey = boardVO.getSearchKey();
        	if (searchKey != null && searchKey.length() != 0) {
        		boardListPstmt2.setString(1, "%" + searchKey + "%");
        		rs = boardListPstmt2.executeQuery();
        	} else {
        		rs = boardListPstmt.executeQuery();
        	}
            
            while (rs.next()) {
                BoardVO board = new BoardVO(rs.getInt("bno")
                        , rs.getString("btitle")
                        , rs.getString("bcontent")
                        , rs.getString("buserid")
                        , rs.getString("bdate"));
                
                list.add(board);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public BoardVO read(BoardVO boardVO) {

        BoardVO board = null;
        try {
            boardDetailPstmt.setInt(1, boardVO.getBno());

            ResultSet rs = boardDetailPstmt.executeQuery();
            if (rs.next()) {
            	board = BoardVO.builder()
            			.btitle(rs.getString("btitle"))
            			.bcontent(rs.getString("bcontent"))
            			.bno(rs.getInt("bno"))
            			.bdate(rs.getString("bdate"))
            			.buserid(rs.getString("buserid"))
            			.build();
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return board;
    }

}