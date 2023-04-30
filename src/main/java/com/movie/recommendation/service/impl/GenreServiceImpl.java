package com.movie.recommendation.service.impl;
import com.movie.recommendation.dto.GenreDto;
import com.movie.recommendation.helper.QueryClass;
import com.movie.recommendation.model.Genre;
import com.movie.recommendation.model.User;
import com.movie.recommendation.repo.GenreRepository;
import com.movie.recommendation.repo.UserRepository;
import com.movie.recommendation.service.GenreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private QueryClass queryClass;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public GenreDto createGenre(GenreDto genreDto, Principal principal)  {
        User retrievedUser=userRepository.findByUserEmail(principal.getName());
        Genre genre=new Genre();
        genre.setGenreName(genreDto.getGenreName());
        genre.setGenreDescription(genreDto.getGenreDescription());
        genre.setUser(retrievedUser);
        genre=genreRepository.save(genre);
        genreDto.setGenreId(genre.getGenreId());
        return genreDto;
    }

    @Override
    public List<GenreDto> getGenreByUser(Long userId) {
        List<GenreDto> genres=new ArrayList<>();
        User user=queryClass.getUserById(userId);
        List<Genre> genreList=genreRepository.findByUser(user);
        if(genreList.isEmpty()){
            return null;
        }
         genres=genreList.stream().map(genre -> modelMapper.map(genre,GenreDto.class)).collect(Collectors.toList());

        return genres;
    }

    @Override
    public String updateGenre(GenreDto genreDto) throws Exception {

        Optional<Genre> genre=genreRepository.findById(genreDto.getGenreId());
        if(genreDto.getGenreName()!=null){
            genre.get().setGenreName(genreDto.getGenreName());
        }
        if(genreDto.getGenreDescription()!=null){
            genre.get().setGenreDescription(genreDto.getGenreDescription());
        }
        genreRepository.save(genre.get());
        String message="genre updated successfully!!!";
        return message;
    }

    @Override
    public String deleteGenre(Long genreId,Principal principal) throws Exception {
        String deleteMessage="";
        User retrievedUser=userRepository.findByUserEmail(principal.getName());
        Optional<Genre> genre=genreRepository.findById(genreId);
        if(retrievedUser.getUserId().equals(genre.get().getUser().getUserId())){
            genre.get().setUser(null);
            genreRepository.delete(genre.get());
            deleteMessage="genre deleted successfully";
        }else {
            return null;
        }
        return deleteMessage;
    }



    @Override
    public GenreDto getGenreByName(String genreName){
        Genre genre=genreRepository.findByGenreName(genreName);
        if(genre==null){
            return null;
        }
        return modelMapper.map(genre,GenreDto.class);
    }
}
