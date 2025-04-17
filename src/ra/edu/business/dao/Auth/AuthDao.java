package ra.edu.business.dao.Auth;

import ra.edu.business.dao.AppDao;
import ra.edu.business.model.Admin;
import ra.edu.business.model.Student;

public interface AuthDao extends AppDao {
    Student studentLogin(String username, String password);
    Admin adminLogin(String username, String password);

}
