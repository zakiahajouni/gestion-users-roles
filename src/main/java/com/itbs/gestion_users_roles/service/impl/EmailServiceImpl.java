package com.itbs.gestion_users_roles.service.impl;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.itbs.gestion_users_roles.service.EmailService;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendAccountActivatedEmail(
            String to,
            String nom,
            String password
    ) {

        try {

            MimeMessage message =
                    mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            helper.setTo(to);

            helper.setSubject(
                    "Votre compte a été activé"
            );

            String html = """
                <div style="
                    font-family: Arial;
                    padding: 20px;
                ">

                    <h2>
                        Bonjour %s,
                    </h2>

                    <p>
                        Votre compte a été activé
                        par l'administrateur.
                    </p>

                    <p>
                        Vous pouvez maintenant
                        vous connecter à la plateforme.
                    </p>

                    <div style="
                        background:#f3f4f6;
                        padding:15px;
                        border-radius:10px;
                    ">

                        <p>
                            <b>Email :</b> %s
                        </p>

                        <p>
                            <b>Mot de passe temporaire :</b> %s
                        </p>

                    </div>

                    <br>

                    <a href="http://localhost:4200/login"
                       style="
                        background:#4f46e5;
                        color:white;
                        padding:12px 20px;
                        border-radius:8px;
                        text-decoration:none;
                        display:inline-block;
                       ">

                        Se connecter

                    </a>

                </div>
            """.formatted(nom, to, password);

            helper.setText(html, true);

            mailSender.send(message);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Erreur lors de l'envoi de l'email",
                    e
            );
        }
    }
}