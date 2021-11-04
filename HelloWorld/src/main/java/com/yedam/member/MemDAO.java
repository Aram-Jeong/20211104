package com.yedam.member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemDAO extends DAO {

	// 수정 기능
	public Map<String, Object> updateMember(MemberVO vo) {
		// retCode: OK, retVal: vo
		// retCode: NG, retVal: errMsg
		String sql = "update member ";
		sql += "       set    user_name = ?";
		sql += "            , birth_date = ?";
		sql += "            , gender = ?";
		sql += "            , address = ?";
		sql += "            , phone = ?";
		sql += "       where user_id = ?";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("retCode", "NG");
		map.put("retVal", "Error!");
		connect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getUserName());
			psmt.setString(2, vo.getBirthDate());
			psmt.setString(3, vo.getGender());
			psmt.setString(4, vo.getAddress());
			psmt.setString(5, vo.getPhone());
			psmt.setString(6, vo.getUserId());
			int r = psmt.executeUpdate();
			System.out.println(r + "건 수정");
			if(r > 0) {
				map.put("retCode", "OK");
				map.put("retVal", vo); // 입력받은 vo 보여주기
				return map; // 한 건 정상처리되면 리턴해줌
			}
		} catch (SQLException e) {
			e.printStackTrace();
			map.put("retVal", e.getMessage());
			return map;
		} finally {
			disconnect();
		}
		return map;

	}

	// 한 건 삭제
	public boolean deleteMember(String id) {
		String sql = "delete from member where user_id = ?"; // 파라메타로 들어오는 값을 받아서 지움
		connect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			int r = psmt.executeUpdate(); // 이걸 실행
			System.out.println(r + "건 삭제");
			if (r > 0) { // 지워졌다는 의미
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	// 한 건 조회
	public MemberVO getMember(String id) {
		String sql = "select * from member where user_id = ?";
		connect();
		MemberVO vo = null;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			if (rs.next()) {
				vo = new MemberVO();
				vo.setAddress(rs.getString("address"));
				vo.setBirthDate(rs.getString("birth_date"));
				vo.setGender(rs.getString("gender"));
				vo.setPhone(rs.getString("phone"));
				vo.setUserId(rs.getString("user_id"));
				vo.setUserName(rs.getString("user_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null; // 예외 발생
		} finally {
			disconnect();
		}
		return vo;

	}

	// 한 건 입력
	public boolean insertMember(MemberVO vo) {
		String sql = "insert into member(user_id, user_name, address, phone, birth_date, gender) values(?,?,?,?,?,?)";
		connect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getUserId());
			psmt.setString(2, vo.getUserName());
			psmt.setString(3, vo.getAddress());
			psmt.setString(4, vo.getPhone());
			psmt.setString(5, vo.getBirthDate());
			psmt.setString(6, vo.getGender());
			System.out.println(vo.getBirthDate());
			int r = psmt.executeUpdate();
			System.out.println(r + "건 입력됨.");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnect();
		}
		return true;

	}

	// 전체 리스트
	public List<MemberVO> getMemberList() {
		String sql = "select * from member order by 1";
		connect();
		List<MemberVO> memberList = new ArrayList<>();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				MemberVO vo = new MemberVO();
				vo.setUserId(rs.getString("user_id"));
				vo.setUserName(rs.getString("user_name"));
				vo.setBirthDate(rs.getString("birth_date"));
				vo.setPhone(rs.getString("phone"));
				vo.setAddress(rs.getString("address"));
				vo.setGender(rs.getString("gender"));
				memberList.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return memberList;
	}

}
