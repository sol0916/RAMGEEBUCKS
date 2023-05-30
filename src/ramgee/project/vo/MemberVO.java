package ramgee.project.vo;

public class MemberVO {
    private int member_no;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phoneNumber;
    private AuthorityVO authorityVO;

    public MemberVO(int member_no, String username, String password, String name, String email, String phoneNumber, AuthorityVO authorityVO) {
        this.member_no = member_no;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.authorityVO = authorityVO;
    }

    public int getMember_no() {
        return member_no;
    }

    public void setMember_no(int member_no) {
        this.member_no = member_no;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public AuthorityVO getAuthorityVO() {
        return authorityVO;
    }

    public void setAuthorityVO(AuthorityVO authorityVO) {
        this.authorityVO = authorityVO;
    }
}
