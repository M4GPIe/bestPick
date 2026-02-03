package com.bestpick.mocks;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bestpick.model.User;
import com.bestpick.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class MockDatabaseSetter implements CommandLineRunner {

    @Value("${spring.profiles.active:localhost}")
    String profile;

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) {

        if (!profile.equals("prod")) {

            // delete DB
            userRepository.deleteAll();

            // Add mock users
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            List<User> users = new ArrayList<>();

            // ===== USERS WITH INTERNAL LOGIN (1–25) =====
            users.add(internalUser("user01", "Password01!", "Amante de la tecnología y el café ☕️",
                    "https://picsum.photos/seed/user01/300/300", encoder));
            users.add(internalUser("user02", "Password02!", "Runner de madrugada y lector empedernido 📚",
                    "https://picsum.photos/seed/user02/300/300", encoder));
            users.add(internalUser("user03", "Password03!", "Fan del cine clásico y las palomitas 🎬",
                    "https://picsum.photos/seed/user03/300/300", encoder));
            users.add(internalUser("user04", "Password04!", "Programador nocturno, gamer diurno 🎮",
                    "https://picsum.photos/seed/user04/300/300", encoder));
            users.add(internalUser("user05", "Password05!",
                    "Siempre buscando la mejor pizza de la ciudad 🍕",
                    "https://picsum.photos/seed/user05/300/300", encoder));
            users.add(internalUser("user06", "Password06!", "Amante de los gatos y del buen código 😺",
                    "https://picsum.photos/seed/user06/300/300", encoder));
            users.add(internalUser("user07", "Password07!", "Viajar, fotografiar y repetir ✈️📷",
                    "https://picsum.photos/seed/user07/300/300", encoder));
            users.add(internalUser("user08", "Password08!", "Serieadict@ profesional 📺",
                    "https://picsum.photos/seed/user08/300/300", encoder));
            users.add(internalUser("user09", "Password09!", "Café, teclado mecánico y TypeScript ☕⌨️",
                    "https://picsum.photos/seed/user09/300/300", encoder));
            users.add(internalUser("user10", "Password10!",
                    "Minimalismo y productividad como estilo de vida ✅",
                    "https://picsum.photos/seed/user10/300/300", encoder));
            users.add(internalUser("user11", "Password11!", "Aprendiendo algo nuevo cada día 🤓",
                    "https://picsum.photos/seed/user11/300/300", encoder));
            users.add(internalUser("user12", "Password12!", "Música lo-fi y líneas de código 🎧",
                    "https://picsum.photos/seed/user12/300/300", encoder));
            users.add(internalUser("user13", "Password13!", "Coleccionista de tazas frikis 🦄",
                    "https://picsum.photos/seed/user13/300/300", encoder));
            users.add(internalUser("user14", "Password14!", "Backend lover, pero UI-friendly 😉",
                    "https://picsum.photos/seed/user14/300/300", encoder));
            users.add(internalUser("user15", "Password15!", "Fan de los puzzles y los escape rooms 🔐",
                    "https://picsum.photos/seed/user15/300/300", encoder));
            users.add(internalUser("user16", "Password16!", "Haciendo side projects eternamente en beta 🧪",
                    "https://picsum.photos/seed/user16/300/300", encoder));
            users.add(internalUser("user17", "Password17!", "Apasionado del testing y el TDD ✅",
                    "https://picsum.photos/seed/user17/300/300", encoder));
            users.add(internalUser("user18", "Password18!", "Entre montañas y commits 🏔️",
                    "https://picsum.photos/seed/user18/300/300", encoder));
            users.add(internalUser("user19", "Password19!", "Buscador oficial de bugs 🐞",
                    "https://picsum.photos/seed/user19/300/300", encoder));
            users.add(internalUser("user20", "Password20!",
                    "Jugador de equipo y amante del pair programming 👥",
                    "https://picsum.photos/seed/user20/300/300", encoder));
            users.add(internalUser("user21", "Password21!", "Friki de las APIs y los microservicios 🌐",
                    "https://picsum.photos/seed/user21/300/300", encoder));
            users.add(internalUser("user22", "Password22!", "Té verde, refactors y buenas prácticas 🍵",
                    "https://picsum.photos/seed/user22/300/300", encoder));
            users.add(internalUser("user23", "Password23!", "Huerto urbano y código limpio 🌱",
                    "https://picsum.photos/seed/user23/300/300", encoder));
            users.add(internalUser("user24", "Password24!", "Aprendiendo Kubernetes sin llorar… casi 🐳",
                    "https://picsum.photos/seed/user24/300/300", encoder));
            users.add(internalUser("user25", "Password25!", "De hackathones y noches sin dormir 🌙",
                    "https://picsum.photos/seed/user25/300/300", encoder));

            // ===== USUARIOS CON LOGIN EXTERNO GOOGLE (26–50) =====
            users.add(googleUser("user26", "Full stack en modo curiosidad infinita 🔍",
                    "https://picsum.photos/seed/user26/300/300", "google.com", "google-uid-00026"));
            users.add(googleUser("user27", "Orgullos@ de su to-do list (aunque nunca esté vacía) 📋",
                    "https://picsum.photos/seed/user27/300/300", "google.com", "google-uid-00027"));
            users.add(googleUser("user28", "Java, Kotlin y todo lo que se le parezca ☕",
                    "https://picsum.photos/seed/user28/300/300", "google.com", "google-uid-00028"));
            users.add(googleUser("user29", "Fan de los patrones de diseño y los diagramas UML 📊",
                    "https://picsum.photos/seed/user29/300/300", "google.com", "google-uid-00029"));
            users.add(googleUser("user30", "Siempre con música de fondo mientras codeo 🎶",
                    "https://picsum.photos/seed/user30/300/300", "google.com", "google-uid-00030"));
            users.add(googleUser("user31", "Haciendo refactor de mi propia vida 🔁",
                    "https://picsum.photos/seed/user31/300/300", "google.com", "google-uid-00031"));
            users.add(googleUser("user32", "Ciclista de fin de semana y developer entre semana 🚴",
                    "https://picsum.photos/seed/user32/300/300", "google.com", "google-uid-00032"));
            users.add(googleUser("user33", "API-first y documentación al día 📘",
                    "https://picsum.photos/seed/user33/300/300", "google.com", "google-uid-00033"));
            users.add(googleUser("user34", "Intentando no romper producción… otra vez 😅",
                    "https://picsum.photos/seed/user34/300/300", "google.com", "google-uid-00034"));
            users.add(googleUser("user35", "DevOps wannabe y fan de los logs 📜",
                    "https://picsum.photos/seed/user35/300/300", "google.com", "google-uid-00035"));
            users.add(googleUser("user36", "Leyendo sobre arquitectura hexagonal por diversión 🧱",
                    "https://picsum.photos/seed/user36/300/300", "google.com", "google-uid-00036"));
            users.add(googleUser("user37", "Me verás en los pull requests de madrugada 🌙",
                    "https://picsum.photos/seed/user37/300/300", "google.com", "google-uid-00037"));
            users.add(googleUser("user38", "Fan de los side projects que nunca acaban 🧩",
                    "https://picsum.photos/seed/user38/300/300", "google.com", "google-uid-00038"));
            users.add(googleUser("user39", "Intentando mantener a raya la deuda técnica 🧹",
                    "https://picsum.photos/seed/user39/300/300", "google.com", "google-uid-00039"));
            users.add(googleUser("user40", "Code reviews con cariño y sinceridad 💬",
                    "https://picsum.photos/seed/user40/300/300", "google.com", "google-uid-00040"));
            users.add(googleUser("user41", "Arquitectura, diseño y cafés infinitos ☕",
                    "https://picsum.photos/seed/user41/300/300", "google.com", "google-uid-00041"));
            users.add(googleUser("user42", "Entre bugs y features, vamos tirando 🐛✨",
                    "https://picsum.photos/seed/user42/300/300", "google.com", "google-uid-00042"));
            users.add(googleUser("user43", "Fan del dark mode en todo 🌑",
                    "https://picsum.photos/seed/user43/300/300", "google.com", "google-uid-00043"));
            users.add(googleUser("user44", "Testing, CI/CD y chocolate negro 🍫",
                    "https://picsum.photos/seed/user44/300/300", "google.com", "google-uid-00044"));
            users.add(googleUser("user45", "Microservicios pero sin dramas, por favor 🙏",
                    "https://picsum.photos/seed/user45/300/300", "google.com", "google-uid-00045"));
            users.add(googleUser("user46",
                    "Aficionad@ a los juegos de mesa y a los diagramas de secuencia 🎲",
                    "https://picsum.photos/seed/user46/300/300", "google.com", "google-uid-00046"));
            users.add(googleUser("user47", "Fan del Clean Architecture y del buen café ☕",
                    "https://picsum.photos/seed/user47/300/300", "google.com", "google-uid-00047"));
            users.add(googleUser("user48", "Pendiente de las últimas tendencias en frameworks web 🌐",
                    "https://picsum.photos/seed/user48/300/300", "google.com", "google-uid-00048"));
            users.add(googleUser("user49", "Construyendo side projects que ojalá use alguien algún día 🚀",
                    "https://picsum.photos/seed/user49/300/300", "google.com", "google-uid-00049"));
            users.add(googleUser("user50", "Entre pull requests y café, se hace el día ☕✅",
                    "https://picsum.photos/seed/user50/300/300", "google.com", "google-uid-00050"));

            userRepository.saveAll(users);

        }
    };

    private User internalUser(String username,
            String plainPassword,
            String description,
            String profileImagePath,
            BCryptPasswordEncoder encoder) {
        User u = new User();
        u.setUsername(username);
        u.setPasswordHash(encoder.encode(plainPassword));
        u.setDescription(description);
        u.setProfileImagePath(profileImagePath);
        u.setIss(null);
        u.setUid(null);
        return u;
    }

    private User googleUser(String username,
            String description,
            String profileImagePath,
            String iss,
            String sub) {
        User u = new User();
        u.setUsername(username);
        u.setPasswordHash(null);
        u.setDescription(description);
        u.setProfileImagePath(profileImagePath);
        u.setIss(iss);
        u.setUid(sub);
        return u;
    }
}
