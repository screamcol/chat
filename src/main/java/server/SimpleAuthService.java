package server;

import org.apache.commons.codec.digest.DigestUtils;

public class SimpleAuthService implements AuthService {
    DBHelper DBHelper;

    public SimpleAuthService(DBHelper helper) {
        DBHelper = helper;
        DBHelper.init();
        if (DBHelper.selectAll().size() == 0) {
            for (int i = 1; i <= 10; i++) {
                String password = DigestUtils.md5Hex("pass" + i);   // хэширование пароля md5
                DBHelper.insert(i, "login" + i, password, "nick" + i);
            }
        }
    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        String md5Password = DigestUtils.md5Hex(password); // получение пароля и хэширование в md5
        String result = DBHelper.selectNickname(login, md5Password);
        if (result != null) return result;
        else return null;
    }
}

