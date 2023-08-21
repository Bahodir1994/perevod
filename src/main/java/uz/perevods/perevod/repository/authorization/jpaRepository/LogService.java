package uz.perevods.perevod.repository.authorization.jpaRepository;


import uz.perevods.perevod.entitiy.authorization.ActivUserCount;
import uz.perevods.perevod.entitiy.authorization.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

public interface LogService {

    Log create(HttpSession session, HttpServletRequest request, String pnfl, int check) throws SocketException, UnknownHostException;

    Log getCarrierById(String id);

    List<Log> getAllLog();

    boolean deleteLog(String id);

    ActivUserCount count();

    Integer activeCount();

    List<Log> getByAplcPnflId(String ip);

    Optional<Log> getBySesId(String sesId);

    void updateLog(Log log);

}
