package uz.perevods.perevod.service.authorization.jpaServices;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.perevods.perevod.entitiy.authorization.ActivUserCount;
import uz.perevods.perevod.entitiy.authorization.Log;
import uz.perevods.perevod.entitiy.authorization.User;
import uz.perevods.perevod.repository.authorization.jpaRepository.LogRepository;
import uz.perevods.perevod.repository.authorization.jpaRepository.LogService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Log create(HttpSession session, HttpServletRequest request, String sesId, int check) throws SocketException, UnknownHostException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String S_id = (String) request.getSession().getAttribute("SessionID");
        request.getSession().setAttribute("SessionID", sesId);
        Log log = new Log();
        User user = (User) authentication.getPrincipal();

        if (check == 0) {
            log.setSesId(sesId);
            log.setIp(request.getRemoteAddr());
            log.setName(user.getFullname());
//            log.setUserId(request.getServerName());
            log.setUserId(user.getUserid());
            log.setLast(new Date(System.currentTimeMillis()));
            log.setExit(null);
        } else {
            log = repository.findById(sesId).get();
            log.setExit(new Date(System.currentTimeMillis()));
        }

        return repository.save(log);
    }

    @Override
    public List<Log> getByAplcPnflId(String ip) {
        return repository.findByIp(ip);
    }

    @Override
    public Optional<Log> getBySesId(String sesId) {
        return repository.findBySesId(sesId);
    }

    @Override
    public void updateLog(Log log) {
        repository.save(log);
    }

    @Override
    public Log getCarrierById(String id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Log> getAllLog() {
        return repository.findAll();
    }

    @Override
    public boolean deleteLog(String id) {
        repository.deleteById(id);
        return true;
    }

    @Override
    public ActivUserCount count() {
        String countQuery = "select\n" +
                "    (   select\n" +
                "            count(*)\n" +
                "        from\n" +
                "            globalsearch.user_e m\n" +
                "        where\n" +
                "            m.isdeleted=0) soni,\n" +
                "    (   select\n" +
                "            count(*)\n" +
                "        from\n" +
                "            (   select\n" +
                "                    l.user_id\n" +
                "                from\n" +
                "                    globalsearch.log l\n" +
                "                where\n" +
                "                    (\n" +
                "                        l.enter +1 hour)\n" +
                "                    >=(current timestamp)\n" +
                "                and l.exit is null\n" +
                "                group by\n" +
                "                    l.user_id) tt) activ\n" +
                "from\n" +
                "    sysibm.sysdummy1";

        List<Object[]> result = entityManager.createNativeQuery(countQuery).getResultList();
        ActivUserCount activUserCount = new ActivUserCount();
        for (Object[] objects : result) {
            activUserCount.setCount(objects[0].toString());
            activUserCount.setActiv(objects[1].toString());
        }
        return activUserCount;
    }

    @Override
    public Integer activeCount() {
        String activeCountQuery = "SELECT\n" +
                "    count(*)\n" +
                "FROM\n" +
                "    globalsearch.log l\n" +
                "WHERE\n" +
                "    l.ISDELETED=0\n" +
                "AND l.UPDTIME BETWEEN CURRENT_TIMESTAMP - 60 minutes AND CURRENT_TIMESTAMP";
        List<Integer> count = entityManager.createNativeQuery(activeCountQuery).getResultList();
        return count.get(0);
    }


}
