package org.rikkei;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;
import static org.rikkei.Constant.*;

public class Job {
//    private static LocalDateTime sendDate = LocalDateTime.now();
    private static LocalDateTime sendDate = LocalDateTime.of(2023, 1, 1,00,00);
    public static void schedule(Properties props){
        List<Service> services= Service.getListService(props);
        LocalDateTime currentDate= LocalDateTime.now();
        sendDate.plusHours(Integer.parseInt(props.getProperty(MAIL_TIME_RESEND)));
        if (currentDate.isAfter(sendDate)&&services.size()!=0){
            sendDate=currentDate;
            MailProvider.sendEmail(MAIL_SUBJECT,services,props);
        }
    }
}
