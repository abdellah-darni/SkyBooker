package skybooker.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skybooker.server.DTO.AeroportDTO;
import skybooker.server.entity.Aeroport;
import skybooker.server.repository.AeroportRepository;
import skybooker.server.service.AeroportService;
import skybooker.server.service.VilleService;

import java.util.List;
import java.util.Optional;

@Service
public class AeroportServiceImpl implements AeroportService {

    private final AeroportRepository aeroportRepository;

    public AeroportServiceImpl(AeroportRepository aeroportRepository, VilleService villeService) {
        this.aeroportRepository = aeroportRepository;
        this.villeService = villeService;
    }

    private final VilleService villeService;

    @Override
    @Transactional(readOnly = true)
    public List<Aeroport> findAll() {
        return aeroportRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Aeroport findById(Long aLong) {
        Optional<Aeroport> aeroport = aeroportRepository.findById(aLong);
        return aeroport.orElse(null);
    }

    @Override
    @Transactional
    public Aeroport create(Aeroport entity) {
        return aeroportRepository.save(entity);
    }

    @Override
    @Transactional
    public Aeroport createDTO(AeroportDTO entity) {
        Aeroport aeroport = new Aeroport(entity);
        aeroport.setVille(villeService.findById(entity.getVilleId()));
        return aeroportRepository.save(aeroport);
    }

    @Override
    @Transactional
    public Aeroport update(Aeroport entity) {
        Aeroport aeroport = findById(entity.getId());
        if (aeroport != null) {
            // modifying the airport
            aeroport.setVille(entity.getVille());
            aeroport.setNom(entity.getNom());
            aeroport.setIataCode(entity.getIataCode());
            aeroport.setIcaoCode(entity.getIcaoCode());
            aeroport.setLatitude(entity.getLatitude());
            aeroport.setLongitude(entity.getLongitude());

            // saving the modifications
            return aeroportRepository.save(aeroport);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public Aeroport updateDTO(AeroportDTO entity) {
        Aeroport aeroport = findById(entity.getId());
        if (aeroport != null) {
            // modifying the airport
            aeroport.setNom(entity.getNom());
            aeroport.setIataCode(entity.getIataCode());
            aeroport.setIcaoCode(entity.getIcaoCode());
            aeroport.setLatitude(entity.getLatitude());
            aeroport.setLongitude(entity.getLongitude());
            aeroport.setVille(villeService.findById(entity.getVilleId()));

            // saving the modifications
            return aeroportRepository.save(aeroport);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void deleteById(Long aLong) {
        aeroportRepository.deleteById(aLong);
    }

    @Override
    @Transactional
    public void delete(Aeroport entity) {
        aeroportRepository.delete(entity);
    }

}
