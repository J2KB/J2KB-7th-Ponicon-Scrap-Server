//package j2kb.ponicon.scrap;
//
//import org.springframework.context.ApplicationContextInitializer;
//import org.springframework.context.ConfigurableApplicationContext;
//
//public class ServiceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
//    @Override
//    public void initialize(ConfigurableApplicationContext applicationContext) {
//        String password = getPasswordFromAWSParameterStore();
//        setJasyptPassword(password);
//    }
//
//    private String getPasswordFromAWSParameterStore() {
//        //  Get password from AWS
//        return "password";
//    }
//
//    private void setJasyptPassword(String password) {
//        System.setProperty("jasypt.encryptor.password", password);
//    }
//}
