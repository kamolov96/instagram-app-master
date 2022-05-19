package uz.pdp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import uz.pdp.entity.Story;
import uz.pdp.repository.StoryRepository;

import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.util.Date;

@SpringBootApplication
@EnableScheduling
public class InstagramAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(InstagramAppApplication.class, args);
    }


    @Autowired
    StoryRepository storyRepository;

    @Scheduled(cron = "10/20 * * * * *") //ishga tushgandan boshlab 20sekund kutib
    public void scheduleCron() {
//        long now = System.currentTimeMillis() / 1000;
//        System.out.println("Fixed cron - " + now);
        // logica :
        for (Story story : storyRepository.findAll()) {
            Timestamp createdDate = story.getCreatedDate();

            long time = createdDate.getTime();
            long time1 = new Date().getTime();

            if (time1 > time + 120000l*720  && story.getActive()) {
                story.setActive(false);
                storyRepository.save(story);
            }
        }
    }
}
