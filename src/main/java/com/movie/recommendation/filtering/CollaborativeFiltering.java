package com.movie.recommendation.filtering;

import com.movie.recommendation.model.Rating;
import com.movie.recommendation.repo.MovieRepository;
import com.movie.recommendation.repo.RatingRepo;
import com.movie.recommendation.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


public class CollaborativeFiltering {
    //Data structure to store user-item ratings
    private Map<Long, Map<Long,Float>> userRatings=new HashMap<>();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private RatingRepo ratingRepo;


    public CollaborativeFiltering(UserRepository userRepository,MovieRepository movieRepository,RatingRepo ratingRepo){
        this.userRepository=userRepository;
        this.movieRepository=movieRepository;
        this.ratingRepo=ratingRepo;
    }


    private Rating rating;
    public CollaborativeFiltering(){
        userRatings=new HashMap<>();
    }



    //Add user-item ratings to the system


    public void addUserRating(){
        List<Rating> ratings=ratingRepo.findAll();
        if(!ratings.isEmpty()){
            for (Rating eachUserRatingMovie:ratings
                 ) {
                if(userRatings.containsKey(eachUserRatingMovie.getUser().getUserId())){
                    continue;
                }
                List<Rating> ratings1=ratingRepo.findByUser(eachUserRatingMovie.getUser());
                Map<Long,Float> localRatings=new HashMap<>();
                for (Rating eachRating:ratings1
                     ) {
                    localRatings.put(eachRating.getMovie().getMovieId(),eachRating.getRatingNumber());
                }
                userRatings.put(eachUserRatingMovie.getUser().getUserId(),localRatings);

            }
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

            public List<Long> recommendMovies(Long targetUser,int numRecommendation) {
        addUserRating();
                if (!userRatings.containsKey(targetUser)) {
                    System.out.println("User " + targetUser + " not found");
                    return Collections.emptyList();
                }

                //Calculate similarity between the target user and other users
                Map<Long, Double> similarityMap = new HashMap<>();
                for (Map.Entry<Long, Map<Long, Float>> entry : userRatings.entrySet()) {
                    Long userId = entry.getKey();
                    if (!userId.equals(targetUser)) {
                        double similarity = calculateSimilarity(targetUser, userId);
                        similarityMap.put(userId, similarity);
                    }
                }

                //sort users by similarity in descending order
                List<Map.Entry<Long, Double>> sortedSimilarities = new ArrayList<>(similarityMap.entrySet());
                sortedSimilarities.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

                //Generate movie recommendations based on similar users
                Set<Long> targetUserMovies = userRatings.get(targetUser).keySet();
                List<Long> recommendations = new ArrayList<>();

                for (Map.Entry<Long, Double> entry : sortedSimilarities) {
                    Long similarUser = entry.getKey();
                    Map<Long, Float> similarUserRatings = userRatings.get(similarUser);

                    for (Long movie : similarUserRatings.keySet()
                    ) {
                        if (!targetUserMovies.contains(movie) && !recommendations.contains(movie)) {
                            recommendations.add(movie);
                            if (recommendations.size() >= numRecommendation) {
                                return recommendations;
                            }
                        }
                    }

                }
                return recommendations;
            }
}
