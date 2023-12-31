package tn.esprit.usermanagement.servicesImpl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.usermanagement.entities.Media;
import tn.esprit.usermanagement.repositories.MediaRepo;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class MediaService {
	 @Autowired
	 MediaRepo mediaRepo;

	    

	    public Optional<Media> getOne(Integer id){
	        return mediaRepo.findById(id);
	    }

	    public void save(Media media){
	    	mediaRepo.save(media);
	    }

	    public void delete(Integer id){
	    	mediaRepo.deleteById(id);
	    }

	    public boolean exists(Integer id){
	        return mediaRepo.existsById(id);
	    }
}
