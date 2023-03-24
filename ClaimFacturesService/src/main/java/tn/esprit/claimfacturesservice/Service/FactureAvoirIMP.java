package tn.esprit.claimfacturesservice.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.claimfacturesservice.Entities.Claim;
import tn.esprit.claimfacturesservice.Entities.FactureAvoir;
import tn.esprit.claimfacturesservice.Mapper.FactureAvoirMapper;
import tn.esprit.claimfacturesservice.Repository.FactureAvoirRepository;
import tn.esprit.claimfacturesservice.dtoEntities.FactureAvoirDto;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class FactureAvoirIMP implements FacturAvoirService {

    @Autowired
    FactureAvoirRepository factureAvoirRepository;
    @Override
    public FactureAvoir addFactureAvoir(FactureAvoir factureAvoir) {
        return factureAvoirRepository.save(factureAvoir);
    }

    @Override
    public FactureAvoir UpdateFactureAvoir(FactureAvoir factureAvoir) {
        return factureAvoirRepository.save(factureAvoir);
    }

    @Override
    public Boolean DeleteFactureAvoir(Long id) {
        if (factureAvoirRepository.existsById(id)) {
            factureAvoirRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public FactureAvoir retrieveFactureAvoirById(Long id) {
        return factureAvoirRepository.findById(id).orElse(null);
    }

    @Override
    public List<FactureAvoir> retrieveAllFactureAvoir() {
        return factureAvoirRepository.findAll();
    }




//
//    private final
//    FactureAvoirDto factureAvoirDto;
//
//    @Autowired
//    FactureAvoirRepository factureAvoirRepository;
//    @Override
//    public FactureAvoirDto addFactureAvoir(FactureAvoirDto factureAvoirDto) {
//        FactureAvoir factureAvoir =factureAvoirRepository.save(FactureAvoirMapper.mapFactureAvoirDtoToEntity(factureAvoirDto));
//        return FactureAvoirMapper.mapFactureAvoirToDto(factureAvoir);
//    }
//
//    @Override
//    public FactureAvoirDto UpdateFactureAvoir(FactureAvoirDto factureAvoirDto) {
//        FactureAvoir factureAvoir =factureAvoirRepository.save(FactureAvoirMapper.mapFactureAvoirDtoToEntity(factureAvoirDto));
//        return FactureAvoirMapper.mapFactureAvoirToDto(factureAvoir);
//    }
//
//    @Override
//    public Boolean DeleteFactureAvoir(Long id) {
//        if (factureAvoirRepository.existsById(id)) {
//            factureAvoirRepository.deleteById(id);
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    @Override
//    public FactureAvoirDto retrieveFactureAvoirById(Long id) {
//        FactureAvoir factureAvoir =factureAvoirRepository.findById(id).orElse(null);
//        return FactureAvoirMapper.mapFactureAvoirToDto(factureAvoir);
//    }
//
//    @Override
//    public List<FactureAvoirDto> retrieveAllFactureAvoir() {
//
//        return factureAvoirRepository.findAll().stream().
//                map(factureAvoir -> FactureAvoirMapper.mapFactureAvoirToDto(factureAvoir))
//                .collect(Collectors.toList());
//    }
}
