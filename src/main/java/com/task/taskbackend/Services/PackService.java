package com.task.taskbackend.Services;

import com.task.taskbackend.Models.Pack;
import com.task.taskbackend.Models.PrixCondition;
import com.task.taskbackend.Models.Produits;
import com.task.taskbackend.Models.Stock;
import com.task.taskbackend.Repositories.PackRepository;
import com.task.taskbackend.Repositories.ProduitsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PackService {

    private  final PackRepository packRepository;
    private  final ProduitsRepository produitsRepository;




    public List<Pack> getAllPacks() {
        return packRepository.findAll();
    }

    public Pack getPackById(Long id) {
        return packRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pack not found with id: " + id));
    }

    public Pack addPack(Pack pack, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            pack.setDisplayPicture(file.getBytes());
        }


        return packRepository.save(pack);
    }
    public void deletePack(Long id) {
        packRepository.deleteById(id);
    }
    public Pack AddProductToPack(Long packID, Long ProductID) throws IOException {

        Pack pack= packRepository.findById(packID).orElseThrow(null);
        Produits produit= produitsRepository.findById(ProductID).orElseThrow(null);


        List<Produits> produitList=pack.getProduits();

        if(produitList==null){
            produitList=new ArrayList<>();
            pack.setProduits(produitList);
        }
        produitList.add(produit);


        return packRepository.save(pack);
    }

    public List<Produits> RemiseProduits(Long packId, double discountValue, PrixCondition prixCondition) {
        Pack pack = packRepository.findById(packId)
                .orElseThrow(() -> new RuntimeException("Pack not found"));

        for (Produits produit : pack.getProduits()) {
            double discountedPriceHt = produit.getPrixHt();

            if (prixCondition == PrixCondition.DISCOUNT) {
                discountedPriceHt -= discountValue;
            } else if (prixCondition == PrixCondition.PERCENTAGE) {
                discountedPriceHt -= discountedPriceHt * (discountValue / 100);
            }

            produit.setPrixHt((float) discountedPriceHt);

            produitsRepository.save(produit);
        }

        return pack.getProduits();
    }


}
