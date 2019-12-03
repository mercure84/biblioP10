package com.biblioP7.feignClient;

import com.biblioP7.beans.CreationEmprunt;
import com.biblioP7.beans.Emprunt;
import com.biblioP7.beans.Livre;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="emprunt-service", url="http://localhost:8080")
public interface EmpruntServiceClient {


    @GetMapping(value="/api/listeEmprunts")
    List<Emprunt> listeEmprunts(@RequestHeader("Authorization") String token);

    @GetMapping(value="/api/listeEmpruntsEncours")
    List<Emprunt> listeEmpruntsEncours(@RequestHeader("Authorization") String token);

    @GetMapping(value="/api/EmpruntsMembre/{membreId}")
    List<Emprunt> empruntsParMembre(@RequestHeader("Authorization") String token, @PathVariable int membreId);

    @GetMapping(value="/api/Emprunt/{empruntId}")
    Emprunt detailEmprunt(@RequestHeader("Authorization") String token, @PathVariable int empruntId);


    @PostMapping(value="/api/creerEmprunt")
    Emprunt creerEmprunt(@RequestHeader("Authorization") String token, @RequestBody CreationEmprunt creationEmprunt);


    @GetMapping(value="/api/prolongerEmprunt/{id}")
    Emprunt prolongerEmprunt(@RequestHeader("Authorization") String token, @PathVariable int id);


    @GetMapping(value="/api/stopperEmprunt/{id}")
    Livre livreRendu(@RequestHeader("Authorization") String token, @PathVariable int id);


    @CrossOrigin("*")
    @GetMapping(value="/api/listeEmpruntsExpires")
    List<Emprunt> empruntsExpires (@RequestHeader("Authorization") String token);

    @GetMapping(value="/api/runBatch")
    String lancerBatch(@RequestHeader("Authorization") String token);



}
