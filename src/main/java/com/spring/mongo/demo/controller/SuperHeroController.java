package com.spring.mongo.demo.controller;


import com.spring.mongo.demo.model.SuperHero;
import com.spring.mongo.demo.service.SuperHeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import java.util.List;

@RestController
@RequestMapping("/incident-request")
public class SuperHeroController {

    @Autowired
    private SuperHeroService superHeroService;
    
    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping
    public ResponseEntity<List<?>> findAll() {
        List<?> list = superHeroService.findAll();
        return ResponseEntity.ok().body(list);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        SuperHero superHero = superHeroService.findById(id);
        return ResponseEntity.ok().body(superHero);
    }


    @PostMapping("/save")
    public void save(@RequestBody SuperHero superHero) {
    	
    //	System.out.println(superHero.getId()+ "************ ");
    
        SuperHero savedSuperHero = superHeroService.save(superHero);
        
        try {
            //sendEmail();
            sendEmailWithAttachment(superHero.getEmail());

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
      //  return ResponseEntity.ok().body(savedSuperHero);
    }


    @PutMapping
    public ResponseEntity<?> update(@RequestBody SuperHero superHero) {
        SuperHero updatedSuperHero = superHeroService.update(superHero);
        return ResponseEntity.ok().body(updatedSuperHero);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        superHeroService.delete(id);
        return ResponseEntity.ok().body("Deleted successfully...!");
    }
    
    void sendEmailWithAttachment(String s) throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo("mike.liadov@optum.com");
        System.out.println("Email sent..");

        helper.setSubject("Incident Request #987689");

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText("Thank you for submitting request. Your Ticket Number 87640975", true);

        //FileSystemResource file = new FileSystemResource(new File("classpath:android.png"));

        //Resource resource = new ClassPathResource("android.png");
        //InputStream input = resource.getInputStream();

        //ResourceUtils.getFile("classpath:android.png");

      //  helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

        javaMailSender.send(msg);

    }
}
