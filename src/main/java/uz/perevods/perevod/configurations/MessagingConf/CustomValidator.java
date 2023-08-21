package uz.perevods.perevod.configurations.MessagingConf;//package uz.customs.appmonitoring.config.MessagingConf;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
//import org.springframework.context.i18n.LocaleContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//import org.springframework.validation.ValidationUtils;
//import org.springframework.validation.Validator;
//import uz.customs.customprice.service.helperClasses.decisioncharges.DecisionPaymentDTO;
//
//@Component
//public class CustomValidator implements Validator {
//
//    @Autowired
//    private MessageSource messageSource;
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return DecisionPaymentDTO.class.equals(clazz);
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cmdtId", "cmdtId.required");
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "g47Type", "g47Type.required");
//        // add more validation rules here
//    }
//
//    public String getMessage(String code) {
//        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
//    }
//}
