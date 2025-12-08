package com.bestpick.mocks;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bestpick.comments.dto.CommentRequestDto;
import com.bestpick.comments.repository.CommentsRepository;
import com.bestpick.comments.service.CommentsService;
import com.bestpick.testPosts.dto.TextPostDto;
import com.bestpick.testPosts.dto.TextPostRequestDto;
import com.bestpick.testPosts.repository.TextPostRepository;
import com.bestpick.testPosts.service.TextPostService;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class MockDatabaseSetter implements CommandLineRunner {

    @Value("${spring.profiles.active}")
    String profile;

    @Autowired
    CommentsRepository commentsRepository;

    @Autowired
    CommentsService commentsService;

    @Autowired
    TextPostRepository textPostRepository;

    @Autowired
    TextPostService textPostService;

    @Bean
    public void run(String... args) {

        if (profile.equals("dev")) {

            // delete DB
            commentsRepository.deleteAll();
            textPostRepository.deleteAll();

            // ===== CREACIÓN DE POSTS =====
            List<TextPostDto> posts = new ArrayList<>();

            TextPostDto p1 = textPostService.createTextPost(
                    new TextPostRequestDto(
                            "1",
                            """
                                    Primera prueba de post en BestPick 🎉
                                    Probando el feed, los comentarios y las notificaciones.
                                    ¿Quién se anima a comentar primero? 👇
                                    """));
            posts.add(p1);

            TextPostDto p2 = textPostService.createTextPost(
                    new TextPostRequestDto(
                            "3",
                            """
                                    Hoy he estado refactorizando un servicio enorme…
                                    A veces borrar código da más gusto que escribirlo 😌
                                    """));
            posts.add(p2);

            TextPostDto p3 = textPostService.createTextPost(
                    new TextPostRequestDto(
                            "5",
                            """
                                    ¿Team café ☕ o team té 🍵 para programar?
                                    Yo necesito al menos dos cafés para arrancar el día.
                                    """));
            posts.add(p3);

            TextPostDto p4 = textPostService.createTextPost(
                    new TextPostRequestDto(
                            "8",
                            """
                                    Acabo de terminar una maratón de series y ahora
                                    no sé qué ver… recomendaciones de sci-fi? 🚀
                                    """));
            posts.add(p4);

            TextPostDto p5 = textPostService.createTextPost(
                    new TextPostRequestDto(
                            "12",
                            """
                                    Playlist de lo-fi + lluvia de fondo + IDE abierto = combo perfecto 🎧🌧️
                                    ¿Alguien más programa con música casi siempre?
                                    """));
            posts.add(p5);

            TextPostDto p6 = textPostService.createTextPost(
                    new TextPostRequestDto(
                            "15",
                            """
                                    Hoy tocó escape room con el equipo 🧩
                                    Más difícil que entender un bug en producción 🤯
                                    """));
            posts.add(p6);

            TextPostDto p7 = textPostService.createTextPost(
                    new TextPostRequestDto(
                            "18",
                            """
                                    Nada como hacer hiking por la mañana y deploy por la tarde 🏔️🚀
                                    Balance perfecto entre naturaleza y código.
                                    """));
            posts.add(p7);

            TextPostDto p8 = textPostService.createTextPost(
                    new TextPostRequestDto(
                            "21",
                            """
                                    Probando una nueva API REST que hemos montado en el curro.
                                    El swagger ha quedado bastante limpio 😎
                                    """));
            posts.add(p8);

            TextPostDto p9 = textPostService.createTextPost(
                    new TextPostRequestDto(
                            "26",
                            """
                                    Me he pasado medio día peleándome con la configuración de OAuth2 😅
                                    Pero ya funciona el login con Google, por fin.
                                    """));
            posts.add(p9);

            TextPostDto p10 = textPostService.createTextPost(
                    new TextPostRequestDto(
                            "30",
                            """
                                    ¿Alguien más usa dark mode en TODO?
                                    IDE, sistema operativo, navegador… 🌑
                                    """));
            posts.add(p10);

            TextPostDto p11 = textPostService.createTextPost(
                    new TextPostRequestDto(
                            "35",
                            """
                                    Hoy ha sido día de logs, métricas y dashboards 📊
                                    Observabilidad > adivinar qué pasa en producción.
                                    """));
            posts.add(p11);

            TextPostDto p12 = textPostService.createTextPost(
                    new TextPostRequestDto(
                            "40",
                            """
                                    Viernes de refactors suaves y poca presión 🧹
                                    Ideal para dejar el repo un poco más limpio antes del finde.
                                    """));
            posts.add(p12);

            // ===== CREACIÓN DE COMENTARIOS =====

            // Post 1 (user 1)
            commentsService.postComment(
                    new CommentRequestDto(
                            "2",
                            "Aquí el primer comentario 🙋‍♂️ ¡Bienvenido a BestPick!",
                            p1.id()));
            commentsService.postComment(
                    new CommentRequestDto(
                            "4",
                            "Buen texto para probar el feed, se ve todo OK 👌",
                            p1.id()));
            commentsService.postComment(
                    new CommentRequestDto(
                            "10",
                            "Yo solo vengo por los emojis 😄",
                            p1.id()));

            // Post 2 (user 3)
            commentsService.postComment(
                    new CommentRequestDto(
                            "1",
                            "Refactorizar es terapéutico, totalmente de acuerdo 😌",
                            p2.id()));
            commentsService.postComment(
                    new CommentRequestDto(
                            "6",
                            "Mientras pases los tests, todo está bien 😂",
                            p2.id()));

            // Post 3 (user 5)
            commentsService.postComment(
                    new CommentRequestDto(
                            "7",
                            "Team café ☕ siempre. Sin café no hay código.",
                            p3.id()));
            commentsService.postComment(
                    new CommentRequestDto(
                            "9",
                            "Yo soy más de té, pero el café huele mejor 😅",
                            p3.id()));

            // Post 4 (user 8)
            commentsService.postComment(
                    new CommentRequestDto(
                            "11",
                            "Te recomiendo 'The Expanse' si no la has visto 🚀",
                            p4.id()));
            commentsService.postComment(
                    new CommentRequestDto(
                            "13",
                            "Si encuentras una buena serie sci-fi, comparte por aquí 🙌",
                            p4.id()));

            // Post 5 (user 12)
            commentsService.postComment(
                    new CommentRequestDto(
                            "14",
                            "Lo-fi para concentrarse es top 🔝",
                            p5.id()));
            commentsService.postComment(
                    new CommentRequestDto(
                            "16",
                            "Yo necesito silencio absoluto, soy un bicho raro 🙈",
                            p5.id()));

            // Post 6 (user 15)
            commentsService.postComment(
                    new CommentRequestDto(
                            "17",
                            "¿Escapasteis a tiempo? 😏",
                            p6.id()));
            commentsService.postComment(
                    new CommentRequestDto(
                            "19",
                            "Los bugs de producción son el escape room definitivo 😂",
                            p6.id()));

            // Post 7 (user 18)
            commentsService.postComment(
                    new CommentRequestDto(
                            "20",
                            "Suena a día perfecto, la verdad 👌",
                            p7.id()));
            commentsService.postComment(
                    new CommentRequestDto(
                            "22",
                            "Yo cambio el hiking por sofá, pero el deploy lo dejo 😅",
                            p7.id()));

            // Post 8 (user 21)
            commentsService.postComment(
                    new CommentRequestDto(
                            "23",
                            "Una buena API con buen swagger vale oro 💎",
                            p8.id()));
            commentsService.postComment(
                    new CommentRequestDto(
                            "24",
                            "¿Habéis publicado la documentación? Me interesa echarle un ojo.",
                            p8.id()));

            // Post 9 (user 26, google)
            commentsService.postComment(
                    new CommentRequestDto(
                            "27",
                            "OAuth2 siempre duele la primera vez 😂",
                            p9.id()));
            commentsService.postComment(
                    new CommentRequestDto(
                            "28",
                            "Bien ahí ese login con Google, calidad de vida para usuarios 🙌",
                            p9.id()));

            // Post 10 (user 30, google)
            commentsService.postComment(
                    new CommentRequestDto(
                            "29",
                            "Dark mode o nada. La luz blanca hace daño 😎",
                            p10.id()));
            commentsService.postComment(
                    new CommentRequestDto(
                            "31",
                            "Solo uso light mode para impresionar en las demos 😂",
                            p10.id()));

            // Post 11 (user 35, google)
            commentsService.postComment(
                    new CommentRequestDto(
                            "33",
                            "Sin métricas es imposible saber si algo va realmente bien.",
                            p11.id()));
            commentsService.postComment(
                    new CommentRequestDto(
                            "34",
                            "Totalmente, los dashboards son la nueva bola de cristal 🔮",
                            p11.id()));

            // Post 12 (user 40, google)
            commentsService.postComment(
                    new CommentRequestDto(
                            "36",
                            "Buen momento del año para limpiar el repo 👌",
                            p12.id()));
            commentsService.postComment(
                    new CommentRequestDto(
                            "38",
                            "Refactors de viernes: menos riesgo, más satisfacción 😄",
                            p12.id()));

        }
    };
}
