package com.equipo_1.SkyShop.config;

import com.equipo_1.SkyShop.entity.Item;
import com.equipo_1.SkyShop.entity.User;
import com.equipo_1.SkyShop.entity.enums.Categories;
import com.equipo_1.SkyShop.entity.enums.UserRole;
import com.equipo_1.SkyShop.repository.ItemRepository;
import com.equipo_1.SkyShop.service.implementations.ItemService;
import com.equipo_1.SkyShop.service.implementations.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

@Configuration
public class DataInitializer {
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    @Bean
    public CommandLineRunner loadData(ItemRepository itemRepository) {
        return args -> {


            if (userService.findByEmail("sysadmin@example.com").isEmpty()) {
                userService.registerUser(User.builder()
                        .role(UserRole.ADMIN)
                        .username("sa")
                        .email("sysadmin@example.com")
                        .password("Admin")
                        .createdAt(LocalDateTime.now())
                        .build());
            }
            if(itemService.listItems().isEmpty()) {
                itemRepository.save(new Item(
                        null,
                        "Paquete Medicamentos",
                        1990f,
                        "Analgésicos (paracetamol ibuprofeno), Antiséptico para heridas, Bandas adhesivas (curitas), Jarabe para la tos",
                        Categories.SALUD_Y_BELLEZA,
                        new ArrayList<String>(Arrays.asList("/paquete_medicamentos.png")),
                        new ArrayList<String>(Arrays.asList(
                                "Alimentos",
                                "Higiene",
                                "Diversion",
                                "Mascotas",
                                "Perros"))
                ));
                itemRepository.save(new Item(
                        null,
                        "Paquete Cuidado Facial",
                        45099f,
                        "Limpiador facial, Tónico, Hidratante, Protector solar",
                        Categories.SALUD_Y_BELLEZA,
                        new ArrayList<>(Arrays.asList("/paquete_facial.png")),
                        new ArrayList<>(Arrays.asList(
                                "Alimentos",
                                "Higiene",
                                "Diversion",
                                "Mascotas",
                                "Perros"))
                ));
                itemRepository.save(new Item(
                        null,
                        "Paquete Almuerzo Rápido",
                        9670f,
                        "Wraps de pollo y vegetales, Fruta fresca (manzanas naranjas), Botellas de agua mineral, Barras de granola",
                        Categories.ALIMENTOS,
                        new ArrayList<>(Arrays.asList("/paquete_almuerzo.png")),
                        new ArrayList<>(Arrays.asList(
                                "Alimentos",
                                "Higiene",
                                "Diversion",
                                "Mascotas",
                                "Perros"))
                ));
                itemRepository.save(new Item(
                        null,
                        "Paquete Noche de Películas",
                        21700f,
                        "Palomitas de maíz, Chocolates y dulces, Snacks salados (papas fritas nachos)",
                        Categories.ALIMENTOS,
                        new ArrayList<>(Arrays.asList("/paquete_noche_peliculas.png")),
                        new ArrayList<>(Arrays.asList(
                                "Alimentos",
                                "Higiene",
                                "Diversion",
                                "Mascotas",
                                "Perros"))
                ));
                itemRepository.save(new Item(
                        null,
                        "Paquete Perro Feliz",
                        8990f,
                        "Comida premium para perros, Juguetes masticables, Premios para perros, Shampoo para perros",
                        Categories.MASCOTAS,
                        new ArrayList<>(Arrays.asList("/paquete_perro.png")),
                        new ArrayList<>(Arrays.asList(
                                "Alimentos",
                                "Higiene",
                                "Diversion",
                                "Mascotas",
                                "Perros"))
                ));
                itemRepository.save(new Item(
                        null,
                        "Paquete Gato Consentido",
                        1820f,
                        "Arena para gatos, Juguetes interactivos, Snacks para gatos",
                        Categories.MASCOTAS,
                        new ArrayList<>(Arrays.asList("/paquete_gato.png")),
                        new ArrayList<>(Arrays.asList(
                                "Alimentos",
                                "Higiene",
                                "Diversion",
                                "Mascotas",
                                "Perros"))
                ));
                itemRepository.save(new Item(
                        null,
                        "Paquete Oficina en Casa",
                        1425f,
                        "Cuadernos y bolígrafos, Café premium y tazas, Snacks saludables (frutos secos barras de proteína), Auriculares con micrófono",
                        Categories.OFICINA,
                        new ArrayList<>(Arrays.asList("/paquete_oficina_casa.png")),
                        new ArrayList<>(Arrays.asList(
                                "Alimentos",
                                "Higiene",
                                "Diversion",
                                "Mascotas",
                                "Perros"))
                ));
                itemRepository.save(new Item(
                        null,
                        "Paquete Oficina Organizada",
                        7600f,
                        "Organizadores de escritorio, Soporte para laptop, Calendario y planificador semanal, Grapadora y grapas",
                        Categories.OFICINA,
                        new ArrayList<>(Arrays.asList("/paquete_oficina_organizada.png")),
                        new ArrayList<>(Arrays.asList(
                                "Alimentos",
                                "Higiene",
                                "Diversion",
                                "Mascotas",
                                "Perros"))
                ));
                itemRepository.save(new Item(
                        null,
                        "Paquete dulce",
                        1535f,
                        "Barra de Kitkat, Ositos Mogul",
                        Categories.ALIMENTOS,
                        new ArrayList<>(Arrays.asList("/paquete_dulce.png")),
                        new ArrayList<>(Arrays.asList(
                                "Alimentos",
                                "Higiene",
                                "Diversion",
                                "Mascotas",
                                "Perros"))
                ));
                itemRepository.save(new Item(
                        null,
                        "Paquete Previa",
                        9900f,
                        "10 Latas Guiness Stout, Maní",
                        Categories.OFICINA,
                        new ArrayList<>(Arrays.asList("/paquete_previa.png")),
                        new ArrayList<>(Arrays.asList(
                                "Alimentos",
                                "Higiene",
                                "Diversion",
                                "Mascotas",
                                "Perros"))
                ));
                itemRepository.save(new Item(
                        null,
                        "Paquete Salado",
                        3100f,
                        "Doritos Sabor Queso, Papitas Lays Sabor Asado",
                        Categories.ALIMENTOS,
                        new ArrayList<>(Arrays.asList("https://raw.githubusercontent.com/MateoPane/SupermercadoParcial/main/imgs/doritos.png")),
                        new ArrayList<>(Arrays.asList(
                                "Alimentos",
                                "Higiene",
                                "Diversion",
                                "Mascotas",
                                "Perros"))
                ));
            }
        };

    }

}