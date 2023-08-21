package uz.perevods.perevod.component.httpSession;//package uz.customs.customprice.component.httpSession;
//
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import uz.customs.customprice.entity.authorization.User;
//
//@Service
//@Transactional
//public class UserService {
//    @Autowired
//    private SessionFactory sessionFactory;
//
//    @CacheEvict(value = "users", allEntries = true)
//    public void save(User user) {
//        sessionFactory.getCurrentSession().save(user);
//    }
//
//    @Cacheable(value = "users", key = "#id")
//    public User get(Long id) {
//        return sessionFactory.getCurrentSession().get(User.class, id);
//    }
//
//    @CacheEvict(value = "users", allEntries = true)
//    public void delete(Long id) {
//        User user = get(id);
//        if (user != null) {
//            sessionFactory.getCurrentSession().delete(user);
//        }
//    }
//}
