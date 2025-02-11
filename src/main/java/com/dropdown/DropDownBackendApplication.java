package com.dropdown;

import com.dropdown.entity.GPSLocation;
import com.dropdown.entity.ServiceProviderDAO;
import com.dropdown.service.ServiceProviderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DropDownBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DropDownBackendApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner commandLineRunner(UserRepository userRepository,
//                                               ServiceProviderRepository serviceProviderRepository,
//                                               PasswordEncoder passwordEncoder,
//                                               H3UberGridService h3UberGridService,
//                                               NominatimService nominatimService) {
//        return args -> {
//            Faker faker = new Faker();
//
//            List<Coordinates> indoreUserLocation = List.of(
//                    new Coordinates(22.7196, 75.8577), new Coordinates(22.7252, 75.8682),
//                    new Coordinates(22.7255, 75.8880), new Coordinates(22.7194, 75.8333),
//                    new Coordinates(22.7206, 75.9023), new Coordinates(22.7112, 75.8144),
//                    new Coordinates(22.7239, 75.8764), new Coordinates(22.7340, 75.8605),
//                    new Coordinates(22.7054, 75.8721), new Coordinates(22.7170, 75.8484),
//                    new Coordinates(22.7301, 75.8621), new Coordinates(22.7125, 75.8412),
//                    new Coordinates(22.7389, 75.8901), new Coordinates(22.6998, 75.8805),
//                    new Coordinates(22.7263, 75.8775), new Coordinates(22.7314, 75.8543),
//                    new Coordinates(22.7402, 75.8719), new Coordinates(22.7138, 75.8876),
//                    new Coordinates(22.7475, 75.8423), new Coordinates(22.7089, 75.8572),
//                    new Coordinates(22.7190, 75.8994), new Coordinates(22.7274, 75.8168),
//                    new Coordinates(22.7095, 75.8777), new Coordinates(22.7378, 75.8654),
//                    new Coordinates(22.7465, 75.8547), new Coordinates(22.7293, 75.8362),
//                    new Coordinates(22.7188, 75.8105), new Coordinates(22.7269, 75.8815),
//                    new Coordinates(22.7192, 75.8284), new Coordinates(22.7385, 75.8512),
//                    new Coordinates(22.7014, 75.8633), new Coordinates(22.7437, 75.8724),
//                    new Coordinates(22.7278, 75.8911), new Coordinates(22.7345, 75.8576),
//                    new Coordinates(22.7211, 75.8839), new Coordinates(22.7408, 75.8495),
//                    new Coordinates(22.7502, 75.8701), new Coordinates(22.7132, 75.8659),
//                    new Coordinates(22.7356, 75.8125), new Coordinates(22.7173, 75.8221),
//                    new Coordinates(22.7286, 75.8898), new Coordinates(22.7319, 75.8807),
//                    new Coordinates(22.7413, 75.8379), new Coordinates(22.7459, 75.8195),
//                    new Coordinates(22.7090, 75.8339), new Coordinates(22.7165, 75.8533),
//                    new Coordinates(22.7250, 75.8661), new Coordinates(22.7105, 75.8917),
//                    new Coordinates(22.7197, 75.8755), new Coordinates(22.7440, 75.8609),
//                    new Coordinates(22.7509, 75.8448), new Coordinates(22.7391, 75.8252),
//                    new Coordinates(22.7044, 75.8783), new Coordinates(22.7258, 75.8593),
//                    new Coordinates(22.7155, 75.8962), new Coordinates(22.7372, 75.8685),
//                    new Coordinates(22.7311, 75.8832), new Coordinates(22.7448, 75.8327),
//                    new Coordinates(22.7058, 75.8518), new Coordinates(22.7119, 75.8194),
//                    new Coordinates(22.7337, 75.8372), new Coordinates(22.7471, 75.8109),
//                    new Coordinates(22.7092, 75.8747), new Coordinates(22.7260, 75.8441),
//                    new Coordinates(22.7308, 75.8674), new Coordinates(22.7381, 75.8552),
//                    new Coordinates(22.7493, 75.8905), new Coordinates(22.7225, 75.8078),
//                    new Coordinates(22.7183, 75.8439), new Coordinates(22.7352, 75.8746),
//                    new Coordinates(22.7462, 75.8293), new Coordinates(22.7231, 75.8135),
//                    new Coordinates(22.7400, 75.8870), new Coordinates(22.7198, 75.8215),
//                    new Coordinates(22.7427, 75.8557), new Coordinates(22.7152, 75.8772),
//                    new Coordinates(22.7505, 75.8786), new Coordinates(22.7082, 75.8579),
//                    new Coordinates(22.7215, 75.8707), new Coordinates(22.7267, 75.8564),
//                    new Coordinates(22.7350, 75.8869), new Coordinates(22.7443, 75.8132),
//                    new Coordinates(22.7317, 75.8691), new Coordinates(22.7478, 75.8357),
//                    new Coordinates(22.7383, 75.8820), new Coordinates(22.7176, 75.8073),
//                    new Coordinates(22.7048, 75.8535), new Coordinates(22.7115, 75.8893),
//                    new Coordinates(22.7333, 75.8114), new Coordinates(22.7485, 75.8816),
//                    new Coordinates(22.7204, 75.8262), new Coordinates(22.7257, 75.8477),
//                    new Coordinates(22.7306, 75.8189), new Coordinates(22.7435, 75.8778),
//                    new Coordinates(22.7370, 75.8894), new Coordinates(22.7140, 75.8296),
//                    new Coordinates(22.7023, 75.8741), new Coordinates(22.7086, 75.8382),
//                    new Coordinates(22.7500, 75.8121), new Coordinates(22.7399, 75.8695)
//            );
//
//
//            List<Coordinates> indoreServiceProviderLocations = List.of(
//                    new Coordinates(22.7512, 75.8603), new Coordinates(22.7429, 75.8721),
//                    new Coordinates(22.7331, 75.8889), new Coordinates(22.7265, 75.8992),
//                    new Coordinates(22.7158, 75.8536), new Coordinates(22.7043, 75.8824),
//                    new Coordinates(22.7187, 75.8149), new Coordinates(22.7305, 75.8767),
//                    new Coordinates(22.7407, 75.8642), new Coordinates(22.7504, 75.8389),
//                    new Coordinates(22.7289, 75.8475), new Coordinates(22.7171, 75.8614),
//                    new Coordinates(22.7019, 75.8923), new Coordinates(22.7449, 75.8560),
//                    new Coordinates(22.7097, 75.8408), new Coordinates(22.7355, 75.8184),
//                    new Coordinates(22.7238, 75.8751), new Coordinates(22.7313, 75.8990),
//                    new Coordinates(22.7470, 75.8115), new Coordinates(22.7059, 75.8727),
//                    new Coordinates(22.7134, 75.8293), new Coordinates(22.7508, 75.8789),
//                    new Coordinates(22.7199, 75.8704), new Coordinates(22.7083, 75.8607),
//                    new Coordinates(22.7387, 75.8452), new Coordinates(22.7423, 75.8891),
//                    new Coordinates(22.7281, 75.8156), new Coordinates(22.7139, 75.8508),
//                    new Coordinates(22.7495, 75.8647), new Coordinates(22.7342, 75.8105),
//                    new Coordinates(22.7272, 75.8836), new Coordinates(22.7193, 75.8394),
//                    new Coordinates(22.7358, 75.8903), new Coordinates(22.7467, 75.8124),
//                    new Coordinates(22.7222, 75.8976), new Coordinates(22.7375, 75.8574),
//                    new Coordinates(22.7441, 75.8472), new Coordinates(22.7506, 75.8591),
//                    new Coordinates(22.7118, 75.8872), new Coordinates(22.7052, 75.8415),
//                    new Coordinates(22.7476, 75.8790), new Coordinates(22.7297, 75.8913),
//                    new Coordinates(22.7380, 75.8618), new Coordinates(22.7246, 75.8725),
//                    new Coordinates(22.7147, 75.8106), new Coordinates(22.7261, 75.8984),
//                    new Coordinates(22.7510, 75.8219), new Coordinates(22.7433, 75.8857),
//                    new Coordinates(22.7310, 75.8578), new Coordinates(22.7492, 75.8345),
//                    new Coordinates(22.7091, 75.8467), new Coordinates(22.7253, 75.8192),
//                    new Coordinates(22.7349, 75.8781), new Coordinates(22.7415, 75.8524),
//                    new Coordinates(22.7129, 75.8283), new Coordinates(22.7488, 75.8656),
//                    new Coordinates(22.7302, 75.8103), new Coordinates(22.7214, 75.8825),
//                    new Coordinates(22.7401, 75.8972), new Coordinates(22.7151, 75.8427),
//                    new Coordinates(22.7279, 75.8689), new Coordinates(22.7430, 75.8117),
//                    new Coordinates(22.7499, 75.8805), new Coordinates(22.7398, 75.8629),
//                    new Coordinates(22.7087, 75.8499), new Coordinates(22.7228, 75.8306),
//                    new Coordinates(22.7359, 75.8863), new Coordinates(22.7468, 75.8442),
//                    new Coordinates(22.7233, 75.8799), new Coordinates(22.7419, 75.8396),
//                    new Coordinates(22.7191, 75.8014), new Coordinates(22.7270, 75.8957),
//                    new Coordinates(22.7514, 75.8331), new Coordinates(22.7446, 75.8505),
//                    new Coordinates(22.7320, 75.8179), new Coordinates(22.7474, 75.8768),
//                    new Coordinates(22.7109, 75.8592), new Coordinates(22.7264, 75.8240),
//                    new Coordinates(22.7347, 75.8829), new Coordinates(22.7503, 75.8197),
//                    new Coordinates(22.7136, 75.8998), new Coordinates(22.7491, 75.8539),
//                    new Coordinates(22.7386, 75.8720), new Coordinates(22.7195, 75.8648),
//                    new Coordinates(22.7452, 75.8783), new Coordinates(22.7283, 75.8098),
//                    new Coordinates(22.7351, 75.8602), new Coordinates(22.7472, 75.8194),
//                    new Coordinates(22.7259, 75.8817), new Coordinates(22.7409, 75.8550),
//                    new Coordinates(22.7121, 75.8336), new Coordinates(22.7501, 75.8927),
//                    new Coordinates(22.7373, 75.8993), new Coordinates(22.7159, 75.8058)
//            );
//
//
//
//            for (int i = 0; i < 100; i++) {
//                userRepository.save(
//                        User.builder()
//                                .name(faker.name().fullName())
//                                .email(faker.internet().emailAddress())
//                                .password(passwordEncoder.encode(faker.internet().password()))
//                                .phoneNo(faker.phoneNumber().cellPhone())
//                                .role(Role.USER)
//                                .city(nominatimService.getCityName(indoreUserLocation.get(i).lat, indoreUserLocation.get(i).lon))
//                                .location(new GPSLocation(
//                                        indoreUserLocation.get(i).lat,
//                                        indoreUserLocation.get(i).lon,
//                                        h3UberGridService.getGridAddress(indoreUserLocation.get(i))
//                                ))
//                                .build()
//                );
//                serviceProviderRepository.save(
//                        ServiceProvider.builder()
//                                .name(faker.name().fullName())
//                                .email(faker.internet().emailAddress())
//                                .password(passwordEncoder.encode(faker.internet().password()))
//                                .phoneNo(faker.phoneNumber().cellPhone())
//                                .role(Role.SERVICE_PROVIDER)
//                                .city(nominatimService.getCityName(indoreServiceProviderLocations.get(i).lat, indoreServiceProviderLocations.get(i).lon))
//                                .location(new GPSLocation(
//                                        indoreUserLocation.get(i).lat,
//                                        indoreUserLocation.get(i).lon,
//                                        h3UberGridService.getGridAddress(indoreUserLocation.get(i))
//                                ))
//                                .build()
//                );
//            }
//
//        };
//
//    }

    @Bean
    public CommandLineRunner commandLineRunner(
            ServiceProviderService serviceProviderService
    ) {
        return args -> {
//            System.out.println(serviceProviderService.getServiceProvidersInArea(new GPSLocation(
//                    22.7286,
//                    75.8898,
//                    "873d92926ffffff"
//            )).size());
        };
    }

}
