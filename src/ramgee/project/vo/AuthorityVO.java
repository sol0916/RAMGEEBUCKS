package ramgee.project.vo;

public class AuthorityVO {
    private int authority_no;
    private String authorityName;

    public AuthorityVO(int authority_no, String authorityName) {
        this.authority_no = authority_no;
        this.authorityName = authorityName;
    }

    public int getAuthority_no() {
        return authority_no;
    }

    public void setAuthority_no(int authority_no) {
        this.authority_no = authority_no;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }
}
