package com.example.profile.apache_kafka.producers;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class TemplateEmail {
    String username;
    String genderUser;

    public String subjectEmailOnboarding() {
        return String.format("HELLO %s %s - WELCOME TO ONBOARD ", genderUser, username);
    }

    public String contentEmailOnboarding() {
        return "<h1 style=\"margin: auto; margin-top: 20px; border: 1px solid #indigo; text-align: center;\">Hello " +
                genderUser +
                username +  " </h1> </br> " +
                "<h3  style=\"color: #dd3f05!important; font-weight: bold; padding: 15px 0; color: #333; text-align: center;\"> CONGRATULATE RECEIVED A COUPON CODE FOR NEW USER : uyw23er&h23  </h3> </br> " +
                "<h4 style=\"width: 70%; text-align: center;margin:auto\"> Welcome to eSportFlick, the premier social network designed exclusively for gamers. </br> " +
                "Whether you're a casual player or a hardcore enthusiast. </br> " +
                "eSportFlick offers a vibrant platform where you can connect, share, " +
                "and discover new gaming experiences. </h4> </br> " +
                "<h4  style=\"padding: 15px 0; color: #333; text-align: center;\">" +
                "NOTE THAT: let's contact FaceBook: LY HUYNH HUU TRI, if you have concern about us," +
                " </h4>  </br> <h4 style=\"padding: 15px 0; color: red; text-align: center;\">Follow link at: https://www.facebook.com/lyshuynhshuustris.tris/</h4>";
    }

}
