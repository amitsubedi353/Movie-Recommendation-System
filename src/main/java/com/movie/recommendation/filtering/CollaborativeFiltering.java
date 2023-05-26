package com.movie.recommendation.filtering;

import com.movie.recommendation.model.Rating;
import com.movie.recommendation.model.User;
import com.movie.recommendation.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class CollaborativeFiltering {
    //Data structure to store user-item ratings
    private Map<Long, Map<Long,Float>> userRatings;
    @Autowired
    private UserRepository userRepository;
    private Rating rating;
    public CollaborativeFiltering(){
        userRatings=new HashMap<>();
    }
    Long userId= rating.getUser().getUserId();
    Long movieId=rating.getMovie().getMovieId();
    Float retrievedRating=rating.getRatingNumber();


    //Add user-item ratings to the system
    public void addUserRating(Rating rating, Principal principal) {
        if (rating.getUser().getUserId().equals(userRepository.findByUserEmail(principal.getName()))) {

            if (!userRatings.containsKey(rating.getUser().getUserId())) {
                userRatings.put(userId, new HashMap<>());
            }
            userRatings.get(userId).put(movieId, retrievedRating);
        }
    }

            //calculate similarity between two users using cosine similarity
            private double calculateSimilarity(Long user1,Long user2){

                Set<Long> user1Movies=userRatings.get(user1).keySet();
                Set<Long> user2Movies=userRatings.get(user2).keySet();
                float dotProdut=0;
                double user1Norm=0;
                double user2Norm=0;
                for (Long movie:user1Movies
                     ) {
                    if(user2Movies.contains(movie)){
                        dotProdut=dotProdut+userRatings.get(user1).get(movie)*userRatings.get(user2).get(movie);
                    }
                    user1Norm=user1Norm+Math.pow(userRatings.get(user1).get(movie),2);

                }
                for(Long movie:user2Movies){
                    user2Norm=user2Norm+Math.pow(userRatings.get(user2).get(movie),2);
                }
                if(user1Norm==0||user2Norm==0){
                    return 0.0;//return 0 if one of the user has no ratings
                }
                return dotProdut/(Math.sqrt(user1Norm)*Math.sqrt(user2Norm));

            }

            //Recommend top-k movies for the given user

            public void recommendMovies(Long userId,int k){
                    if(!userRatings.containsKey(userId)){
                        System.out.println("User "+userId+" not found");
                        return;
                    }
                    Map<Long,Double> similarityMap=new HashMap<>();
                for (Long user:userRatings.keySet()
                     ) {
                    if(!user.equals(userId)) {
                        double similarity = calculateSimilarity(userId, user);
                    }

                }
                Map<Long,Double> similarAttribute=new HashMap<>();
                        similarityMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Collections.reverseOrder())).limit(k)
                        .forEach(longDoubleEntry -> {
                          similarAttribute.put(longDoubleEntry.getKey(), longDoubleEntry.getValue());
                        });
                        Long similarUser=0l;
                        Double similarityScore=0d;
                for (Map.Entry<Long,Double> entry:similarAttribute.entrySet()
                     ) {
                    similarUser=entry.getKey();
                    similarityScore= entry.getValue();

                }
                Map<Long,Float> similarUserRatings=userRatings.get(similarUser);

                for (Long movie:similarUserRatings.keySet()
                     ) {
                    //check if the movie is not rated by the given user
                    if(userRatings.get(userId).containsKey(movie)){
                        //get the rating of the similar user for the movie
                        float rating=similarUserRatings.get(movie);
                        //apply recommendation logic (e.g threshold similarity score,minimum rating threshold)
                        if(similarityScore>0.5 && rating>=4){





                        }

                    }

                }



    }






}
