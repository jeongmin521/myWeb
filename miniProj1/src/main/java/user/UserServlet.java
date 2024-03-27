package user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.BoardVO;


/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	UserDAO userDAO = new UserDAO();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doService(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doService(request, response);
	
	}
	
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		UserVO userVO = null;
		
		switch(action) {
		case "list" -> list(request, response);
		case "view" -> view(request, response);
		case "jointForm" -> joinForm(request, response);
		case "loginForm" -> loginForm(request, response);
		case "updateForm" -> updateForm(request, response);
		case "myPage" -> myPage(request, response);
		}
		//jsp 포워딩 
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/user/"+action+".jsp");
		rd.forward(request, response);
	}
	
	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 처리
		String searchKey = request.getParameter("searchKey");
		List<UserVO> list = userDAO.list(searchKey);
		//2. jsp출력할 값 설정
		request.setAttribute("list", list);
	}
	
	private void view(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("상세보기");
		String userid = request.getParameter("userid");
		//1. 처리
		UserVO user = userDAO.read(userid);
		
		//2. jsp출력할 값 설정
		request.setAttribute("user", user);
	}
	
	private void joinForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> joinForm = new ArrayList<>();
		joinForm.add("회원가입하기");
		request.setAttribute("joinForm", joinForm);		
	}
	
	private void loginForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> loginForm = new ArrayList<>();
		loginForm.add("로그인하기");
		request.setAttribute("loginForm", loginForm);	
	}
	
	private void updateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> updateForm = new ArrayList<>();
		updateForm.add("수정하기");
		request.setAttribute("updateForm", updateForm);		
	}
	
	private void myPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> myPage = new ArrayList<>();
		myPage.add("마이페이지");
		request.setAttribute("myPage", myPage);
	}
	
}
