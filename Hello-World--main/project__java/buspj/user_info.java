package buspj;

public class user_info {
    String id;
    String pw;
    user_info(String id, String pw){
        this.id =id;
        this.pw =pw;
    }
    public String get_id (){
        return id;
    }
    public String get_pw (){
        return pw;
    }
}
