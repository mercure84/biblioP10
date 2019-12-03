package com.biblioP7.feignClient;


import com.biblioP7.beans.Livre;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name="livre-service", url="http://localhost:8080")
public interface LivreServiceClient {


    @GetMapping(value="/api/Livre/listeLivres")
    List<Livre> listeLivres(@RequestHeader("Authorization") String token);

    @GetMapping(value="/api/Livre/listeLivresDisponibles")
    List<Livre> listeLivresDisponibles(@RequestHeader("Authorization") String token);


    @GetMapping(value="/api/Livre/nbLivres")
    Map<String, Integer> nbLivres();


    @GetMapping(value="/api/Livre/{id}")
    Livre detailLivre(@RequestHeader("Authorization") String token, @PathVariable int id);

    @GetMapping(value="/api/Livre/randomLivre")
    Livre randomLivreDispo(@RequestHeader("Authorization") String token);

    @GetMapping(value="/api/Livre/filtrerLivres")
    List<Livre> filtrerLivres(@RequestHeader("Authorization") String token, @RequestParam(name="typeRecherche") String typeRecherche, @RequestParam(name="champRecherche") String champRecherche);

    @PostMapping(value="/api/Livre/ajouterLivre")
    void ajouterLivre(@RequestHeader("Authorization") String token, @RequestBody Livre livre);

}
