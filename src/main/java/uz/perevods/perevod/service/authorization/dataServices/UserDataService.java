//package uz.perevods.perevod.service.authorization.dataServices;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
//import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//import uz.perevods.perevod.component.httpSession.GetterSessionData;
//import uz.perevods.perevod.component.httpSession.SessionDataValue;
//import uz.perevods.perevod.entitiy.authorization.User;
//import uz.perevods.perevod.repository.authorization.dataRepository.UserDataRepository;
//
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//import javax.servlet.http.HttpServletRequest;
//
//@Service
//@RequiredArgsConstructor
//public class UserDataService {
//
//    private final UserDataRepository userDataRepository;
//    private final GetterSessionData getterSessionData;
//
//    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
//    public DataTablesOutput<User> dataTableV1(DataTablesInput input, HttpServletRequest httpServletRequest) {
//
//        SessionDataValue sessionGetterDataValue = getterSessionData.onlyGetSessionData(httpServletRequest);
//        DataTablesOutput<User> tablesOutput = userDataRepository.findAll(
//                input,
////                rangeSpecification.and(
//                new ExcludeAnalystsSpecificationV1(input, sessionGetterDataValue.getUserLocation(), sessionGetterDataValue.getUserPost())
////                        )
//        );
//        return tablesOutput;
//    }
//
//    private static class ExcludeAnalystsSpecificationV1 implements Specification<User> {
//        private final DataTablesInput input;
//        private final String locationId;
//        private final String postId;
//
//        public ExcludeAnalystsSpecificationV1(DataTablesInput input, String locationId, String postId) {
//            this.input = input;
//            this.locationId = locationId;
//            this.postId = postId;
//        }
//
//        @Override
//        public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//            Predicate postList, locationList;
//
//            postList = criteriaBuilder.equal(root.get("post"), postId);
//            locationList = criteriaBuilder.equal(root.get("location"), locationId);
//
//            return criteriaBuilder.and(postList, locationList);
//        }
//    }
//}
