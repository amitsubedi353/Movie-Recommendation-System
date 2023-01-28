package com.movie.recommendation.service.impl;

import com.movie.recommendation.Exception.ResourcenotFoundException;
import com.movie.recommendation.helper.QueryClass;
import com.movie.recommendation.model.Genre;
import com.movie.recommendation.model.Role;
import com.movie.recommendation.model.User;
import com.movie.recommendation.model.userRole;
import com.movie.recommendation.repo.GenreRepository;
import com.movie.recommendation.repo.RoleRepository;
import com.movie.recommendation.repo.UserRepository;
import com.movie.recommendation.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private QueryClass queryClass;

    @Override
    public Genre createGenre(Long userId, Genre genre,List<Role> roles) throws Exception {
        User retrievedUser=queryClass.getUserById(userId);
        Genre retrievedGenre=this.genreRepository.findByGenreName(genre.getGenreName());

        if(retrievedGenre!=null)

        {
            throw new Exception("Genre with the given name "+genre.getGenreName()+" already exist");

        }else {
            for (Role eachRole : roles
            ) {
                Role role = this.roleRepository.findByRoleName(eachRole.getRoleName());
                List<userRole> userRoles = retrievedUser.getUserRoles();
                for (userRole eachRole1 : userRoles
                ) {
                    //System.out.println(eachRole1);
                    if (role.getRoleName().equals("ADMIN")&&eachRole1.getRole().getRoleName().equals("ADMIN")) {

                        LocalDateTime now=LocalDateTime.now();
                        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                        String formatDateTime=now.format(dateTimeFormatter);
                        genre.setGenreCreated(formatDateTime);


                        retrievedUser.getGenreList().add(genre);

                        genre.setUser(retrievedUser);
                        retrievedGenre = this.genreRepository.save(genre);
                    }

                }

            }
        }







        return retrievedGenre;
    }

    @Override
    public List<Genre> getGenreByUser(Long userId,List<Role> roles) {
        List<Genre> genres=null;


        User user=queryClass.getUserById(userId);
        for (Role eachRole:roles
             ) {
            Role role=this.roleRepository.findByRoleName(eachRole.getRoleName());
            List<userRole> userRoleList=user.getUserRoles();
            for (userRole eachUserRole:userRoleList
                 ) {
                if((eachUserRole.getRole().getRoleName().equalsIgnoreCase("admin")||eachUserRole.getRole().getRoleName().equalsIgnoreCase("normal"))&&(role.getRoleName().equalsIgnoreCase("normal")||role.getRoleName().equals("admin"))){
                     genres=this.genreRepository.findByUser(user);
                }

            }
            
        }








        return genres;
    }

    @Override
    public Genre updateGenre(Long userId,Long genreId, Genre genre,List<Role> roles) throws Exception {
        User retrievedUser=queryClass.getUserById(userId);
        Genre genre1=queryClass.getgenreById(genreId);
        List<Genre> genreList=retrievedUser.getGenreList();

        Genre  updatedGenre=null;
        if(genreList==null){
            throw new Exception("No genre has been created by the user:"+userId);

        }
        else {
            for (Genre eachGenre:genreList
                 ) {
                if(eachGenre.getUser().getUserId().equals(genre1.getUser().getUserId())){
                    for (Role role:roles
                         ) {
                        Role retrievedRole=this.roleRepository.findByRoleName(role.getRoleName());
                        if(retrievedRole==null){
                            throw new Exception("No role has been found by the given role name");
                        }
                        else{
                            List<userRole> userRoles=retrievedUser.getUserRoles();
                            for (userRole eachUserRole:userRoles
                                 ) {
                                if(eachUserRole.getRole().getRoleName().equals("ADMIN")&&retrievedRole.getRoleName().equals("ADMIN")){
                                   eachGenre.setGenreName(genre.getGenreName());
                                   eachGenre.setGenreCreated(genre.getGenreCreated());
                                   eachGenre.setGenreDescription(genre.getGenreDescription());
                                    updatedGenre=this.genreRepository.save(eachGenre);
                                }
                            }

                        }

                    }
                }

            }

        }






        return updatedGenre;
    }

    @Override
    public String deleteGenre(Long userId, Long genreId, List<Role> roles) throws Exception {

        String deleteMessage="";

        Genre retrievedGenre=queryClass.getgenreById(genreId);
        User retrievedUser=queryClass.getUserById(userId);

        for (Role role:roles
             ) {
                Role retrievedRole=this.roleRepository.findByRoleName(role.getRoleName());
                if(retrievedRole==null){
                    throw new Exception("role with the given roleName doesnot exist!!");
                }
                else {
                    List<userRole> userRoles=retrievedUser.getUserRoles();
                    for (userRole eachUserRole:userRoles
                         ) {
                        if(eachUserRole.getRole().getRoleName().equals("ADMIN")&&retrievedRole.getRoleName().equals("ADMIN")){
                            retrievedGenre.getUser().setGenreList(null);
                            retrievedGenre.getMovieGenreList().add(null);
                            this.genreRepository.delete(retrievedGenre);
                            deleteMessage="Genre deleted successfully";

                        }

                    }
                }

        }






        return deleteMessage;
    }



    @Override
    public Genre getGenreByName(Long userId,String genreName,List<Role> roleList) throws Exception {
        Genre resultGenre=null;
        User retrievedUser=queryClass.getUserById(userId);
        for (Role eachrole:roleList
             ) {
            Role role=this.roleRepository.findByRoleName(eachrole.getRoleName());
            if(role==null){
                throw new Exception("No user exist with the given role name");
            }
            else{
                List<userRole> userRoles=retrievedUser.getUserRoles();
                for (userRole eachUserRole:userRoles
                     ) {
                    if((eachUserRole.getRole().getRoleName().equals("ADMIN")||eachUserRole.getRole().getRoleName().equals("NORMAL"))&&(role.getRoleName().equals("ADMIN")||role.getRoleName().equals("NORMAL"))){
                        resultGenre=this.genreRepository.findByGenreName(genreName);
                    }

                }
            }

        }

        return resultGenre;
    }
}
